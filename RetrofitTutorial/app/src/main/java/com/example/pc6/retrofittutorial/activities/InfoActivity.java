package com.example.pc6.retrofittutorial.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc6.retrofittutorial.APIClient;
import com.example.pc6.retrofittutorial.R;
import com.example.pc6.retrofittutorial.RequestApi;
import com.example.pc6.retrofittutorial.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textName, textBirthDay, textGender, textEmail, textAddr, textPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        textName = findViewById(R.id.text_name);
        textBirthDay = findViewById(R.id.text_bdate);
        textGender = findViewById(R.id.text_gender);
        textEmail = findViewById(R.id.text_email);
        textAddr = findViewById(R.id.text_addr);
        textPhone = findViewById(R.id.text_phone);

        final Intent intent = getIntent();

        if (intent != null) {
            String userId = intent.getStringExtra("ID");
            getInfoUser(userId);
        }

        final Button btnSignOut = findViewById(R.id.button_sign_out);
        btnSignOut.setOnClickListener(this);
    }

    public void getInfoUser(final String userId) {
        Retrofit retrofit = APIClient.getClient();

        RequestApi requestApi = retrofit.create(RequestApi.class);

        Call<User> call = requestApi.getInfoUser(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User.UserData userData = response.body().getInfo();
                textName.setText(userData.getName());
                textBirthDay.setText(userData.getBdate());
                textGender.setText(userData.getGender().equals("0") ? "Male":"Female");
                textEmail.setText(userData.getEmail());
                textAddr.setText(userData.getAddr());
                textPhone.setText(userData.getPhone());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                new AlertDialog.Builder(getBaseContext())
                        .setTitle("Waring")
                        .setMessage("Can't get user data")
                        .setCancelable(true)
                        .show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        SharedPreferences preferences = getSharedPreferences("PREF", MODE_PRIVATE);
        preferences.edit().putString("USER_ID", "").apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
