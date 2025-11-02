package com.example.goalmanagement;

import android.content.Context;
import android.content.Intent; // Thêm import
import android.graphics.Color; // Thêm import
import android.graphics.drawable.GradientDrawable; // Thêm import
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat; // Thêm import
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationItem> notifications;
    private Context context;

    public NotificationAdapter(Context context, List<NotificationItem> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View unreadDot;
        ImageView ivIcon;
        TextView tvTitle, tvTime, tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unreadDot = itemView.findViewById(R.id.unread_dot);
            ivIcon = itemView.findViewById(R.id.iv_notification_icon);
            tvTitle = itemView.findViewById(R.id.tv_notification_title);
            tvTime = itemView.findViewById(R.id.tv_notification_time);
            tvContent = itemView.findViewById(R.id.tv_notification_content);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationItem item = notifications.get(position);

        // Gắn dữ liệu
        holder.tvTitle.setText(item.getTitle());
        holder.tvTime.setText(item.getTimeAgo());
        holder.tvContent.setText(item.getContent());

        // Lấy màu nền (cần tạo trong res/drawable)
        GradientDrawable iconBg = (GradientDrawable) holder.ivIcon.getBackground();

        // Xử lý logic Đã đọc / Chưa đọc
        if (item.isRead()) {
            // Trạng thái ĐÃ ĐỌC (Ảnh 2)
            holder.unreadDot.setVisibility(View.GONE); // Ẩn chấm đỏ
            holder.tvTitle.setTextColor(Color.parseColor("#757575")); // Chữ xám
            holder.tvContent.setTextColor(Color.parseColor("#757575"));
            iconBg.setColor(Color.parseColor("#F5F5F5")); // Nền icon xám nhạt
            holder.ivIcon.setColorFilter(Color.parseColor("#757575")); // Icon xám
        } else {
            // Trạng thái CHƯA ĐỌC (Ảnh 1)
            holder.unreadDot.setVisibility(View.VISIBLE); // Hiện chấm đỏ
            holder.tvTitle.setTextColor(Color.BLACK); // Chữ đen
            holder.tvContent.setTextColor(Color.parseColor("#333333"));

            // Đổi màu nền và icon dựa trên loại thông báo
            if ("warning".equals(item.getType())) {
                iconBg.setColor(Color.parseColor("#FFF0F0")); // Nền đỏ nhạt
                holder.ivIcon.setImageResource(R.drawable.ic_warning); // Cần icon này
                holder.ivIcon.setColorFilter(Color.RED); // Icon đỏ
            } else { // "reminder"
                iconBg.setColor(Color.parseColor("#FFF0F0")); // Nền đỏ nhạt
                holder.ivIcon.setImageResource(R.drawable.ic_notifications);
                holder.ivIcon.setColorFilter(Color.RED); // Icon đỏ
            }
        }

        // Xử lý Click vào item -> Mở màn hình Detail (Ảnh 3)
        holder.itemView.setOnClickListener(v -> {
            // Đánh dấu item này là đã đọc
            item.setRead(true);
            notifyItemChanged(holder.getAdapterPosition()); // Cập nhật lại giao diện item này

            // Mở NotificationDetailActivity
            Intent intent = new Intent(context, NotificationDetailActivity.class);
            intent.putExtra("notification_item", item); // Gửi cả đối tượng qua
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    // Hàm để đánh dấu tất cả là đã đọc
    public void markAllAsRead() {
        for (NotificationItem item : notifications) {
            item.setRead(true);
        }
        notifyDataSetChanged(); // Cập nhật lại toàn bộ danh sách
    }
}