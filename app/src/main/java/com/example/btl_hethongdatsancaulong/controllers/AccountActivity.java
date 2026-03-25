package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
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

//        // Xử lý nút Đăng nhập / Đăng ký trong trang tài khoản
        binding.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        binding.navHome.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MainHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        //2
        binding.navMap.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MapActivity.class);
            // Dùng cờ này để chuyển tab mượt mà, không bị hiệu ứng trượt như mở trang mới
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // 3. Nút Khám phá (Nút giữa) - Tạm thời hiển thị thông báo
        binding.navExplore.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Tính năng ghép kèo đang phát triển!", android.widget.Toast.LENGTH_SHORT).show();
        });

        // 4. Nút Nổi bật
        binding.navFeatured.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, FeaturedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // 5. Nút Tài khoản
        binding.navAccount.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
    }
}