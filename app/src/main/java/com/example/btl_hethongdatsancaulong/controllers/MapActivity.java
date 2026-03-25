package com.example.btl_hethongdatsancaulong.controllers;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityMapBinding;

public class MapActivity extends AppCompatActivity {
    private ActivityMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút Back
        binding.btnMapLayer.setOnClickListener(v -> finish());
    }
}