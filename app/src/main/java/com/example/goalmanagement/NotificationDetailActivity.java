package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationDetailActivity extends AppCompatActivity {

    TextView tvDetailTitle, tvDetailTimestamp, tvDetailContent;
    Button btnViewSchedule, btnStartNow;
    ImageView btnBack;

    private NotificationItem item; // Biến lưu thông báo được gửi qua

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        // Ánh xạ View
        tvDetailTitle = findViewById(R.id.tv_detail_title);
        tvDetailTimestamp = findViewById(R.id.tv_detail_timestamp);
        tvDetailContent = findViewById(R.id.tv_detail_content);
        btnViewSchedule = findViewById(R.id.btn_detail_view_schedule);
        btnStartNow = findViewById(R.id.btn_detail_start);
        btnBack = findViewById(R.id.btn_back_detail);

        btnBack.setOnClickListener(v -> finish());

        // Nhận dữ liệu từ Intent
        item = (NotificationItem) getIntent().getSerializableExtra("notification_item");

        if (item != null) {
            // Gắn dữ liệu lên View
            tvDetailTitle.setText(item.getTitle());
            tvDetailContent.setText(item.getContent());

            // Tạo timestamp giả lập (bạn có thể truyền timestamp thật qua Intent)
            String timestamp = new SimpleDateFormat("EEEE, dd/MM/yyyy • hh:mm a", new Locale("vi", "VN")).format(new Date());
            tvDetailTimestamp.setText(timestamp);

        } else {
            tvDetailTitle.setText("Lỗi thông báo");
            tvDetailContent.setText("Không nhận được dữ liệu.");
        }

        // Xử lý nút "Xem lịch học"
        btnViewSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(NotificationDetailActivity.this, ScheduleActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Mở ScheduleActivity
            startActivity(intent);
            finish(); // Đóng màn hình này
        });

        // Xử lý nút "Bắt đầu ngay"
        btnStartNow.setOnClickListener(v -> {
            Intent intent = new Intent(NotificationDetailActivity.this, StudyModeActivity.class);
            // TODO: Truyền dữ liệu task (Tên, thời gian) vào Intent
            // Dùng dữ liệu từ 'item' để tìm task tương ứng và gửi đi
            intent.putExtra("TASK_NAME", "TOEIC Reading Practice"); // Dữ liệu giả lập
            intent.putExtra("TASK_DURATION_MINUTES", 60L); // Dữ liệu giả lập

            startActivity(intent);
            finish(); // Đóng màn hình này
        });
    }
}