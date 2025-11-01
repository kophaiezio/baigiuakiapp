package com.example.goalmanagement; // Đảm bảo tên package khớp với dự án của bạn

/**
 * Class (lớp) đơn giản để chứa dữ liệu cho một mục công việc trong ngày.
 */
public class TodayTaskItem { // Thêm 'public' để các class khác có thể sử dụng
    String title;
    String time;
    String statusText;
    String statusType; // Các loại trạng thái: "completed", "postponed", "pending", "inprogress"

    // Constructor để tạo đối tượng TodayTaskItem
    public TodayTaskItem(String title, String time, String statusText, String statusType) {
        this.title = title;
        this.time = time;
        this.statusText = statusText;
        this.statusType = statusType;
    }

    // (Tùy chọn) Thêm các hàm getter nếu bạn cần truy cập dữ liệu từ bên ngoài
    // Ví dụ:
    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getStatusType() {
        return statusType;
    }
}