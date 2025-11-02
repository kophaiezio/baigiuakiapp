package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnLoginSubmit;
    TextView tvGoToRegister, tvForgotPassword;
    EditText etLoginEmail, etLoginPassword; // Thêm các EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các nút
        btnLoginSubmit = findViewById(R.id.btn_login_submit);
        tvGoToRegister = findViewById(R.id.tv_go_to_register);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPassword = findViewById(R.id.et_login_password);

        // Xử lý sự kiện click
        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Xử lý khi nhấn nút ĐĂNG NHẬP
        btnLoginSubmit.setOnClickListener(v -> {
            // Lấy dữ liệu người dùng nhập
            String email = etLoginEmail.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();

            // --- KIỂM TRA DỮ LIỆU ---
            if (email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                return; // Dừng lại
            }
            // TODO: (Sau này) Kiểm tra email, password với Firebase/Database
            // ...

            // --- SỬA LOGIC LẤY TÊN NGƯỜI DÙNG TẠI ĐÂY ---
            String userName;
            if (email.contains("@")) {
                // Tách email thành 2 phần tại dấu @ và lấy phần đầu tiên
                userName = email.split("@")[0];
            } else {
                // Nếu người dùng nhập tên không có @, tạm dùng chính tên đó
                userName = email;
            }
            // Dòng "String name = "Hoahoang";" đã bị xóa
            // --- KẾT THÚC SỬA ---

            // --- GIẢ SỬ ĐĂNG NHẬP THÀNH CÔNG ---
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            // 1. Lưu trạng thái (dùng tên và email THẬT)
            saveLoginState(true, userName, email);

            // 2. Đóng màn hình Login
            finish();
        });
    }

    /**
     * Hàm tiện ích để lưu trạng thái đăng nhập
     * Nó sẽ lưu vào file "UserPrefs"
     */
    private void saveLoginState(boolean isLoggedIn, String name, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ProfileActivity.PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(ProfileActivity.IS_LOGGED_IN_KEY, isLoggedIn);
        editor.putString(ProfileActivity.USER_NAME_KEY, name); // Lưu tên THẬT
        editor.putString(ProfileActivity.USER_EMAIL_KEY, email); // Lưu email THẬT
        editor.apply(); // Dùng apply() là đủ ở đây
    }
}