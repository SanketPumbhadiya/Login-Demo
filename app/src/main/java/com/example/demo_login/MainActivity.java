package com.example.demo_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_login.model.ModelApiRequestLogin;
import com.example.demo_login.model.ModelLoginCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText etUserName, etPassword;
    TextView tvApiResponse, tvApiSuccessStatus;
    CheckBox checkBox;
    Button btnLogin;
    SharedPreferenceClass sharedPreferences;
    LayoutInflater inflater;
    AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndWidgets();
        inflater = getLayoutInflater();
        sharedPreferences = new SharedPreferenceClass(this);
        btnLogin.setOnClickListener(onClick_Login);
        progressDialogSetup();
        checkAndPerformAutoLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //checkAndPerformAutoLogin();
    }

    private void setLayoutAndWidgets() {
        setContentView(R.layout.activity_main);
        etUserName = findViewById(R.id.edt_username);
        etPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvApiResponse = findViewById(R.id.tv_response);
        tvApiSuccessStatus = findViewById(R.id.tv_display);
        checkBox = findViewById(R.id.checkBox);
    }

    View.OnClickListener onClick_Login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = etUserName.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() && password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please Enter Username & Password", Toast.LENGTH_SHORT).show();
            } else if (username.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            } else {
                apiCall_Login(username, password);
            }
        }
    };

    private void checkAndPerformAutoLogin() {
        ModelLoginCredentials loginCredentials = sharedPreferences.getLoginCredentials();
        if (loginCredentials.isCredentialsAvailable()) {
            apiCall_Login(loginCredentials.getUserName(), loginCredentials.getPassword());
        }
    }

    public void apiCall_Login(String username, String password) {

        ModelApiRequestLogin requestModel = new ModelApiRequestLogin(username, password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApis.apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApis retrofitApi = retrofit.create(RetrofitApis.class);

        Call<String> call = retrofitApi.PostRetrofitApi(requestModel);
        progressDialogShow();
        call.enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.d("onResponse", "onResponse function called");
                dismissProgressDialogAfterDelay();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Response", response.body());
                    String loginResponseString = response.body();

                    if (loginResponseString.equals("Invalid")) {
                        tvApiSuccessStatus.setText("Login Unsuccessfully, please try again");
                        tvApiSuccessStatus.setTextColor(Color.parseColor("#FF0000"));
                        tvApiResponse.setText("Invalid");
                    } else {
                        tvApiSuccessStatus.setText("Login Successfully..");
                        tvApiSuccessStatus.setTextColor(Color.parseColor("#008000"));

                        if (checkBox.isChecked()) {
                            sharedPreferences.setLoginCredentials(username, password);
                        }

                        int index = loginResponseString.indexOf('#');
                        String baseAuthString = loginResponseString.substring(0, index);
                        String userIdString = loginResponseString.substring(index + 3);
                        tvApiResponse.setText("Base Auth : " + baseAuthString + "\n" + "User Id : " + userIdString);

                        Intent i = new Intent(getApplicationContext(), LogoutActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                t.printStackTrace();
                dismissProgressDialogAfterDelay();
            }
        });
    }

    private void progressDialogSetup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = inflater.inflate(R.layout.customdialog, null);
        builder.setView(v);
        progressDialog = builder.create();
    }

    public void progressDialogShow() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            return;
        }
        progressDialog.show();
    }

    public void progressDialogHide() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void dismissProgressDialogAfterDelay() {
//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(3000);
        progressDialogHide();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });
    }
}