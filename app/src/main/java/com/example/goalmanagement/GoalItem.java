package com.example.goalmanagement; // Đảm bảo tên package này khớp với dự án của bạn

import java.io.Serializable; // <-- THÊM DÒNG IMPORT NÀY

/**
 * Class (lớp) chứa dữ liệu cho một Mục tiêu
 */
public class GoalItem implements Serializable { // <-- THÊM "implements Serializable" VÀO ĐÂY
    String iconName; // Tên icon (ví dụ: "ic_book", "ic_code")
    String title;
    String deadline;
    int percentage;
    String daysLeft;
    String progressDetail; // Ví dụ: "62/270 giờ học"
    String status; // Ví dụ: "Đang tiến hành"

    // Constructor để tạo đối tượng mới
    public GoalItem(String iconName, String title, String deadline, int percentage, String daysLeft, String progressDetail, String status) {
        this.iconName = iconName;
        this.title = title;
        this.deadline = deadline;
        this.percentage = percentage;
        this.daysLeft = daysLeft;
        this.progressDetail = progressDetail;
        this.status = status;
    }
}