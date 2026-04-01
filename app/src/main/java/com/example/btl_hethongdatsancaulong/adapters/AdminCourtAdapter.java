package com.example.btl_hethongdatsancaulong.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_hethongdatsancaulong.controllers.admin.AdminAddCourtActivity;
import com.example.btl_hethongdatsancaulong.databinding.ItemAdminCourtCardBinding;
import com.example.btl_hethongdatsancaulong.controllers.admin.AdminCourt;
import com.google.firebase.database.FirebaseDatabase;

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

        // --- LẤY ẢNH TĨNH TỪ DRAWABLE VÀ GẮN VÀO IMAGEVIEW ---
        holder.binding.imgAdminCourt.setImageResource(san.getHinhAnh());

        // --- NÚT SỬA SÂN ---
        holder.binding.btnAdminEditCourt.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminAddCourtActivity.class);
            intent.putExtra("COURT_ID", san.getId());
            intent.putExtra("COURT_NAME", san.getTenSan());

            String rawPrice = san.getGiaTien().replaceAll("[^0-9]", "");
            intent.putExtra("COURT_PRICE", rawPrice);

            context.startActivity(intent);
        });

        // --- NÚT XÓA SÂN ---
        holder.binding.btnAdminDeleteCourt.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Cảnh báo Xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa " + san.getTenSan() + " khỏi hệ thống không?")
                    .setNegativeButton("Hủy", null)
                    .setPositiveButton("Xóa luôn", (dialog, which) -> {
                        FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app")
                                .getReference("Courts").child(san.getId()).removeValue()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(context, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> Toast.makeText(context, "Lỗi khi xóa!", Toast.LENGTH_SHORT).show());
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