package com.example.goalmanagement;


public class Notification {
    public String title;
    public String content;
    public String timeAgo;
    public String iconType; // Dùng để xác định icon và màu nền
    public boolean isRead;
    public String detailDateTime; // Thời gian chi tiết

    public Notification(String title, String content, String timeAgo, String iconType, boolean isRead, String detailDateTime) {
        this.title = title;
        this.content = content;
        this.timeAgo = timeAgo;
        this.iconType = iconType;
        this.isRead = isRead;
        this.detailDateTime = detailDateTime;
    }
}