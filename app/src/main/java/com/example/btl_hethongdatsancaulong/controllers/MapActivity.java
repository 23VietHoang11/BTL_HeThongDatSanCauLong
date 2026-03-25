package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// Nhớ import mấy thư viện của Google Map này nhé
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.databinding.ActivityMapBinding;

// Thêm cái implements OnMapReadyCallback vào đuôi class
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityMapBinding binding;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi động bản đồ ngầm
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Nút Back
        binding.btnMapLayer.setOnClickListener(v -> finish());

        // Xử lý nút Trang chủ ở Bottom Navigation
        binding.navHome.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, MainHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        binding.navMap.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, MapActivity.class);
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
            Intent intent = new Intent(MapActivity.this, FeaturedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        // 5. Nút Tài khoản
        binding.navAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, AccountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });
    }

    // Hàm này tự động chạy khi bản đồ đã load xong
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // 1. Tạo tọa độ (Kinh độ, Vĩ độ) cho 3 sân bóng ở Hà Nội
        LatLng sanSuPham = new LatLng(21.0365, 105.7818);
        LatLng sanXuanThuy = new LatLng(21.0375, 105.7828);
        LatLng sanMyDinh = new LatLng(21.0223, 105.7628);

        // 2. Cắm cờ (Marker) lên bản đồ
        mMap.addMarker(new MarkerOptions().position(sanSuPham).title("Sân Cầu Lông ĐHSP"));
        mMap.addMarker(new MarkerOptions().position(sanXuanThuy).title("Sân Cầu Lông Xuân Thủy"));
        mMap.addMarker(new MarkerOptions().position(sanMyDinh).title("Sân Cầu Lông Mỹ Đình"));

        // 3. Di chuyển Camera (Góc nhìn) về khu vực Cầu Giấy và zoom lên (mức 14f)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanSuPham, 14f));
    }
}