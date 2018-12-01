package com.demo.swapi.api;

import com.demo.swapi.model.ResourceDetailModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("people")
    Single<ResourceDetailModel> getRsourceDetail(@Query("search") String resourceName);
}
