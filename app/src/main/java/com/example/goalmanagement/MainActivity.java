package com.example.goalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem; // Thêm import này
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull; // Thêm import này
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView; // Thêm import này
import com.google.android.material.navigation.NavigationBarView; // Thêm import này (có thể cần)

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav; // Khai báo biến cho BottomNavigationView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ BottomNavigationView
        bottomNav = findViewById(R.id.bottom_navigation);

        // 1. Chạy hàm setup cho mục tiêu phổ biến
        setupPopularGoals();

        // 2. Chạy hàm setup cho nút tạo mục tiêu
        setupCreateGoalButton();

        // 3. Thiết lập Listener cho BottomNavigationView (MỚI)
        setupBottomNavigation();

    }

    /**
     * Hàm này tìm và thiết lập nội dung cho các mục tiêu phổ biến
     */
    private void setupPopularGoals() {
        // --- SỬA MỤC IELTS ---
        View goalIelts = findViewById(R.id.goal_ielts);
        if (goalIelts != null) {
            TextView tvIelts = goalIelts.findViewById(R.id.tv_goal_name);
            ImageView ivIelts = goalIelts.findViewById(R.id.img_goal_icon);
            if (tvIelts != null) tvIelts.setText("Ielts");
            if (ivIelts != null) ivIelts.setImageResource(R.drawable.ic_ielts_icon);
        }

        // --- SỬA MỤC ĐỌC SÁCH ---
        View goalReading = findViewById(R.id.goal_reading);
        if (goalReading != null) {
            TextView tvReading = goalReading.findViewById(R.id.tv_goal_name);
            ImageView ivReading = goalReading.findViewById(R.id.img_goal_icon);
            if (tvReading != null) tvReading.setText("Đọc sách");
            if (ivReading != null) ivReading.setImageResource(R.drawable.ic_reading_icon);
        }

        // --- SỬA MỤC TẬP THỂ DỤC ---
        View goalExercise = findViewById(R.id.goal_exercise);
        if (goalExercise != null) {
            TextView tvExercise = goalExercise.findViewById(R.id.tv_goal_name);
            ImageView ivExercise = goalExercise.findViewById(R.id.img_goal_icon);
            if (tvExercise != null) tvExercise.setText("Tập thể dục");
            if (ivExercise != null) ivExercise.setImageResource(R.drawable.ic_exercise_icon);
        }
    }

    /**
     * Hàm này thiết lập sự kiện click cho nút "Tạo mục tiêu"
     */
    private void setupCreateGoalButton() {
        CardView createGoalCard = findViewById(R.id.card_create_goal);
        if (createGoalCard != null) {
            View createGoalButton = createGoalCard.findViewById(R.id.btn_create_goal);
            if (createGoalButton != null) {
                createGoalButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, CreateGoalActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(this, "Lỗi: Không tìm thấy btn_create_goal", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Lỗi: Không tìm thấy card_create_goal", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Hàm thiết lập xử lý sự kiện cho BottomNavigationView (MỚI)
     */
    private void setupBottomNavigation() {
        // Đặt mục Trang chủ là mục được chọn mặc định khi mở app
        bottomNav.setSelectedItemId(R.id.nav_home);

        // Gán Listener để xử lý khi người dùng nhấn vào các mục
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    // Đã ở trang chủ, không cần làm gì
                    return true; // Return true để đánh dấu là đã xử lý
                } else if (itemId == R.id.nav_schedule) {
                    // Mở màn hình Lịch học
                    Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                    // Thêm flag để không tạo lại MainActivity nếu nó đã tồn tại
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_progress) {
                    // TODO: Mở màn hình Tiến độ
                    Toast.makeText(MainActivity.this, "Mở Tiến độ", Toast.LENGTH_SHORT).show();
                    // Nếu bạn có Activity mới, hãy tạo Intent tương tự như ScheduleActivity
                    return true;
                } else if (itemId == R.id.nav_community) {
                    // TODO: Mở màn hình Cộng đồng
                    Toast.makeText(MainActivity.this, "Mở Cộng đồng", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    // TODO: Mở màn hình Cá nhân
                    Toast.makeText(MainActivity.this, "Mở Cá nhân", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false; // Return false nếu không xử lý mục này
            }
        });
    }
}