package com.example.goalmanagement;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

// Import AlertDialog (cho hàm ví dụ)
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment để hiển thị danh sách các mục tiêu học tập.
 * 1. ĐÃ IMPLEMENT INTERFACE (NHƯ TRONG ẢNH)
 */
public class GoalsProgressFragment extends Fragment implements OnGoalClickListener {

    // Khai báo View và Adapter
    RecyclerView rvGoalsList;
    GoalProgressAdapter goalAdapter;
    List<GoalItem> goalItems; // Danh sách dữ liệu mục tiêu (Dùng class GoalItem từ file riêng)
    TextView btnAddNewGoal;

    public GoalsProgressFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals_progress, container, false);

        // Ánh xạ View
        rvGoalsList = view.findViewById(R.id.rv_goals_list);
        btnAddNewGoal = view.findViewById(R.id.btn_add_new_goal);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load dữ liệu mẫu
        loadGoalData();

        // 2. SỬA LẠI TẠO ADAPTER (NHƯ TRONG ẢNH)
        // Thiết lập RecyclerView và Adapter
        if (getContext() != null && goalItems != null) {
            // Khởi tạo Adapter, truyền 'this' làm listener
            goalAdapter = new GoalProgressAdapter(getContext(), goalItems, this);
            rvGoalsList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvGoalsList.setAdapter(goalAdapter);
        }

        // Xử lý nút "+ Thêm mới"
        btnAddNewGoal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateGoalActivity.class);
            startActivity(intent);
        });
    }

    // Hàm tạo dữ liệu mẫu
    private void loadGoalData() {
        goalItems = new ArrayList<>();
        // Dùng class GoalItem từ file GoalItem.java
        goalItems.add(new GoalItem("ic_toeic_logo", "TOEIC 800 điểm", "15/04/2025", 23, "82 ngày còn lại", "62/270 giờ học", "Đang tiến hành"));
        goalItems.add(new GoalItem("ic_book", "Đọc 24 cuốn sách", "31/12/2025", 33, "350 ngày còn lại", "8/24 cuốn", "Đang tiến hành"));
        goalItems.add(new GoalItem("ic_code", "Python cơ bản", "28/02/2025", 67, "45 ngày còn lại", "20/30 bài học", "Sắp hoàn thành"));
    }

    // 3. THÊM HÀM onGoalClick ĐÃ IMPLEMENT (NHƯ TRONG ẢNH)
    @Override
    public void onGoalClick(GoalItem goal) {
        // Tạo và hiển thị BottomSheet khi item được click
        GoalDetailBottomSheetFragment bottomSheet = GoalDetailBottomSheetFragment.newInstance(goal);

        // Dùng getParentFragmentManager() trong Fragment
        bottomSheet.show(getParentFragmentManager(), bottomSheet.getTag());
    }

    /* // Hàm ví dụ để hiển thị Dialog chi tiết (Giữ lại nếu bạn muốn tham khảo sau)
    private void showGoalDetailDialog(GoalItem goal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_goal_detail, null); // Cần tạo layout dialog_goal_detail.xml

        // Ánh xạ View trong dialog và set data từ 'goal'
        // ...

        builder.setView(dialogView)
               .setPositiveButton("Đóng", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    */

} // Kết thúc class GoalsProgressFragment