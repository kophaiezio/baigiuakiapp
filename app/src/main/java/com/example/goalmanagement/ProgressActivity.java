package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2; // Import ViewPager2

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout; // Import TabLayout
import com.google.android.material.tabs.TabLayoutMediator; // Import TabLayoutMediator

public class ProgressActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter; // Adapter cho ViewPager2 (sẽ tạo sau)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        tabLayout = findViewById(R.id.tab_layout_progress);
        viewPager = findViewById(R.id.view_pager_progress);

        // Tạo Adapter (sẽ tạo class ViewPagerAdapter sau)
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // Kết nối TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Hôm nay");
                    break;
                case 1:
                    tab.setText("Tuần");
                    break;
                case 2:
                    tab.setText("Mục tiêu");
                    break;
            }
        }).attach();

        // TODO: Xử lý nút chuông thông báo (img_notification_progress)
    }
}
