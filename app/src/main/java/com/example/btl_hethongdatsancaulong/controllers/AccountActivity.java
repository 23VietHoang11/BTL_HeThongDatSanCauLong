package com.example.btl_hethongdatsancaulong.controllers;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {
    private ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Xử lý nút Đăng nhập / Đăng ký trong trang tài khoản
        binding.btnLogin.setOnClickListener(v -> finish()); // Quay lại trang Login
    }
}