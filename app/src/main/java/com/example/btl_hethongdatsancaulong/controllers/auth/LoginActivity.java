package com.example.btl_hethongdatsancaulong.controllers.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_hethongdatsancaulong.controllers.admin.AdminDashboardActivity;
import com.example.btl_hethongdatsancaulong.controllers.customer.MainHomeActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút Đăng nhập
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.edtPhoneLogin.getText().toString().trim();

            if (email.equalsIgnoreCase("111")) {
                // 1. Luồng của CHỦ SÂN
                Toast.makeText(this, "Đăng nhập quyền Quản trị viên", Toast.LENGTH_SHORT).show();
                android.content.Intent intent = new android.content.Intent(LoginActivity.this, com.example.btl_hethongdatsancaulong.controllers.admin.AdminDashboardActivity.class);
                intent.putExtra("ROLE", "ADMIN"); // Gắn thẻ ADMIN
                startActivity(intent);
                finish();

            } else if (email.equalsIgnoreCase("222")) {
                // 2. Luồng của NHÂN VIÊN
                Toast.makeText(this, "Đăng nhập ca trực Nhân viên", Toast.LENGTH_SHORT).show();
                android.content.Intent intent = new android.content.Intent(LoginActivity.this, com.example.btl_hethongdatsancaulong.controllers.admin.AdminDashboardActivity.class);
                intent.putExtra("ROLE", "STAFF"); // Gắn thẻ STAFF
                startActivity(intent);
                finish();

            } else {
                // 3. Luồng của KHÁCH HÀNG (Dành cho tất cả các số điện thoại hoặc tài khoản khác)
                Toast.makeText(this, "Xin chào Khách hàng!", Toast.LENGTH_SHORT).show();
                android.content.Intent intent = new android.content.Intent(LoginActivity.this, com.example.btl_hethongdatsancaulong.controllers.customer.MainHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Nút chuyển sang trang Đăng ký
        binding.tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Nút Back (Thoát app hoặc về trang trước)
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void handleLogin() {
        // Lấy dữ liệu (Chưa cần check pass vội, cứ điền là cho qua để test luồng)
        String phone = binding.edtPhoneLogin.getText() != null ? binding.edtPhoneLogin.getText().toString().trim() : "";
        String password = binding.edtPasswordLogin.getText() != null ? binding.edtPasswordLogin.getText().toString().trim() : "";

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại và mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

        // CHUYỂN VÀO TRANG CHỦ
        Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
        startActivity(intent);
        finish(); // Đóng trang Đăng nhập lại để người dùng ấn Back không bị quay lại đây
    }
}