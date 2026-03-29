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
import java.util.List;

public class AdminStaffAdapter extends RecyclerView.Adapter<AdminStaffAdapter.StaffViewHolder> {

    private List<AdminStaff> listStaff;
    private Context context;

    public AdminStaffAdapter(Context context, List<AdminStaff> listStaff) {
        this.context = context;
        this.listStaff = listStaff;
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

        // Format hiển thị tiền lương
        holder.binding.tvStaffSalary.setText("Lương: " + String.format("%,d đ", staff.getMucLuong()));

        // --- NÚT SỬA (BẬT DIALOG NÂNG CAO) ---
        holder.binding.btnEditStaff.setOnClickListener(v -> {
            // 1. Khởi tạo Dialog
            android.app.Dialog dialog = new android.app.Dialog(context);
            dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
            dialog.setContentView(com.example.btl_hethongdatsancaulong.R.layout.dialog_edit_staff); // Nhớ import R.layout
            dialog.getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));

            // 2. Ánh xạ các thành phần trong Dialog
            android.widget.TextView tvName = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.tvDialogStaffName);
            android.widget.Spinner spinnerShift = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.spinnerDialogShift);            android.widget.EditText edtSalary = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.edtDialogSalary);
            android.widget.Button btnSave = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.btnDialogSave);
            android.widget.Button btnCancel = dialog.findViewById(com.example.btl_hethongdatsancaulong.R.id.btnDialogCancel);

            // 3. Đổ dữ liệu hiện tại lên để Admin xem trước
            tvName.setText("Nhân viên: " + staff.getTenNhanVien());
            edtSalary.setText(String.valueOf(staff.getMucLuong()));
            String[] dsCaLam = {
                    "Full-time Sáng (06:00 - 14:00)",
                    "Full-time Chiều (14:00 - 22:00)",
                    "Part-time Sáng (06:00 - 10:00)",
                    "Part-time Trưa (10:00 - 14:00)",
                    "Part-time Chiều (14:00 - 18:00)",
                    "Part-time Tối (18:00 - 22:00)"
            };

            android.widget.ArrayAdapter<String> spinnerAdapter = new android.widget.ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, dsCaLam);
            spinnerShift.setAdapter(spinnerAdapter);

            // Tìm xem nhân viên đang làm ca nào để "chọn sẵn" ca đó khi mở Dialog lên
            for (int i = 0; i < dsCaLam.length; i++) {
                if (dsCaLam[i].equals(staff.getCaLamViec())) {
                    spinnerShift.setSelection(i);
                    break;
                }
            }

            // 4. Xử lý khi bấm nút Cập nhật
            btnSave.setOnClickListener(v1 -> {
                String newShift = spinnerShift.getSelectedItem().toString();
                String newSalaryStr = edtSalary.getText().toString();

                if(newShift.isEmpty() || newSalaryStr.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu lại dữ liệu mới vào Model
                staff.setCaLamViec(newShift);
                staff.setMucLuong(Integer.parseInt(newSalaryStr));

                // Cập nhật lại giao diện cái Thẻ nhân viên ngay lập tức
                notifyItemChanged(position);
                Toast.makeText(context, "Đã cập nhật ca và lương thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Đóng bảng
            });

            btnCancel.setOnClickListener(v12 -> dialog.dismiss());
            dialog.show(); // Lệnh hiển thị bảng
        });

        // Nút Xóa (Giữ nguyên như cũ)
        holder.binding.btnDeleteStaff.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Sa thải nhân viên")
                    .setMessage("Bạn có chắc chắn muốn xóa tài khoản của " + staff.getTenNhanVien() + "?")
                    .setNegativeButton("Hủy", null)
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        listStaff.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listStaff.size());
                        Toast.makeText(context, "Đã xóa nhân viên!", Toast.LENGTH_SHORT).show();
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