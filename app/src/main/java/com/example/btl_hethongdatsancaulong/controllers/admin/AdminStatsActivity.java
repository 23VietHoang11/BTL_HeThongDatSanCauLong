package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminStatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminStatsActivity extends AppCompatActivity {

    private ActivityAdminStatsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackStats.setOnClickListener(v -> finish());

        // Gọi hàm phân tích toàn bộ dữ liệu
        loadFullStatsFromFirebase();
    }

    private void loadFullStatsFromFirebase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Bookings");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Biến đếm Doanh thu
                long doanhThuSan = 0;
                long doanhThuPos = 0;

                // Biến đếm Đơn hàng
                int tongSoDon = 0;
                int donThanhCong = 0;
                int donDaHuy = 0;

                for (DataSnapshot data : snapshot.getChildren()) {
                    tongSoDon++; // Cứ quét qua 1 đơn là cộng vào Tổng số đơn

                    String trangThai = data.child("trangThai").getValue(String.class);
                    String tienStr = data.child("tongTien").getValue(String.class);
                    String tenSan = data.child("tenSan").getValue(String.class);

                    // Phân loại đơn hàng
                    if ("Đã xác nhận".equals(trangThai)) {
                        donThanhCong++; // Cộng 1 đơn thành công

                        // Xử lý cộng tiền
                        if (tienStr != null) {
                            try {
                                long soTien = Long.parseLong(tienStr.replaceAll("[^0-9]", ""));

                                // Phân loại nguồn thu (Từ App hay từ quầy POS)
                                if (tenSan != null && tenSan.contains("Dịch vụ")) {
                                    doanhThuPos += soTien;
                                } else {
                                    doanhThuSan += soTien;
                                }
                            } catch (Exception ignored) {}
                        }
                    } else if ("Đã hủy".equals(trangThai)) {
                        donDaHuy++; // Cộng 1 đơn đã hủy
                    }
                }

                // 1. TỔNG HỢP VÀ HIỂN THỊ DOANH THU
                long tongDoanhThu = doanhThuSan + doanhThuPos;
                binding.tvRevenueCourts.setText(String.format("%,d", doanhThuSan));
                binding.tvRevenuePos.setText(String.format("%,d", doanhThuPos));
                binding.tvTotalRevenue.setText(String.format("%,d đ", tongDoanhThu));

                // Cập nhật thanh ProgressBar Tỷ trọng
                if (tongDoanhThu > 0) {
                    int phanTramSan = (int) ((doanhThuSan * 100) / tongDoanhThu);
                    binding.progressBarRevenue.setProgress(phanTramSan);
                } else {
                    binding.progressBarRevenue.setProgress(0);
                }

                // 2. HIỂN THỊ THỐNG KÊ CHI TIẾT ĐƠN HÀNG
                binding.tvTotalOrders.setText(String.valueOf(tongSoDon));
                binding.tvSuccessOrders.setText(String.valueOf(donThanhCong));
                binding.tvCanceledOrders.setText(String.valueOf(donDaHuy));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminStatsActivity.this, "Lỗi kết nối máy chủ!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}