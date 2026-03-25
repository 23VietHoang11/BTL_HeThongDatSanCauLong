package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_hethongdatsancaulong.R;
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

        Intent intent = getIntent();
        if (intent != null) {
            String tenSan = intent.getStringExtra("TEN_SAN");
            String diaChi = intent.getStringExtra("DIA_CHI");

            TextView tvCheckoutName = findViewById(R.id.tvCheckoutName);
            TextView tvCheckoutAddress = findViewById(R.id.tvCheckoutAddress);

            if (tenSan != null && tvCheckoutName != null) {
                tvCheckoutName.setText("Tên CLB: " + tenSan);
            }
            if (diaChi != null && tvCheckoutAddress != null) {
                // Xóa phần khoảng cách (ví dụ 357.4m) đi cho hóa đơn nó chuyên nghiệp
                String diaChiSach = diaChi.replaceAll("\\(.*?\\)\\s*", "");
                tvCheckoutAddress.setText("Địa chỉ: " + diaChiSach);
            }
        }
    }
}