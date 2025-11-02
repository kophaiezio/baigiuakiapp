package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class ForgotPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button btnContinue = findViewById(R.id.btn_forgot_continue);
        ImageView btnBack = findViewById(R.id.btn_forgot_back);

        btnBack.setOnClickListener(v -> finish());
        btnContinue.setOnClickListener(v -> {
            // TODO: Gửi email
            Intent intent = new Intent(ForgotPasswordActivity.this, VerifyAccountActivity.class);
            // TODO: Gửi email đã nhập qua intent
            // intent.putExtra("USER_EMAIL", ...);
            startActivity(intent);
        });
    }
}