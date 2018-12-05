package com.demo.swapi.viewmodel;

import android.app.Application;

import com.demo.swapi.model.Result;
import com.demo.swapi.view.fragment.DetailFragment;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.lifecycle.MutableLiveData;

/**
 * Class used for handle the business logic for {@link DetailFragment}
 */
public class DetailViewModel extends BaseViewModel {

    private MutableLiveData<Result> mResult;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    @UiThread
    public MutableLiveData<Result> getResult() {
        if(this.mResult == null){
            this.mResult = new MutableLiveData<>();
        }
        return this.mResult;
    }

    @UiThread
    public void setResult(@NonNull Result mResult) {
        this.mResult.setValue(mResult);
    }
}
