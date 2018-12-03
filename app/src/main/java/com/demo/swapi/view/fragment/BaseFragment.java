package com.demo.swapi.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.demo.swapi.interfaces.IBaseView;
import com.demo.swapi.view.activity.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements IBaseView {

    private BaseActivity mActivity;
    private Unbinder mUnbinder;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void showError(@NonNull String message) {
        if (this.mActivity != null) {
            mActivity.showError(message);
        }
    }

    @Override
    public void showError(int resId) {
        if (this.mActivity != null) {
            mActivity.showError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (this.mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int resId) {
        if (this.mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (this.mActivity != null) {
            mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void hideKeyboard() {
        if (this.mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void showContent(@NonNull View progressView, @NonNull View contentView) {
        progressView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress(@NonNull View progressView, @NonNull View contentView) {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView(@NonNull View progressView, @NonNull View contentView, @NonNull View errorView) {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    public void replaceFragment(@NonNull Fragment fragment){
        if (this.mActivity != null) {
            mActivity.replaceFragment(fragment);
        }
    }

    protected abstract void setUp(@NonNull View view);

    public void setUnBinder(Unbinder unBinder) {
        this.mUnbinder = unBinder;
    }

    public BaseActivity getBaseActivity() {
        return this.mActivity;
    }

    @Override
    public void onDestroy() {
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
