package controller;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RequestAPI implements Callback<String> {

    private static final String TAG = "RequestAPI";
    private IUpdateDataCallback callback;

    public void start(final String baseUrl, final IUpdateDataCallback callback) {

        this.callback = callback;

        OkHttpClient client = new OkHttpClient.Builder().build();

        // Khởi tạo retrofit để gán API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();

        // Khởi tạo đối tượng gọi api
        NgoaiTeAPI ngoaiTeAPI = retrofit.create(NgoaiTeAPI.class);

        Call<String> call = ngoaiTeAPI.getGiaNgoaiTe();

        // Gọi api
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        this.callback.updateOnSucceed(response.body());
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        this.callback.updateOnFailed(t.getMessage());
    }
}
