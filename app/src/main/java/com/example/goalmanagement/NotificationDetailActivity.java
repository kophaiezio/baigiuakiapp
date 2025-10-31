package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Áp dụng thiết lập full screen tương tự
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        setContentView(R.layout.activity_notification_detail);

        // Lấy dữ liệu từ Intent
        Bundle extras = getIntent().getExtras();
        String title = extras != null ? extras.getString("title", "Không có tiêu đề") : "Không có tiêu đề";
        String content = extras != null ? extras.getString("content", "Không có nội dung") : "Không có nội dung";
        String dateTime = extras != null ? extras.getString("dateTime", "") : "";

        // Ánh xạ và thiết lập nội dung
        TextView detailTitle = findViewById(R.id.detailTitle);
        TextView detailDateTime = findViewById(R.id.detailDateTime);
        TextView detailContent = findViewById(R.id.detailContent);

        detailTitle.setText(title);
        detailDateTime.setText(dateTime);
        detailContent.setText(content);

        // Xử lý sự kiện
        findViewById(R.id.closeButton).setOnClickListener(v -> finish());
        findViewById(R.id.actionButton).setOnClickListener(v -> Toast.makeText(this, "Mở tùy chọn...", Toast.LENGTH_SHORT).show());
        findViewById(R.id.viewScheduleButton).setOnClickListener(v -> Toast.makeText(this, "Xem lịch học...", Toast.LENGTH_SHORT).show());
        findViewById(R.id.startButton).setOnClickListener(v -> Toast.makeText(this, "Bắt đầu ngay!", Toast.LENGTH_SHORT).show());
    }
}