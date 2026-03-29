package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.adapters.AdminUserAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminUserBinding;
import com.example.btl_hethongdatsancaulong.models.AdminUser;
import java.util.ArrayList;
import java.util.List;

public class AdminUserActivity extends AppCompatActivity {

    private ActivityAdminUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackUsers.setOnClickListener(v -> finish());

        // Tạo dữ liệu Demo
        List<AdminUser> danhSachKhach = new ArrayList<>();
        danhSachKhach.add(new AdminUser("Bùi Ngọc Hân", "0987654321", "10.000.000 đ", "Hoạt động"));
        danhSachKhach.add(new AdminUser("Bùi Ngọc Diệp", "0912345678", "2.500.000 đ", "Sắp hết hạn"));
        danhSachKhach.add(new AdminUser("Hoàng Thanh Hằng", "0988888888", "15.000.000 đ", "Hoạt động"));
        danhSachKhach.add(new AdminUser("Trần Văn Khách", "0333444555", "500.000 đ", "Ngừng h.động"));

        AdminUserAdapter adapter = new AdminUserAdapter(danhSachKhach);
        binding.recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewUsers.setAdapter(adapter);
    }
}