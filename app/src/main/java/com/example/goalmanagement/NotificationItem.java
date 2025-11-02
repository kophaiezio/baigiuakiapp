package com.example.goalmanagement;

import java.io.Serializable; // Thêm import này để truyền qua Intent

// Dùng Serializable để có thể gửi cả đối tượng qua Intent
public class NotificationItem implements Serializable {

    private String title;
    private String content;
    private String timeAgo; // Ví dụ: "5 phút trước"
    private String type; // "reminder" (nhắc nhở - icon chuông), "warning" (cảnh báo - icon chấm than)
    private boolean isRead;

    public NotificationItem(String title, String content, String timeAgo, String type, boolean isRead) {
        this.title = title;
        this.content = content;
        this.timeAgo = timeAgo;
        this.type = type;
        this.isRead = isRead;
    }

    // Tạo các hàm Getter
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTimeAgo() { return timeAgo; }
    public String getType() { return type; }
    public boolean isRead() { return isRead; }

    // Hàm Setter để thay đổi trạng thái
    public void setRead(boolean read) {
        isRead = read;
    }
}