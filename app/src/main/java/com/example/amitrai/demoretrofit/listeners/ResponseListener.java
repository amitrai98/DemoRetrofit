package com.example.amitrai.demoretrofit.listeners;

/**
 * Created by amitrai on 29/12/16.
 */

public interface ResponseListener {
    public void onSuccess(String response);
    public void onError(Error error);
}
