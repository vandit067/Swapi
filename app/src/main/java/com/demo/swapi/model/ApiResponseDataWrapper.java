package com.demo.swapi.model;

public class ApiResponseDataWrapper<T> {
    private Throwable throwable;
    private T response;

    public ApiResponseDataWrapper(Throwable throwable) {
        this.throwable = throwable;
    }

    public ApiResponseDataWrapper(T response) {
        this.response = response;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
