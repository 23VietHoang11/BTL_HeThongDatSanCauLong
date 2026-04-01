package com.example.btl_hethongdatsancaulong.controllers.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_hethongdatsancaulong.databinding.ActivityRegisterBinding;
import com.example.btl_hethongdatsancaulong.models.KhachHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    // Sửa lại hàm handleRegister() trong file RegisterActivity.java:
    private void handleRegister() {
        String phone = binding.edtPhone.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String fullName = binding.edtFullName.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

        if (phone.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Users");

        // BƯỚC 1: KIỂM TRA XEM SỐ ĐIỆN THOẠI ĐÃ CÓ TRÊN MẠNG CHƯA
        myRef.child(phone).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(RegisterActivity.this, "Số điện thoại này đã được đăng ký!", Toast.LENGTH_LONG).show();
                } else {
                    // BƯỚC 2: NẾU CHƯA CÓ THÌ MỚI CHO ĐĂNG KÝ
                    com.example.btl_hethongdatsancaulong.models.KhachHang newCustomer =
                            new com.example.btl_hethongdatsancaulong.models.KhachHang(fullName, phone, email, password);

                    myRef.child(phone).setValue(newCustomer).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {}
        });
    }
}