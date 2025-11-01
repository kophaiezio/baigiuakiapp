package com.example.goalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import này vẫn cần dù hàm cũ bị comment

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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

        // 2. BÌNH LUẬN (COMMENT OUT) HÀM CŨ VÀ GỌI HÀM MỚI
        // setupCreateGoalButton(); // Đã comment out
        setupMiniTimerCard(); // Gọi hàm mới

        // 3. Thiết lập Listener cho BottomNavigationView
        setupBottomNavigation();

    }

    /**
     * Hàm này tìm và thiết lập nội dung cho các mục tiêu phổ biến (Giữ nguyên)
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
     * (ĐÃ ĐƯỢC BÌNH LUẬN LẠI VÌ CARD NÀY ĐANG BỊ ẨN)
     */
    /* // Bắt đầu bình luận
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
    */ // Kết thúc bình luận

    /**
     * HÀM MỚI: Thiết lập sự kiện click cho card mini timer
     */
    private void setupMiniTimerCard() {
        // Dùng ID của thẻ <include> trong file activity_home.xml (hoặc content_home.xml)
        View miniTimerCard = findViewById(R.id.mini_timer_card);

        if (miniTimerCard != null) {
            // Xử lý khi nhấn vào TOÀN BỘ card -> Mở màn hình StudyModeActivity
            miniTimerCard.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, StudyModeActivity.class);

                // TODO: Bạn cần logic để lấy task hiện tại từ database
                // Code dưới đây chỉ là dữ liệu mẫu (để test):
                intent.putExtra("TASK_NAME", "Nghe TOEIC - Listening Practice");
                intent.putExtra("TASK_DURATION_MINUTES", 30L); // Gửi 30 phút (dạng long)

                startActivity(intent);
            });

            // TODO: Xử lý logic cho các nút bấm BÊN TRONG card (Dời lịch, Bắt đầu, Hoàn thành)
            // Ví dụ:
            // ImageButton btnMiniPlay = miniTimerCard.findViewById(R.id.btn_mini_pause_play);
            // btnMiniPlay.setOnClickListener(v_play -> { ... xử lý play/pause ... });

            // ImageButton btnMiniPostpone = miniTimerCard.findViewById(R.id.btn_mini_postpone);
            // btnMiniPostpone.setOnClickListener(v_postpone -> { ... xử lý dời lịch ... });

        } else {
            // Thông báo lỗi nếu không tìm thấy card (giúp gỡ lỗi)
            // Có thể xảy ra nếu bạn đang dùng file layout chưa được cập nhật
            Toast.makeText(this, "Lỗi: Không tìm thấy @id/mini_timer_card", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Hàm thiết lập xử lý sự kiện cho BottomNavigationView (Giữ nguyên)
     */
    private void setupBottomNavigation() {
        // Đặt mục Trang chủ là mục được chọn mặc định khi mở app
        bottomNav.setSelectedItemId(R.id.nav_home);

        // Gán Listener để xử lý khi người dùng nhấn vào các mục
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                // Khai báo Intent ở ngoài
                Intent intent = null;

                if (itemId == R.id.nav_home) {
                    // Đã ở trang chủ, không cần chuyển
                    return true;
                } else if (itemId == R.id.nav_schedule) {
                    // Mở màn hình Lịch học
                    intent = new Intent(MainActivity.this, ScheduleActivity.class);
                } else if (itemId == R.id.nav_progress) {
                    // SỬA Ở ĐÂY: Mở ProgressActivity
                    intent = new Intent(MainActivity.this, ProgressActivity.class);
                } else if (itemId == R.id.nav_community) {
                    // TODO: Mở màn hình Cộng đồng
                    Toast.makeText(MainActivity.this, "Mở Cộng đồng", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.nav_profile) {
                    // TODO: Mở màn hình Cá nhân
                    Toast.makeText(MainActivity.this, "Mở Cá nhân", Toast.LENGTH_SHORT).show();
                }

                // Nếu có Intent được tạo thì mới startActivity
                if (intent != null) {
                    // Cờ này tối ưu hóa việc chuyển đổi giữa các Activity trong BottomNavigationView
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    return true; // Đánh dấu đã xử lý
                }

                return false; // Chưa xử lý
            }
        });
    }
}