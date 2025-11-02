package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial; // Import Switch

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    ImageView btnBack;
    SwitchMaterial switchPomodoro, switchAutoPostpone, switchReminders, switchWeeklyReport, switchGoalWarnings, switchAchievements;
    Spinner spinnerBreakTime;
    TextView tvGoogleCalendarStatus; // Giả sử là TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Ánh xạ View
        btnBack = findViewById(R.id.btn_back_settings);
        switchPomodoro = findViewById(R.id.switch_pomodoro);
        switchAutoPostpone = findViewById(R.id.switch_auto_postpone);
        spinnerBreakTime = findViewById(R.id.spinner_break_time);
        tvGoogleCalendarStatus = findViewById(R.id.tv_google_calendar_status);
        switchReminders = findViewById(R.id.switch_reminders);
        switchWeeklyReport = findViewById(R.id.switch_weekly_report);
        switchGoalWarnings = findViewById(R.id.switch_goal_warnings);
        switchAchievements = findViewById(R.id.switch_achievements);

        // Xử lý nút quay lại
        btnBack.setOnClickListener(v -> finish());

        // Cấu hình Spinner "Nghỉ giữa task"
        setupBreakTimeSpinner();

        // TODO: Thêm logic lưu/tải trạng thái của các Switch (dùng SharedPreferences)
        switchPomodoro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Xử lý khi gạt nút Pomodoro
            Toast.makeText(this, "Pomodoro: " + isChecked, Toast.LENGTH_SHORT).show();
        });

        tvGoogleCalendarStatus.setOnClickListener(v -> {
            // TODO: Xử lý logic kết nối Google Calendar
            Toast.makeText(this, "Kết nối Google Calendar...", Toast.LENGTH_SHORT).show();
        });
    }

    // Hàm thiết lập Spinner thời gian nghỉ
    private void setupBreakTimeSpinner() {
        List<String> breakOptions = new ArrayList<>();
        breakOptions.add("5 phút");
        breakOptions.add("10 phút");
        breakOptions.add("15 phút");
        breakOptions.add("Không nghỉ");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, breakOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBreakTime.setAdapter(adapter);

        // TODO: Tải và đặt giá trị đã lưu cho Spinner (dùng SharedPreferences)
        // spinnerBreakTime.setSelection(đã_lưu);
    }
}