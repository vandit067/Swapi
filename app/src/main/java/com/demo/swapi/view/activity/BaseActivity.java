package com.demo.swapi.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.demo.swapi.R;
import com.demo.swapi.interfaces.IBaseView;
import com.demo.swapi.util.NetworkUtils;
import com.demo.swapi.util.UiUtils;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
    }

    private void showSnackBar(String message) {
        UiUtils.showSnackBar(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showError(@NonNull String message) {
        showSnackBar(message);
    }

    @Override
    public void showError(@StringRes int resId) {
        showError(getString(resId));
    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkAvailable(getApplicationContext());
    }

    @Override
    public void showContent(@NonNull View progressView, @NonNull View contentView) {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(@NonNull View progressView, @NonNull View contentView) {
        progressView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(@NonNull View progressView, @NonNull View contentView, @NonNull View errorView) {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if(view == null){
            return;
        }
        UiUtils.hideKeyBoard(view);
    }

    public void replaceFragment(@NonNull Fragment fragment, @NonNull String backFragmentName){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_master_container, fragment,
                fragment.getClass().getSimpleName()).addToBackStack(backFragmentName).commit();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract void setUp();
}
