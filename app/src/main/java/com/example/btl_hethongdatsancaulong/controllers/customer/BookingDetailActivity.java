package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.databinding.ActivityBookingDetailBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BookingDetailActivity extends AppCompatActivity {

    private ActivityBookingDetailBinding binding;

    // Khai báo biến toàn cục để dùng chung cho cả giao diện, QR và Firebase
    private String tenSan, sanCon, gio, gia;
    private int finalAmountToPay = 0;
    private String contentQR = "Thanh toan san";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. NHẬN DỮ LIỆU TỪ MÀN TRỰC QUAN
        tenSan = getIntent().getStringExtra("TEN_SAN_CHINH");
        sanCon = getIntent().getStringExtra("TEN_SAN_CON");
        gio = getIntent().getStringExtra("KHUNG_GIO");
        gia = getIntent().getStringExtra("GIA_TIEN");

        // Xử lý giá tiền: Lọc lấy các con số để đưa vào API VietQR (VD: "200.000 đ" -> 200000)
        try {
            if (gia != null) {
                String cleanGia = gia.replaceAll("[^0-9]", "");
                finalAmountToPay = Integer.parseInt(cleanGia);
            }
        } catch (Exception e) {
            finalAmountToPay = 0; // Đề phòng lỗi parse số
        }

        // Tạo nội dung chuyển khoản (bỏ dấu cách đặc biệt nếu có)
        if (sanCon != null) {
            contentQR = "Thanh toan " + sanCon.replaceAll("[^a-zA-Z0-9 ]", "");
        }

        // Hiển thị lên màn hình
        binding.tvDetailVenueName.setText(tenSan);
        binding.tvDetailSubCourt.setText("Sân: " + sanCon);
        binding.tvDetailTime.setText("Giờ: " + gio);
        binding.tvDetailTotalPrice.setText(gia);

        binding.btnBack.setOnClickListener(v -> finish());

        // 2. NÚT ĐẶT LỊCH: Nhấn vào thì gọi Dialog QR ra thay vì lưu luôn
        binding.btnConfirmBooking.setOnClickListener(v -> {
            showQrPaymentDialog();
        });
    }

    private void showQrPaymentDialog() {
        Dialog dialog = new Dialog(BookingDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_qr_payment);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imgQrCode = dialog.findViewById(R.id.imgQrCode);
        Button btnConfirmPaid = dialog.findViewById(R.id.btnConfirmPaid);
        TextView btnCancelPayment = dialog.findViewById(R.id.btnCancelPayment);

        // ==========================================
        // TẠO MÃ QR ĐỘNG BẰNG VIETQR API
        // ==========================================
        String bankId = "MB";
        String accountNo = "0983633639";
        String accountName = "PHAM DINH KIEN";

        // Gắn số tiền và nội dung động vào link API
        String qrUrl = "https://img.vietqr.io/image/" + bankId + "-" + accountNo + "-compact2.png"
                + "?amount=" + finalAmountToPay
                + "&addInfo=" + contentQR
                + "&accountName=" + accountName;

        Glide.with(BookingDetailActivity.this)
                .load(qrUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.my_qr) // Đảm bảo bạn có sẵn ảnh này trong res/drawable để đề phòng mất mạng
                .into(imgQrCode);

        // 3. XÁC NHẬN ĐÃ THANH TOÁN -> BẮT ĐẦU LƯU LÊN FIREBASE
        btnConfirmPaid.setOnClickListener(v1 -> {
            dialog.dismiss(); // Tắt popup QR
            saveBookingToFirebase(); // Gọi hàm lưu database
        });

        // Nút hủy thanh toán
        btnCancelPayment.setOnClickListener(v12 -> dialog.dismiss());
        dialog.show();
    }

    // Hàm tách riêng logic lưu Firebase để code gọn gàng hơn
    private void saveBookingToFirebase() {
        // Tự động lấy tên & SĐT từ bộ nhớ máy
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = prefs.getString("NAME", "Khách hàng");
        String phone = prefs.getString("PHONE", "");
        String ghiChu = binding.edtNote.getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Bookings");

        String maDon = "BILL" + System.currentTimeMillis();

        HashMap<String, Object> map = new HashMap<>();
        map.put("maDon", maDon);
        map.put("tenKhachHang", name);
        map.put("soDienThoai", phone);
        map.put("tenSan", tenSan);
        map.put("thoiGian", sanCon + "|" + gio);
        map.put("tongTien", gia);
        map.put("ghiChu", ghiChu);
        map.put("trangThai", "Chờ duyệt");

        ref.child(maDon).setValue(map).addOnSuccessListener(aVoid -> {
            // Hiện popup chúc mừng thay vì Toast cho đẹp
            new androidx.appcompat.app.AlertDialog.Builder(BookingDetailActivity.this)
                    .setTitle("🎉 Thanh toán & Đặt sân thành công!")
                    .setMessage("Hệ thống đã nhận được yêu cầu. Vui lòng chờ Admin duyệt nhé!")
                    .setCancelable(false)
                    .setPositiveButton("Về Trang chủ", (d, which) -> {
                        Intent homeIntent = new Intent(BookingDetailActivity.this, MainHomeActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);
                        finish();
                    })
                    .show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Lỗi khi đặt sân: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}