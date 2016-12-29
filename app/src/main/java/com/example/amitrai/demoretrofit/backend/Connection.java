package com.example.amitrai.demoretrofit.backend;

import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amitrai on 29/12/16.
 */

@Module
public class Connection {

    private final String TAG = getClass().getSimpleName();

    private static Connection connection;
    private static String BASE_URL;
    /**
     * creats the instance of connection.
     * @return connection
     */
    @Provides
    @Singleton
    public Connection getInstance(){
        return  new Connection();
    }

    @Provides
    @Singleton
    public Retrofit getClient() {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }


    public void makeRequest(){
        Log.e(TAG, "got your call");
    }

}
