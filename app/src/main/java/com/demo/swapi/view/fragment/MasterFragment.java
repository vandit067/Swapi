package com.demo.swapi.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.swapi.R;
import com.demo.swapi.interfaces.IMasterFragmentInteractionListener;
import com.demo.swapi.model.ApiResponseDataWrapper;
import com.demo.swapi.model.ResourceDetailModel;
import com.demo.swapi.model.Result;
import com.demo.swapi.view.adapter.ResourcesAdapter;
import com.demo.swapi.view.custom.EndlessRecyclerViewScrollListener;
import com.demo.swapi.viewmodel.MasterViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * This class is used for perform resource search.
 */
public class MasterFragment extends BaseFragment implements IMasterFragmentInteractionListener {

    @BindView(R.id.fragment_master_iv_search)
    ImageView mIvSearchResource;
    @BindView(R.id.fragment_master_til_resource)
    TextInputLayout mTilResource;
    @BindView(R.id.fragment_master_tiet_resource)
    TextInputEditText mTietResource;
    @BindView(R.id.fragment_master_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.fragment_master_cv_content)
    CardView mCvContentView;
    @BindView(R.id.fragment_master_rv_resources)
    RecyclerView mRvResources;

    private MasterViewModel mMasterViewModel;
    private ResourcesAdapter mResourcesAdapter;
    private EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;

    /**
     * {@link ApiResponseDataWrapper} observer which will observe event through {@link androidx.lifecycle.LiveData} and update UI accordingly.
     */
    private final Observer<ApiResponseDataWrapper> mApiResponseDataWrapperObserver = new Observer<ApiResponseDataWrapper>() {
        @Override
        public void onChanged(@Nullable ApiResponseDataWrapper apiResponseDataWrapper) {
            //Handle UI
            showContent(mProgressBar, mCvContentView);
            mResourcesAdapter.removeLoadingView();
            if (apiResponseDataWrapper == null) {
                showError(R.string.message_no_data_available);
                return;
            }
            if (apiResponseDataWrapper.getThrowable() != null) {
                showError(displayError(apiResponseDataWrapper.getThrowable()));
                return;
            }
            ResourceDetailModel resourceDetailModel = (ResourceDetailModel) apiResponseDataWrapper.getResponse();
            if (resourceDetailModel == null || resourceDetailModel.getResults() == null || resourceDetailModel.getResults().isEmpty()) {
                showError(R.string.message_no_data_available);
                return;
            }
            mMasterViewModel.setTotalItemCount(resourceDetailModel.getCount());
            mResourcesAdapter.addItems(resourceDetailModel.getResults());
        }
    };

    // Create new instance of MasterFragment
    public static MasterFragment newInstance() {
        return new MasterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We are not creating instance of ViewModel class here.
        this.mMasterViewModel = ViewModelProviders.of(this).get(MasterViewModel.class);
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        this.mMasterViewModel.getResourceDetailModelObserver().observe(this, mApiResponseDataWrapperObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    /**
     * Initialize Ui Component
     */
    private void initUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseActivity());
        this.mRvResources.setLayoutManager(linearLayoutManager);
        setRecyclerViewItemAnimation(this.mRvResources, R.anim.layout_animation_from_bottom);
        this.mResourcesAdapter = new ResourcesAdapter(new ArrayList<>(), this);
        this.mRvResources.setAdapter(this.mResourcesAdapter);
        this.mEndlessRecyclerViewScrollListener = getRecyclerViewScrollListener(linearLayoutManager);
        this.mRvResources.addOnScrollListener(this.mEndlessRecyclerViewScrollListener);
    }

    /**
     * Perform click event of Search button.
     */
    @OnClick(R.id.fragment_master_iv_search)
    void performResourceSearch() {
        if (TextUtils.isEmpty(this.mTietResource.getText())) {
            this.mTilResource.setError(getString(R.string.message_enter_resource_name));
            return;
        }
        this.performSearch("" + this.mTietResource.getText());
    }

    /**
     * Called when text change happen in mTilResource {@link TextInputEditText}
     * @param editable instance of {@link Editable} to perform operation.
     */
    @OnTextChanged(value = R.id.fragment_master_tiet_resource, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(Editable editable) {
        if (editable.length() > 0) {
            this.mTilResource.setError("");
        }
        if (editable.length() == 0 && this.mResourcesAdapter != null) {
            this.mResourcesAdapter.removeAll();
        }
    }

    /**
     * Action to perform for {@link TextInputEditText}
     * @param v view
     * @param actionId action id to perform action.
     * @return true to perform action else false
     */
    @OnEditorAction(R.id.fragment_master_tiet_resource)
    boolean onEditorAction(TextView v, int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (TextUtils.isEmpty(v.getText())) {
                this.mTilResource.setError(getString(R.string.message_enter_resource_name));
                return false;
            }
            performSearch(v.getText().toString());
            return true;
        }
        return false;
    }

    /**
     * Call when recycler view item click happen.
     * @param position Selected item position.
     * @param result Selected {@link Result} object at position.
     */
    @Override
    public void onResourceSelected(int position, @NonNull Result result) {
        addFragment(DetailFragment.newInstance(result), getClass().getSimpleName());
    }

    /**
     * Check network connection and pass search resource name to  {@link DetailFragment}
     *
     * @param resourceName name of resource
     */
    private void performSearch(@NonNull String resourceName) {
        hideKeyboard();
        if (!isNetworkConnected()) {
            showError(R.string.error_network_check);
            return;
        }
        this.mResourcesAdapter.removeAll();
        this.mEndlessRecyclerViewScrollListener.resetState();
        this.retrieveAndSetResourcesList(resourceName);
    }

    /**
     * Initiate resources list api call and get response.
     */
    private void retrieveAndSetResourcesList(@NonNull String mResourceName) {
        showProgress(this.mProgressBar, this.mCvContentView);
        this.mMasterViewModel.retrieveResourceDetails(mResourceName, 1);
    }

    /**
     * Scroll listner of {@link RecyclerView} to load more content on scroll
     * @param layoutManager Layout manager for {@link RecyclerView} which will arrange data in adapter accordingly.
     * @return @{@link EndlessRecyclerViewScrollListener} instance.
     */
    private EndlessRecyclerViewScrollListener getRecyclerViewScrollListener(@NonNull LinearLayoutManager layoutManager) {
        return new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (totalItemsCount < mMasterViewModel.getTotalItemCount() && mResourcesAdapter.addLoadingView()) {
                    mMasterViewModel.retrieveResourceDetails("" + mTietResource.getText(), page);
                }
            }
        };
    }

    /**
     * Initialize your UI components here.
     *
     * @param view current view
     */
    @Override
    protected void setUp(@NonNull View view) {
        this.initUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mMasterViewModel != null) {
            this.mMasterViewModel = null;
        }
    }
}
