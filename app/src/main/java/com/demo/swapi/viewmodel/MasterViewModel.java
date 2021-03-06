package com.demo.swapi.viewmodel;

import android.app.Application;

import com.demo.swapi.model.ApiResponseDataWrapper;
import com.demo.swapi.model.ResourceDetailModel;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class used for handle the business logic for {@link com.demo.swapi.view.fragment.MasterFragment}
 */
public class MasterViewModel extends BaseViewModel {

    private MutableLiveData<ApiResponseDataWrapper> mResourceDetailModelObserver;

    private int totalItemCount = 0;

    public MasterViewModel(@NonNull Application application) {
        super(application);
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    @UiThread
    public MutableLiveData<ApiResponseDataWrapper> getResourceDetailModelObserver() {
        if(mResourceDetailModelObserver == null){
            this.mResourceDetailModelObserver = new MutableLiveData<>();
        }
        return this.mResourceDetailModelObserver;
    }

    @WorkerThread
    public void retrieveResourceDetails(@NonNull String resourceName, int page){
        addDisposable(getApiServices().getApiInterface().getResourceDetail(resourceName, page)
                .subscribeOn(getAppRxSchedulers().io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resourceDetailModel -> mResourceDetailModelObserver.postValue(new ApiResponseDataWrapper<ResourceDetailModel>(resourceDetailModel))
                        , throwable -> mResourceDetailModelObserver.postValue(new ApiResponseDataWrapper(throwable))));

    }

}
