package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// import android.content.Intent;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etName, etPassword, etConfirmPassword;
    Button btnRegisterSubmit;
    TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ View
        etEmail = findViewById(R.id.et_register_email);
        etName = findViewById(R.id.et_register_name);
        etPassword = findViewById(R.id.et_register_password);
        etConfirmPassword = findViewById(R.id.et_register_confirm_password);
        btnRegisterSubmit = findViewById(R.id.btn_register_submit);
        tvGoToLogin = findViewById(R.id.tv_go_to_login);

        // Xử lý nút "Tiếp tục" (Đăng ký)
        btnRegisterSubmit.setOnClickListener(v -> {
            // TODO: Thêm logic kiểm tra (email hợp lệ, mật khẩu khớp,...)
            // ...

            // Giả lập đăng ký thành công
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

            // TODO: Tự động đăng nhập và lưu SharedPreferences
            // saveLoginState(true);

            // Đóng màn hình Đăng ký và quay lại (quay lại Login hoặc Profile)
            finish();
        });

        // Xử lý nút "Đăng nhập"
        tvGoToLogin.setOnClickListener(v -> {
            // Chỉ cần đóng màn hình này để quay lại LoginActivity (nếu được mở từ đó)
            finish();
        });
    }
}