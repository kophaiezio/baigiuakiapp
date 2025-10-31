package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.goalmanagement.Notification;
import com.example.goalmanagement.NotificationAdapter;
import java.util.ArrayList;
import java.util.List;

public class NotificationListActivity extends AppCompatActivity implements NotificationAdapter.OnItemClickListener {

    private NotificationAdapter adapter;
    private List<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // THIẾT LẬP TOÀN MÀN HÌNH VÀ ẨN THANH ĐIỀU HƯỚNG MÀU ĐEN (Navigation Bar)
        // Cách chuyên nghiệp để ẩn thanh hệ thống (cho Android 11+):
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        } else {
            // Cho các phiên bản cũ hơn
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        setContentView(R.layout.activity_notification_list);

        RecyclerView recyclerView = findViewById(R.id.notificationsRecyclerView);
        TextView markAllReadButton = findViewById(R.id.markAllReadButton);
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Dữ liệu mẫu (Tạo 3 loại thông báo theo ảnh)
        notifications = createSampleNotifications();

        adapter = new NotificationAdapter(this, notifications);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        // Xử lý sự kiện Đánh dấu đã đọc
        markAllReadButton.setOnClickListener(v -> {
            adapter.markAllAsRead();
            Toast.makeText(this, "Đã đánh dấu tất cả là đã đọc!", Toast.LENGTH_SHORT).show();
        });
    }

    private List<Notification> createSampleNotifications() {
        List<Notification> list = new ArrayList<>();
        // Thông báo chưa đọc (Reminder)
        list.add(new Notification("Nhắc nhở học tập",
                "Đã đến giờ học TOEIC Reading Practice (19:00)",
                "5 phút trước",
                "reminder",
                false,
                "Thứ 2 ngày 13/09/2025 • 11:50 AM"));

        // Thông báo đã đọc (Report) - Giống ảnh thứ 2
        list.add(new Notification("Báo cáo tuần",
                "Tuần này bạn đã học 10.5 giờ, giảm 2 giờ so với tuần trước.",
                "5 phút trước",
                "report",
                true,
                "Chủ nhật ngày 12/09/2025 • 9:00 AM"));

        // Thông báo chưa đọc (Progress/Warning)
        list.add(new Notification("Cảnh báo tiến độ",
                "Bạn đã bỏ qua 2 task hôm nay. Điều này có thể ảnh hưởng đến mục tiêu.",
                "5 phút trước",
                "progress",
                false,
                "Thứ 2 ngày 13/09/2025 • 7:30 AM"));

        return list;
    }

    @Override
    public void onItemClick(Notification notification) {
        // Đánh dấu là đã đọc và chuyển sang màn hình chi tiết
        adapter.markAsRead(notification);

        Intent intent = new Intent(this, NotificationDetailActivity.class);
        // Chuyển dữ liệu sang màn hình chi tiết (Sử dụng Bundle hoặc Parcelable nếu dữ liệu lớn)
        intent.putExtra("title", notification.title);
        intent.putExtra("content", notification.content);
        intent.putExtra("dateTime", notification.detailDateTime);
        startActivity(intent);
    }
}