package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminSettingsBinding;

public class AdminSettingsActivity extends AppCompatActivity {

    private ActivityAdminSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Nút Back (Quay lại Dashboard)
        binding.btnBackSettings.setOnClickListener(v -> finish());

        // 2. Các nút chức năng (Hiện tại Demo bằng Toast)
        binding.btnChangePassword.setOnClickListener(v ->
                Toast.makeText(this, "Mở màn hình Đổi mật khẩu", Toast.LENGTH_SHORT).show()
        );

        binding.btnAppInfo.setOnClickListener(v ->
                Toast.makeText(this, "Ứng dụng Quản lý Sân Cầu Lông - Đồ án 2026", Toast.LENGTH_SHORT).show()
        );

        // 3. Xử lý ĐĂNG XUẤT (Quan trọng)
        binding.btnLogout.setOnClickListener(v -> {
            // Hiển thị thông báo
            Toast.makeText(this, "Đã đăng xuất an toàn", Toast.LENGTH_SHORT).show();

            // Chuyển hướng về màn hình Login và XÓA SẠCH lịch sử trang (tránh ấn nút Back quay lại)
            android.content.Intent intent = new android.content.Intent(AdminSettingsActivity.this, com.example.btl_hethongdatsancaulong.controllers.auth.LoginActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}