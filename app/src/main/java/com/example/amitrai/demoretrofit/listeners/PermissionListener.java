package com.example.amitrai.demoretrofit.listeners;

/**
 * Created by amitrai on 2/1/17.
 * see more at www.github.com/amitrai98
 */

public interface PermissionListener {
     void onPermissionGranted(int permission_code);
     void onPermissionDenied(int permission_code);
}
