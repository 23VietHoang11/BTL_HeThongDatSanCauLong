package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.adapters.AdminCourtAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminCourtBinding;
import com.example.btl_hethongdatsancaulong.models.AdminCourt;
import java.util.ArrayList;
import java.util.List;

public class AdminCourtActivity extends AppCompatActivity {

    private ActivityAdminCourtBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCourtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Nút Back
        binding.btnBackAdminCourt.setOnClickListener(v -> finish());

        // 2. Tạo dữ liệu giả lập các sân đang có trong hệ thống
        List<AdminCourt> danhSachSan = new ArrayList<>();
        danhSachSan.add(new AdminCourt("S01", "Sân Cầu Lông ĐHSP", "180.000đ", "Đang hoạt động"));
        danhSachSan.add(new AdminCourt("S02", "Sân Cầu Lông Xuân Thủy", "200.000đ", "Đang hoạt động"));
        danhSachSan.add(new AdminCourt("S03", "Sân Cầu Lông Mỹ Đình", "250.000đ", "Đang bảo trì"));
        danhSachSan.add(new AdminCourt("S04", "Sân Cầu Lông Cầu Giấy", "150.000đ", "Đang hoạt động"));

        // 3. Đổ dữ liệu vào Adapter
        AdminCourtAdapter adapter = new AdminCourtAdapter(this, danhSachSan);
        binding.recyclerViewAdminCourt.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewAdminCourt.setAdapter(adapter);

        // 4. Xử lý nút Thêm sân mới (FAB)
        binding.fabAddCourt.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(AdminCourtActivity.this, AdminAddCourtActivity.class);
            startActivity(intent);
        });

        // Kiểm tra quyền: Nếu là STAFF thì ẩn nút Thêm Sân (FAB)
        String role = getIntent().getStringExtra("ROLE");
        if ("STAFF".equals(role)) {
            binding.fabAddCourt.setVisibility(android.view.View.GONE);
        } else {
            // Còn nếu là ADMIN thì vẫn hoạt động bình thường
            binding.fabAddCourt.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(AdminCourtActivity.this, AdminAddCourtActivity.class);
                startActivity(intent);
            });
        }
    }
}