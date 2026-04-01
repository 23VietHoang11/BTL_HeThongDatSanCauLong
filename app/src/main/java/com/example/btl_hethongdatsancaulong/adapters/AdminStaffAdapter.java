package com.example.btl_hethongdatsancaulong.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_hethongdatsancaulong.databinding.ItemAdminStaffCardBinding;
import com.example.btl_hethongdatsancaulong.models.AdminStaff;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdminStaffAdapter extends RecyclerView.Adapter<AdminStaffAdapter.StaffViewHolder> {

    private List<AdminStaff> listStaff;
    private Context context;
    private DatabaseReference staffRef;

    public AdminStaffAdapter(Context context, List<AdminStaff> listStaff) {
        this.context = context;
        this.listStaff = listStaff;
        // Kết nối sẵn tới nhánh Staffs
        this.staffRef = FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Staffs");
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminStaffCardBinding binding = ItemAdminStaffCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new StaffViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        AdminStaff staff = listStaff.get(position);

        holder.binding.tvStaffName.setText(staff.getTenNhanVien());
        holder.binding.tvStaffPhone.setText("📞 " + staff.getSoDienThoai());
        holder.binding.tvStaffRole.setText(staff.getCaLamViec());

        // Ép kiểu ép về số nguyên để hiển thị đẹp
        holder.binding.tvStaffSalary.setText("Lương: " + String.format("%,d đ", (long) staff.getMucLuong()));

        // --- SỬA THÔNG TIN NHÂN VIÊN ---
        holder.binding.btnEditStaff.setOnClickListener(v -> {
            android.app.Dialog dialog = new android.app.Dialog(context);
            dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
            dialog.setContentView(com.example.btl_hethongdatsancaulong.R.layout.dialog_edit_staff);
            dialog.getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));

            android.widget.TextView tvName = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.tvDialogStaffName);
            android.widget.Spinner spinnerShift = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.spinnerDialogShift);
            android.widget.EditText edtSalary = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.edtDialogSalary);
            android.widget.Button btnSave = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.btnDialogSave);
            android.widget.Button btnCancel = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.btnDialogCancel);

            tvName.setText("Nhân viên: " + staff.getTenNhanVien());
            edtSalary.setText(String.valueOf((long)staff.getMucLuong()));

            String[] dsCaLam = {
                    "Full-time Sáng (06:00 - 14:00)", "Full-time Chiều (14:00 - 22:00)",
                    "Part-time Sáng (06:00 - 10:00)", "Part-time Trưa (10:00 - 14:00)",
                    "Part-time Chiều (14:00 - 18:00)", "Part-time Tối (18:00 - 22:00)"
            };

            android.widget.ArrayAdapter<String> spinnerAdapter = new android.widget.ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, dsCaLam);
            spinnerShift.setAdapter(spinnerAdapter);

            for (int i = 0; i < dsCaLam.length; i++) {
                if (dsCaLam[i].equals(staff.getCaLamViec())) {
                    spinnerShift.setSelection(i);
                    break;
                }
            }

            // Xử lý Cập nhật lên Firebase
            btnSave.setOnClickListener(v1 -> {
                String newShift = spinnerShift.getSelectedItem().toString();
                String newSalaryStr = edtSalary.getText().toString();

                if(newShift.isEmpty() || newSalaryStr.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tìm nhân viên bằng Số điện thoại và cập nhật
                staffRef.orderByChild("soDienThoai").equalTo(staff.getSoDienThoai()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            child.getRef().child("caLamViec").setValue(newShift);
                            child.getRef().child("mucLuong").setValue(Integer.parseInt(newSalaryStr));
                        }
                        Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            });

            btnCancel.setOnClickListener(v12 -> dialog.dismiss());
            dialog.show();
        });

        // --- NÚT XÓA (SA THẢI) LÊN FIREBASE ---
        holder.binding.btnDeleteStaff.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Sa thải nhân viên")
                    .setMessage("Bạn có chắc chắn muốn xóa tài khoản của " + staff.getTenNhanVien() + "?")
                    .setNegativeButton("Hủy", null)
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Tìm nhân viên bằng Số điện thoại và xóa khỏi Firebase
                        staffRef.orderByChild("soDienThoai").equalTo(staff.getSoDienThoai()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    child.getRef().removeValue();
                                }
                                Toast.makeText(context, "Đã sa thải nhân viên!", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() { return listStaff.size(); }

    public static class StaffViewHolder extends RecyclerView.ViewHolder {
        ItemAdminStaffCardBinding binding;
        public StaffViewHolder(ItemAdminStaffCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}