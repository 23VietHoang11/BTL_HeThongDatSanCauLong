package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.btl_hethongdatsancaulong.adapters.AdminBookingAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminBookingBinding;
import com.example.btl_hethongdatsancaulong.models.AdminBooking;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminBookingActivity extends AppCompatActivity {

    private ActivityAdminBookingBinding binding;
    private AdminBookingAdapter adapter;

    // Tạo 2 danh sách: Gốc (chứa tất cả) và Hiển thị (để vẽ lên màn hình tùy theo Tab)
    private List<AdminBooking> danhSachGoc;
    private List<AdminBooking> danhSachHienThi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackAdminBooking.setOnClickListener(v -> finish());

        danhSachGoc = new ArrayList<>();
        danhSachHienThi = new ArrayList<>();

        adapter = new AdminBookingAdapter(danhSachHienThi);
        binding.recyclerViewAdminBooking.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewAdminBooking.setAdapter(adapter);

        // Kéo dữ liệu Realtime từ Firebase
        loadBookingsFromFirebase();

        // LẮNG NGHE SỰ KIỆN KHI ADMIN CHUYỂN TAB
        binding.tabLayoutBooking.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterData(tab.getPosition()); // Lọc dữ liệu theo tab
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                filterData(tab.getPosition());
            }
        });
    }

    private void loadBookingsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Bookings");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhSachGoc.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String maDon = data.child("maDon").getValue(String.class);
                    String tenKhachHang = data.child("tenKhachHang").getValue(String.class);
                    String soDienThoai = data.child("soDienThoai").getValue(String.class);
                    String tenSan = data.child("tenSan").getValue(String.class);
                    String thoiGian = data.child("thoiGian").getValue(String.class);
                    String tongTien = data.child("tongTien").getValue(String.class);
                    String trangThai = data.child("trangThai").getValue(String.class);

                    if (maDon != null) {
                        danhSachGoc.add(new AdminBooking(maDon, tenKhachHang, soDienThoai, tenSan, thoiGian, tongTien, trangThai));
                    }
                }

                // Đảo ngược danh sách để đơn MỚI NHẤT luôn hiện lên trên cùng
                Collections.reverse(danhSachGoc);

                // Lọc dữ liệu hiển thị theo cái Tab đang chọn hiện tại
                int currentTab = binding.tabLayoutBooking.getSelectedTabPosition();
                filterData(currentTab != -1 ? currentTab : 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminBookingActivity.this, "Lỗi tải đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // HÀM LỌC DỮ LIỆU THÔNG MINH (3 TAB)
    private void filterData(int tabIndex) {
        danhSachHienThi.clear();

        for (AdminBooking don : danhSachGoc) {
            if (tabIndex == 0 && "Chờ duyệt".equals(don.getTrangThai())) {
                // Tab 0: Chỉ lấy đơn Chờ duyệt
                danhSachHienThi.add(don);
            } else if (tabIndex == 1 && "Đã xác nhận".equals(don.getTrangThai())) {
                // Tab 1: Chỉ lấy đơn Đã duyệt (Trên Firebase lưu là "Đã xác nhận")
                danhSachHienThi.add(don);
            } else if (tabIndex == 2 && "Đã hủy".equals(don.getTrangThai())) {
                // Tab 2: Chỉ lấy đơn Đã bị từ chối/hủy
                danhSachHienThi.add(don);
            }
        }

        // Cập nhật lại giao diện ngay lập tức
        adapter.notifyDataSetChanged();
    }
}