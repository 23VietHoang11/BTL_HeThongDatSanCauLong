package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminDashboardBinding;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String role = getIntent().getStringExtra("ROLE");

        // Bắt sự kiện Lưới Menu
        binding.btnManageCourts.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(AdminDashboardActivity.this, AdminCourtActivity.class);
            intent.putExtra("ROLE", role); // Chuyền tay cái thẻ ROLE đi tiếp
            startActivity(intent);
        });
        binding.btnManageServices.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(AdminDashboardActivity.this, AdminPosActivity.class);
            startActivity(intent);
        });

        binding.btnManageUsers.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(AdminDashboardActivity.this, AdminUserActivity.class);
            startActivity(intent);
        });

        // Bắt sự kiện Bottom Navigation (Duyệt đơn)
        binding.navAdminBooking.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(AdminDashboardActivity.this, AdminBookingActivity.class);
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // --- NỐI DÂY CHO THANH BOTTOM NAVIGATION ---

        // Nút Tổng Quan (Đang ở trang này rồi nên không cần code mở trang khác)
        binding.navAdminHome.setOnClickListener(v -> {
            // Không làm gì cả
        });

        // Nút Thống Kê
        binding.navAdminStats.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(AdminDashboardActivity.this, AdminStatsActivity.class);
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION); // Chuyển trang không cần hiệu ứng lướt cho mượt
            startActivity(intent);
        });

        // --- XỬ LÝ NÚT NHÂN SỰ ---
        binding.btnManageStaff.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(AdminDashboardActivity.this, AdminStaffActivity.class);
            startActivity(intent);
        });

        // --- XỬ LÝ NÚT KHUYẾN MÃI ---
        binding.btnManageVouchers.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
            // Bạn có thể tạo màn hình Khuyến mãi sau nếu còn dư thời gian
        });

// --- XỬ LÝ NÚT THÔNG BÁO (Nút mới trên Grid) ---
        binding.btnAdminNotifications.setOnClickListener(v -> {
            Toast.makeText(this, "Bạn không có thông báo mới", Toast.LENGTH_SHORT).show();
        });

        // --- XỬ LÝ NÚT CÀI ĐẶT (Nằm dưới Bottom Nav) ---
        binding.navAdminAccount.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(AdminDashboardActivity.this, AdminSettingsActivity.class);
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION); // Chuyển trang mượt mà
            startActivity(intent);
        });

        // ==========================================
        // KIỂM TRA QUYỀN VÀ ẨN CHỨC NĂNG (RBAC)
        // ==========================================

// ==========================================
        // KIỂM TRA QUYỀN VÀ ẨN CHỨC NĂNG (RBAC)
        // ==========================================

        if ("STAFF".equals(role)) {
            // 1. Đổi câu chào
            binding.tvGreeting.setText("Xin chào, Nhân viên!");

            // 2. GIẤU THẺ DOANH THU & DÃN ĐỀU
            binding.cardDoanhThu.setVisibility(android.view.View.GONE);
            binding.layoutTopCards.setWeightSum(2); // Chia đều không gian cho 2 thẻ còn lại

            // 3. Ẩn nút Nhân sự & Khuyến mãi
            binding.btnManageStaff.setVisibility(android.view.View.GONE);
            binding.btnManageVouchers.setVisibility(android.view.View.GONE);

            // 4. FIX LƯỚI GRID: Ép ô Thông báo lấp vào chỗ trống để tạo thành ô 2x2 hoàn hảo
            android.widget.GridLayout.LayoutParams params = (android.widget.GridLayout.LayoutParams) binding.btnAdminNotifications.getLayoutParams();
            params.rowSpec = android.widget.GridLayout.spec(1, 1f);    // Ép lên hàng số 2
            params.columnSpec = android.widget.GridLayout.spec(1, 1f); // Ép sang cột số 2
            binding.btnAdminNotifications.setLayoutParams(params);

            // 5. CĂN GIỮA THANH BOTTOM NAV (Dùng chuẩn ID có sẵn)
            binding.navAdminStats.setVisibility(android.view.View.GONE);
            binding.layoutAdminBottomNav.setWeightSum(3); // Giảm từ 4 phần xuống 3 phần để 3 nút dãn đều ra giữa
        }
    }

}