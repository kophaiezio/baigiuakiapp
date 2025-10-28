package com.example.goalmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    // Khai báo biến
    EditText etTaskName, etTaskContent, etDeadline, etHoursPerDayTask, etRepeatDays;
    ToggleButton btnPriorityHigh, btnPriorityMedium, btnPriorityLow;
    ToggleButton btnGoalTypeToeic, btnGoalTypeIelts, btnGoalTypeReading, btnGoalTypeExercise;
    ToggleButton btnRepeatDaily;
    Button btnCreateScheduleFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // Ánh xạ View
        etTaskName = findViewById(R.id.et_task_name);
        etTaskContent = findViewById(R.id.et_task_content);
        etDeadline = findViewById(R.id.et_deadline);
        etHoursPerDayTask = findViewById(R.id.et_hours_per_day_task);
        btnPriorityHigh = findViewById(R.id.btn_priority_high);
        btnPriorityMedium = findViewById(R.id.btn_priority_medium);
        btnPriorityLow = findViewById(R.id.btn_priority_low);
        btnGoalTypeToeic = findViewById(R.id.btn_goal_type_toeic);
        btnGoalTypeIelts = findViewById(R.id.btn_goal_type_ielts);
        btnGoalTypeReading = findViewById(R.id.btn_goal_type_reading);
        btnGoalTypeExercise = findViewById(R.id.btn_goal_type_exercise);
        btnRepeatDaily = findViewById(R.id.btn_repeat_daily);
        etRepeatDays = findViewById(R.id.et_repeat_days);
        btnCreateScheduleFinal = findViewById(R.id.btn_create_schedule_final);


        // --- Cập nhật các Listener ---

        etDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(etDeadline);
            }
        });

        etHoursPerDayTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(etHoursPerDayTask);
            }
        });

        etRepeatDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDaysDialog();
            }
        });

        btnRepeatDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảm bảo nút được check khi bấm
                if (!btnRepeatDaily.isChecked()) {
                    btnRepeatDaily.setChecked(true);
                }
                etRepeatDays.setText(""); // Xóa ngày đã chọn
            }
        });

        setupPriorityToggleButtons();
        setupGoalTypeToggleButtons(); // Gọi hàm đã sửa

        btnCreateScheduleFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu người dùng nhập
                String taskName = etTaskName.getText().toString();
                String taskContent = etTaskContent.getText().toString(); // Lấy thêm nội dung
                String deadline = etDeadline.getText().toString(); // Ngày hạn chót (ví dụ: "28.10.2025")
                String taskTime = etHoursPerDayTask.getText().toString(); // Giờ học (ví dụ: "09:30")
                // Lấy thêm các thông tin khác: ưu tiên, loại mục tiêu, ngày lặp...

                // --- KIỂM TRA DỮ LIỆU ---
                if (taskName.isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Vui lòng nhập tên bài tập", Toast.LENGTH_SHORT).show();
                    etTaskName.requestFocus();
                    return; // Dừng lại
                }
                if (deadline.isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Vui lòng chọn hạn chót", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (taskTime.isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Vui lòng chọn số giờ học", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: Thêm các kiểm tra khác (ưu tiên, ...)


                // --- GỬI DỮ LIỆU QUA INTENT ---
                Intent intent = new Intent(CreateTaskActivity.this, ScheduleActivity.class);

                // Đóng gói dữ liệu vào Intent
                intent.putExtra("NEW_TASK_NAME", taskName);
                intent.putExtra("NEW_TASK_CONTENT", taskContent);
                intent.putExtra("NEW_TASK_TIME", taskTime); // Gửi giờ học
                // intent.putExtra("NEW_TASK_DEADLINE", deadline); // Gửi hạn chót nếu cần
                // intent.putExtra("NEW_TASK_TYPE", "study"); // Gửi loại (để tô màu)

                startActivity(intent);
                finish(); // Đóng màn hình này lại
            }
        });
    }

    // --- Các hàm Helper ---

    private void showDatePickerDialog(final EditText editText) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editText.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final EditText editText) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editText.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void setupPriorityToggleButtons() {
        View.OnClickListener priorityClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bỏ check các nút khác khi 1 nút được nhấn
                btnPriorityHigh.setChecked(v.getId() == R.id.btn_priority_high);
                btnPriorityMedium.setChecked(v.getId() == R.id.btn_priority_medium);
                btnPriorityLow.setChecked(v.getId() == R.id.btn_priority_low);
                // Đảm bảo nút được nhấn luôn ở trạng thái checked
                ((ToggleButton)v).setChecked(true);
            }
        };
        btnPriorityHigh.setOnClickListener(priorityClickListener);
        btnPriorityMedium.setOnClickListener(priorityClickListener);
        btnPriorityLow.setOnClickListener(priorityClickListener);
    }

    // Hàm xử lý logic cho các nút loại mục tiêu (ĐÃ SỬA)
    private void setupGoalTypeToggleButtons() {
        View.OnClickListener goalTypeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Không cần làm gì ở đây nữa, ToggleButton tự xử lý
                // ((ToggleButton) v).setChecked( !((ToggleButton) v).isChecked() ); // DÒNG NÀY ĐÃ BỊ XÓA/COMMENT
            }
        };
        btnGoalTypeToeic.setOnClickListener(goalTypeClickListener);
        btnGoalTypeIelts.setOnClickListener(goalTypeClickListener);
        btnGoalTypeReading.setOnClickListener(goalTypeClickListener);
        btnGoalTypeExercise.setOnClickListener(goalTypeClickListener);
    }

    private void showSelectDaysDialog() {
        final String[] daysOfWeek = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
        final boolean[] checkedDays = new boolean[daysOfWeek.length];

        String currentSelection = etRepeatDays.getText().toString();
        if (!currentSelection.isEmpty()) {
            for (int i = 0; i < daysOfWeek.length; i++) {
                if (currentSelection.contains(daysOfWeek[i])) {
                    checkedDays[i] = true;
                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ngày lặp lại");
        builder.setMultiChoiceItems(daysOfWeek, checkedDays, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedDays[which] = isChecked;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder selectedDaysText = new StringBuilder();
                boolean anyDaySelected = false;
                for (int i = 0; i < checkedDays.length; i++) {
                    if (checkedDays[i]) {
                        if (selectedDaysText.length() > 0) {
                            selectedDaysText.append(", ");
                        }
                        selectedDaysText.append(daysOfWeek[i]);
                        anyDaySelected = true;
                    }
                }
                etRepeatDays.setText(selectedDaysText.toString());

                if (anyDaySelected) {
                    btnRepeatDaily.setChecked(false); // Bỏ chọn "Hàng ngày"
                }
            }
        });

        builder.setNegativeButton("Hủy", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}