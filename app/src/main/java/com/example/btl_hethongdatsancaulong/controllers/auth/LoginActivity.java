package com.example.btl_hethongdatsancaulong.controllers.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.controllers.admin.AdminDashboardActivity;
import com.example.btl_hethongdatsancaulong.controllers.customer.MainHomeActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_BTL_HeThongDatSanCauLong);
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> handleLogin());

        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void handleLogin() {
        String phone = binding.edtPhoneLogin.getText().toString().trim();
        String password = binding.edtPasswordLogin.getText().toString().trim();

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Quyền Admin/Staff test nhanh bằng mã (Giữ nguyên để đi thi test cho lẹ)
        if (phone.equals("111") || phone.equals("222")) {
            Intent intent = new Intent(this, AdminDashboardActivity.class);
            intent.putExtra("ROLE", phone.equals("111") ? "ADMIN" : "STAFF");
            startActivity(intent);
            finish();
            return;
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        // BƯỚC 1: TÌM TRONG NHÁNH KHÁCH HÀNG (USERS) TRƯỚC
        rootRef.child("Users").child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // => TÀI KHOẢN LÀ KHÁCH HÀNG
                    String dbPass = snapshot.child("matKhau").getValue(String.class);
                    if (dbPass != null && dbPass.equals(password)) {
                        // Lưu thông tin vào SharedPreferences
                        SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
                        editor.putString("PHONE", phone);
                        editor.putString("NAME", snapshot.child("hoTen").getValue(String.class));
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this, MainHomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai mật khẩu khách hàng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // BƯỚC 2: NẾU KHÔNG CÓ TRONG USERS -> TÌM TRONG NHÁNH NHÂN VIÊN (STAFFS)
                    rootRef.child("Staffs").orderByChild("soDienThoai").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot staffSnap) {
                            if (staffSnap.exists()) {
                                // => TÀI KHOẢN LÀ NHÂN VIÊN
                                // Mật khẩu mặc định cấp cho mọi nhân viên mới tạo là "123"
                                if (password.equals("123")) {
                                    Toast.makeText(LoginActivity.this, "Đăng nhập ca trực Nhân viên!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                    intent.putExtra("ROLE", "STAFF"); // Gắn thẻ quyền STAFF để giấu bớt chức năng Admin
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Sai mật khẩu! (Mặc định của nhân viên là 123456)", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại trên hệ thống!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}