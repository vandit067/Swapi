package com.demo.swapi.interfaces;

import android.view.View;

import com.demo.swapi.model.Result;

import androidx.annotation.NonNull;

/**
 * Use to communicate with @{@link com.demo.swapi.view.adapter.ResourcesAdapter}
 */
public interface IMasterFragmentInteractionListener {
    void onResourceSelected(int position, @NonNull Result result);
}
