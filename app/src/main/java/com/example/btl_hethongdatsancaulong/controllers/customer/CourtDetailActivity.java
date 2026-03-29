package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityChooseBookingBinding;
import com.example.btl_hethongdatsancaulong.databinding.ActivityHomeBinding;

public class CourtDetailActivity extends AppCompatActivity {
    private String tenSan = "";
    private String diaChi = "";
    private String thoiGian = "";
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // --- NHẬN DỮ LIỆU VÀ ĐỔ LÊN GIAO DIỆN ---
        Intent intent = getIntent();
        if (intent != null) {
            tenSan = intent.getStringExtra("TEN_SAN");
            diaChi = intent.getStringExtra("DIA_CHI");
            thoiGian = intent.getStringExtra("THOI_GIAN");

            // Cập nhật ViewBinding (đảm bảo bạn đã thêm ID ở bước 4.1 nhé)
            if (tenSan != null) binding.tvDetailName.setText(tenSan);
            if (diaChi != null) binding.tvDetailAddress.setText(diaChi);
            if (thoiGian != null) binding.tvDetailTime.setText(thoiGian);
        }

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
            intent.putExtra("TEN_SAN", tenSan);
            intent.putExtra("DIA_CHI", diaChi);
            intent.putExtra("THOI_GIAN", thoiGian);
            startActivity(intent);
        });

        dialog.show();
    }
}