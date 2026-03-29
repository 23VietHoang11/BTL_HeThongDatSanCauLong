package com.example.btl_hethongdatsancaulong.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_hethongdatsancaulong.databinding.ItemAdminCourtCardBinding;
import com.example.btl_hethongdatsancaulong.models.AdminCourt;
import java.util.List;

public class AdminCourtAdapter extends RecyclerView.Adapter<AdminCourtAdapter.CourtViewHolder> {

    private List<AdminCourt> listCourt;
    private Context context;

    public AdminCourtAdapter(Context context, List<AdminCourt> listCourt) {
        this.context = context;
        this.listCourt = listCourt;
    }

    @NonNull
    @Override
    public CourtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminCourtCardBinding binding = ItemAdminCourtCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CourtViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourtViewHolder holder, int position) {
        AdminCourt san = listCourt.get(position);

        holder.binding.tvAdminCourtTitle.setText(san.getTenSan());
        holder.binding.tvAdminCourtPrice.setText("Giá: " + san.getGiaTien() + " / giờ");
        holder.binding.tvAdminCourtStatus.setText(san.getTrangThai());

        // --- NÚT SỬA SÂN ---
        holder.binding.btnAdminEditCourt.setOnClickListener(v -> {
            Toast.makeText(context, "Mở màn hình Chỉnh sửa sân: " + san.getTenSan(), Toast.LENGTH_SHORT).show();
            // Lát nữa làm trang Sửa sân thì gọi Intent ở đây
        });

        // --- NÚT XÓA SÂN (CÓ CẢNH BÁO AN TOÀN) ---
        holder.binding.btnAdminDeleteCourt.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Cảnh báo Xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa " + san.getTenSan() + " khỏi hệ thống không?")
                    .setNegativeButton("Hủy", null)
                    .setPositiveButton("Xóa luôn", (dialog, which) -> {
                        listCourt.remove(position); // Xóa khỏi danh sách
                        notifyItemRemoved(position); // Hiệu ứng xóa mượt mà
                        notifyItemRangeChanged(position, listCourt.size()); // Cập nhật lại vị trí
                        Toast.makeText(context, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listCourt.size();
    }

    public static class CourtViewHolder extends RecyclerView.ViewHolder {
        ItemAdminCourtCardBinding binding;

        public CourtViewHolder(ItemAdminCourtCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}