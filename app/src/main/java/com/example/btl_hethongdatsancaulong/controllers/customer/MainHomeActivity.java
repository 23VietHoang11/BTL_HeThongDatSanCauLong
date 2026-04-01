package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.adapters.VenueAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityMainHomeBinding;
import com.example.btl_hethongdatsancaulong.models.SanThiDau;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainHomeActivity extends AppCompatActivity {

    private ActivityMainHomeBinding binding;
    private VenueAdapter adapter;

    // Kho chứa 2 danh sách: Gốc (Từ Firebase) và Hiển thị (Sau khi tìm kiếm)
    private List<SanThiDau> danhSachGoc;
    private List<SanThiDau> danhSachHienThi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ==========================================
        // 1. CẬP NHẬT HEADER (TÊN & NGÀY THÁNG)
        // ==========================================
        try {
            // Lấy tên từ bộ nhớ tạm
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String name = prefs.getString("NAME", "Khách hàng");
            if (binding.tvCustomerName != null) {
                binding.tvCustomerName.setText(name);
            }

            // Lấy ngày tháng hiện tại
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
            String currentDate = sdf.format(new Date());
            currentDate = currentDate.substring(0, 1).toUpperCase() + currentDate.substring(1);

            if (binding.tvCurrentDate != null) {
                binding.tvCurrentDate.setText(currentDate);
            }
        } catch (Exception ignored) {}

        // ==========================================
        // 2. KHỞI TẠO RECYCLERVIEW
        // ==========================================
        danhSachGoc = new ArrayList<>();
        danhSachHienThi = new ArrayList<>();

        adapter = new VenueAdapter(danhSachHienThi, san -> {
            Intent intent = new Intent(MainHomeActivity.this, CourtDetailActivity.class);
            intent.putExtra("TEN_SAN", san.getTenSan());
            intent.putExtra("DIA_CHI", san.getDiaChi());
            intent.putExtra("THOI_GIAN", san.getThoiGian());
            startActivity(intent);
        });

        binding.recyclerViewVenues.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewVenues.setAdapter(adapter);

        // Kéo dữ liệu sân từ mạng về
        loadCourtsFromFirebase();

        // ==========================================
        // 3. TÍNH NĂNG TÌM KIẾM THEO TÊN (REALTIME)
        // ==========================================
        if (binding.edtSearch != null) {
            binding.edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterCourts(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        // ==========================================
        // 4. XỬ LÝ BOTTOM NAVIGATION
        // ==========================================
        binding.navMap.setOnClickListener(v -> {
            startActivity(new Intent(MainHomeActivity.this, MapActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        });

        binding.navExplore.setOnClickListener(v -> {
            String[] vouchers = {"GIAM50K", "FREE_NUOC", "GIAM20%", "KHACH_MOI", "CHAO_HE"};
            String randomVoucher = vouchers[new java.util.Random().nextInt(vouchers.length)];

            new androidx.appcompat.app.AlertDialog.Builder(MainHomeActivity.this)
                    .setTitle("🎁 Voucher Khám Phá")
                    .setMessage("Bạn nhận được mã giảm giá bí mật:\n\n" + randomVoucher + "\n\nHãy đưa mã này cho nhân viên thu ngân để nhận ưu đãi nhé!")
                    .setPositiveButton("Tuyệt vời", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        binding.navFeatured.setOnClickListener(v -> {
            startActivity(new Intent(MainHomeActivity.this, FeaturedActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        });

        binding.navAccount.setOnClickListener(v -> {
            startActivity(new Intent(MainHomeActivity.this, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        });
    }

    // --- HÀM KÉO DỮ LIỆU SÂN TỪ FIREBASE (BẢN TỔNG LỰC) ---
    private void loadCourtsFromFirebase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Courts");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhSachGoc.clear();

                if (!snapshot.exists()) {
                    Log.d("FIREBASE_DEBUG", "Nhánh Courts trống hoặc không tồn tại!");
                }

                for (DataSnapshot data : snapshot.getChildren()) {
                    // Đọc các trường dữ liệu trên Firebase
                    String tenSan = data.child("tenSan").getValue(String.class);
                    String diaChi = data.child("diaChi").getValue(String.class);
                    String thoiGian = data.child("thoiGian").getValue(String.class);

                    // BÙ DỮ LIỆU: Nếu sân bạn tạo trên Firebase bị thiếu trường địa chỉ hoặc thời gian
                    if (tenSan == null || tenSan.isEmpty()) tenSan = "Sân chưa đặt tên";
                    if (diaChi == null || diaChi.isEmpty()) diaChi = "Địa chỉ đang cập nhật";
                    if (thoiGian == null || thoiGian.isEmpty()) thoiGian = "06:00 - 22:00";

                    // --- MẸO GẮN ẢNH ĐỘNG MÀ KHÔNG CẦN DATABASE ---
                    // 1. Tạo 1 danh sách các ảnh. Tạm thời mình lấy các ảnh feat1, feat2 bạn đang có sẵn.
                    int[] danhSachAnh = {
                            R.drawable.san1,
                            R.drawable.san2,
                            R.drawable.san3
                    };

                    // 2. Thuật toán lấy ảnh cố định cho từng sân dựa vào tên
                    int indexAnh = Math.abs(tenSan.hashCode()) % danhSachAnh.length;
                    int anhDuocChon = danhSachAnh[indexAnh];

                    // 3. Thêm vào danh sách gốc với ảnh đã chọn
                    danhSachGoc.add(new SanThiDau(tenSan, diaChi, thoiGian, anhDuocChon));
                    Log.d("FIREBASE_DEBUG", "Đã lấy được sân: " + tenSan);
                }

                // Giữ nguyên từ khóa tìm kiếm (nếu khách hàng đang gõ dở thì danh sách tự update theo)
                String currentKeyword = (binding.edtSearch != null && binding.edtSearch.getText() != null)
                        ? binding.edtSearch.getText().toString() : "";
                filterCourts(currentKeyword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainHomeActivity.this, "Lỗi kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- HÀM LỌC TÌM KIẾM ---
    private void filterCourts(String keyword) {
        danhSachHienThi.clear();

        if (keyword.isEmpty()) {
            danhSachHienThi.addAll(danhSachGoc);
        } else {
            String lowerKeyword = keyword.toLowerCase().trim();
            for (SanThiDau san : danhSachGoc) {
                if (san.getTenSan().toLowerCase().contains(lowerKeyword)) {
                    danhSachHienThi.add(san);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}