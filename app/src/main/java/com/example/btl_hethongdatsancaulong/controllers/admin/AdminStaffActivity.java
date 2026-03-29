package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.adapters.AdminStaffAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminStaffBinding;
import com.example.btl_hethongdatsancaulong.models.AdminStaff;
import java.util.ArrayList;
import java.util.List;

public class AdminStaffActivity extends AppCompatActivity {

    private ActivityAdminStaffBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackStaff.setOnClickListener(v -> finish());

        List<AdminStaff> danhSachNhanVien = new ArrayList<>();
        danhSachNhanVien.add(new AdminStaff("Lê Thu ngân", "0911222333", "Full-time Sáng (06:00 - 14:00)",5000000));
        danhSachNhanVien.add(new AdminStaff("Trần Bảo Vệ", "0944555666", "Full-time Chiều (14:00 - 22:00)",4000000));
        danhSachNhanVien.add(new AdminStaff("Phạm Quản Lý", "0977888999", "Part-time Sáng (06:00 - 10:00)",8000000));

        AdminStaffAdapter adapter = new AdminStaffAdapter(this, danhSachNhanVien);
        binding.recyclerViewStaff.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewStaff.setAdapter(adapter);

        binding.fabAddStaff.setOnClickListener(v ->
                Toast.makeText(this, "Mở form thêm Nhân viên mới", Toast.LENGTH_SHORT).show()
        );
    }
}