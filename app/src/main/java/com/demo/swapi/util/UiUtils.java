package com.demo.swapi.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

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
    @UiThread
    public static void showSnackBar(View view, String message, int length) {
        Snackbar.make(view, message, length).show();
    }

    /**
     * hide keyboard
     * @param view view which is focused.
     */
    @UiThread
    public static void hideKeyBoard(@NonNull View view){
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Set recyclerview item animation
     * @param recyclerView instance of {@link RecyclerView}
     * @param animId animation id.
     */
    @UiThread
    public static void setRecyclerViewItemAnimation(@NonNull RecyclerView recyclerView, int animId) {
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), animId);
        recyclerView.setLayoutAnimation(animation);
    }

    /**
     * Add decoration of divider for {@link RecyclerView}
     * @param recyclerView instance of {@link RecyclerView}
     * @param orientation {@link androidx.recyclerview.widget.RecyclerView.LayoutManager} orientation
     */
    @UiThread
    public static void addItemDividerDecorationToRecyclerView(@NonNull RecyclerView recyclerView, int orientation) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), orientation);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
