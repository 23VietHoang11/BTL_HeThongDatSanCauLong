package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.btl_hethongdatsancaulong.adapters.AdminStaffAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminStaffBinding;
import com.example.btl_hethongdatsancaulong.models.AdminStaff;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminStaffActivity extends AppCompatActivity {

    private ActivityAdminStaffBinding binding;
    private AdminStaffAdapter adapter;
    private List<AdminStaff> danhSachNhanVien;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackStaff.setOnClickListener(v -> finish());

        // Khởi tạo danh sách và Adapter
        danhSachNhanVien = new ArrayList<>();
        adapter = new AdminStaffAdapter(this, danhSachNhanVien);
        binding.recyclerViewStaff.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewStaff.setAdapter(adapter);

        // Kết nối Firebase nhánh Nhân viên
        mDatabase = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Staffs");

        // 1. TẢI DỮ LIỆU TỪ FIREBASE
        loadStaffFromFirebase();

        // 2. XỬ LÝ NÚT THÊM NHÂN VIÊN
        binding.fabAddStaff.setOnClickListener(v -> showAddStaffDialog());
    }

    private void loadStaffFromFirebase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhSachNhanVien.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String ten = data.child("tenNhanVien").getValue(String.class);
                    String sdt = data.child("soDienThoai").getValue(String.class);
                    String ca = data.child("caLamViec").getValue(String.class);
                    Integer luong = data.child("mucLuong").getValue(Integer.class);

                    if (ten != null && sdt != null) {
                        danhSachNhanVien.add(new AdminStaff(ten, sdt, ca, luong != null ? luong : 0));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminStaffActivity.this, "Lỗi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddStaffDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Nhân Viên Mới");

        // Tạo layout cho Dialog hoàn toàn bằng Java
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);

        // Ô nhập Tên
        final EditText edtName = new EditText(this);
        edtName.setHint("Họ và tên nhân viên");
        layout.addView(edtName);

        // Ô nhập Số điện thoại
        final EditText edtPhone = new EditText(this);
        edtPhone.setHint("Số điện thoại (Dùng làm mã QL)");
        edtPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(edtPhone);

        // Chọn Ca làm việc
        final Spinner spinnerShift = new Spinner(this);
        String[] dsCaLam = {
                "Full-time Sáng (06:00 - 14:00)", "Full-time Chiều (14:00 - 22:00)",
                "Part-time Sáng (06:00 - 10:00)", "Part-time Trưa (10:00 - 14:00)",
                "Part-time Chiều (14:00 - 18:00)", "Part-time Tối (18:00 - 22:00)"
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dsCaLam);
        spinnerShift.setAdapter(spinnerAdapter);
        layout.addView(spinnerShift);

        // Ô nhập Lương
        final EditText edtSalary = new EditText(this);
        edtSalary.setHint("Mức lương (VNĐ)");
        edtSalary.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(edtSalary);

        builder.setView(layout);

        // Nút Lưu lên Firebase
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String ten = edtName.getText().toString().trim();
            String sdt = edtPhone.getText().toString().trim();
            String ca = spinnerShift.getSelectedItem().toString();
            String luongStr = edtSalary.getText().toString().trim();

            if (ten.isEmpty() || sdt.isEmpty() || luongStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap<String, Object> staffMap = new HashMap<>();
            staffMap.put("tenNhanVien", ten);
            staffMap.put("soDienThoai", sdt);
            staffMap.put("caLamViec", ca);
            staffMap.put("mucLuong", Integer.parseInt(luongStr));

            // Đẩy dữ liệu lên Firebase
            mDatabase.push().setValue(staffMap).addOnSuccessListener(aVoid -> {
                Toast.makeText(this, "Đã thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
            });
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}