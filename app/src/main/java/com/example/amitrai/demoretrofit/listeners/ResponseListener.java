package com.example.amitrai.demoretrofit.listeners;

/**
 * Created by amitrai on 29/12/16.
 * see more at www.github.com/amitrai98
 */

public interface ResponseListener {
    void onSuccess(String response);
    void onError(String error);
}
