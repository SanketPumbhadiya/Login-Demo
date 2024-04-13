package com.example.demo_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText usernameEdittext, passwordEdittext;
    TextView responseTextview, displayTextview;
    CheckBox checkBox;

    Button loginButton;
    SharedPreferenceClass sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEdittext = findViewById(R.id.edt_username);
        passwordEdittext = findViewById(R.id.edt_password);
        loginButton = findViewById(R.id.btn_login);
        responseTextview = findViewById(R.id.tv_response);
        displayTextview = findViewById(R.id.tv_display);
        checkBox = findViewById(R.id.checkBox);


        sharedPreferences = new SharedPreferenceClass(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdittext.getText().toString();
                String password = passwordEdittext.getText().toString();

                if (username.isEmpty() && password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Username & Password", Toast.LENGTH_SHORT).show();
                } else if (username.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    ApiResponse(username, password);
                }
            }
        });
    }

    public void ApiResponse(String username, String password) {

        LoginRequestModel requestModel = new LoginRequestModel(username, password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApis.loginUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApis retrofitApi = retrofit.create(RetrofitApis.class);

        Call<String> call = retrofitApi.PostRetrofitApi(requestModel);
        call.enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.d("onResponse", "onResponse function called");
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Response", response.body());
                    String loginResponseString = response.body();

                    if (loginResponseString.equals("Invalid")) {
                        displayTextview.setText("Login Unsuccessfully, please try again");
                        displayTextview.setTextColor(Color.parseColor("#FF0000"));
                        responseTextview.setText("Invalid");
                    }
                    else {
                        displayTextview.setText("Login Successfully..");
                        displayTextview.setTextColor(Color.parseColor("#008000"));

                        if (checkBox.isChecked()) {
                            sharedPreferences.putCredentials(username, password);
                        }

                        Intent i = new Intent(getApplicationContext(), LogoutActivity.class);
                        startActivity(i);

                        int index = loginResponseString.indexOf('#');
                        String baseAuthString = loginResponseString.substring(0, index);
                        String userIdString = loginResponseString.substring(index + 3);
                        responseTextview.setText("Base Auth : " + baseAuthString + "\n" + "User Id : " + userIdString);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
