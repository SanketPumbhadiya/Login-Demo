package com.example.demo_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogoutActivity extends AppCompatActivity {
    Button logoutButton;
    SharedPreferenceClass sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        logoutButton = findViewById(R.id.btn_logout);
        sharedPreferences = new SharedPreferenceClass(this);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.clearLoginCredentials();
                Intent i = new Intent(LogoutActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}