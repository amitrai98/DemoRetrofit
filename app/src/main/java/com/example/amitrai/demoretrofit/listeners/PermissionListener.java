package com.example.amitrai.demoretrofit.listeners;

/**
 * Created by amitrai on 2/1/17.
 */

public interface PermissionListener {
    public void onPermissionGranted(int permission_code);
    public void onPermissionDenied(int permission_code);
}
