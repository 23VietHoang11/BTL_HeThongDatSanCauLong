package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_hethongdatsancaulong.databinding.ActivityVisualBookingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class VisualBookingActivity extends AppCompatActivity {

    private ActivityVisualBookingBinding binding;
    private DatabaseReference mDatabase;

    private String tenChiNhanh = "";
    private int giaGocMotGio = 80000; // Đặt mặc định 80k phòng trường hợp mạng chậm chưa lấy kịp

    // Cấu hình Sơ đồ
    private String[] danhSachSanNho = {"C.Lông 1", "C.Lông 2", "C.Lông 3", "C.Lông 4"};
    private int startHour = 6;  // Từ 6h sáng
    private int endHour = 22;   // Đến 22h tối
    private int soGioMotCa = 2; // Ca 2 tiếng

    private int slotWidthDp = 130; // Ô rộng hơn để chứa text "06:00 - 08:00"

    // Lưu trạng thái chọn
    private String selectedSubCourt = "";
    private String selectedTimeSlot = "";

    private List<String> bookedSlots = new ArrayList<>();
    private HashMap<String, TextView> mapCellViews = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVisualBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. NHẬN DỮ LIỆU TỪ MÀN TRƯỚC
        tenChiNhanh = getIntent().getStringExtra("TEN_SAN");
        if (tenChiNhanh == null) tenChiNhanh = "Sân Cầu Lông ĐHSP";

        binding.tvHeaderTitle.setText(tenChiNhanh);

        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        binding.tvCurrentDate.setText(today);

        binding.btnBack.setOnClickListener(v -> finish());

        // 2. KẾT NỐI FIREBASE & LẤY GIÁ TIỀN
        mDatabase = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        fetchCourtPrice();

        // 3. VẼ GIAO DIỆN LƯỚI & LẮNG NGHE LỊCH ĐẶT
        drawGrid();
        listenToBookings();

        // 4. XỬ LÝ NÚT XÁC NHẬN
        binding.btnNext.setOnClickListener(v -> submitBooking());
    }

    // =========================================
    // HÀM LẤY GIÁ TIỀN TỪ NHÁNH "Courts" CỦA ADMIN
    // =========================================
    private void fetchCourtPrice() {
        DatabaseReference courtsRef = mDatabase.child("Courts");

        courtsRef.orderByChild("tenSan").equalTo(tenChiNhanh).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String giaTienStr = data.child("giaTien").getValue(String.class);
                        if (giaTienStr != null) {
                            // Lọc bỏ chữ "đ" hoặc các ký tự thừa (VD: "100000đ" -> 100000)
                            String cleanGia = giaTienStr.replaceAll("[^0-9]", "");
                            if (!cleanGia.isEmpty()) {
                                giaGocMotGio = Integer.parseInt(cleanGia);
                            }
                        }
                        break; // Tìm thấy sân đầu tiên là dừng
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lỗi mạng thì vẫn dùng giá mặc định 80k đã set ở trên
            }
        });
    }

    // =========================================
    // HÀM VẼ GIAO DIỆN
    // =========================================
    private void drawGrid() {
        TextView tvCorner = new TextView(this);
        tvCorner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(30)));
        binding.layoutCourtNames.addView(tvCorner);
        binding.layoutCourtNames.addView(createDividerLine());

        // Vẽ Hàng Tiêu Đề Giờ (Nhảy theo soGioMotCa = 2)
        for (int i = startHour; i < endHour; i += soGioMotCa) {
            String timeText = String.format(Locale.getDefault(), "%02d:00 - %02d:00", i, i + soGioMotCa);

            TextView tvTime = new TextView(this);
            tvTime.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(slotWidthDp), LinearLayout.LayoutParams.MATCH_PARENT));
            tvTime.setText(timeText);
            tvTime.setGravity(Gravity.CENTER);
            tvTime.setTextSize(12f);
            tvTime.setTextColor(Color.BLACK);
            binding.layoutTimeHeaders.addView(tvTime);

            View vLine = new View(this);
            vLine.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(1), LinearLayout.LayoutParams.MATCH_PARENT));
            vLine.setBackgroundColor(Color.parseColor("#BDBDBD"));
            binding.layoutTimeHeaders.addView(vLine);
        }

        // Vẽ Các Hàng Của Từng Sân Nhỏ
        for (String tenSanNho : danhSachSanNho) {
            TextView tvCourtName = new TextView(this);
            tvCourtName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(50)));
            tvCourtName.setText(tenSanNho);
            tvCourtName.setGravity(Gravity.CENTER);
            tvCourtName.setTextColor(Color.BLACK);
            tvCourtName.setTypeface(null, android.graphics.Typeface.BOLD);
            binding.layoutCourtNames.addView(tvCourtName);
            binding.layoutCourtNames.addView(createDividerLine());

            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(50)));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = startHour; i < endHour; i += soGioMotCa) {
                String timeSlot = String.format(Locale.getDefault(), "%02d:00 - %02d:00", i, i + soGioMotCa);
                String cellKey = tenSanNho + "|" + timeSlot;

                TextView tvCell = new TextView(this);
                tvCell.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(slotWidthDp), LinearLayout.LayoutParams.MATCH_PARENT));
                tvCell.setBackgroundColor(Color.WHITE);
                tvCell.setGravity(Gravity.CENTER);

                tvCell.setOnClickListener(v -> handleCellClick(cellKey));

                mapCellViews.put(cellKey, tvCell);
                rowLayout.addView(tvCell);

                View vLine = new View(this);
                vLine.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(1), LinearLayout.LayoutParams.MATCH_PARENT));
                vLine.setBackgroundColor(Color.parseColor("#E0E0E0"));
                rowLayout.addView(vLine);
            }

            binding.layoutGridRows.addView(rowLayout);
            binding.layoutGridRows.addView(createDividerLine());
        }
    }

    private void handleCellClick(String cellKey) {
        if (bookedSlots.contains(cellKey)) {
            Toast.makeText(this, "Ca này đã bị đặt hoặc đang chờ duyệt!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cellKey.equals(selectedSubCourt + "|" + selectedTimeSlot)) {
            // Bỏ chọn
            selectedSubCourt = "";
            selectedTimeSlot = "";
            binding.tvSelectedInfo.setText("Chưa chọn ca nào");
        } else {
            // Chọn ca mới
            String[] parts = cellKey.split("\\|");
            selectedSubCourt = parts[0];
            selectedTimeSlot = parts[1];

            // Tính toán tiền linh hoạt dựa theo giá lấy từ Firebase
            int tongTien = giaGocMotGio * soGioMotCa;
            DecimalFormat df = new DecimalFormat("###,###,###");
            binding.tvSelectedInfo.setText(selectedSubCourt + "  -  " + selectedTimeSlot + " (" + df.format(tongTien) + " đ)");
        }

        refreshColors();
    }

    private void listenToBookings() {
        mDatabase.child("Bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookedSlots.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    String ten = data.child("tenSan").getValue(String.class);
                    String chiTietGhe = data.child("thoiGian").getValue(String.class);
                    String trangThai = data.child("trangThai").getValue(String.class);

                    if (tenChiNhanh.equals(ten) && ("Đã xác nhận".equals(trangThai) || "Chờ duyệt".equals(trangThai))) {
                        if (chiTietGhe != null) {
                            bookedSlots.add(chiTietGhe);
                        }
                    }
                }

                // Nếu ca đang chọn bị người khác đặt mất trong lúc đang nghĩ
                if (bookedSlots.contains(selectedSubCourt + "|" + selectedTimeSlot)) {
                    selectedSubCourt = "";
                    selectedTimeSlot = "";
                    binding.tvSelectedInfo.setText("Ca bạn chọn vừa bị người khác đặt!");
                }

                refreshColors();
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void refreshColors() {
        for (String key : mapCellViews.keySet()) {
            TextView cell = mapCellViews.get(key);

            if (bookedSlots.contains(key)) {
                cell.setBackgroundColor(Color.parseColor("#C62828"));
                cell.setText("Đã đặt");
                cell.setTextColor(Color.WHITE);
                cell.setTextSize(12f);
            } else if (key.equals(selectedSubCourt + "|" + selectedTimeSlot)) {
                cell.setBackgroundColor(Color.parseColor("#F4B33E"));
                cell.setText("Chọn");
                cell.setTextColor(Color.WHITE);
                cell.setTextSize(12f);
            } else {
                cell.setBackgroundColor(Color.WHITE);
                cell.setText("");
            }
        }
    }

    private void submitBooking() {
        if (selectedSubCourt.isEmpty() || selectedTimeSlot.isEmpty()) {
            Toast.makeText(this, "Bạn chưa chọn ca nào!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tính tổng tiền dựa trên giá Firebase
        int tongTien = giaGocMotGio * soGioMotCa;
        DecimalFormat df = new DecimalFormat("###,###,###");
        String chuoiTienTe = df.format(tongTien) + " đ"; // VD: "200,000 đ"

        Intent intent = new Intent(VisualBookingActivity.this, BookingDetailActivity.class);

        intent.putExtra("TEN_SAN_CHINH", tenChiNhanh);
        intent.putExtra("TEN_SAN_CON", selectedSubCourt);
        intent.putExtra("KHUNG_GIO", selectedTimeSlot);
        intent.putExtra("GIA_TIEN", chuoiTienTe);

        startActivity(intent);
    }

    private View createDividerLine() {
        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(1)));
        v.setBackgroundColor(Color.parseColor("#BDBDBD"));
        return v;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}