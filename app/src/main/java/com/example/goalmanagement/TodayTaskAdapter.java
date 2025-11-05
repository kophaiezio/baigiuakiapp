package com.example.goalmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adapter để hiển thị danh sách công việc hôm nay trong RecyclerView.
 */
public class TodayTaskAdapter extends RecyclerView.Adapter<TodayTaskAdapter.ViewHolder> {

    private List<TodayTaskItem> taskItems; // Danh sách dữ liệu
    private Context context; // Context để lấy tài nguyên (ví dụ: drawable)

    // Constructor để nhận dữ liệu và context
    public TodayTaskAdapter(Context context, List<TodayTaskItem> taskItems) {
        this.context = context;
        this.taskItems = taskItems;
    }

    /**
     * ViewHolder: Giữ các tham chiếu đến các View trong layout item_today_task.xml
     * Giống như một cái "hộp" chứa các thành phần giao diện của một dòng.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStatusIcon;
        TextView tvTaskTitle, tvTaskTime, tvTaskStatusText;
        CheckBox checkboxTaskDone; // Mặc dù ẩn, vẫn có thể cần tham chiếu

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các View từ item layout
            ivStatusIcon = itemView.findViewById(R.id.iv_task_status_icon);
            tvTaskTitle = itemView.findViewById(R.id.tv_task_title);
            tvTaskTime = itemView.findViewById(R.id.tv_task_time);
            tvTaskStatusText = itemView.findViewById(R.id.tv_task_status_text);
            checkboxTaskDone = itemView.findViewById(R.id.checkbox_task_done);
        }
    }

    /**
     * Được gọi khi RecyclerView cần tạo một ViewHolder mới (một dòng mới).
     * Nhiệm vụ: Inflate layout item_today_task.xml và tạo ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo View mới từ file XML layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_today_task, parent, false);
        // Trả về một ViewHolder mới chứa View đó
        return new ViewHolder(view);
    }

    /**
     * Được gọi khi RecyclerView cần hiển thị dữ liệu tại một vị trí cụ thể.
     * Nhiệm vụ: Lấy dữ liệu từ `taskItems` tại vị trí `position` và
     * đặt dữ liệu đó vào các View bên trong `holder`.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy đối tượng dữ liệu tại vị trí này
        TodayTaskItem currentItem = taskItems.get(position);

        // Gắn dữ liệu vào các View trong ViewHolder
        holder.tvTaskTitle.setText(currentItem.title);
        holder.tvTaskTime.setText(currentItem.time);
        holder.tvTaskStatusText.setText(currentItem.statusText);

        // --- Xử lý hiển thị Icon trạng thái dựa vào statusType ---
        int statusIconResId; // Biến lưu ID của drawable icon
        switch (currentItem.statusType.toLowerCase()) { // Chuyển sang chữ thường để so sánh dễ hơn
            case "completed": // Hoàn thành
                statusIconResId = R.drawable.ic_check_circle_filled; // Icon tick xanh
                holder.checkboxTaskDone.setVisibility(View.GONE); // Ẩn checkbox (nếu có)
                break;
            case "postponed": // Dời lịch (Giả sử dùng icon Play như trong hình)
            case "inprogress": // Đang thực hiện (Giả sử cũng dùng icon Play)
                statusIconResId = R.drawable.ic_play_circle; // Icon nút Play (Cần tạo icon này)
                holder.checkboxTaskDone.setVisibility(View.GONE);
                break;
            case "pending": // Chưa bắt đầu
            default: // Các trường hợp khác
                statusIconResId = R.drawable.ic_radio_button_unchecked; // Icon tròn rỗng (Cần tạo icon này)
                holder.checkboxTaskDone.setVisibility(View.VISIBLE); // Hiện Checkbox để đánh dấu hoàn thành
                // TODO: Thêm OnCheckedChangeListener cho checkboxTaskDone nếu cần
                break;
        }
        // Đặt icon tương ứng cho ImageView
        holder.ivStatusIcon.setImageResource(statusIconResId);

        // TODO: Thêm OnClickListener cho itemView nếu muốn xử lý khi nhấn vào cả một dòng
        // holder.itemView.setOnClickListener(v -> { ... });
    }

    /**
     * Trả về tổng số lượng item trong danh sách dữ liệu.
     */
    @Override
    public int getItemCount() {
        // Trả về kích thước của danh sách taskItems (nếu null thì trả về 0)
        return taskItems != null ? taskItems.size() : 0;
    }

    // (Tùy chọn) Hàm cập nhật dữ liệu nếu danh sách thay đổi sau này
    public void updateData(List<TodayTaskItem> newItems) {
        if (newItems != null) {
            this.taskItems.clear();
            this.taskItems.addAll(newItems);
            notifyDataSetChanged();
        }
    }
}