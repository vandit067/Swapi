package com.demo.swapi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.RemoteException;

import com.demo.swapi.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import androidx.annotation.NonNull;
import retrofit2.HttpException;

/**
 * This class is use for perform network related operations.
 */
public class NetworkUtils {

    private static final int SOCKET_TIMEOUT_EXCEPTION_CODE = 3000;
    private static final int UNKNOWN_HOST_EXCEPTION_CODE = 3001;
    private static final int IO_EXCEPTION_CODE = 3002;
    private static final int REMOTE_EXCEPTION_CODE = 3003;
    private static final int CONNECTION_TIMEOUT_EXCEPTION_CODE = 3004;
    private static final int UNKNOWN_ERROR_CODE = 3005;

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
     *
     * @param e throwable instance to find the exception
     * @return error code.
     */
    private static int getErrorCode(@NonNull Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            return httpException.code();
        } else if (e instanceof IOException) {
            if (e instanceof SocketTimeoutException) {
                return SOCKET_TIMEOUT_EXCEPTION_CODE;
            } else if (e instanceof UnknownHostException) {
                return UNKNOWN_HOST_EXCEPTION_CODE;
            } else {
                return IO_EXCEPTION_CODE;
            }
        } else if (e instanceof RemoteException) {
            return REMOTE_EXCEPTION_CODE;
        } else if (e instanceof Exception) {
            if (e instanceof TimeoutException) {
                return CONNECTION_TIMEOUT_EXCEPTION_CODE;
            }
        }
        return UNKNOWN_ERROR_CODE;

    }

    /**
     * Get local message based on the {@link Throwable}
     * @param context application context
     * @param e throwable
     * @return error message to display in UI
     */
    public static String getApiErrorMessage(@NonNull Context context, @NonNull Throwable e) {
        return getErrorBasedOnErrorCode(context, getErrorCode(e));
    }

    /**
     * Get error based on error code.
     * @param context application context
     * @param errorCode error code.
     * @return error message based on error code.
     */
    private static String getErrorBasedOnErrorCode(@NonNull Context context, int errorCode) {
        switch (errorCode) {
            case SOCKET_TIMEOUT_EXCEPTION_CODE:
                return context.getString(R.string.error_request_timeout);
            case UNKNOWN_HOST_EXCEPTION_CODE:
                return context.getString(R.string.error_network_check);
            case IO_EXCEPTION_CODE:
                return context.getString(R.string.error_io);
            case REMOTE_EXCEPTION_CODE:
                return context.getString(R.string.error_remote_exception);
            case CONNECTION_TIMEOUT_EXCEPTION_CODE:
                return context.getString(R.string.error_connection_timeout);
            case UNKNOWN_ERROR_CODE:
                return context.getResources().getString(R.string.error_went_wrong);
            default:
                return getHttpErrorBasedOnErrorCode(context, errorCode);
        }
    }

    /**
     * Retrieve http error message based on error code.
     * @param context application context
     * @param errorCode error code.
     * @return error message based on http error code
     */
    private static String getHttpErrorBasedOnErrorCode(@NonNull Context context, int errorCode) {
        if (isError4xx(errorCode)) {
            if (errorCode == 404) {
                return context.getString(R.string.error_404);
            } else {
                return context.getResources().getString(R.string.error_unknown);
            }
        } else if (isError5xx(errorCode)) {
            return context.getString(R.string.error_server_unable_to_process_request);
        } else {
            return context.getResources().getString(R.string.error_went_wrong);
        }
    }

    /**
     * Check error code is in the range of 400 to 499.
     * @param errorCode error code
     * @return true if error code in range else return false
     */
    private static boolean isError4xx(int errorCode) {
        return (errorCode >= 400 && errorCode <= 499);
    }

    /**
     * Check error code is in the range of 500 to 599.
     * @param errorCode error code
     * @return true if error code in range else return false
     */
    private static boolean isError5xx(int errorCode) {
        return (errorCode >= 500 && errorCode <= 599);
    }

}
