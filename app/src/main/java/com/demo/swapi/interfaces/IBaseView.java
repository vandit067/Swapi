package com.demo.swapi.interfaces;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public interface IBaseView {
    void showProgress(@NonNull View progressView, @NonNull View contentView);

    void showContent(@NonNull View progressView, @NonNull View contentView);

    void showErrorView(@NonNull View progressView, @NonNull View contentView, @NonNull View errorView);

    void showError(@NonNull String message);

    void showError(@StringRes int resId);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
