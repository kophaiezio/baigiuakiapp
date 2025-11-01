package com.example.goalmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// --- PHẦN CLASS GOALITEM BỊ TRÙNG LẶP ĐÃ BỊ XÓA KHỎI ĐÂY ---
// (Adapter sẽ tự động sử dụng file GoalItem.java)

// 1. Thêm interface (Như trong ảnh)
/**
 * Interface để gửi sự kiện click
 */
interface OnGoalClickListener {
    void onGoalClick(GoalItem goal);
}

public class GoalProgressAdapter extends RecyclerView.Adapter<GoalProgressAdapter.ViewHolder> {

    private List<GoalItem> goalItems; // Dùng GoalItem từ file GoalItem.java
    private Context context;

    // 2. Thêm biến listener (Như trong ảnh)
    private OnGoalClickListener listener;

    // 3. Sửa Constructor để nhận listener (Như trong ảnh)
    public GoalProgressAdapter(Context context, List<GoalItem> goalItems, OnGoalClickListener listener) {
        this.context = context;
        this.goalItems = goalItems;
        this.listener = listener; // Gán listener
    }

    // --- ViewHolder (Giữ nguyên) ---
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoalIcon;
        TextView tvGoalTitle, tvGoalDeadline, tvGoalPercentage, tvGoalDaysLeft, tvGoalProgressDetail, tvGoalStatus;
        ProgressBar progressGoal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGoalIcon = itemView.findViewById(R.id.iv_goal_icon);
            tvGoalTitle = itemView.findViewById(R.id.tv_goal_title);
            tvGoalDeadline = itemView.findViewById(R.id.tv_goal_deadline);
            tvGoalPercentage = itemView.findViewById(R.id.tv_goal_percentage);
            tvGoalDaysLeft = itemView.findViewById(R.id.tv_goal_days_left);
            progressGoal = itemView.findViewById(R.id.progress_goal);
            tvGoalProgressDetail = itemView.findViewById(R.id.tv_goal_progress_detail);
            tvGoalStatus = itemView.findViewById(R.id.tv_goal_status);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal_progress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoalItem item = goalItems.get(position);

        // --- Phần code gán dữ liệu (Giữ nguyên) ---
        int iconResId = R.drawable.ic_book; // Mặc định
        if ("ic_code".equals(item.iconName)) {
            iconResId = R.drawable.ic_code;
        } else if ("ic_toeic_logo".equals(item.iconName)){
            iconResId = R.drawable.ic_toeic_logo;
        }
        holder.ivGoalIcon.setImageResource(iconResId);

        holder.tvGoalTitle.setText(item.title);
        holder.tvGoalDeadline.setText("Deadline: " + item.deadline);
        holder.tvGoalPercentage.setText(item.percentage + "%");
        holder.tvGoalDaysLeft.setText(item.daysLeft);
        holder.progressGoal.setProgress(item.percentage);
        holder.tvGoalProgressDetail.setText(item.progressDetail);
        holder.tvGoalStatus.setText(item.status);

        // 4. Thêm OnClickListener cho itemView (Như trong ảnh)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                // Gọi listener và truyền cả đối tượng GoalItem
                listener.onGoalClick(goalItems.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return goalItems.size();
    }
}