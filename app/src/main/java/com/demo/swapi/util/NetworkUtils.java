package com.demo.swapi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import io.reactivex.Observable;

/**
 * This class is use for perform network related operations.
 */
public class NetworkUtils {

    /**
     * Check network is available or not.
     * @param context application context
     * @return boolean is network available or not.
     */
    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Observable which will check network is available or not and emit the boolean value.
     * @param context application context
     * @return Observable<Boolean> network is available or not.
     */
    public static Observable<Boolean> isNetworkAvailableObservable(@NonNull Context context) {
        return Observable.just(isNetworkAvailable(context));
    }
}
