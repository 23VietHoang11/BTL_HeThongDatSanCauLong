package com.example.btl_hethongdatsancaulong.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.databinding.ActivityChooseBookingBinding;
import com.example.btl_hethongdatsancaulong.databinding.ActivityFeaturedBinding;

public class FeaturedActivity extends AppCompatActivity {

    private ActivityFeaturedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeaturedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Gắn sự kiện cho các nút ĐẶT LỊCH NGAY trên banner
        binding.btnBookNowCard1.setOnClickListener(v -> showChooseBookingTypeDialog());
        binding.btnBookNowCard2.setOnClickListener(v -> showChooseBookingTypeDialog());
        binding.btnBookNowCard3.setOnClickListener(v -> showChooseBookingTypeDialog());

        // Xử lý nút Trang chủ ở Bottom Navigation
        binding.navHome.setOnClickListener(v -> {
            Intent intent = new Intent(FeaturedActivity.this, MainHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void showChooseBookingTypeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Dùng ViewBinding cho cả Dialog luôn cho xịn!
        ActivityChooseBookingBinding dialogBinding = ActivityChooseBookingBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        dialogBinding.btnClose.setOnClickListener(v -> dialog.dismiss());

        dialogBinding.cardVisualBooking.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(FeaturedActivity.this, VisualBookingActivity.class);
            startActivity(intent);
        });

        dialog.show();
    }
}