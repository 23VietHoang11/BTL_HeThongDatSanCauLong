package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityRegisterBinding;
import com.example.btl_hethongdatsancaulong.models.KhachHang;

public class RegisterActivity extends AppCompatActivity {

    // Khai báo biến binding để kết nối với activity_register.xml
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo ViewBinding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Xử lý nút Đăng Ký
        binding.btnRegister.setOnClickListener(v -> handleRegister());

        // Xử lý nút quay lại hoặc nút "Đăng nhập" ở dưới cùng
        binding.tvLogin.setOnClickListener(v -> {
            // Chuyển sang màn hình Đăng nhập
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Đóng màn hình đăng ký
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void handleRegister() {
        // Lấy dữ liệu từ giao diện
        String phone = binding.edtPhone.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String fullName = binding.edtFullName.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

        // Ràng buộc điều kiện (Validation)
        if (phone.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (*)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Model (Lúc này có thể lưu vào Database/API)
        KhachHang newCustomer = new KhachHang(fullName, phone, email, password);

        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

        // Chuyển về trang Đăng nhập sau khi đăng ký thành công
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
