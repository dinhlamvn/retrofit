package com.example.pc6.retrofittutorial;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestApi {

    @GET("data/")
    Call<String> getData();

    @FormUrlEncoded
    @POST("dangki/")
    Call<Result> postSignUp(@Field("name") String name, @Field("age") String age);

    @GET("user/")
    Call<User> getInfoUser(@Query("user_id") String userId);
}
