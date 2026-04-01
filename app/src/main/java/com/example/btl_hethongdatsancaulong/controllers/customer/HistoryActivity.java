package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.adapters.HistoryAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityHistoryBinding;
import com.example.btl_hethongdatsancaulong.models.LichDat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private HistoryAdapter adapter;
    private List<LichDat> danhSachLich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackHistory.setOnClickListener(v -> finish());

        danhSachLich = new ArrayList<>();
        adapter = new HistoryAdapter(danhSachLich);
        binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewHistory.setAdapter(adapter);

        // Kéo dữ liệu thật từ Firebase
        loadHistoryFromFirebase();
    }

    private void loadHistoryFromFirebase() {
        // ⚠️ Lấy SĐT của khách đang dùng app
        android.content.SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String myPhone = sharedPreferences.getString("PHONE", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Bookings");

        myRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull com.google.firebase.database.DataSnapshot snapshot) {
                danhSachLich.clear();
                for (com.google.firebase.database.DataSnapshot data : snapshot.getChildren()) {
                    String phone = data.child("soDienThoai").getValue(String.class);

                    // ⚠️ ĐIỀU KIỆN VÀNG: Nếu SĐT trên đơn trùng với SĐT máy thì mới hiển thị
                    if (phone != null && phone.equals(myPhone)) {
                        String tenSan = data.child("tenSan").getValue(String.class);
                        String thoiGian = data.child("thoiGian").getValue(String.class);
                        String tongTien = data.child("tongTien").getValue(String.class);
                        String trangThai = data.child("trangThai").getValue(String.class);

                        String ngayDat = "Hôm nay";
                        if(thoiGian != null && thoiGian.contains("|")) {
                            ngayDat = thoiGian.split("\\|")[1].trim();
                        }

                        if (tenSan != null) {
                            danhSachLich.add(new LichDat(tenSan, ngayDat, thoiGian, tongTien, trangThai));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull com.google.firebase.database.DatabaseError error) {
                Toast.makeText(HistoryActivity.this, "Lỗi tải lịch sử!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}