package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class NewPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Button btnContinue = findViewById(R.id.btn_new_pass_continue);
        ImageView btnBack = findViewById(R.id.btn_new_pass_back);

        btnBack.setOnClickListener(v -> finish());
        btnContinue.setOnClickListener(v -> {
            // TODO: Kiểm tra 2 mật khẩu khớp nhau, đủ mạnh
            // ...

            // Giả lập thành công
            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();

            // TODO: Quay về màn hình Đăng nhập
            // Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            // startActivity(intent);
            finish(); // Đóng màn hình này
        });
    }
}