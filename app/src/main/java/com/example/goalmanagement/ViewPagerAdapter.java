package com.example.goalmanagement;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity; // Quan trọng: dùng FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Trả về Fragment tương ứng với vị trí tab
        switch (position) {
            case 0:
                return new TodayProgressFragment(); // Tab "Hôm nay"
            case 1:
                return new WeekProgressFragment(); // Tab "Tuần"
            case 2:
                return new GoalsProgressFragment(); // Tab "Mục tiêu"
            default:
                return new TodayProgressFragment(); // Mặc định
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Số lượng tab
    }
}
