package com.example.pc6.retrofittutorial.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pc6.retrofittutorial.APIClient;
import com.example.pc6.retrofittutorial.R;
import com.example.pc6.retrofittutorial.RequestApi;
import com.example.pc6.retrofittutorial.models.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final Button btnSignIn = findViewById(R.id.button_sign_in);
        btnSignIn.setOnClickListener(this);

        edtEmail = findViewById(R.id.edit_email);
        edtPassword = findViewById(R.id.edit_pws);
    }

    @Override
    public void onClick(View v) {
        signIn();
    }

    public void signIn() {
        String email = edtEmail.getText().toString();
        String pws = edtPassword.getText().toString();

        if (email.isEmpty() && pws.isEmpty()) {
            return;
        }

        Retrofit retrofit = APIClient.getClient();

        RequestApi requestApi = retrofit.create(RequestApi.class);

        Call<Result> call = requestApi.signIn(email, pws);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (result.getResultCode() == 200) {
                    if (!result.getId().equals("-1")) {
                        signInSucceed(result.getId());
                    } else {
                        signInFailed();
                    }
                } else {
                    signInFailed();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                signInFailed();
            }
        });
    }

    public void signInSucceed(String userId) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("ID", userId);
        startActivity(intent);
        finish();
    }

    public void signInFailed() {
        new AlertDialog.Builder(this)
                .setTitle("Notice")
                .setMessage("Sign in failed....")
                .setPositiveButton("OK", null)
                .setCancelable(true)
                .show();
    }
}
