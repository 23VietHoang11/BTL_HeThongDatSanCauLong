package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminStatsBinding;

public class AdminStatsActivity extends AppCompatActivity {

    private ActivityAdminStatsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút Back
        binding.btnBackStats.setOnClickListener(v -> finish());

        // (Sau này khi có Firebase, bạn sẽ dùng code ở đây để kéo dữ liệu thật về
        // rồi dùng hàm binding.tvTotalRevenue.setText(...) để hiển thị lên,
        // còn bây giờ giao diện XML đã chứa sẵn số liệu Demo cực đẹp rồi).
    }
}