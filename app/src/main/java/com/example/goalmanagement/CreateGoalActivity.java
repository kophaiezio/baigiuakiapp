package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Thêm import này
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color; // Thêm import này
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
// import android.widget.TextView; // Import này có thể không cần nữa nếu bạn không dùng TextView cho Loại mục tiêu
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
// import java.util.ArrayList; // Import này không cần thiết trong code mới này

public class CreateGoalActivity extends AppCompatActivity {

    EditText etCurrentScore, etTargetScore, etGoalTime, etHoursPerDay; // Thêm etCurrentScore, etTargetScore
    Button btnCreateGoalFinal;

    // Khai báo các CardView mới cho "Loại mục tiêu"
    CardView cardGoalTypeToeic, cardGoalTypeIelts, cardGoalTypeReading, cardGoalTypeExercise;
    // Để lưu trạng thái chọn
    private CardView selectedGoalTypeCard = null;
    private String selectedGoalType = ""; // Lưu tên loại mục tiêu đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        // 1. Ánh xạ View
        etCurrentScore = findViewById(R.id.et_current_score);
        etTargetScore = findViewById(R.id.et_target_score);
        etGoalTime = findViewById(R.id.et_goal_time);
        etHoursPerDay = findViewById(R.id.et_hours_per_day);
        btnCreateGoalFinal = findViewById(R.id.btn_create_goal_final);

        // Ánh xạ các CardView loại mục tiêu
        cardGoalTypeToeic = findViewById(R.id.card_goal_type_toeic);
        cardGoalTypeIelts = findViewById(R.id.card_goal_type_ielts);
        cardGoalTypeReading = findViewById(R.id.card_goal_type_reading);
        cardGoalTypeExercise = findViewById(R.id.card_goal_type_exercise);

        // 2. Xử lý chọn ngày mục tiêu
        etGoalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(etGoalTime); // Truyền EditText vào hàm
            }
        });

        // 3. Xử lý chọn giờ học mỗi ngày
        etHoursPerDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(etHoursPerDay); // Truyền EditText vào hàm
            }
        });

        // 4. Xử lý click cho các CardView loại mục tiêu (MỚI)
        setupGoalTypeCards();

        // 5. Xử lý khi nhấn nút "Tạo mục tiêu" (nút cuối trang)
        btnCreateGoalFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra các trường bắt buộc
                if (selectedGoalTypeCard == null) {
                    Toast.makeText(CreateGoalActivity.this, "Vui lòng chọn loại mục tiêu", Toast.LENGTH_SHORT).show();
                    return; // Dừng lại nếu chưa chọn loại mục tiêu
                }
                if (etGoalTime.getText().toString().isEmpty()) {
                    Toast.makeText(CreateGoalActivity.this, "Vui lòng chọn thời gian mục tiêu", Toast.LENGTH_SHORT).show();
                    etGoalTime.requestFocus(); // Focus vào ô bị thiếu
                    return;
                }
                if (etHoursPerDay.getText().toString().isEmpty()) {
                    Toast.makeText(CreateGoalActivity.this, "Vui lòng chọn số giờ học mỗi ngày", Toast.LENGTH_SHORT).show();
                    etHoursPerDay.requestFocus(); // Focus vào ô bị thiếu
                    return;
                }

                // Lấy dữ liệu (ví dụ)
                String currentScore = etCurrentScore.getText().toString();
                String targetScore = etTargetScore.getText().toString();
                String goalTime = etGoalTime.getText().toString();
                String hoursPerDay = etHoursPerDay.getText().toString();
                // selectedGoalType đã được lưu khi chọn Card

                // Nếu đã điền đủ -> Chuyển sang màn hình "Tạo bài tập mới"
                Intent intent = new Intent(CreateGoalActivity.this, CreateTaskActivity.class);
                // Bạn có thể truyền dữ liệu qua Intent nếu cần
                intent.putExtra("GOAL_TYPE", selectedGoalType);
                intent.putExtra("TARGET_SCORE", targetScore); // Ví dụ
                startActivity(intent);
            }
        });
    }

    // Hàm hiển thị DatePickerDialog (Sửa để nhận EditText)
    private void showDatePickerDialog(final EditText editText) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Định dạng ngày theo ý bạn, ví dụ: "dd.MM.yyyy"
                        editText.setText(String.format("%02d.%02d.%d", dayOfMonth, (monthOfYear + 1), year));
                    }
                }, mYear, mMonth, mDay);
        // Tùy chọn: Giới hạn ngày tối thiểu là ngày hiện tại
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    // Hàm hiển thị TimePickerDialog (Sửa để nhận EditText)
    private void showTimePickerDialog(final EditText editText) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Định dạng giờ: "HH:mm"
                        editText.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, true); // true = 24 giờ
        timePickerDialog.show();
    }

    // Hàm xử lý logic cho các CardView loại mục tiêu (MỚI)
    private void setupGoalTypeCards() {
        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bỏ chọn card hiện tại (nếu có)
                if (selectedGoalTypeCard != null) {
                    resetCardBackground(selectedGoalTypeCard);
                }

                // Xác định CardView được nhấn và chọn nó
                selectedGoalTypeCard = (CardView) v;
                highlightCardBackground(selectedGoalTypeCard);

                // Lưu tên loại mục tiêu dựa trên ID của CardView
                int id = v.getId();
                if (id == R.id.card_goal_type_toeic) {
                    selectedGoalType = "Toeic";
                } else if (id == R.id.card_goal_type_ielts) {
                    selectedGoalType = "Ielts";
                } else if (id == R.id.card_goal_type_reading) {
                    selectedGoalType = "Đọc sách";
                } else if (id == R.id.card_goal_type_exercise) {
                    selectedGoalType = "Tập thể dục";
                }
            }
        };

        // Gán listener cho từng CardView
        cardGoalTypeToeic.setOnClickListener(cardClickListener);
        cardGoalTypeIelts.setOnClickListener(cardClickListener);
        cardGoalTypeReading.setOnClickListener(cardClickListener);
        cardGoalTypeExercise.setOnClickListener(cardClickListener);
    }

    // Hàm đặt lại nền CardView (trở về trạng thái chưa chọn - nền trắng)
    private void resetCardBackground(CardView cardView) {
        cardView.setCardBackgroundColor(Color.WHITE);
        // Nếu bạn muốn viền xám, bạn cần dùng drawable selector cho background
    }

    // Hàm làm nổi bật nền CardView (trạng thái đã chọn - nền xanh nhạt)
    private void highlightCardBackground(CardView cardView) {
        // Lấy màu từ colors.xml thay vì hardcode
        int highlightColor = getResources().getColor(R.color.colorCardCreateGoal); // Màu xanh nhạt
        cardView.setCardBackgroundColor(highlightColor);
        // Nếu bạn muốn viền xanh, bạn cần dùng drawable selector cho background
    }
}