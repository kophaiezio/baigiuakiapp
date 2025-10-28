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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import androidx.appcompat.app.AlertDialog;

// Implement CẢ HAI interface: chọn ngày VÀ tương tác sửa/xóa
public class ScheduleActivity extends AppCompatActivity implements
        DateSelectorAdapter.OnDateSelectedListener,
        ScheduleAdapter.OnScheduleInteractionListener { // <-- THÊM INTERFACE NÀY

    // --- Khai báo biến (giữ nguyên) ---
    RecyclerView rvScheduleItems, rvDateSelector;
    ScheduleAdapter scheduleAdapter;
    DateSelectorAdapter dateAdapter;
    List<ScheduleItem> scheduleDataList;
    List<DateItem> dateDataList;
    Button btnAddSchedule, btnStartLearning;
    ImageView btnPrevDay, btnNextDay;
    Calendar selectedDate = Calendar.getInstance();

    // --- onCreate (giữ nguyên phần lớn) ---
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

        // Thiết lập RecyclerView lịch trình (sẽ sửa đổi cách tạo adapter)
        setupScheduleList(selectedDate);

        // Xử lý nút bấm (giữ nguyên)
        btnAddSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(ScheduleActivity.this, CreateTaskActivity.class);
            startActivity(intent);
        });
        btnStartLearning.setOnClickListener(v -> {
            Toast.makeText(this, "Bắt đầu học...", Toast.LENGTH_SHORT).show();
        });
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

    // --- setupScheduleList (SỬA ĐỔI CÁCH TẠO ADAPTER) ---
    private void setupScheduleList(Calendar date) {
        // --- Phần lấy dữ liệu (giữ nguyên) ---
        scheduleDataList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("NEW_TASK_NAME") && isSameDay(date, Calendar.getInstance())) {
            String newName = intent.getStringExtra("NEW_TASK_NAME");
            String newContent = intent.getStringExtra("NEW_TASK_CONTENT");
            String newTime = intent.getStringExtra("NEW_TASK_TIME");
            String startTime = newTime != null ? newTime : "00:00";
            String endTime = calculateEndTime(startTime, 1);
            scheduleDataList.add(new ScheduleItem(startTime, endTime, newName, newContent, "study"));
            intent.removeExtra("NEW_TASK_NAME"); // Quan trọng: Xóa để không thêm lại
        }
        if (scheduleDataList.isEmpty() && isSameDay(date, Calendar.getInstance())) {
            scheduleDataList.add(new ScheduleItem("08:00", "09:00", "Làm bài tập Anh", "Unit 5 Workbook", "study"));
            scheduleDataList.add(new ScheduleItem("09:00", "09:15", "Nghỉ ngơi", "Uống nước, đi lại", "rest"));
        } else if (scheduleDataList.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
            // scheduleDataList.add(new ScheduleItem("", "", "Không có lịch trình", "Cho ngày " + dateFormat.format(date.getTime()), "rest"));
        }
        // --- Kết thúc phần lấy dữ liệu ---

        // --- Cập nhật Adapter (SỬA ĐỔI) ---
        if (scheduleAdapter == null) {
            // Khi tạo mới, truyền 'this' làm listener
            scheduleAdapter = new ScheduleAdapter(this, scheduleDataList, this); // <-- THAY ĐỔI Ở ĐÂY
            rvScheduleItems.setLayoutManager(new LinearLayoutManager(this));
            rvScheduleItems.setAdapter(scheduleAdapter);
        } else {
            // Khi cập nhật, chỉ cần gọi updateData
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
                // Dùng isSelected vì selectedPosition trong adapter là private
                if(dateDataList.get(i).isSelected){
                    currentSelectedPos = i;
                    break;
                }
            }
            // Hoặc dùng selectedPosition nếu bạn làm nó public/có getter trong adapter

            int targetPosition = currentSelectedPos + amount;

            if (targetPosition >= 0 && targetPosition < dateAdapter.getItemCount()) {
                Calendar targetDate = dateDataList.get(targetPosition).calendar;
                dateAdapter.setSelectedPosition(targetPosition); // Yêu cầu adapter cập nhật lựa chọn
                onDateSelected(targetDate); // Gọi hàm để load lại lịch

                int firstVisible = layoutManager.findFirstVisibleItemPosition();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                if (targetPosition < firstVisible || targetPosition > lastVisible) {
                    rvScheduleItems.smoothScrollToPosition(targetPosition); // Cuộn nếu cần
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


    // --- HÀM MỚI: XỬ LÝ SỰ KIỆN SỬA TỪ ADAPTER ---
    @Override
    public void onEditClick(int position) {
        if (position >= 0 && position < scheduleDataList.size()) {
            ScheduleItem itemToEdit = scheduleDataList.get(position);
            Toast.makeText(this, "Sửa: " + itemToEdit.title + " (Chưa implement)", Toast.LENGTH_SHORT).show();
            // TODO: Mở CreateTaskActivity với dữ liệu của itemToEdit để sửa
            // Intent intent = new Intent(this, CreateTaskActivity.class);
            // intent.putExtra("MODE", "EDIT"); // Đánh dấu là chế độ sửa
            // intent.putExtra("ITEM_POSITION", position);
            // intent.putExtra("ITEM_TITLE", itemToEdit.title);
            // ... gửi các dữ liệu khác
            // startActivityForResult(intent, REQUEST_CODE_EDIT); // Dùng nếu cần nhận kết quả trả về
        }
    }

    // --- HÀM MỚI: XỬ LÝ SỰ KIỆN XÓA TỪ ADAPTER ---
    @Override
    public void onDeleteClick(int position) {
        if (position >= 0 && position < scheduleDataList.size()) {
            ScheduleItem itemToDelete = scheduleDataList.get(position);

            // Hiện hộp thoại xác nhận xóa (khuyến khích)
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa lịch trình '" + itemToDelete.title + "'?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // TODO: Xóa item khỏi Database (nếu có) trước khi xóa khỏi list

                        // Xóa item khỏi danh sách dữ liệu
                        scheduleDataList.remove(position);
                        // Thông báo cho adapter biết item đã bị xóa tại vị trí đó
                        scheduleAdapter.notifyItemRemoved(position);
                        // Thông báo thay đổi vị trí các item còn lại (quan trọng để index không bị sai)
                        scheduleAdapter.notifyItemRangeChanged(position, scheduleDataList.size());

                        Toast.makeText(this, "Đã xóa: " + itemToDelete.title, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null) // Không làm gì khi nhấn Hủy
                    .show();
        }
    }
}