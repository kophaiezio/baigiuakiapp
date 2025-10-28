package com.example.goalmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.graphics.Color; // <-- Thêm dòng này

// Giả sử bạn có class DateItem hoặc dùng trực tiếp Calendar
class DateItem {
    Calendar calendar;
    boolean isSelected;

    public DateItem(Calendar calendar, boolean isSelected) {
        this.calendar = calendar;
        this.isSelected = isSelected;
    }
}

public class DateSelectorAdapter extends RecyclerView.Adapter<DateSelectorAdapter.ViewHolder> {

    private List<DateItem> dateItems;
    private Context context;
    private int selectedPosition = -1; // Vị trí của ngày đang được chọn
    private OnDateSelectedListener listener;

    // Interface để thông báo cho Activity biết ngày nào được chọn
    public interface OnDateSelectedListener {
        void onDateSelected(Calendar selectedDate);
    }

    public DateSelectorAdapter(Context context, List<DateItem> dateItems, OnDateSelectedListener listener) {
        this.context = context;
        this.dateItems = dateItems;
        this.listener = listener;

        // Tìm vị trí được chọn ban đầu
        for (int i = 0; i < dateItems.size(); i++) {
            if (dateItems.get(i).isSelected) {
                selectedPosition = i;
                break;
            }
        }
    }
    public void setSelectedPosition(int position) {
        // Chỉ thực hiện nếu vị trí hợp lệ và khác vị trí hiện tại
        if (position >= 0 && position < dateItems.size() && position != selectedPosition) {
            int previousSelectedPosition = selectedPosition;
            selectedPosition = position;

            // Cập nhật trạng thái isSelected trong danh sách dữ liệu
            if (previousSelectedPosition != -1) {
                dateItems.get(previousSelectedPosition).isSelected = false;
            }
            dateItems.get(selectedPosition).isSelected = true;


            // Thông báo chỉ cập nhật giao diện cho 2 item thay đổi
            if (previousSelectedPosition != -1) {
                notifyItemChanged(previousSelectedPosition); // Bỏ highlight item cũ
            }
            notifyItemChanged(selectedPosition); // Highlight item mới
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_selector, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DateItem item = dateItems.get(position);
        Calendar cal = item.calendar;

        // Định dạng Thứ (ví dụ: T2, T3)
        SimpleDateFormat dayFormat = new SimpleDateFormat("EE", new Locale("vi", "VN"));
        holder.tvDayOfWeek.setText(dayFormat.format(cal.getTime()));

        // Định dạng Ngày (ví dụ: 13, 14)
        holder.tvDateNumber.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));

        // Đặt trạng thái selected cho item view để drawable selector hoạt động
        holder.itemView.setSelected(position == selectedPosition);
        // Đổi màu chữ cho ngày được chọn
        if (position == selectedPosition) {
            holder.tvDateNumber.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.tvDayOfWeek.setTextColor(context.getResources().getColor(R.color.colorButtonBlue)); // Màu xanh
        } else {
            holder.tvDateNumber.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.tvDayOfWeek.setTextColor(Color.parseColor("#757575")); // Màu xám
        }


        holder.itemView.setOnClickListener(v -> {
            if (position != selectedPosition) {
                // Cập nhật vị trí được chọn mới
                int previousSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition(); // Lấy vị trí hiện tại

                // Bỏ chọn item cũ và chọn item mới
                if (previousSelectedPosition != -1) {
                    dateItems.get(previousSelectedPosition).isSelected = false; // Cập nhật data
                    notifyItemChanged(previousSelectedPosition); // Cập nhật giao diện item cũ
                }
                dateItems.get(selectedPosition).isSelected = true; // Cập nhật data
                notifyItemChanged(selectedPosition); // Cập nhật giao diện item mới

                // Thông báo cho Activity ngày mới được chọn
                if (listener != null) {
                    listener.onDateSelected(dateItems.get(selectedPosition).calendar);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayOfWeek, tvDateNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tv_day_of_week);
            tvDateNumber = itemView.findViewById(R.id.tv_date_number);
        }
    }
}