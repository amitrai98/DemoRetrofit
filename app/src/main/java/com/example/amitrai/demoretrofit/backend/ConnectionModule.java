package com.example.amitrai.demoretrofit.backend;

import android.content.Context;

import com.example.amitrai.demoretrofit.databases.AppPreference;
import com.example.amitrai.demoretrofit.utility.AppConstants;
import com.example.amitrai.demoretrofit.utility.Utility;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amitrai on 29/12/16.
 * see more at www.github.com/amitrai98
 */

@Module
public class ConnectionModule {

    private String mBaseUrl;
    private Context context;

    // Constructor needs one parameter to instantiate.
    public ConnectionModule(String baseUrl, Context context) {
        this.mBaseUrl = baseUrl;
        this.context = context;
    }

    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    AppPreference providesSharedPreferences() {
        return new AppPreference(context);
    }



    @Provides
    @Singleton
    Utility provideUtility(){
        return new Utility();
    }

    @Provides
    @Singleton
    AppConstants provideAppConstants(){
        return new AppConstants();
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiInterface.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiInterface provideIterface(){
        return provideRetrofit().create(ApiInterface.class);
    }

    @Provides
    @Singleton
    Connection provideConnection(){
        return new Connection();
    }

    @Provides
    @Singleton
    String provideDisplaceMsg (){
        return "message from provider";
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new Gson();
    }
}