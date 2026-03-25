package com.example.btl_hethongdatsancaulong.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityChooseBookingBinding;
import com.example.btl_hethongdatsancaulong.databinding.ActivityHomeBinding;

public class CourtDetailActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lưu ý: Layout của trang này là activity_home.xml
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút Back
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        // Nút Đặt lịch (Góc trên bên phải)
        binding.btnBook.setOnClickListener(v -> showChooseBookingTypeDialog());
    }

    private void showChooseBookingTypeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActivityChooseBookingBinding dialogBinding = ActivityChooseBookingBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        dialogBinding.btnClose.setOnClickListener(v -> dialog.dismiss());

        dialogBinding.cardVisualBooking.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(CourtDetailActivity.this, VisualBookingActivity.class);
            startActivity(intent);
        });

        dialog.show();
    }
}