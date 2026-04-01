package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminPosBinding;
import com.example.btl_hethongdatsancaulong.models.ServiceItem;
import java.util.ArrayList;
import java.util.List;

public class AdminPosActivity extends AppCompatActivity {

    private ActivityAdminPosBinding binding;
    private List<ServiceItem> menuBanHang;
    private AdminServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackPos.setOnClickListener(v -> finish());

        // 1. Lên Menu các mặt hàng (CÓ KÈM ẢNH)
        menuBanHang = new ArrayList<>();
        menuBanHang.add(new ServiceItem("Nước Lọc Aquafina", 10000, R.drawable.nuoc));
        menuBanHang.add(new ServiceItem("Revive Chanh Muối", 15000, R.drawable.revine));
        menuBanHang.add(new ServiceItem("Bò Húc (Redbull)", 20000, R.drawable.bo_huc));
        menuBanHang.add(new ServiceItem("Trà Đá (Ca)", 10000, R.drawable.tra_da));
        menuBanHang.add(new ServiceItem("Ống Cầu Lông Vinastar", 180000, R.drawable.ong_cau));
        menuBanHang.add(new ServiceItem("Thuê Vợt (Buổi)", 30000, R.drawable.vot));

        // 2. Setup Adapter và nhận tín hiệu (Callback)
        adapter = new AdminServiceAdapter(menuBanHang, new AdminServiceAdapter.OnCartChangeListener() {
            @Override
            public void onCartChanged() {
                tinhTongTien();
            }
        });

        binding.recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewServices.setAdapter(adapter);

        // 3. Xử lý nút Lên Đơn
        binding.btnCreateOrder.setOnClickListener(v -> {
            int tongTien = tinhTongTien();
            if (tongTien == 0) {
                Toast.makeText(this, "Chưa chọn mặt hàng nào!", Toast.LENGTH_SHORT).show();
            } else {
                // ĐẨY ĐƠN HÀNG LÊN FIREBASE
                com.google.firebase.database.FirebaseDatabase database = com.google.firebase.database.FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app");
                com.google.firebase.database.DatabaseReference myRef = database.getReference("Bookings");

                String maDon = myRef.push().getKey();
                java.util.HashMap<String, Object> donHang = new java.util.HashMap<>();
                donHang.put("maDon", maDon);
                donHang.put("tenKhachHang", "Khách mua tại quầy POS");
                donHang.put("soDienThoai", "N/A");
                donHang.put("tenSan", "Dịch vụ ăn uống / Phụ kiện");
                donHang.put("thoiGian", "Mua trực tiếp");
                donHang.put("tongTien", tongTien + "đ");
                donHang.put("trangThai", "Đã xác nhận");

                if (maDon != null) {
                    myRef.child(maDon).setValue(donHang);
                }

                Toast.makeText(this, "🎉 Lên đơn thành công! Đã cộng vào doanh thu", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    // Hàm tính toán tổng tiền
    private int tinhTongTien() {
        int tong = 0;
        for (ServiceItem item : menuBanHang) {
            tong += (item.getGiaTien() * item.getSoLuong());
        }
        binding.tvPosTotalPrice.setText(String.format("%,d đ", tong));
        return tong;
    }
}