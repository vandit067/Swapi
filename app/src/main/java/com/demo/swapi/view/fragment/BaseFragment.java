package com.demo.swapi.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.demo.swapi.interfaces.IBaseView;
import com.demo.swapi.util.NetworkUtils;
import com.demo.swapi.util.UiUtils;
import com.demo.swapi.view.activity.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements IBaseView {

    private BaseActivity mActivity;
    private Unbinder mUnbinder;


    @Override
    @UiThread
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    @UiThread
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    @UiThread
    public void showError(@NonNull String message) {
        if (this.mActivity != null) {
            mActivity.showError(message);
        }
    }

    @Override
    @UiThread
    public void showError(int resId) {
        if (this.mActivity != null) {
            mActivity.showError(resId);
        }
    }

    @Override
    @UiThread
    public void showMessage(String message) {
        if (this.mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    @UiThread
    public void showMessage(int resId) {
        if (this.mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    @UiThread
    public boolean isNetworkConnected() {
        if (this.mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    @UiThread
    public void hideKeyboard() {
        if (this.mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    @UiThread
    public void showContent(@NonNull View progressView, @NonNull View contentView) {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    @UiThread
    public void showProgress(@NonNull View progressView, @NonNull View contentView) {
        progressView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    @UiThread
    public void showErrorView(@NonNull View progressView, @NonNull View contentView, @NonNull View errorView) {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    /**
     * Pop fragment from fragment stack
     */
    @UiThread
    void popFragment(){
        if(getFragmentManager() == null){
            return;
        }
        getFragmentManager().popBackStack();
    }

    protected abstract void setUp(@NonNull View view);

    @UiThread
    void setUnBinder(Unbinder unBinder) {
        this.mUnbinder = unBinder;
    }

    @NonNull
    @UiThread
    BaseActivity getBaseActivity() {
        return this.mActivity;
    }

    /**
     * Set recyclerview item animation
     * @param recyclerView instance of {@link RecyclerView}
     * @param animId animation id.
     */
    @UiThread
    void setRecyclerViewItemAnimation(@NonNull RecyclerView recyclerView, int animId) {
        UiUtils.setRecyclerViewItemAnimation(recyclerView, animId);
    }

    /**
     * Display error message in screen based on {@link Throwable}
     * @param e instance of {@link Throwable}
     * @return error message to display in Ui
     */
    @UiThread
    @NonNull
    String displayError(@NonNull Throwable e){
        return NetworkUtils.getApiErrorMessage(getBaseActivity().getApplicationContext(), e);
    }

    @Override
    @UiThread
    public void onDestroy() {
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
