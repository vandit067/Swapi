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
import com.demo.swapi.model.Result;
import com.demo.swapi.view.adapter.ResourcesAdapter;
import com.demo.swapi.viewmodel.MasterViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

    // Create new instance of MasterFragment
    public static MasterFragment newInstance() {
       return new MasterFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We are not creating instance of ViewModel class here.
        this.mMasterViewModel = ViewModelProviders.of(this).get(MasterViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    private void initUI(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseActivity());
        this.mRvResources.setLayoutManager(linearLayoutManager);
        setRecyclerViewItemAnimation(this.mRvResources, R.anim.layout_animation_from_bottom);
        this.mResourcesAdapter = new ResourcesAdapter(new ArrayList<>(), this);
        this.mRvResources.setAdapter(this.mResourcesAdapter);
    }

    @OnClick(R.id.fragment_master_iv_search)
    void performResourceSearch(){
        if (TextUtils.isEmpty(this.mTietResource.getText())) {
            this.mTilResource.setError(getString(R.string.message_enter_resource_name));
            return;
        }
        this.performSearch(""+this.mTietResource.getText());
    }

    @OnTextChanged(value = R.id.fragment_master_tiet_resource, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(Editable editable){
        if(editable.length() > 0){
            this.mTilResource.setError("");
        }
        if(editable.length() == 0 && this.mResourcesAdapter != null){
           this.mResourcesAdapter.clearList();
        }
    }

    @OnEditorAction(R.id.fragment_master_tiet_resource)
    boolean onEditorAction(TextView v, int actionId){
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

    @Override
    public void onResourceSelected(int position, @NonNull Result result) {
        addFragment(Detailfragment.newInstance(result), getClass().getSimpleName());
    }

    /**
     * Check network connection and pass search resource name to  {@link Detailfragment}
     * @param resourceName name of resource
     */
    private void performSearch(@NonNull String resourceName){
        hideKeyboard();
        if(!isNetworkConnected()){
            showError(R.string.message_network_check);
            return;
        }
        this.mResourcesAdapter.clearList();
        this.retrieveAndSetResourcesList(resourceName);
    }

    /**
     * Initiate resources list api call and get response.
     */
    private void retrieveAndSetResourcesList(@NonNull String mResourceName){
        showProgress(this.mProgressBar, this.mCvContentView);
        this.mMasterViewModel.retrieveResourceDetails(mResourceName);
        this.mMasterViewModel.getResourceDetailModelObserver().observe(this, resourceDetailModel -> {
            showContent(this.mProgressBar, this.mCvContentView);
            if(resourceDetailModel == null || resourceDetailModel.getResults() == null || resourceDetailModel.getResults().isEmpty()){
                showMessage(R.string.message_no_data_available);
                return;
            }
            this.mResourcesAdapter.swapAdapter(resourceDetailModel.getResults());
        });
    }

    @Override
    protected void setUp(@NonNull View view) {
        this.initUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(this.mMasterViewModel != null){
            this.mMasterViewModel = null;
        }
    }
}
