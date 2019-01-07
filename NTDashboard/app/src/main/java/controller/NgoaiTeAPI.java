package controller;

import model.NgoaiTe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NgoaiTeAPI {

    @GET("exchange/export")
    @Headers({
            "Content-Type: Application/Json;charset=UTF-8",
            "Accept: Application/Json",
    })
    Call<String> getGiaNgoaiTe();
}
