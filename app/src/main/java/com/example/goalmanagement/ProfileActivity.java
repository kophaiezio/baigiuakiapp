package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    // Định nghĩa các tài nguyên drawable (icons)
    private static final int ICON_PERSON = R.drawable.ic_person; // Icon người nhỏ
    private static final int ICON_SHARE = R.drawable.ic_share; // Icon chia sẻ
    private static final int ICON_PALETTE = R.drawable.ic_palette; // Icon màu nền
    private static final int ICON_SCHEDULE = R.drawable.ic_schedule; // Icon lịch/công việc
    private static final int ICON_SETTINGS = R.drawable.ic_settings; // Icon bánh răng cài đặt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ các mục menu (Views) đã được include
        View itemPersonalInfo = findViewById(R.id.item_personal_info);
        View itemShare = findViewById(R.id.item_share);
        View itemTheme = findViewById(R.id.item_theme);
        View itemSchedule = findViewById(R.id.item_schedule);
        View itemNotifications = findViewById(R.id.item_notifications);

        // --- Cấu hình nội dung và Icon cho từng mục menu (Theo ảnh gốc) ---

        // Mục 1: Thông tin cá nhân
        setupMenuItem(itemPersonalInfo, ICON_PERSON, "Thông tin cá nhân", null);
        itemPersonalInfo.setOnClickListener(v -> handleClick("Thông tin cá nhân"));

        // Mục 2: Chia sẻ GoalTracker
        setupMenuItem(itemShare, ICON_SHARE, "Chia sẻ GoalTracker", null);
        itemShare.setOnClickListener(v -> handleClick("Chia sẻ GoalTracker"));

        // Mục 3: Màu nền (Có Subtitle là "Goal Tracker" như trong ảnh)
        setupMenuItem(itemTheme, ICON_PALETTE, "Màu nền", "Goal Tracker");
        itemTheme.setOnClickListener(v -> handleClick("Màu nền"));

        // Mục 4: Lịch sinh hoạt của bạn
        setupMenuItem(itemSchedule, ICON_SCHEDULE, "Lịch sinh hoạt của bạn", null);
        itemSchedule.setOnClickListener(v -> handleClick("Lịch sinh hoạt của bạn"));

        // Mục 5: Cài đặt thông báo
        setupMenuItem(itemNotifications, ICON_SETTINGS, "Cài đặt thông báo", null);
        itemNotifications.setOnClickListener(v -> handleClick("Cài đặt thông báo"));

        // --- Xử lý các Button khác (Giữ nguyên) ---

        ImageView backButton = findViewById(R.id.backButton);
        TextView logoutButton = findViewById(R.id.logoutButton);

        backButton.setOnClickListener(v -> onBackPressed());

        logoutButton.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Đang đăng xuất...", Toast.LENGTH_SHORT).show();
            // TODO: Thêm logic chuyển sang màn hình Login/Intro
        });
    }

    /**
     * Hàm hỗ trợ cài đặt nội dung cho từng mục menu (row)
     * @param rowView View của mục menu (đã được include)
     * @param iconRes Id tài nguyên của Icon
     * @param title Tiêu đề chính
     * @param subtitle Tiêu đề phụ (null nếu không có)
     */
    private void setupMenuItem(View rowView, int iconRes, String title, String subtitle) {
        // Tìm kiếm các thành phần con bên trong View của mục menu (từ file menu_item_row.xml)
        ImageView icon = rowView.findViewById(R.id.itemIcon);
        TextView titleText = rowView.findViewById(R.id.itemTitle);
        TextView subtitleText = rowView.findViewById(R.id.itemSubtitle);

        // Thiết lập các giá trị
        if (icon != null) {
            icon.setImageResource(iconRes);
        }
        if (titleText != null) {
            titleText.setText(title);
        }

        // Thiết lập Subtitle nếu có
        if (subtitleText != null) {
            if (subtitle != null) {
                subtitleText.setText(subtitle);
                subtitleText.setVisibility(View.VISIBLE);
            } else {
                subtitleText.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Hàm xử lý click chung cho các mục menu
     */
    private void handleClick(String featureName) {
        Toast.makeText(this, "Mở chức năng: " + featureName, Toast.LENGTH_SHORT).show();
        // TODO: Thêm Intent để mở Activity/Fragment tương ứng
    }
}