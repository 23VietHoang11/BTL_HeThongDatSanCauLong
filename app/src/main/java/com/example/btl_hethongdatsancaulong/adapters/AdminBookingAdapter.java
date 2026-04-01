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
import com.google.firebase.database.FirebaseDatabase;
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

        holder.binding.tvCustomerName.setText("Khách: " + donHang.getTenKhachHang());
        holder.binding.tvCustomerPhone.setText("SĐT: " + donHang.getSoDienThoai());
        holder.binding.tvAdminCourtName.setText(donHang.getTenSan());
        holder.binding.tvAdminBookingTime.setText(donHang.getThoiGian());
        holder.binding.tvAdminBookingPrice.setText(donHang.getTongTien());
        holder.binding.tvAdminBookingStatus.setText(donHang.getTrangThai());

        if (donHang.getTrangThai().equals("Chờ duyệt")) {
            holder.binding.tvAdminBookingStatus.setTextColor(Color.parseColor("#E65100"));
            holder.binding.tvAdminBookingStatus.setBackgroundColor(Color.parseColor("#FFF3E0"));
            holder.binding.layoutActionButtons.setVisibility(View.VISIBLE);
        } else if (donHang.getTrangThai().equals("Đã xác nhận")) {
            holder.binding.tvAdminBookingStatus.setTextColor(Color.parseColor("#2E7D32"));
            holder.binding.tvAdminBookingStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
            holder.binding.layoutActionButtons.setVisibility(View.GONE);
        } else {
            holder.binding.tvAdminBookingStatus.setTextColor(Color.parseColor("#D32F2F"));
            holder.binding.tvAdminBookingStatus.setBackgroundColor(Color.parseColor("#FFEBEE"));
            holder.binding.layoutActionButtons.setVisibility(View.GONE);
        }

        // --- BẮN LÊN FIREBASE KHI BẤM XÁC NHẬN ---
        holder.binding.btnAdminApprove.setOnClickListener(v -> {
            FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("Bookings").child(donHang.getMaDon()).child("trangThai").setValue("Đã xác nhận");
            Toast.makeText(v.getContext(), "Đã duyệt đơn!", Toast.LENGTH_SHORT).show();
        });

        // --- BẮN LÊN FIREBASE KHI BẤM TỪ CHỐI ---
        holder.binding.btnAdminReject.setOnClickListener(v -> {
            FirebaseDatabase.getInstance("https://db-btl-cnpm-lttbdd-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("Bookings").child(donHang.getMaDon()).child("trangThai").setValue("Đã hủy");
            Toast.makeText(v.getContext(), "Đã hủy đơn!", Toast.LENGTH_SHORT).show();
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