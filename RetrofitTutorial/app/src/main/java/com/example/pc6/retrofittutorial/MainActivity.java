package com.example.pc6.retrofittutorial;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    private EditText editName;
    private EditText editAge;
    private Button btnSubmit;
    private TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit_name);
        editAge = findViewById(R.id.edit_age);
        btnSubmit = findViewById(R.id.submit_button);
        textInfo = findViewById(R.id.text_info);

        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Retrofit retrofit = APIClient.getClientJson();

        RequestApi requestApi = retrofit.create(RequestApi.class);

        Call<Result> call = requestApi.postSignUp(editName.getText().toString(), editAge.getText().toString());

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (result.result_code == 200) {
                    Retrofit retrofit = APIClient.getClientJson();

                    RequestApi requestApi = retrofit.create(RequestApi.class);

                    Call<User> call2 = requestApi.getInfoUser(result.id);

                    call2.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            textInfo.setText(response.body().toString());
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            textInfo.setText("Get info user failed");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                showDialog("Thất bại",t.getMessage());
            }
        });
    }

    public void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message).setCancelable(false)
                .setPositiveButton("OK", null).show();
    }
}
