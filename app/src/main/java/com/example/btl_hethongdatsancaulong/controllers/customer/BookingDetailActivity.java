package com.example.btl_hethongdatsancaulong.controllers.customer;

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

            // 2. Ánh xạ các thành phần (Giữ nguyên)
            ImageView imgQrCode = dialog.findViewById(R.id.imgQrCode);
            Button btnConfirmPaid = dialog.findViewById(R.id.btnConfirmPaid);
            TextView btnCancelPayment = dialog.findViewById(R.id.btnCancelPayment);

            // ==========================================
            // 3. ĐÂY LÀ ĐOẠN THAY ĐỔI: Gọi thẳng ảnh QR cá nhân của bạn ra
            // Giả sử ảnh của bạn đặt tên là ma_qr_cua_toi
            imgQrCode.setImageResource(R.drawable.my_qr);
            // ==========================================

            // 4. Xử lý nút "Tôi đã chuyển khoản" (Giữ nguyên)
            btnConfirmPaid.setOnClickListener(v1 -> {
                dialog.dismiss(); // Tắt popup QR

                // Hiện thông báo thành công và đá về Trang chủ
                new androidx.appcompat.app.AlertDialog.Builder(BookingDetailActivity.this)
                        .setTitle("🎉 Đặt sân thành công!")
                        .setMessage("Hệ thống đã xác nhận thanh toán. Chúc bạn có một trận cầu vui vẻ!")
                        .setCancelable(false)
                        .setPositiveButton("Về Trang chủ", (d, which) -> {
                            Intent homeIntent = new Intent(BookingDetailActivity.this, MainHomeActivity.class);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeIntent);
                            finish();
                        })
                        .show();
            });

            // 5. Xử lý nút Hủy (Giữ nguyên)
            btnCancelPayment.setOnClickListener(v12 -> dialog.dismiss());

            dialog.show();
        });
    }
}