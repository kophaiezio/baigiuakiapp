package com.example.goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException; // Thêm import này
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date; // Thêm import này
import java.util.List;
import java.util.Locale;
import androidx.appcompat.app.AlertDialog;

// Implement CẢ HAI interface: chọn ngày VÀ tương tác sửa/xóa
public class ScheduleActivity extends AppCompatActivity implements
        DateSelectorAdapter.OnDateSelectedListener,
        ScheduleAdapter.OnScheduleInteractionListener {

    // --- Khai báo biến (giữ nguyên) ---
    RecyclerView rvScheduleItems, rvDateSelector;
    ScheduleAdapter scheduleAdapter;
    DateSelectorAdapter dateAdapter;
    List<ScheduleItem> scheduleDataList;
    List<DateItem> dateDataList;
    Button btnAddSchedule, btnStartLearning;
    ImageView btnPrevDay, btnNextDay;
    Calendar selectedDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Ánh xạ View (giữ nguyên)
        rvScheduleItems = findViewById(R.id.rv_schedule_items);
        btnAddSchedule = findViewById(R.id.btn_add_schedule);
        btnStartLearning = findViewById(R.id.btn_start_learning);
        rvDateSelector = findViewById(R.id.rv_date_selector);
        btnPrevDay = findViewById(R.id.btn_prev_day);
        btnNextDay = findViewById(R.id.btn_next_day);

        // Thiết lập RecyclerView chọn ngày (giữ nguyên)
        setupDateSelector();

        // Thiết lập RecyclerView lịch trình (giữ nguyên)
        setupScheduleList(selectedDate);

        // Xử lý nút "Thêm lịch học" (giữ nguyên)
        btnAddSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(ScheduleActivity.this, CreateTaskActivity.class);
            startActivity(intent);
        });

        // --- SỬA LỖI Ở ĐÂY: CẬP NHẬT OnClickListener CHO btnStartLearning ---
        btnStartLearning.setOnClickListener(v -> {
            // Logic để mở StudyModeActivity

            // TODO: Bạn cần logic thông minh hơn để xác định task "hiện tại".
            // Ví dụ: Tìm task đầu tiên chưa hoàn thành trong ngày hôm nay.
            // Tạm thời, chúng ta lấy task đầu tiên trong danh sách (nếu có).

            if (scheduleDataList != null && !scheduleDataList.isEmpty()) {
                ScheduleItem currentTask = scheduleDataList.get(0); // Lấy task đầu tiên

                // Tính thời gian (ví dụ: 08:00 - 09:00 -> 60 phút)
                long durationMinutes = 45; // Mặc định 45 phút

                try {
                    // Thử tính toán thời gian thực tế
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    Date startTime = timeFormat.parse(currentTask.startTime);
                    Date endTime = timeFormat.parse(currentTask.endTime);
                    long diffInMillis = endTime.getTime() - startTime.getTime();
                    // Chuyển sang phút, xử lý trường hợp qua ngày (nếu endTime < startTime)
                    if (diffInMillis < 0) {
                        diffInMillis += 24 * 60 * 60 * 1000; // Thêm 24 giờ
                    }
                    durationMinutes = diffInMillis / (60 * 1000);

                } catch (ParseException e) {
                    // Nếu lỗi parse, dùng 45 phút mặc định
                    e.printStackTrace();
                } catch (Exception e) {
                    // Bắt các lỗi khác
                    e.printStackTrace();
                }

                // Mở StudyModeActivity
                Intent intent = new Intent(ScheduleActivity.this, StudyModeActivity.class);
                intent.putExtra("TASK_NAME", currentTask.title);
                intent.putExtra("TASK_DURATION_MINUTES", durationMinutes); // Gửi số phút (long)
                startActivity(intent);

            } else {
                // Không có lịch trình để bắt đầu
                Toast.makeText(this, "Không có lịch trình để bắt đầu", Toast.LENGTH_SHORT).show();
            }
        });
        // --- KẾT THÚC SỬA LỖI ---

        // Xử lý nút bấm mũi tên (giữ nguyên)
        btnPrevDay.setOnClickListener(v -> scrollDays(-1));
        btnNextDay.setOnClickListener(v -> scrollDays(1));
    }

    // --- setupDateSelector (giữ nguyên) ---
    private void setupDateSelector() {
        dateDataList = new ArrayList<>();
        Calendar today = Calendar.getInstance();
        selectedDate.setTime(today.getTime());

        int daysBefore = 10;
        int daysAfter = 10;
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(today.getTime());
        tempCal.add(Calendar.DAY_OF_YEAR, -daysBefore);

        for (int i = 0; i < daysBefore + 1 + daysAfter; i++) {
            boolean isSelected = isSameDay(tempCal, today);
            dateDataList.add(new DateItem((Calendar) tempCal.clone(), isSelected));
            tempCal.add(Calendar.DAY_OF_YEAR, 1);
        }

        dateAdapter = new DateSelectorAdapter(this, dateDataList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvDateSelector.setLayoutManager(layoutManager);
        rvDateSelector.setAdapter(dateAdapter);
        rvDateSelector.scrollToPosition(daysBefore);
    }

    // --- setupScheduleList (giữ nguyên) ---
    private void setupScheduleList(Calendar date) {
        scheduleDataList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("NEW_TASK_NAME") && isSameDay(date, Calendar.getInstance())) {
            String newName = intent.getStringExtra("NEW_TASK_NAME");
            String newContent = intent.getStringExtra("NEW_TASK_CONTENT");
            String newTime = intent.getStringExtra("NEW_TASK_TIME");
            String startTime = newTime != null ? newTime : "00:00";
            String endTime = calculateEndTime(startTime, 1);
            scheduleDataList.add(new ScheduleItem(startTime, endTime, newName, newContent, "study"));
            intent.removeExtra("NEW_TASK_NAME");
        }
        if (scheduleDataList.isEmpty() && isSameDay(date, Calendar.getInstance())) {
            scheduleDataList.add(new ScheduleItem("08:00", "09:00", "Làm bài tập Anh", "Unit 5 Workbook", "study"));
            scheduleDataList.add(new ScheduleItem("09:00", "09:15", "Nghỉ ngơi", "Uống nước, đi lại", "rest"));
        } else if (scheduleDataList.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
            // (Bạn có thể bỏ comment dòng sau để hiện thông báo ngày trống)
            // scheduleDataList.add(new ScheduleItem("", "", "Không có lịch trình", "Cho ngày " + dateFormat.format(date.getTime()), "rest"));
        }

        if (scheduleAdapter == null) {
            scheduleAdapter = new ScheduleAdapter(this, scheduleDataList, this);
            rvScheduleItems.setLayoutManager(new LinearLayoutManager(this));
            rvScheduleItems.setAdapter(scheduleAdapter);
        } else {
            scheduleAdapter.updateData(scheduleDataList);
        }
    }

    // --- calculateEndTime (giữ nguyên) ---
    private String calculateEndTime(String startTime, int durationHours) {
        try {
            String[] parts = startTime.split(":");
            int startHour = Integer.parseInt(parts[0]);
            int startMinute = Integer.parseInt(parts[1]);
            int endHour = (startHour + durationHours) % 24;
            return String.format(Locale.getDefault(), "%02d:%02d", endHour, startMinute);
        } catch (Exception e) {
            return startTime;
        }
    }

    // --- onDateSelected (giữ nguyên) ---
    @Override
    public void onDateSelected(Calendar selectedDate) {
        this.selectedDate = selectedDate;
        setupScheduleList(selectedDate);
    }

    // --- scrollDays (giữ nguyên) ---
    private void scrollDays(int amount) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvDateSelector.getLayoutManager();
        if (layoutManager != null && dateAdapter != null) {
            int currentSelectedPos = -1;
            for(int i=0; i<dateDataList.size(); i++){
                if(dateDataList.get(i).isSelected){
                    currentSelectedPos = i;
                    break;
                }
            }

            int targetPosition = currentSelectedPos + amount;

            if (targetPosition >= 0 && targetPosition < dateAdapter.getItemCount()) {
                Calendar targetDate = dateDataList.get(targetPosition).calendar;
                dateAdapter.setSelectedPosition(targetPosition);
                onDateSelected(targetDate);

                int firstVisible = layoutManager.findFirstVisibleItemPosition();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                if (targetPosition < firstVisible || targetPosition > lastVisible) {
                    rvScheduleItems.smoothScrollToPosition(targetPosition);
                }
            }
        }
    }

    // --- isSameDay (giữ nguyên) ---
    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) return false;
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    // --- onEditClick (giữ nguyên) ---
    @Override
    public void onEditClick(int position) {
        if (position >= 0 && position < scheduleDataList.size()) {
            ScheduleItem itemToEdit = scheduleDataList.get(position);
            Toast.makeText(this, "Sửa: " + itemToEdit.title + " (Chưa implement)", Toast.LENGTH_SHORT).show();
            // TODO: Mở CreateTaskActivity với dữ liệu của itemToEdit để sửa
        }
    }

    // --- onDeleteClick (giữ nguyên) ---
    @Override
    public void onDeleteClick(int position) {
        if (position >= 0 && position < scheduleDataList.size()) {
            ScheduleItem itemToDelete = scheduleDataList.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa lịch trình '" + itemToDelete.title + "'?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // TODO: Xóa item khỏi Database
                        scheduleDataList.remove(position);
                        scheduleAdapter.notifyItemRemoved(position);
                        scheduleAdapter.notifyItemRangeChanged(position, scheduleDataList.size());
                        Toast.makeText(this, "Đã xóa: " + itemToDelete.title, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        }
    }
}