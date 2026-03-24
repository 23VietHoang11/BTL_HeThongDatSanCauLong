package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.R;

public class MainHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        // 1. Chuyển sang màn hình Chi tiết sân khi ấn vào Card sân đầu tiên (Sân ĐHSP)
        // Lưu ý: Bạn cần đặt id cho cái TextView "ĐẶT LỊCH" trong activity_main_home.xml (ví dụ: btnBookHome1)
        TextView btnBookHome1 = findViewById(R.id.btnBookHome1);
        if(btnBookHome1 != null) {
            btnBookHome1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainHomeActivity.this, CourtDetailActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. Chuyển sang màn hình Nổi bật khi ấn vào icon Nổi bật ở Bottom Nav
        // Bạn cần đặt ID cho cái LinearLayout chứa icon Nổi bật (ví dụ: navFeatured)
        LinearLayout navFeatured = findViewById(R.id.navFeatured);
        if(navFeatured != null) {
            navFeatured.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainHomeActivity.this, FeaturedActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 3. Chuyển sang màn hình Đăng nhập (hoặc Tài khoản) khi ấn vào icon Tài khoản
        LinearLayout navAccount = findViewById(R.id.navAccount);
        if (navAccount != null) {
            navAccount.setOnClickListener(v -> {
                Intent intent = new Intent(MainHomeActivity.this, LoginActivity.class);
                startActivity(intent);
            });
        }
    }
}