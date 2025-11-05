package com.example.goalmanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.goalmanagement.Notification;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final List<Notification> notificationList;
    private final Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Notification notification);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);

        // 1. Cấu hình Icon và Màu sắc dựa trên type
        int iconResId;
        int color;

        // Giả lập các loại icon và màu sắc
        if ("reminder".equals(notification.iconType)) {
            iconResId = R.drawable.ic_notifications;
            color = Color.parseColor("#007AFF"); // Xanh dương
        } else if ("report".equals(notification.iconType)) {
            iconResId = R.drawable.ic_progress; // Cần tạo icon này
            color = Color.parseColor("#FF577F"); // Hồng
        } else { // progress
            iconResId = R.drawable.ic_task_book; // Cần tạo icon này
            color = Color.parseColor("#FF6B6B"); // Đỏ/Cam
        }

        // Thiết lập icon
        holder.icon.setImageResource(iconResId);

        // Thiết lập màu nền cho icon (Lấy background drawable đã định nghĩa)
        GradientDrawable background = (GradientDrawable) holder.icon.getBackground().mutate();
        background.setColor(color);

        // 2. Cấu hình Nội dung và trạng thái Đã đọc/Chưa đọc
        holder.title.setText(notification.title);
        holder.content.setText(notification.content);
        holder.time.setText(notification.timeAgo);

        // Hiển thị/Ẩn vòng tròn báo chưa đọc
        holder.unreadIndicator.setVisibility(notification.isRead ? View.GONE : View.VISIBLE);

        // Đặt lắng nghe sự kiện
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    // Đánh dấu một thông báo đã đọc
    public void markAsRead(Notification notification) {
        int index = notificationList.indexOf(notification);
        if (index != -1) {
            notification.isRead = true;
            notifyItemChanged(index);
        }
    }

    // Đánh dấu tất cả đã đọc
    public void markAllAsRead() {
        for (Notification n : notificationList) {
            n.isRead = true;
        }
        notifyDataSetChanged();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView content;
        TextView time;
        View unreadIndicator;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.notificationIcon);
            title = itemView.findViewById(R.id.notificationTitle);
            content = itemView.findViewById(R.id.notificationContent);
            time = itemView.findViewById(R.id.notificationTime);
            unreadIndicator = itemView.findViewById(R.id.unreadIndicator);
        }
    }
}