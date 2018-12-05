package com.demo.swapi.api;

import com.demo.swapi.model.ResourceDetailModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("people")
    Single<ResourceDetailModel> getResourceDetail(@Query("search") String resourceName, @Query("page") int page);
}
