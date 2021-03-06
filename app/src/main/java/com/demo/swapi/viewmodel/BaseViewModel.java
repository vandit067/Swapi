package com.demo.swapi.viewmodel;

import android.app.Application;
import android.content.Context;

import com.demo.swapi.api.ApiServices;
import com.demo.swapi.util.rx.AppRxSchedulers;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends AndroidViewModel {

    private CompositeDisposable mCompositeDisposable;
    private AppRxSchedulers mAppRxSchedulers;
    private ApiServices mApiServices;

    /**
     * Constructor with application instance and initialize {@link CompositeDisposable}, {@link ApiServices} and {@link AppRxSchedulers}
     * @param application instance of application
     */
    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.mCompositeDisposable = new CompositeDisposable();
        this.mAppRxSchedulers = new AppRxSchedulers();
        this.mApiServices = ApiServices.getInstance(this.getAppContext());
    }

    /**
     * Get instance of {@link CompositeDisposable}
     * @return CompositeDisposable instance of {@link CompositeDisposable}
     */
    @NonNull
    public CompositeDisposable getCompositeDisposable() {
        return this.mCompositeDisposable;
    }

    /**
     * Add disposable to {@link CompositeDisposable}
     * @param disposable instance of {@link Disposable}
     */
    public void addDisposable(@NonNull Disposable disposable){
        this.mCompositeDisposable.add(disposable);
    }

    /**
     * Get instance of {@link AppRxSchedulers}
     * @return AppRxSchedulers instance of {@link AppRxSchedulers}
     */
    @NonNull
    public AppRxSchedulers getAppRxSchedulers() {
        return this.mAppRxSchedulers;
    }

    /**
     * Get application context.
     * @return Context application context.
     */
    @NonNull
    public Context getAppContext(){
        return getApplication().getApplicationContext();
    }

    /**
     * Get api services.
     * @return ApiServices instane of  {@link ApiServices}.
     */
    @NonNull
    public ApiServices getApiServices() {
        return this.mApiServices;
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        // Clear everything here.
        if(this.mCompositeDisposable != null && !this.mCompositeDisposable.isDisposed()){
            this.mCompositeDisposable.dispose();
        }
        if(this.mAppRxSchedulers != null){
            this.mAppRxSchedulers = null;
        }
        if(this.mApiServices != null){
            this.mApiServices = null;
        }
    }
}
