package com.example.amitrai.demoretrofit.backend;

import android.support.annotation.NonNull;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by amitrai on 16/12/16.
 */


public interface ApiInterface {
//    @GET("movie/top_rated")
//    Call<Repo> getTopRatedMovies();

    public static final  String SERVER_URL = "http://192.168.1.91";

    @GET("users/{user}/repos")
    Call<Object> listRepos(@Path("user") @NonNull String user);

    @FormUrlEncoded
    @POST("/php/task_manager/v1/login")
    Call<ResponseBody> login(@Field("email") @NonNull String email,
                                 @Field("password") @NonNull String password);

    @FormUrlEncoded
    @Headers("API_KEY: 123456")
    @POST("/php/task_manager/v1/register")
    Call<ResponseBody> register(@Field("name") @NonNull String name,
                                    @Field("email") @NonNull String email,
                                    @Field("password") @NonNull String password,
                                    @Field("mobile_no") @NonNull String mobile_no);

    @Multipart
    @POST("/php/task_manager/v1/upload.php")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);
}
