package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminAddCourtBinding;

public class AdminAddCourtActivity extends AppCompatActivity {

    private ActivityAdminAddCourtBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddCourtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Nút quay lại
        binding.btnBackAddCourt.setOnClickListener(v -> finish());

        // 2. Xử lý sự kiện bấm nút Lưu
        binding.btnSaveCourt.setOnClickListener(v -> {
            String tenSan = binding.edtCourtName.getText().toString().trim();
            String giaTien = binding.edtCourtPrice.getText().toString().trim();

            // Rào chắn bảo mật: Không cho phép bỏ trống
            if (tenSan.isEmpty()) {
                binding.edtCourtName.setError("Vui lòng nhập tên sân!");
                binding.edtCourtName.requestFocus();
                return;
            }

            if (giaTien.isEmpty()) {
                binding.edtCourtPrice.setError("Vui lòng nhập giá tiền!");
                binding.edtCourtPrice.requestFocus();
                return;
            }

            // Nếu nhập đầy đủ -> Hiện thông báo thành công và Đóng form
            Toast.makeText(this, "🎉 Đã thêm sân: " + tenSan + " thành công!", Toast.LENGTH_LONG).show();

            // Lệnh kết thúc màn hình hiện tại, búng người dùng về lại Danh sách sân
            finish();
        });
    }
}