package com.example.goalmanagement;

import android.os.Bundle;
import androidx.annotation.NonNull; // Thêm import này
import androidx.annotation.Nullable; // Thêm import này
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Fragment để hiển thị tiến độ học tập trong ngày.
 */
public class TodayProgressFragment extends Fragment {

    // Khai báo các biến View và Adapter
    RecyclerView rvTodayTasks;
    TodayTaskAdapter taskAdapter; // Adapter này đã được tạo ở bước trước
    List<TodayTaskItem> taskItems; // Danh sách chứa dữ liệu các task
    TextView tvTodayDate;
    // TODO: Khai báo thêm các View khác nếu cần (ví dụ: ProgressBar, TextView streak...)

    // Constructor rỗng là bắt buộc
    public TodayProgressFragment() {}

    @Nullable // Đổi thành Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout (nạp giao diện) cho Fragment này
        View view = inflater.inflate(R.layout.fragment_today_progress, container, false);

        // Ánh xạ các View từ layout
        rvTodayTasks = view.findViewById(R.id.rv_today_tasks);
        tvTodayDate = view.findViewById(R.id.tv_today_date);
        // TODO: Ánh xạ các View khác (ProgressBar, TextView streak...)

        return view; // Trả về View đã được tạo
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Code xử lý logic sẽ đặt ở đây, sau khi View đã được tạo ---

        // 1. Hiển thị ngày hiện tại
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
        tvTodayDate.setText(dateFormat.format(today.getTime()));

        // 2. Load dữ liệu (hiện tại là dữ liệu mẫu)
        loadTaskData();

        // 3. Thiết lập RecyclerView và Adapter
        // Kiểm tra getContext() không null trước khi dùng
        if (getContext() != null && taskItems != null) {
            // Khởi tạo Adapter với context và danh sách dữ liệu
            taskAdapter = new TodayTaskAdapter(getContext(), taskItems);
            // Thiết lập LayoutManager và Adapter cho RecyclerView
            rvTodayTasks.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTodayTasks.setAdapter(taskAdapter);
        }

        // TODO: Cập nhật giao diện cho Streak, ProgressBar thống kê...
    }


    // Hàm tạo dữ liệu mẫu (Sẽ thay thế bằng logic lấy dữ liệu thật sau này)
    private void loadTaskData() {
        taskItems = new ArrayList<>();
        // Dữ liệu mẫu giống trong ảnh
        taskItems.add(new TodayTaskItem("Nghe TOEIC", "19:00 - 20:00", "Hoàn thành", "completed"));
        taskItems.add(new TodayTaskItem("Nghỉ giải lao", "20:00 - 20:10", "Hoàn thành", "completed"));
        taskItems.add(new TodayTaskItem("Đọc hiểu", "20:10 - 21:00", "Dời lịch", "postponed"));
        taskItems.add(new TodayTaskItem("Từ vựng", "21:00 - 21:50", "Chưa bắt đầu", "pending"));
        taskItems.add(new TodayTaskItem("Ôn tập ngữ pháp", "21:50 - 22:30", "Chưa bắt đầu", "pending")); // Thêm item cuối bị che
    }

    // --- PHẦN CLASS TodayTaskItem ĐÃ ĐƯỢC XÓA KHỎI ĐÂY ---

} // Kết thúc class TodayProgressFragment