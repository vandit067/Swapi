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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class will display detail information of resource.
 */
public class Detailfragment extends BaseFragment {

    private static final String KEY_RESULT = "key_result";
    private DetailViewModel mDetailViewModel;

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
     *
     * @param result instnce of @{@link Result} from {@link MasterFragment}
     * @return instance of {@link Detailfragment}
     */
    static Detailfragment newInstance(@NonNull Result result) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_RESULT, result);
        Detailfragment detailfragment = new Detailfragment();
        detailfragment.setArguments(bundle);
        return detailfragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            popFragment();
            return;
        }
        // We are not creating instance of ViewModel class here.
        this.mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        Result result = getArguments().getParcelable(KEY_RESULT);
        if(result == null){
            popFragment();
            return;
        }
        this.mDetailViewModel.setResult(result);
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
     *
     * @param view current view.
     */
    @Override
    protected void setUp(@NonNull View view) {
        this.retrieveResourceDetail();
    }

    /**
     * Initiate resource detail api call and get response.
     */
    private void retrieveResourceDetail() {
        showProgress(this.mProgressBar, this.mRlContentView);
        if (this.mDetailViewModel.getResult() == null) {
            showMessage(R.string.message_no_data_available);
            popFragment();
            return;
        }
        this.mDetailViewModel.getResult().observe(this, result -> {
            showContent(mProgressBar, mRlContentView);
            setResourceData(result);
        });
    }

    /**
     * Set resource details in view from @{@link Result}
     */
    private void setResourceData(@NonNull Result result) {
        this.mTvResourceName.setText(result.getName());
        this.mTvResourceEyeColor.setText(result.getEyeColor());
        this.mTvResourceGender.setText(result.getGender());
        this.mTvResourceHairColor.setText(result.getHairColor());
        this.mTvResourceSkinColor.setText(result.getSkinColor());
        this.mTvResourceHeight.setText(String.format("%s Cm",result.getHeight()));
        this.mTvResourceMass.setText(String.format("%s Kg", result.getMass()));
        this.mTvResourceBirthYear.setText(result.getBirthYear());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mDetailViewModel != null) {
            this.mDetailViewModel = null;
        }
    }
}
