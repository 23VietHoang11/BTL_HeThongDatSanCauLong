package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminAddCourtBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class AdminAddCourtActivity extends AppCompatActivity {

    private ActivityAdminAddCourtBinding binding;

    // Biến để phân biệt Thêm hay Sửa
    private boolean isEditMode = false;
    private String editCourtId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddCourtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackAddCourt.setOnClickListener(v -> finish());

        // 1. KIỂM TRA CHẾ ĐỘ (THÊM hay SỬA)
        checkEditMode();

        // 2. XỬ LÝ LƯU DỮ LIỆU
        binding.btnSaveCourt.setOnClickListener(v -> saveCourtData());
    }

    private void checkEditMode() {
        editCourtId = getIntent().getStringExtra("COURT_ID");

        if (editCourtId != null && !editCourtId.isEmpty()) {
            isEditMode = true;

            // Cập nhật Giao diện sang chế độ Sửa
            binding.tvAddCourtTitle.setText("Chỉnh Sửa Sân");
            binding.btnSaveCourt.setText("CẬP NHẬT THÔNG TIN");

            // Điền sẵn dữ liệu cũ vào form
            String oldName = getIntent().getStringExtra("COURT_NAME");
            String oldPrice = getIntent().getStringExtra("COURT_PRICE");

            binding.edtCourtName.setText(oldName);
            binding.edtCourtPrice.setText(oldPrice);
        }
    }

    private void saveCourtData() {
        String tenSan = binding.edtCourtName.getText().toString().trim();
        String giaTien = binding.edtCourtPrice.getText().toString().trim();

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

        // KẾT NỐI FIREBASE - Nhánh "Courts"
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Courts");

        if (isEditMode) {
            // --- XỬ LÝ SỬA SÂN ---
            HashMap<String, Object> updateData = new HashMap<>();
            updateData.put("tenSan", tenSan);
            updateData.put("giaTien", giaTien + "đ"); // Gắn lại chữ "đ" khi lưu

            // Dùng updateChildren để chỉ sửa tên và giá, giữ nguyên các thông tin khác
            myRef.child(editCourtId).updateChildren(updateData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "🎉 Đã cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Lỗi mạng, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            // --- XỬ LÝ THÊM SÂN MỚI ---
            String newId = myRef.push().getKey();
            HashMap<String, Object> courtData = new HashMap<>();
            courtData.put("id", newId);
            courtData.put("tenSan", tenSan);
            courtData.put("giaTien", giaTien + "đ"); // Gắn lại chữ "đ" khi lưu
            courtData.put("trangThai", "Đang hoạt động");
            courtData.put("diaChi", "Cầu Giấy, Hà Nội");
            courtData.put("thoiGian", "06:00 - 23:00");

            if (newId != null) {
                myRef.child(newId).setValue(courtData).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "🎉 Đã thêm sân thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Lỗi mạng, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}