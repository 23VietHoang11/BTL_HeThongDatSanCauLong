package com.example.btl_hethongdatsancaulong.controllers.customer;

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

        // SỰ KIỆN BẤM NÚT TIẾP THEO
        binding.btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(VisualBookingActivity.this, BookingDetailActivity.class);

            // --- BƯỚC 2: LẤY DỮ LIỆU TỪ TRANG TRƯỚC VÀ TRUYỀN TIẾP SANG TRANG SAU ---
            Intent currentIntent = getIntent();
            if(currentIntent != null) {
                intent.putExtra("TEN_SAN", currentIntent.getStringExtra("TEN_SAN"));
                intent.putExtra("DIA_CHI", currentIntent.getStringExtra("DIA_CHI"));
                intent.putExtra("THOI_GIAN", currentIntent.getStringExtra("THOI_GIAN"));
            }
            // ----------------------------------------------------------------------

            startActivity(intent);
        });
    }
}