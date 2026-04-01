package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.databinding.ActivityMapBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityMapBinding binding;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.btnMapLayer.setOnClickListener(v -> finish());
        binding.navHome.setOnClickListener(v -> finish());
        binding.navMap.setOnClickListener(v -> {}); // Đang ở Map rồi
        binding.navExplore.setOnClickListener(v -> Toast.makeText(this, "Hãy đến tận nơi để khám phá nhé!", Toast.LENGTH_SHORT).show());
        binding.navFeatured.setOnClickListener(v -> {
            startActivity(new Intent(MapActivity.this, FeaturedActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            finish();
        });
        binding.navAccount.setOnClickListener(v -> {
            startActivity(new Intent(MapActivity.this, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            finish();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Đặt góc nhìn mặc định ở trung tâm Hà Nội
        LatLng trungTamHanoi = new LatLng(21.0285, 105.8048);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trungTamHanoi, 12f));

        // TỰ ĐỘNG QUÉT FIREBASE VÀ CẮM CỜ CÁC SÂN ĐANG CÓ
        FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Courts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        double latOffset = 0.0; // Biến tấu tọa độ để các cờ không đè lên nhau
                        double lngOffset = 0.0;

                        for (DataSnapshot data : snapshot.getChildren()) {
                            String tenSan = data.child("tenSan").getValue(String.class);
                            if (tenSan != null) {
                                // Rải ngẫu nhiên tọa độ các sân quanh khu vực trung tâm
                                LatLng viTriSan = new LatLng(21.0285 + latOffset, 105.8048 + lngOffset);
                                mMap.addMarker(new MarkerOptions().position(viTriSan).title(tenSan));

                                // Dịch chuyển vị trí cho sân tiếp theo
                                latOffset += 0.015;
                                lngOffset += 0.010;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}