package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.btl_hethongdatsancaulong.adapters.AdminUserAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminUserBinding;
import com.example.btl_hethongdatsancaulong.models.AdminUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminUserActivity extends AppCompatActivity {

    private ActivityAdminUserBinding binding;
    private AdminUserAdapter adapter;
    private List<AdminUser> danhSachKhach;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo ViewBinding
        binding = ActivityAdminUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút quay lại
        binding.btnBackUsers.setOnClickListener(v -> finish());

        // Thiết lập Adapter cho RecyclerView
        danhSachKhach = new ArrayList<>();
        adapter = new AdminUserAdapter(danhSachKhach);
        binding.recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewUsers.setAdapter(adapter);

        // Kết nối Firebase
        mDatabase = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        // Bắt đầu tải dữ liệu khách hàng
        loadUsersAndCalculateSpent();
    }

    private void loadUsersAndCalculateSpent() {
        // Quét nhánh "Users" (Danh sách đăng ký tài khoản)
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotUsers) {
                danhSachKhach.clear();

                // Quét tiếp nhánh "Bookings" (Đơn đặt sân) để tính tiền
                mDatabase.child("Bookings").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotBookings) {
                        int countTotal = 0;
                        int countActive = 0;

                        // Chạy vòng lặp qua từng người dùng
                        for (DataSnapshot userSnap : snapshotUsers.getChildren()) {
                            String hoTen = userSnap.child("hoTen").getValue(String.class);
                            String sdt = userSnap.child("soDienThoai").getValue(String.class);

                            // Giả sử các tài khoản đăng ký xong đều đang hoạt động
                            String trangThaiTaiKhoan = "Hoạt động";

                            if (hoTen != null && sdt != null) {
                                countTotal++; // Tăng biến đếm tổng hội viên
                                if ("Hoạt động".equals(trangThaiTaiKhoan)) {
                                    countActive++; // Tăng biến đếm số tài khoản đang active
                                }

                                long tongChiTieu = 0;

                                // Chạy vòng lặp kiểm tra xem người này đặt những đơn nào
                                for (DataSnapshot bookSnap : snapshotBookings.getChildren()) {
                                    String phoneDatSan = bookSnap.child("soDienThoai").getValue(String.class);
                                    String trangThaiDon = bookSnap.child("trangThai").getValue(String.class);

                                    // Chỉ tính tiền các đơn thành công của đúng SĐT này
                                    if (sdt.equals(phoneDatSan) && "Đã xác nhận".equals(trangThaiDon)) {
                                        String tienStr = bookSnap.child("tongTien").getValue(String.class);
                                        if (tienStr != null) {
                                            try {
                                                // Lọc bỏ ký tự thừa (đ, dấu chấm) để cộng dồn
                                                tongChiTieu += Long.parseLong(tienStr.replaceAll("[^0-9]", ""));
                                            } catch (Exception ignored) {}
                                        }
                                    }
                                }

                                // Format số tiền để hiển thị lên thẻ
                                String formatTien = (tongChiTieu == 0) ? "Chưa chi tiêu" : String.format("%,d đ", tongChiTieu);

                                // Thêm khách hàng vào danh sách
                                danhSachKhach.add(new AdminUser(hoTen, sdt, formatTien, trangThaiTaiKhoan));
                            }
                        }

                        // --- CẬP NHẬT 2 CON SỐ LÊN GIAO DIỆN ---
                        if (binding.tvTotalMembers != null && binding.tvActiveMembers != null) {
                            binding.tvTotalMembers.setText(String.valueOf(countTotal));
                            binding.tvActiveMembers.setText(String.valueOf(countActive));
                        }

                        // Báo cho Adapter vẽ lại danh sách
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminUserActivity.this, "Lỗi kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}