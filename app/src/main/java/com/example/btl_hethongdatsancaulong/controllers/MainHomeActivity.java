package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityMainHomeBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.adapters.VenueAdapter;
import com.example.btl_hethongdatsancaulong.models.SanThiDau;
import java.util.ArrayList;
import java.util.List;

public class MainHomeActivity extends AppCompatActivity {

    private ActivityMainHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // --- KHỞI TẠO RECYCLERVIEW ---
        // 1. Tạo dữ liệu giả (Mock data)
        List<SanThiDau> danhSachSan = new ArrayList<>();
        danhSachSan.add(new SanThiDau("Sân Cầu Lông Đại Học Sư Phạm", "(357.4m) 136 Xuân Thủy, Cầu Giấy...", "05:30 - 23:30"));
        danhSachSan.add(new SanThiDau("Sân Cầu Lông Xuân Thủy", "(358.0m) Ngõ 181 Xuân Thủy, Cầu Giấy...", "06:00 - 22:30"));
        danhSachSan.add(new SanThiDau("Sân Cầu Lông Mỹ Đình", "(2.1km) Lê Đức Thọ, Nam Từ Liêm...", "06:00 - 23:00"));

        // 2. Setup Adapter và xử lý sự kiện click
        VenueAdapter adapter = new VenueAdapter(danhSachSan, san -> {
            // Khi bấm nút ĐẶT LỊCH trên bất kỳ sân nào, nó sẽ bay sang trang Chi tiết
            Intent intent = new Intent(MainHomeActivity.this, CourtDetailActivity.class);
            // Bạn có thể gửi thêm tên sân sang trang chi tiết bằng lệnh:
            // intent.putExtra("TEN_SAN", san.getTenSan());
            startActivity(intent);
        });

        // 3. Gắn Adapter vào RecyclerView
        binding.recyclerViewVenues.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewVenues.setAdapter(adapter);

// --- XỬ LÝ BOTTOM NAVIGATION ---

        // 1. Nút Trang chủ (Đang ở trang chủ rồi nên không cần làm gì, hoặc reresh dữ liệu)

        // 2. Nút Bản đồ
        binding.navMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainHomeActivity.this, MapActivity.class);
            // Dùng cờ này để chuyển tab mượt mà, không bị hiệu ứng trượt như mở trang mới
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // 3. Nút Khám phá (Nút giữa) - Tạm thời hiển thị thông báo
        binding.navExplore.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Tính năng ghép kèo đang phát triển!", android.widget.Toast.LENGTH_SHORT).show();
        });

        // 4. Nút Nổi bật
        binding.navFeatured.setOnClickListener(v -> {
            Intent intent = new Intent(MainHomeActivity.this, FeaturedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // 5. Nút Tài khoản
        binding.navAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainHomeActivity.this, AccountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });


    }
}