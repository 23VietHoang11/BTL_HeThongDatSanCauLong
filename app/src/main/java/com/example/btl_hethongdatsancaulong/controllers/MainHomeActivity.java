package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityMainHomeBinding;

public class MainHomeActivity extends AppCompatActivity {

    private ActivityMainHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Chuyển sang màn hình Chi tiết sân khi ấn vào Card sân đầu tiên
        // (Đảm bảo đã thêm id btnBookHome1 trong XML)
        binding.btnBookHome1.setOnClickListener(v -> {
            Intent intent = new Intent(MainHomeActivity.this, CourtDetailActivity.class);
            startActivity(intent);
        });

        // 2. Chuyển sang màn hình Nổi bật khi ấn vào icon Nổi bật ở Bottom Nav
        // (Đảm bảo đã thêm id navFeatured trong XML)
        binding.navFeatured.setOnClickListener(v -> {
            Intent intent = new Intent(MainHomeActivity.this, FeaturedActivity.class);
            startActivity(intent);
        });

        // 3. Chuyển sang màn hình Đăng nhập (hoặc Tài khoản)
        // (Đảm bảo đã thêm id navAccount trong XML)
        binding.navAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainHomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}