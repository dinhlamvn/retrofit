package com.example.pc6.retrofittutorial.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pc6.retrofittutorial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnSignUp = findViewById(R.id.button_sign_up);
        final Button btnSignIn = findViewById(R.id.button_sign_in);

        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        SharedPreferences preferences = getSharedPreferences("PREF", MODE_PRIVATE);
        String userId = preferences.getString("USER_ID", "");

        if (!userId.equals("")) {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("ID", userId);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in: {
                requestSignIn();
                break;
            }
            case R.id.button_sign_up: {
                requestSignUp();
                break;
            }
        }
    }

    private void requestSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private void requestSignIn() {
        startActivity(new Intent(this, SignInActivity.class));
    }

}
