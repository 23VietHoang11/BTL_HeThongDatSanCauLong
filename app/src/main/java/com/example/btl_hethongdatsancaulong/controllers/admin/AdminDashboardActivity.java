package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String role = getIntent().getStringExtra("ROLE");
        mDatabase = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        // 1. Phân quyền và vẽ lại giao diện theo chức vụ
        setupNavigation(role);

        // 2. MỞ KHÓA REALTIME CHO CẢ ADMIN VÀ NHÂN VIÊN
        loadRealtimeStats();
    }

    private void loadRealtimeStats() {
        // Đếm tổng số Sân
        mDatabase.child("Courts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (binding.tvDashCourts != null) {
                    binding.tvDashCourts.setText(String.valueOf(snapshot.getChildrenCount()));
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });

        // Tính Doanh thu và Đếm đơn chờ duyệt
        mDatabase.child("Bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long totalRevenue = 0;
                int pendingOrders = 0;

                for (DataSnapshot data : snapshot.getChildren()) {
                    String status = data.child("trangThai").getValue(String.class);

                    if ("Đã xác nhận".equals(status)) {
                        String priceStr = data.child("tongTien").getValue(String.class);
                        if (priceStr != null) {
                            try {
                                totalRevenue += Long.parseLong(priceStr.replaceAll("[^0-9]", ""));
                            } catch (Exception ignored) {}
                        }
                    } else if ("Chờ duyệt".equals(status)) {
                        // Nhân viên cực kỳ cần nhìn thấy số này nhảy lên
                        pendingOrders++;
                    }
                }

                if (binding.tvDashPending != null) {
                    binding.tvDashPending.setText(String.valueOf(pendingOrders));
                }
                // Thẻ này đã bị ẩn đối với Nhân viên, nhưng hệ thống vẫn tính ngầm và không báo lỗi
                if (binding.tvDashRevenue != null) {
                    binding.tvDashRevenue.setText(String.format("%,d đ", totalRevenue));
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void setupNavigation(String role) {
        binding.btnManageCourts.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, AdminCourtActivity.class);
            intent.putExtra("ROLE", role);
            startActivity(intent);
        });
        binding.btnManageServices.setOnClickListener(v -> startActivity(new android.content.Intent(this, AdminPosActivity.class)));
        binding.btnManageUsers.setOnClickListener(v -> startActivity(new android.content.Intent(this, AdminUserActivity.class)));
        binding.btnManageStaff.setOnClickListener(v -> startActivity(new android.content.Intent(this, AdminStaffActivity.class)));

        binding.navAdminBooking.setOnClickListener(v -> startActivity(new android.content.Intent(this, AdminBookingActivity.class).addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION)));
        binding.navAdminStats.setOnClickListener(v -> startActivity(new android.content.Intent(this, AdminStatsActivity.class).addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION)));
        binding.navAdminAccount.setOnClickListener(v -> startActivity(new android.content.Intent(this, AdminSettingsActivity.class).addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION)));

        binding.btnManageVouchers.setOnClickListener(v -> Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show());
        binding.btnAdminNotifications.setOnClickListener(v -> Toast.makeText(this, "Bạn không có thông báo mới", Toast.LENGTH_SHORT).show());

        // ===============================================
        // ĐIỀU CHỈNH GIAO DIỆN (UI) NẾU LÀ TÀI KHOẢN NHÂN VIÊN
        // ===============================================
        if ("STAFF".equals(role)) {
            binding.tvGreeting.setText("Xin chào, Nhân viên!");

            // Ẩn thẻ doanh thu -> 2 thẻ "Đơn chờ" và "Tổng sân" tự động dãn ra chiếm 100% chiều ngang
            binding.cardDoanhThu.setVisibility(View.GONE);
            binding.layoutTopCards.setWeightSum(2);

            // Ẩn các nút tính năng cấm Nhân viên sử dụng
            binding.btnManageStaff.setVisibility(View.GONE);
            binding.btnManageVouchers.setVisibility(View.GONE);

            // Đẩy nút Thông báo lên lấp vào khoảng trống
            android.widget.GridLayout.LayoutParams params = (android.widget.GridLayout.LayoutParams) binding.btnAdminNotifications.getLayoutParams();
            params.rowSpec = android.widget.GridLayout.spec(1, 1f);
            params.columnSpec = android.widget.GridLayout.spec(1, 1f);
            binding.btnAdminNotifications.setLayoutParams(params);

            // Ẩn tab Thống kê dưới Bottom Nav và căn giữa lại các nút
            binding.navAdminStats.setVisibility(View.GONE);
            binding.layoutAdminBottomNav.setWeightSum(3);
        }
    }
}