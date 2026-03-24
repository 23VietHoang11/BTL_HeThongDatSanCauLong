package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút Đăng nhập
        binding.btnLogin.setOnClickListener(v -> handleLogin());

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