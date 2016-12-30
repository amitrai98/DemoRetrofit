package com.example.amitrai.demoretrofit.backend;

import android.util.Log;

import com.example.amitrai.demoretrofit.listeners.ResponseListener;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amitrai on 29/12/16.
 */

public class Connection {

    private final String TAG = getClass().getSimpleName();

    private static Connection connection;
    private static String BASE_URL;
    /**
     * creats the instance of connection.
     * @return connection
     */
    public Connection getInstance(){
        return  new Connection();
    }

    public void request(Call<ResponseBody> call, final ResponseListener responseListener){

        try {
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        ResponseBody body = response.body();
                        String response_string = body.string();
                        responseListener.onSuccess(response_string);
                    }catch (Exception exp){
                        exp.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "response"+t);
                    responseListener.onError(t.getMessage());
                }
            });
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }

    public void makeRequest(){
        Log.e(TAG, "got your call");
    }

}
