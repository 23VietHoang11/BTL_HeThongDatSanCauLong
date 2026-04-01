package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.adapters.AdminCourtAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminCourtBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AdminCourtActivity extends AppCompatActivity {

    private ActivityAdminCourtBinding binding;
    private AdminCourtAdapter adapter;
    private List<AdminCourt> danhSachSan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCourtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackAdminCourt.setOnClickListener(v -> finish());

        danhSachSan = new ArrayList<>();
        adapter = new AdminCourtAdapter(this, danhSachSan);
        binding.recyclerViewAdminCourt.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewAdminCourt.setAdapter(adapter);

        // GỌI HÀM LẤY DỮ LIỆU TỪ FIREBASE
        loadCourtsFromFirebase();

        String role = getIntent().getStringExtra("ROLE");
        if ("STAFF".equals(role)) {
            binding.fabAddCourt.setVisibility(android.view.View.GONE);
        } else {
            binding.fabAddCourt.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(AdminCourtActivity.this, AdminAddCourtActivity.class);
                startActivity(intent);
            });
        }
    }

    private void loadCourtsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Courts");

        // Lắng nghe dữ liệu realtime
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhSachSan.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.child("id").getValue(String.class);
                    String tenSan = data.child("tenSan").getValue(String.class);
                    String giaTien = data.child("giaTien").getValue(String.class);
                    String trangThai = data.child("trangThai").getValue(String.class);

                    if (id != null && tenSan != null) {
                        // --- THUẬT TOÁN TỰ ĐỘNG CHỌN ẢNH TĨNH ---
                        int[] danhSachAnh = {
                                R.drawable.san1,
                                R.drawable.san2,
                                R.drawable.san3
                        };
                        int indexAnh = Math.abs(tenSan.hashCode()) % danhSachAnh.length;
                        int anhDuocChon = danhSachAnh[indexAnh];

                        danhSachSan.add(new AdminCourt(id, tenSan, giaTien != null ? giaTien : "0đ", trangThai != null ? trangThai : "Đang hoạt động", anhDuocChon));
                    }
                }
                adapter.notifyDataSetChanged(); // Báo cho RecyclerView vẽ lại giao diện
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminCourtActivity.this, "Lỗi tải dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}