package com.example.goalmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProgressActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ImageView btnNotification;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // Ánh xạ View
        tabLayout = findViewById(R.id.tab_layout_progress);
        viewPager = findViewById(R.id.view_pager_progress);
        btnNotification = findViewById(R.id.img_notification_progress);
        bottomNav = findViewById(R.id.bottom_navigation_progress);

        // Cấu hình ViewPager và TabLayout (Giữ nguyên)
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Hôm nay"); break;
                case 1: tab.setText("Tuần"); break;
                case 2: tab.setText("Mục tiêu"); break;
            }
        }).attach();

        setupNotificationButton();
        setupBottomNavigation();
    }

    // THÊM HÀM onResume()
    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_progress); // Đặt lại tab khi quay về
        }
    }

    // Hàm xử lý nút chuông (Giữ nguyên)
    private void setupNotificationButton() {
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v -> {
                Intent intent = new Intent(ProgressActivity.this, NotificationActivity.class);
                startActivity(intent);
            });
        }
    }

    /**
     * HÀM setupBottomNavigation (SỬA LẠI)
     */
    private void setupBottomNavigation() {
        // Xóa dòng setSelectedItemId(R.id.nav_progress) khỏi đây

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;

                if (itemId == R.id.nav_home) {
                    intent = new Intent(ProgressActivity.this, MainActivity.class);
                } else if (itemId == R.id.nav_schedule) {
                    intent = new Intent(ProgressActivity.this, ScheduleActivity.class);
                } else if (itemId == R.id.nav_progress) {
                    return true; // Đã ở đây
                } else if (itemId == R.id.nav_community) {
                    Toast.makeText(ProgressActivity.this, "Mở Cộng đồng", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.nav_profile) {
                    intent = new Intent(ProgressActivity.this, ProfileActivity.class);
                }

                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return true; // Sửa: Luôn trả về true (hoặc false nếu không xử lý)
            }
        });
    }
}