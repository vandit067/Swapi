package com.demo.swapi.viewmodel;

import android.app.Application;

import com.demo.swapi.model.Result;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class DetailViewModel extends BaseViewModel {

    private MutableLiveData<Result> mResult = new MutableLiveData<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Result> getResult() {
        return this.mResult;
    }

    public void setResult(@NonNull Result mResult) {
        this.mResult.setValue(mResult);
    }
}
