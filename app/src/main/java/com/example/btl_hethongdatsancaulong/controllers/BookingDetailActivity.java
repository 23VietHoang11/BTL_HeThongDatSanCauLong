package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.databinding.ActivityBookingDetailBinding;

public class BookingDetailActivity extends AppCompatActivity {

    private ActivityBookingDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.btnConfirmPay.setOnClickListener(v -> {
            Toast.makeText(BookingDetailActivity.this, "Đặt sân thành công!", Toast.LENGTH_SHORT).show();
            // Nếu muốn quay về trang chủ, bạn có thể mở comment các dòng Intent ở đây
        });

        Intent intent = getIntent();
        if (intent != null) {
            String tenSan = intent.getStringExtra("TEN_SAN");
            String diaChi = intent.getStringExtra("DIA_CHI");

            TextView tvCheckoutName = findViewById(R.id.tvCheckoutName);
            TextView tvCheckoutAddress = findViewById(R.id.tvCheckoutAddress);

            if (tenSan != null && tvCheckoutName != null) {
                tvCheckoutName.setText("Tên CLB: " + tenSan);
            }
            if (diaChi != null && tvCheckoutAddress != null) {
                // Xóa phần khoảng cách (ví dụ 357.4m) đi cho hóa đơn nó chuyên nghiệp
                String diaChiSach = diaChi.replaceAll("\\(.*?\\)\\s*", "");
                tvCheckoutAddress.setText("Địa chỉ: " + diaChiSach);
            }
        }

        // --- XỬ LÝ NÚT THANH TOÁN ---
        // (Lưu ý: Nếu bạn đang dùng ViewBinding thì gọi binding.btnPayment,
        // còn nếu đang dùng findViewById thì nhớ khai báo biến btnPayment trước nhé)

        binding.btnConfirmPay.setOnClickListener(v -> {
            // 1. Khởi tạo Dialog (Giữ nguyên)
            Dialog dialog = new Dialog(BookingDetailActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_qr_payment);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);

            // 2. Ánh xạ các thành phần
            ImageView imgQrCode = dialog.findViewById(R.id.imgQrCode);
            Button btnConfirmPaid = dialog.findViewById(R.id.btnConfirmPaid);
            TextView btnCancelPayment = dialog.findViewById(R.id.btnCancelPayment);

            // ==========================================
            // 3. TẠO MÃ QR ĐỘNG BẰNG VIETQR API
            // ==========================================
            String bankId = "MB"; // Mã ngân hàng (VD: MB, VCB, TCB, CTG...)
            String accountNo = "0983633639"; // Số tài khoản của bạn
            String accountName = "PHAM DINH KIEN"; // Tên chủ tài khoản (viết không dấu)
            int amount = 2000; // Số tiền cần thanh toán (Bạn có thể truyền biến vào đây)
            String content = "Thanh toan tien san"; // Nội dung chuyển khoản (viết không dấu)

            // Tạo đường link API của VietQR
            String qrUrl = "https://img.vietqr.io/image/" + bankId + "-" + accountNo + "-compact2.png"
                    + "?amount=" + amount
                    + "&addInfo=" + content
                    + "&accountName=" + accountName;

            // Dùng thư viện Glide để tải ảnh QR từ link mạng và ốp vào imgQrCode
            Glide.with(BookingDetailActivity.this)
                    .load(qrUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Ảnh hiển thị lúc đang tải (bạn có thể đổi thành icon load)
                    .error(R.drawable.my_qr) // Nếu lỗi mạng thì hiện QR tĩnh dự phòng
                    .into(imgQrCode);

            // 4. Xử lý nút "Tôi đã chuyển khoản" (Giả lập tự động nhận tiền)
            btnConfirmPaid.setOnClickListener(v1 -> {
                dialog.dismiss(); // Tắt popup QR

                // Hiện thông báo thành công và đá về Trang chủ
                new androidx.appcompat.app.AlertDialog.Builder(BookingDetailActivity.this)
                        .setTitle("🎉 Thanh toán thành công!")
                        .setMessage("Hệ thống đã nhận được tiền. Chúc bạn có một trận cầu vui vẻ!")
                        .setCancelable(false)
                        .setPositiveButton("Về Trang chủ", (d, which) -> {
                            Intent homeIntent = new Intent(BookingDetailActivity.this, MainHomeActivity.class);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeIntent);
                            finish();
                        })
                        .show();
            });

            // 5. Xử lý nút Hủy
            btnCancelPayment.setOnClickListener(v12 -> dialog.dismiss());

            dialog.show();
        });
    }
}