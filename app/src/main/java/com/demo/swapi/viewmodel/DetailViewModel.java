package com.demo.swapi.viewmodel;

import android.app.Application;
import android.util.Log;

import com.demo.swapi.model.ResourceDetailModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailViewModel extends BaseViewModel {

    private MutableLiveData<ResourceDetailModel> mResourceDetailModelObserver = new MutableLiveData<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ResourceDetailModel> getResourceDetailModelObserver() {
        return this.mResourceDetailModelObserver;
    }

    public void retrieveResourceDetails(@NonNull String resourceName){
        /*addDisposable(NetworkUtils.isNetworkAvailableObservable(getAppContext())
                .subscribeOn(getAppRxSchedulers().io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(isNetworkAvailable -> {

                }, e -> {

        }));*/
        addDisposable(getApiServices().getApiInterface().getResourceDetail(resourceName)
                .subscribeOn(getAppRxSchedulers().io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resourceDetailModel -> mResourceDetailModelObserver.postValue(resourceDetailModel), e -> {
                    Log.e("Error", e.getMessage());
                }));
    }

}
