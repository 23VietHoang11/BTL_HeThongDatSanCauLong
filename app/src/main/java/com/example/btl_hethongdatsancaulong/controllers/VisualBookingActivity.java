package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityVisualBookingBinding;

public class VisualBookingActivity extends AppCompatActivity {

    private ActivityVisualBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVisualBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(VisualBookingActivity.this, BookingDetailActivity.class);
            startActivity(intent);
        });
    }
}