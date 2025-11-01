package com.example.goalmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

// Kế thừa từ BottomSheetDialogFragment
public class GoalDetailBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_GOAL_ITEM = "goal_item";
    private GoalItem goalItem;

    // Views
    TextView tvTitle, tvPercentage, tvProgressDetailValue;
    TextView tvTimeLeftValue, tvCurrentRateValue, tvNeededRateValue;
    ProgressBar progressBarTotal;
    Button btnUpdateScore;
    ImageView ivSettingsIcon;

    // Phương thức newInstance để nhận dữ liệu GoalItem
    public static GoalDetailBottomSheetFragment newInstance(GoalItem goal) {
        GoalDetailBottomSheetFragment fragment = new GoalDetailBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GOAL_ITEM, goal); // Dùng putSerializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Nhận dữ liệu GoalItem
            goalItem = (GoalItem) getArguments().getSerializable(ARG_GOAL_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Nạp layout dialog_goal_detail.xml
        return inflater.inflate(R.layout.dialog_goal_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ các View trong layout
        tvTitle = view.findViewById(R.id.tv_detail_title); // Giả sử bạn đổi ID tiêu đề
        tvPercentage = view.findViewById(R.id.tv_percentage);
        tvProgressDetailValue = view.findViewById(R.id.tv_progress_detail_value);
        tvTimeLeftValue = view.findViewById(R.id.tv_time_left_value);
        tvCurrentRateValue = view.findViewById(R.id.tv_current_rate_value);
        tvNeededRateValue = view.findViewById(R.id.tv_needed_rate_value);
        progressBarTotal = view.findViewById(R.id.progress_bar_total);
        btnUpdateScore = view.findViewById(R.id.btn_update_score);
        ivSettingsIcon = view.findViewById(R.id.iv_settings_icon);

        // Gắn dữ liệu (nếu goalItem không null)
        if (goalItem != null) {
            // Lấy dữ liệu từ item (giả sử)
            // TODO: Tính toán các giá trị này dựa trên dữ liệu thật của goalItem
            int totalProgressValue = 300; // Ví dụ
            int currentProgressValue = (int) (totalProgressValue * (goalItem.percentage / 100.0));

            // Đặt dữ liệu vào View
            // tvTitle.setText(goalItem.title); // Đặt tiêu đề (ví dụ)
            tvPercentage.setText(goalItem.percentage + "%");
            progressBarTotal.setProgress(goalItem.percentage);
            tvProgressDetailValue.setText(currentProgressValue + "/" + totalProgressValue + " giờ");
            tvTimeLeftValue.setText(goalItem.daysLeft);

            // TODO: Tính toán và đặt dữ liệu cho Tốc độ hiện tại, Cần thiết
            tvCurrentRateValue.setText("1.8 giờ/ngày"); // Dữ liệu mẫu
            tvNeededRateValue.setText("1.67 giờ/ngày"); // Dữ liệu mẫu
        }

        // Xử lý sự kiện click
        btnUpdateScore.setOnClickListener(v -> {
            // TODO: Thêm logic cập nhật điểm
            Toast.makeText(getContext(), "Cập nhật điểm...", Toast.LENGTH_SHORT).show();
        });

        ivSettingsIcon.setOnClickListener(v -> {
            // TODO: Thêm logic cài đặt
            Toast.makeText(getContext(), "Mở cài đặt...", Toast.LENGTH_SHORT).show();
        });
    }
}