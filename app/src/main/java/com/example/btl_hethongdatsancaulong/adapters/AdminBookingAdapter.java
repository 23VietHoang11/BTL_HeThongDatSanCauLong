package com.example.btl_hethongdatsancaulong.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_hethongdatsancaulong.databinding.ItemAdminBookingCardBinding;
import com.example.btl_hethongdatsancaulong.models.AdminBooking;
import java.util.List;

public class AdminBookingAdapter extends RecyclerView.Adapter<AdminBookingAdapter.AdminViewHolder> {

    private List<AdminBooking> listBooking;

    public AdminBookingAdapter(List<AdminBooking> listBooking) {
        this.listBooking = listBooking;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminBookingCardBinding binding = ItemAdminBookingCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        AdminBooking donHang = listBooking.get(position);

        // Đổ dữ liệu thật lên giao diện
        holder.binding.tvCustomerName.setText("Khách: " + donHang.getTenKhachHang());
        holder.binding.tvCustomerPhone.setText("SĐT: " + donHang.getSoDienThoai());
        holder.binding.tvAdminCourtName.setText(donHang.getTenSan());
        holder.binding.tvAdminBookingTime.setText(donHang.getThoiGian());
        holder.binding.tvAdminBookingPrice.setText(donHang.getTongTien());
        holder.binding.tvAdminBookingStatus.setText(donHang.getTrangThai());

        // Đổi màu sắc và Ẩn/Hiện nút bấm theo Trạng thái
        if (donHang.getTrangThai().equals("Chờ duyệt")) {
            holder.binding.tvAdminBookingStatus.setTextColor(Color.parseColor("#E65100")); // Cam
            holder.binding.tvAdminBookingStatus.setBackgroundColor(Color.parseColor("#FFF3E0"));
            holder.binding.layoutActionButtons.setVisibility(View.VISIBLE); // Hiện 2 nút duyệt
        } else if (donHang.getTrangThai().equals("Đã xác nhận")) {
            holder.binding.tvAdminBookingStatus.setTextColor(Color.parseColor("#2E7D32")); // Xanh
            holder.binding.tvAdminBookingStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
            holder.binding.layoutActionButtons.setVisibility(View.GONE); // Ẩn nút đi vì đã duyệt rồi
        } else {
            holder.binding.tvAdminBookingStatus.setTextColor(Color.parseColor("#D32F2F")); // Đỏ (Từ chối)
            holder.binding.tvAdminBookingStatus.setBackgroundColor(Color.parseColor("#FFEBEE"));
            holder.binding.layoutActionButtons.setVisibility(View.GONE);
        }

        // --- XỬ LÝ NÚT XÁC NHẬN ---
        holder.binding.btnAdminApprove.setOnClickListener(v -> {
            donHang.setTrangThai("Đã xác nhận");
            notifyItemChanged(position); // Báo cho danh sách load lại dòng này ngay lập tức
            Toast.makeText(v.getContext(), "Đã duyệt đơn " + donHang.getMaDon(), Toast.LENGTH_SHORT).show();
        });

        // --- XỬ LÝ NÚT TỪ CHỐI ---
        holder.binding.btnAdminReject.setOnClickListener(v -> {
            donHang.setTrangThai("Đã hủy");
            notifyItemChanged(position);
            Toast.makeText(v.getContext(), "Đã từ chối đơn " + donHang.getMaDon(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listBooking.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        ItemAdminBookingCardBinding binding;

        public AdminViewHolder(ItemAdminBookingCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}