package com.example.btl_hethongdatsancaulong.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.btl_hethongdatsancaulong.R;
public class CourtDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Nút Back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        // Nút Đặt lịch (Góc trên bên phải)
        TextView btnBook = findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseBookingTypeDialog();
            }
        });
    }

    // Hàm hiển thị Dialog Chọn hình thức đặt (Dùng chung cho cả FeaturedActivity)
    private void showChooseBookingTypeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_choose_booking); // Layout dialog xanh/hồng
        if (dialog.getWindow() != null) {
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // ✨ THÊM DÒNG NÀY VÀO ĐỂ ÉP DIALOG PHẢI RỘNG RA ✨
            dialog.getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        }

        // Tắt dialog
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> dialog.dismiss());

        // Chọn "Đặt lịch ngày trực quan" (Card màu xanh)
        ConstraintLayout cardVisualBooking = dialog.findViewById(R.id.cardVisualBooking);
        cardVisualBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Ẩn dialog đi
                // Chuyển sang màn hình Bảng Lưới Thời Gian
                Intent intent = new Intent(CourtDetailActivity.this, VisualBookingActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}