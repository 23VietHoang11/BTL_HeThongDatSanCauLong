package com.example.btl_hethongdatsancaulong.controllers;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityBookingDetailBinding;

public class BookingDetailActivity extends AppCompatActivity {

    private ActivityBookingDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.btnConfirmPay.setOnClickListener(v -> {
            Toast.makeText(BookingDetailActivity.this, "Đặt sân thành công!", Toast.LENGTH_SHORT).show();
            // Nếu muốn quay về trang chủ, bạn có thể mở comment các dòng Intent ở đây
        });
    }
}