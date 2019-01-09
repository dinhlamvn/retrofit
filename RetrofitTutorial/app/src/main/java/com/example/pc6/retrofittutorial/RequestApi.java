package com.example.pc6.retrofittutorial;

import com.example.pc6.retrofittutorial.models.Result;
import com.example.pc6.retrofittutorial.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestApi {

    @GET("user/")
    Call<User> getInfoUser(@Query("user_id") String userId);

    @POST("signup/")
    Call<Result> signUp(@Body User.UserData user);

    @FormUrlEncoded
    @POST("signin/")
    Call<Result> signIn(@Field("email") String email, @Field("pws") String pws);
}
