package com.example.amitrai.demoretrofit.backend;

import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<ResponseBody> loginCall(@Field("email") @NonNull String email,
                                 @Field("password") @NonNull String password);

    @FormUrlEncoded
    @POST("/php/task_manager/v1/register")
    Call<ResponseBody> registerCall(@Field("name") @NonNull String name,
                                    @Field("email") @NonNull String email,
                                    @Field("password") @NonNull String password,
                                    @Field("mobile_no") @NonNull String mobile_no);

}
