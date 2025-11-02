package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
// Import thêm EditText, TextView nếu cần xử lý logic OTP

public class VerifyAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);

        ImageView btnBack = findViewById(R.id.btn_verify_back);
        btnBack.setOnClickListener(v -> finish());

        // TODO: Thêm logic tự động chuyển sang NewPasswordActivity khi nhập đủ 4 số OTP
        // Ví dụ (giả lập):
        // EditText etOtp4 = findViewById(R.id.et_otp_4);
        // etOtp4.addTextChangedListener(... -> {
        //     Intent intent = new Intent(VerifyAccountActivity.this, NewPasswordActivity.class);
        //     startActivity(intent);
        // });

        // Giả lập nhấn vào nút Gửi lại:
        findViewById(R.id.tv_resend_code).setOnClickListener(v -> {
            // TODO: Thêm logic gửi lại code và bắt đầu đếm ngược
            Toast.makeText(this, "Đang gửi lại mã...", Toast.LENGTH_SHORT).show();
        });
    }
}