package com.example.amitrai.demoretrofit.backend;

import android.support.annotation.NonNull;

import com.example.amitrai.demoretrofit.models.Task;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @POST("/php/task_manager/v1/register")
    Call<ResponseBody> register(@Field("name") @NonNull String name,
                                    @Field("email") @NonNull String email,
                                    @Field("password") @NonNull String password,
                                    @Field("mobile_no") @NonNull String mobile_no);

    @Multipart
    @POST("/php/task_manager/v1/upload.php")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);

    @GET("/php/task_manager/v1/tasks")
    Call<ResponseBody> getTasks(@Header("Authorization") String api_key);

    @POST("/php/task_manager/v1/tasks")
    Call<ResponseBody> createTask(@Header("Authorization") String api_key, @Body Task task);

    @DELETE("/php/task_manager/v1/tasks/{id}")
    Call<ResponseBody> deleteTask(@Header("Authorization") String api_key, @Path("id") String itemId);

    @FormUrlEncoded
    @PUT("/php/task_manager/v1/tasks/{id}")
    Call<ResponseBody> updateTask(@Header("Authorization") String api_key, @Path("id") String id,
                                  @Field("task") String  task, @Field("status") String status);

}
