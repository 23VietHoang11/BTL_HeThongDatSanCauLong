package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.adapters.AdminBookingAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminBookingBinding;
import com.example.btl_hethongdatsancaulong.models.AdminBooking;
import java.util.ArrayList;
import java.util.List;

public class AdminBookingActivity extends AppCompatActivity {

    private ActivityAdminBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút Back
        binding.btnBackAdminBooking.setOnClickListener(v -> finish());

        // Tạo dữ liệu giả lập có khách hàng đặt sân
        List<AdminBooking> danhSachDon = new ArrayList<>();
        danhSachDon.add(new AdminBooking("#DH01", "Bế Việt Hoàng", "0987654321", "Sân Cầu Lông ĐHSP - Sân 1", "18:00 - 20:00 | 29/03/2026", "200.000đ", "Chờ duyệt"));
        danhSachDon.add(new AdminBooking("#DH02", "Nguyễn Văn A", "0912345678", "Sân Cầu Lông Xuân Thủy - Sân VIP", "19:00 - 21:00 | 29/03/2026", "350.000đ", "Chờ duyệt"));
        danhSachDon.add(new AdminBooking("#DH03", "Trần Thị B", "0988888888", "Sân Cầu Lông Mỹ Đình - Sân 3", "06:00 - 08:00 | 30/03/2026", "150.000đ", "Đã xác nhận"));

        // Đổ dữ liệu vào Adapter
        AdminBookingAdapter adapter = new AdminBookingAdapter(danhSachDon);
        binding.recyclerViewAdminBooking.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewAdminBooking.setAdapter(adapter);
    }
}