package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityFeaturedDetailBinding;

public class FeaturedDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFeaturedDetailBinding binding = ActivityFeaturedDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackDetail.setOnClickListener(v -> finish());

        // Nhận dữ liệu
        String title = getIntent().getStringExtra("TITLE");
        String content = getIntent().getStringExtra("CONTENT");
        String date = getIntent().getStringExtra("DATE");

        // Hiển thị
        binding.tvDetailTitle.setText(title);
        binding.tvDetailDate.setText(date);
        binding.tvDetailContent.setText(content);
    }
}