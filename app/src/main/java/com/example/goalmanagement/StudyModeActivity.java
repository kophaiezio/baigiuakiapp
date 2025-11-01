package com.example.goalmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface; // Import này có thể cần cho listener của AlertDialog
import android.content.Intent; // Import này nếu bạn nhận dữ liệu từ Intent
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class StudyModeActivity extends AppCompatActivity {

    private TextView tvTimerCountdown, tvTimerLabel, tvTaskNameStudy;
    private ProgressBar progressBarTimer;
    private ImageButton btnPausePlay, btnPostpone, btnComplete;
    private CountDownTimer countDownTimer;

    // --- SỬA THỜI GIAN Ở ĐÂY ---
    private long totalTimeInMillis = 30 * 1000; // Đã đổi thành 30 giây
    // --- KẾT THÚC SỬA ---

    private long timeLeftInMillis;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode);

        // Ánh xạ View
        tvTimerCountdown = findViewById(R.id.tv_timer_countdown);
        tvTimerLabel = findViewById(R.id.tv_timer_label);
        tvTaskNameStudy = findViewById(R.id.tv_task_name_study);
        progressBarTimer = findViewById(R.id.progress_bar_timer);
        btnPausePlay = findViewById(R.id.btn_pause_play);
        btnPostpone = findViewById(R.id.btn_postpone);
        btnComplete = findViewById(R.id.btn_complete);

        // --- NHẬN DỮ LIỆU TỪ INTENT (QUAN TRỌNG) ---
        // Lấy tên task và thời lượng từ Intent (nếu có)
        String taskName = getIntent().getStringExtra("TASK_NAME");
        long durationMinutes = getIntent().getLongExtra("TASK_DURATION_MINUTES", 0); // Lấy số phút (long)

        if (taskName != null) {
            tvTaskNameStudy.setText(taskName);
        }

        if (durationMinutes > 0) {
            // Nếu có thời gian từ Intent (tính bằng phút), sử dụng nó
            totalTimeInMillis = durationMinutes * 60 * 1000;
        } else {
            // Nếu không có hoặc bằng 0, mới dùng giá trị mặc định (30 giây)
            totalTimeInMillis = 30 * 1000;
        }
        // --- KẾT THÚC NHẬN DỮ LIỆU ---

        timeLeftInMillis = totalTimeInMillis; // Khởi tạo thời gian còn lại
        progressBarTimer.setMax((int) (totalTimeInMillis / 1000)); // Đặt max tính bằng giây
        // progressBarTimer.setProgress((int) (timeLeftInMillis / 1000)); // Có thể đặt progress ban đầu

        startTimer(); // Bắt đầu đếm giờ

        // Xử lý nút Tạm dừng/Tiếp tục
        btnPausePlay.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        // Xử lý nút Hoàn thành
        btnComplete.setOnClickListener(v -> completeTask());

        // Xử lý nút Dời lịch
        btnPostpone.setOnClickListener(v -> {
            pauseTimer(); // Nên dừng timer trước khi dời
            Toast.makeText(this, "Đã dời lịch (chưa implement logic)", Toast.LENGTH_SHORT).show();
            // TODO: Thêm logic cập nhật trạng thái task là "dời lịch" trong database
            finish(); // Đóng màn hình đếm giờ
        });
    }

    private void startTimer() {
        // Hủy timer cũ nếu có để tránh chạy nhiều timer cùng lúc
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // Cập nhật mỗi giây
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownUI();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                timeLeftInMillis = 0; // Đảm bảo về 0
                updateCountdownUI(); // Cập nhật lần cuối về 00:00
                // Tùy chọn: Phát âm thanh hoặc rung khi hết giờ
                // ...
                showTimerFinishedDialog(); // Hiển thị Dialog (Ảnh 2)
            }
        }.start();

        isTimerRunning = true;
        btnPausePlay.setImageResource(R.drawable.ic_pause); // Đổi icon thành Pause
        // Cập nhật text dưới nút nếu cần (ví dụ từ "Bắt đầu" -> "Tạm dừng")
        TextView pauseLabel = findViewById(R.id.tv_pause_label);
        if (pauseLabel != null) pauseLabel.setText("Tạm dừng");
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        btnPausePlay.setImageResource(R.drawable.ic_play); // Đổi icon thành Play
        // Cập nhật text dưới nút nếu cần
        TextView pauseLabel = findViewById(R.id.tv_pause_label);
        if (pauseLabel != null) pauseLabel.setText("Tiếp tục");
    }

    private void updateCountdownUI() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimerCountdown.setText(timeLeftFormatted);

        // Cập nhật ProgressBar (tính bằng giây)
        int progress = (int) (timeLeftInMillis / 1000);
        progressBarTimer.setProgress(progress);
    }

    private void completeTask() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        // TODO: Đánh dấu task là hoàn thành trong database
        Toast.makeText(this, "Đã hoàn thành!", Toast.LENGTH_SHORT).show();
        finish(); // Đóng màn hình đếm giờ
    }

    // Hiển thị Dialog Hết giờ (Ảnh 2 / image_697a80.png)
    private void showTimerFinishedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Không cần setTitle nếu layout đã có title
        builder.setMessage("Bạn cần thêm thời gian để hoàn thành bài này không?");

        builder.setPositiveButton("Có", (dialog, which) -> {
            showAddTimeDialog(); // Hiện dialog thêm giờ
        });
        builder.setNegativeButton("Đã hoàn thành", (dialog, which) -> {
            completeTask(); // Gọi hàm hoàn thành
        });
        builder.setCancelable(false);
        builder.show();
    }

    // Hiển thị Dialog Thêm thời gian (Ảnh 3 / image_697d42.png)
    private void showAddTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Nạp layout custom từ file dialog_add_time.xml
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_time, null);
        final EditText etAddTime = dialogView.findViewById(R.id.et_add_time_input);

        builder.setView(dialogView); // Đặt layout custom cho dialog
        // Không cần setTitle ở đây nếu layout đã có title

        builder.setPositiveButton("Tiếp tục", null); // Set null để override trong OnShowListener
        builder.setNegativeButton("Quay lại", (dialog, which) -> {
            showTimerFinishedDialog(); // Quay lại dialog trước đó
        });

        AlertDialog dialog = builder.create();

        // Override nút Positive để kiểm tra input trước khi dismiss
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                String timeStr = etAddTime.getText().toString().trim(); // Trim khoảng trắng thừa
                if (timeStr.isEmpty()) {
                    Toast.makeText(StudyModeActivity.this, "Vui lòng nhập số phút", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int minutesToAdd = Integer.parseInt(timeStr);
                        if (minutesToAdd <= 0) { // Kiểm tra số dương
                            Toast.makeText(StudyModeActivity.this, "Số phút phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Tính toán thời gian mới và bắt đầu lại timer
                        timeLeftInMillis = minutesToAdd * 60 * 1000;
                        totalTimeInMillis = timeLeftInMillis; // Cập nhật tổng thời gian mới
                        progressBarTimer.setMax((int) (totalTimeInMillis / 1000)); // Cập nhật max progress
                        startTimer(); // Bắt đầu đếm giờ lại với thời gian mới
                        dialog.dismiss(); // Đóng dialog

                    } catch (NumberFormatException e) {
                        Toast.makeText(StudyModeActivity.this, "Số phút không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        dialog.setCancelable(false); // Không cho tắt khi bấm ra ngoài
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy CountDownTimer để tránh rò rỉ bộ nhớ
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}