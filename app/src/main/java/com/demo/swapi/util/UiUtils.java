package com.demo.swapi.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;

/**
 * This class is use for perform ui related operations.
 */
public class UiUtils {

    /**
     * Show snackbar with message detail.
     * @param view parent view.
     * @param message message to display.
     * @param length duration to display snackbar.
     */
    public static void showSnackBar(View view, String message, int length) {
        Snackbar.make(view, message, length).show();
    }

    /**
     * hide keyboard
     * @param view view which is focused.
     */
    public static void hideKeyBoard(@NonNull View view){
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * show keyboard
     * @param view view which is focused.
     */
    public static void showKeyBoard(@NonNull View view){
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
}
