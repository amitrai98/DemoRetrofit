package com.example.amitrai.demoretrofit.ui;

import android.app.Application;

import com.example.amitrai.demoretrofit.backend.ConnectionModule;
import com.example.amitrai.demoretrofit.backend.DaggerNetComponent;
import com.example.amitrai.demoretrofit.backend.NetComponent;

/**
 * Created by amitrai on 29/12/16.
 */

public class AppInitials extends Application {
    NetComponent component;

    private static AppInitials context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        // Dagger%COMPONENT_NAME%
//        component = DaggerNetComponent.builder()
//                // list of modules that are part of this component need to be created here too
////                .ConnectionModule(new ConnectionModule("https://api.github.com"))
//                .ConnectionModule(new ConnectionModule(""));
//                .build();

        component = DaggerNetComponent.builder()
                .connectionModule(new ConnectionModule("https://api.github.com"))
                .build();
    }

    public NetComponent getNetComponent() {
        return component;
    }


    public static AppInitials getContext(){
        return  context;
    }

}
