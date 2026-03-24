package com.example.btl_hethongdatsancaulong.controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.R;
public class BookingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        // Nút Back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        // Nút XÁC NHẬN & THANH TOÁN
        TextView btnConfirmPay = findViewById(R.id.btnConfirmPay);
        btnConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị thông báo đặt sân thành công
                Toast.makeText(BookingDetailActivity.this, "Đặt sân thành công!", Toast.LENGTH_SHORT).show();

                // (Tùy chọn) Quay thẳng về trang chủ sau khi đặt xong
                // Intent intent = new Intent(BookingDetailActivity.this, MainHomeActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                // startActivity(intent);
                // finish();
            }
        });
    }
}