package com.example.btl_hethongdatsancaulong.controllers;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.btl_hethongdatsancaulong.R;
public class FeaturedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);

        // Gắn sự kiện cho các nút ĐẶT LỊCH NGAY trên banner
        View.OnClickListener bookClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseBookingTypeDialog();
            }
        };
        findViewById(R.id.btnBookNowCard1).setOnClickListener(bookClickListener);
        findViewById(R.id.btnBookNowCard2).setOnClickListener(bookClickListener);
        findViewById(R.id.btnBookNowCard3).setOnClickListener(bookClickListener);

        // --- XỬ LÝ BOTTOM NAVIGATION ---
        // Bắt sự kiện khi ấn vào nút "Trang chủ"
        LinearLayout navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(FeaturedActivity.this, MainHomeActivity.class);
                // Dùng cờ CLEAR_TOP để gọi lại Trang chủ cũ đang chạy ngầm,
                // tránh việc mở ra quá nhiều màn hình Trang chủ đè lên nhau gây nặng máy
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish(); // Đóng màn hình Nổi bật đi cho gọn
            });
        }
    }

    private void showChooseBookingTypeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_choose_booking);
        if (dialog.getWindow() != null) {
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        }

        dialog.findViewById(R.id.btnClose).setOnClickListener(v -> dialog.dismiss());

        dialog.findViewById(R.id.cardVisualBooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(FeaturedActivity.this, VisualBookingActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}