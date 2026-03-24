package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_hethongdatsancaulong.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- THÊM 3 DÒNG NÀY ĐỂ CHUYỂN SANG MÀN HÌNH LOGIN ---
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đóng luôn MainActivity để ấn nút Back không bị quay lại màn trắng này nữa
        // -----------------------------------------------------

    }
}