package com.example.btl_hethongdatsancaulong.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút Đăng nhập
        binding.btnLogin.setOnClickListener(v -> handleLogin());

        // Nút chuyển sang trang Đăng ký (Bạn nhớ set ID tvRegister trong XML nhé)
        // binding.tvRegister.setOnClickListener(v -> {
        //    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        //    startActivity(intent);
        // });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void handleLogin() {
        // Lấy ID từ XML (edtPhoneLogin, edtPasswordLogin) bạn vừa thêm
        // Giả lập code:
        /*
        String phone = binding.edtPhoneLogin.getText().toString().trim();
        String password = binding.edtPasswordLogin.getText().toString().trim();

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại và mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Giả lập check pass thành công -> Chuyển vào MainHomeActivity
        if (phone.equals("0983633639") && password.equals("123456")) {
        */
        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        // CHUYỂN VÀO TRANG CHỦ THEO LUỒNG MVC
        // Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
        // startActivity(intent);
        // finish();
        /*
        } else {
            Toast.makeText(this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
        }
        */
    }
}