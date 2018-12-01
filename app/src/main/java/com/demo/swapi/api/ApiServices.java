package com.demo.swapi.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Class to retrieve api client and access the retrofit calls.
 */
public class ApiServices {
    private static ApiServices mApiServices= null;
    private ApiInterface mApiInterface;

    /**
     * Create single instance of {@link ApiServices} class
     * @param appContext application context
     * @return ApiServices instance of {@link ApiServices}
     */
    public static ApiServices getInstance(@NonNull Context appContext){
        if(mApiServices == null){
            mApiServices = new ApiServices(appContext);
        }
        return mApiServices;
    }

    /**
     * Constructor to create {@link ApiInterface}
     * @param appContext application context
     */
    private ApiServices(@NonNull Context appContext) {
        this.mApiInterface = ApiClient.getInstance(appContext).getClient().create(ApiInterface.class);
    }

    /**
     * return instance of {@link ApiInterface} to access the requests
     * @return instance of {@link ApiInterface}
     */
    @NonNull
    public ApiInterface getApiInterface() {
        return this.mApiInterface;
    }
}
