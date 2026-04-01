package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminSettingsBinding;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSettingsActivity extends AppCompatActivity {

    private ActivityAdminSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackSettings.setOnClickListener(v -> finish());

        binding.btnAppInfo.setOnClickListener(v ->
                Toast.makeText(this, "Hệ Thống Đặt Sân Cầu Lông\nPhiên bản 1.0 (Realtime Firebase)", Toast.LENGTH_LONG).show()
        );

        // --- XỬ LÝ ĐỔI MẬT KHẨU ĐỘNG ---
        binding.btnChangePassword.setOnClickListener(v -> showChangePasswordDialog());

        binding.btnLogout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();
            Toast.makeText(this, "Đã đăng xuất an toàn", Toast.LENGTH_SHORT).show();
            android.content.Intent intent = new android.content.Intent(AdminSettingsActivity.this, com.example.btl_hethongdatsancaulong.controllers.auth.LoginActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void showChangePasswordDialog() {
        // Lấy SĐT đang đăng nhập
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String currentPhone = prefs.getString("PHONE", "");

        if (currentPhone.equals("111") || currentPhone.equals("222")) {
            Toast.makeText(this, "Tài khoản mặc định (Admin/Staff) không thể đổi mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentPhone.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy phiên đăng nhập!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đổi mật khẩu");

        // Tạo giao diện nhập liệu trực tiếp bằng code (khỏi cần vẽ XML)
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final EditText edtPassMoi = new EditText(this);
        edtPassMoi.setHint("Nhập mật khẩu mới");
        edtPassMoi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(edtPassMoi);

        builder.setView(layout);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String passMoi = edtPassMoi.getText().toString().trim();
            if (passMoi.length() >= 6) {
                // Đẩy mật khẩu mới lên Firebase thay thế pass cũ
                FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .getReference("Users")
                        .child(currentPhone)
                        .child("matKhau")
                        .setValue(passMoi);
                Toast.makeText(this, "Đã đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Mật khẩu phải từ 6 ký tự trở lên!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}