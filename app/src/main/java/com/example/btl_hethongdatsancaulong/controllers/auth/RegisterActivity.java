package com.example.btl_hethongdatsancaulong.controllers.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns; // Thêm thư viện này để check định dạng Email
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_hethongdatsancaulong.databinding.ActivityRegisterBinding;
import com.example.btl_hethongdatsancaulong.models.KhachHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(v -> handleRegister());

        binding.tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void handleRegister() {
        String fullName = binding.edtFullName.getText().toString().trim();
        String phone = binding.edtPhone.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

        // VALIDATE: Họ tên
        if (fullName.isEmpty()) {
            binding.edtFullName.setError("Vui lòng nhập họ tên!");
            binding.edtFullName.requestFocus();
            return;
        }

        // VALIDATE: Số điện thoại
        if (phone.isEmpty()) {
            binding.edtPhone.setError("Vui lòng nhập số điện thoại!");
            binding.edtPhone.requestFocus();
            return;
        }
        if (phone.length() < 8) {
            binding.edtPhone.setError("Số điện thoại phải có ít nhất 8 chữ số!");
            binding.edtPhone.requestFocus();
            return;
        }

        // VALIDATE: Email
        if (email.isEmpty()) {
            binding.edtEmail.setError("Vui lòng nhập địa chỉ email!");
            binding.edtEmail.requestFocus();
            return;
        }
        // Công cụ của Android giúp tự động nhận diện định dạng abc@xyz.com
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Định dạng email không hợp lệ (VD: abc@gmail.com)!");
            binding.edtEmail.requestFocus();
            return;
        }

        // VALIDATE: Mật khẩu
        if (password.isEmpty()) {
            binding.edtPassword.setError("Vui lòng nhập mật khẩu!");
            binding.edtPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            binding.edtConfirmPassword.setError("Vui lòng xác nhận mật khẩu!");
            binding.edtConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            binding.edtConfirmPassword.setError("Mật khẩu xác nhận không trùng khớp!");
            binding.edtConfirmPassword.requestFocus();
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