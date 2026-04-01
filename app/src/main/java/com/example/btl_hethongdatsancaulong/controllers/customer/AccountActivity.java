package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_hethongdatsancaulong.controllers.auth.LoginActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- 1. XỬ LÝ NÚT ĐĂNG XUẤT ---
        binding.btnLogout.setOnClickListener(v -> {
            // ⚠️ Xóa trắng thông tin tài khoản khỏi bộ nhớ máy
            android.content.SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();

            Toast.makeText(this, "Đã đăng xuất thành công!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // --- 2. XỬ LÝ NÚT LỊCH ĐÃ ĐẶT ---
        binding.btnBookingHistory.setOnClickListener(v -> {
            // Tạm thời mình để Toast, lát nữa làm HistoryActivity xong mình sẽ mở khóa Intent sau nhé
            Toast.makeText(this, "Đang mở Lịch sử đặt sân...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AccountActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        // --- 3. XỬ LÝ BOTTOM NAVIGATION ---
        // Nút Trang chủ
        binding.navHome.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MainHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // Nút Bản đồ
        binding.navMap.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // Nút Nổi bật
        binding.navFeatured.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, FeaturedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // Nút Tài khoản (Đang ở trang này rồi nên không cần code click)
    }
}