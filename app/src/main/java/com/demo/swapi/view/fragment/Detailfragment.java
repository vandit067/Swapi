package com.demo.swapi.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.swapi.R;
import com.demo.swapi.model.Result;
import com.demo.swapi.viewmodel.DetailViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class will display detail information of resource.
 */
public class Detailfragment extends BaseFragment {

    private static final String KEY_RESOURCE_NAME = "key_resource_name";
    private DetailViewModel mDetailViewModel;
    private String mResourceName;

    @BindView(R.id.fragment_detail_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.fragment_detail_tv_birth_year)
    TextView mTvResourceBirthYear;
    @BindView(R.id.fragment_detail_tv_eye_color)
    TextView mTvResourceEyeColor;
    @BindView(R.id.fragment_detail_tv_hair_color)
    TextView mTvResourceHairColor;
    @BindView(R.id.fragment_detail_tv_gender)
    TextView mTvResourceGender;
    @BindView(R.id.fragment_detail_tv_height)
    TextView mTvResourceHeight;
    @BindView(R.id.fragment_detail_tv_mass)
    TextView mTvResourceMass;
    @BindView(R.id.fragment_detail_tv_name)
    TextView mTvResourceName;
    @BindView(R.id.fragment_detail_tv_skin_color)
    TextView mTvResourceSkinColor;
    @BindView(R.id.fragment_detail_rl_content)
    RelativeLayout mRlContentView;

    /**
     * Constructor to create instance of {@link Detailfragment}
     * @param resourceName search resource name from {@link MasterFragment}
     * @return instance of {@link Detailfragment}
     */
    static Detailfragment newInstance(@NonNull String resourceName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RESOURCE_NAME, resourceName);
        Detailfragment detailfragment = new Detailfragment();
        detailfragment.setArguments(bundle);
        return detailfragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() == null){
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
            return;
        }
        this.mResourceName = getArguments().getString(KEY_RESOURCE_NAME,"");
        // We are not creating instance of ViewModel class here.
        this.mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    /**
     * Setup views and entry point to perform operations
     * @param view current view.
     */
    @Override
    protected void setUp(@NonNull View view) {
        this.retrieveResourceDetail();
    }

    /**
     * Initiate resource detail api call and get response.
     */
    private void retrieveResourceDetail(){
        showProgress(this.mProgressBar, this.mRlContentView);
        this.mDetailViewModel.retrieveResourceDetails(this.mResourceName);
        this.mDetailViewModel.getResourceDetailModelObserver().observe(this, resourceDetailModel -> {
            showContent(this.mProgressBar, this.mRlContentView);
            if(resourceDetailModel == null || resourceDetailModel.getResults() == null || resourceDetailModel.getResults().isEmpty()){
                showMessage(R.string.message_no_data_available);
                return;
            }
            setResourceData(resourceDetailModel.getResults());
        });
    }

    /**
     * Set resource details in view from @{@link Result}
     * @param resultList response return from Api call.
     */
    private void setResourceData(@NonNull List<Result> resultList){
        Result result = resultList.get(0);
        if(result == null){
            showMessage(getString(R.string.message_no_data_available));
            return;
        }
        this.mTvResourceName.setText(result.getName());
        this.mTvResourceEyeColor.setText(result.getEyeColor());
        this.mTvResourceGender.setText(result.getGender());
        this.mTvResourceHairColor.setText(result.getHairColor());
        this.mTvResourceSkinColor.setText(result.getSkinColor());
        this.mTvResourceHeight.setText(result.getHeight());
        this.mTvResourceMass.setText(result.getMass());
        this.mTvResourceBirthYear.setText(result.getBirthYear());
    }

}
