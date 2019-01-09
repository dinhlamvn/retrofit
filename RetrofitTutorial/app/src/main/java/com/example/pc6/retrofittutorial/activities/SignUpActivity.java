package com.example.pc6.retrofittutorial.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pc6.retrofittutorial.APIClient;
import com.example.pc6.retrofittutorial.R;
import com.example.pc6.retrofittutorial.RequestApi;
import com.example.pc6.retrofittutorial.models.Result;
import com.example.pc6.retrofittutorial.models.User;

import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, Callback<Result>, DialogInterface.OnClickListener {

    private static final String TAG = "SignUpLog";

    private EditText edtName, edtBirthDay, edtEmail, edtAddr, edtPhoneNumber, edtPassword;

    private RadioButton rbtnMale;

    private TextView textSignUpStatus;

    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final Button btnSignUp = findViewById(R.id.button_sign_up);
        btnSignUp.setOnClickListener(this);

        edtName = findViewById(R.id.edit_name);
        edtBirthDay = findViewById(R.id.edit_bdate);
        edtEmail = findViewById(R.id.edit_email);
        edtAddr = findViewById(R.id.edit_addr);
        edtPhoneNumber = findViewById(R.id.edit_phone);
        rbtnMale = findViewById(R.id.radio_male);
        edtPassword = findViewById(R.id.edit_pws);

        textSignUpStatus = findViewById(R.id.text_sign_up_status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_up: {
                sendSignUp();
            }
        }
    }

    private void sendSignUp() {
        User.UserData userData = new User.UserData();

        userData.setName(edtName.getText().toString());
        userData.setBdate(edtBirthDay.getText().toString());
        userData.setGender(rbtnMale.isChecked() ? "0":"1");
        userData.setEmail(edtEmail.getText().toString());
        userData.setAddr(edtAddr.getText().toString());
        userData.setPhone(edtPhoneNumber.getText().toString());
        userData.setPws(edtPassword.getText().toString());

        Retrofit retrofit = APIClient.getClient();

        RequestApi callApi = retrofit.create(RequestApi.class);

        Call<Result> call = callApi.signUp(userData);

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        Result result = response.body();
        if (result.getResultCode() == 200) {
            userId = result.getId();
            saveCookie();
            signUpSucceed();
        } else {
            signUpFailed(result.getMessage());
        }
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
        signUpFailed(null);
    }

    private void signUpSucceed() {
        new AlertDialog.Builder(this)
                .setTitle("Notice")
                .setMessage("Sign up succeed...!!!")
                .setCancelable(false)
                .setPositiveButton("NEXT", this)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("ID", userId);
        startActivity(intent);
        finish();
    }

    public void saveCookie() {
        SharedPreferences preferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        preferences.edit().putString("USER_ID", userId).apply();
    }

    private void signUpFailed(String message) {
        textSignUpStatus.setText(message);

        if (message == null) {
            textSignUpStatus.setText("Sign up failed.....");
        }

        textSignUpStatus.setVisibility(View.VISIBLE);
    }
}
