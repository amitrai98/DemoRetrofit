package com.example.amitrai.demoretrofit.backend;

import android.util.Log;

import com.example.amitrai.demoretrofit.listeners.ResponseListener;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    /**
     * make api calls for supplied on the call
     * @param call type of retrofit call
     * @param responseListener listener for callbacks
     */
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

    /**
     * uploads image to server
     * @param file_name name of the file to be uploaded
     * @param responseListener listener for callbacks
     */
    public void uploadImage(String file_name, ApiInterface service ,
                            final ResponseListener responseListener){

        File file = new File(file_name);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("fileToUpload", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
        Log.e(TAG, ""+name);

        retrofit2.Call<okhttp3.ResponseBody> req = service.uploadImage(body, name);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Do Something
                try {
                    ResponseBody body = response.body();
                    String response_string = body.string();
                    Log.e(TAG, ""+response_string);
                    responseListener.onSuccess(response_string);

                }catch (Exception exp){
                    exp.printStackTrace();
                    responseListener.onError("some exception occured");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void makeRequest(){
        Log.e(TAG, "got your call");
    }

}
