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
            // Giả sử bạn lấy text từ ô nhập Email ra (Nhớ thay đúng ID ô email của bạn nhé)
            String email = binding.edtPhoneLogin.getText().toString().trim();

            if (email.equalsIgnoreCase("123123")) {
                // NẾU GÕ CHỮ "admin" VÀO Ô EMAIL -> MỞ APP CHỦ SÂN
                Toast.makeText(this, "Đăng nhập quyền Quản trị viên", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            } else {
                // NẾU GÕ BẤT CỨ CHỮ GÌ KHÁC (HOẶC ĐỂ TRỐNG) -> MỞ APP KHÁCH HÀNG
                Toast.makeText(this, "Đăng nhập quyền Khách hàng", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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