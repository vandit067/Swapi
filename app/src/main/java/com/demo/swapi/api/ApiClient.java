package com.demo.swapi.api;

import android.content.Context;

import com.demo.swapi.R;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to create retrofit client will be used in {@link ApiServices}
 */
public class ApiClient {
    private static ApiClient ourInstance = null;
    private static final int CONNECTION_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private Retrofit mRetrofit;

    /**
     * Method to create single instance of {@link ApiClient}
     * @param appContext application context
     * @return ApiClient instance of {@link ApiClient}
     */
    @NonNull
    public static ApiClient getInstance(@NonNull Context appContext) {
        if(ourInstance == null){
            ourInstance = new ApiClient(appContext);
        }
        return ourInstance;
    }

    /**
     * Constructor to create api client instance only once.
     * @param appContext application context
     */
    private ApiClient(@NonNull Context appContext) {
        this.mRetrofit = this.createApiClient(appContext);
    }

    /**
     * Create api client to initiate api call.
     * @param appContext application context
     * @return Retrofit instance of {@link Retrofit}
     */
    @NonNull
    private Retrofit createApiClient(@NonNull Context appContext){
        return new Retrofit.Builder()
                .baseUrl(appContext.getString(R.string.base_url))
                .client(getHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
    }

    /**
     * Create instance of {@link OkHttpClient} with basic information related to network.
     * @return OkHttpClient instance of {@link OkHttpClient}
     */
    @NonNull
    private static OkHttpClient getHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Get instance of {@link Retrofit} api client
     * @return Retrofit instance of {@link Retrofit}
     */
    @NonNull
    public Retrofit getClient() {
        return this.mRetrofit;
    }
}
