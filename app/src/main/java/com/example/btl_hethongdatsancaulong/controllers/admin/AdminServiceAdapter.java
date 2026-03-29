package com.example.btl_hethongdatsancaulong.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_hethongdatsancaulong.databinding.ItemAdminServiceCardBinding;
import com.example.btl_hethongdatsancaulong.models.ServiceItem;
import java.util.List;

public class AdminServiceAdapter extends RecyclerView.Adapter<AdminServiceAdapter.ServiceViewHolder> {

    private List<ServiceItem> listService;
    private OnCartChangeListener cartChangeListener; // Cầu nối gửi tín hiệu về Activity

    // Giao diện (Interface) để báo cáo khi có thay đổi số lượng
    public interface OnCartChangeListener {
        void onCartChanged();
    }

    public AdminServiceAdapter(List<ServiceItem> listService, OnCartChangeListener listener) {
        this.listService = listService;
        this.cartChangeListener = listener;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminServiceCardBinding binding = ItemAdminServiceCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ServiceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceItem item = listService.get(position);

        // Hiển thị Tên và Giá
        holder.binding.tvServiceName.setText(item.getTenDichVu());
        // Định dạng lại số tiền cho đẹp (VD: 15000 -> 15.000 đ)
        holder.binding.tvServicePrice.setText(String.format("%,d đ", item.getGiaTien()));
        holder.binding.tvQuantity.setText(String.valueOf(item.getSoLuong()));

        // --- NÚT CỘNG ---
        holder.binding.btnAdd.setOnClickListener(v -> {
            item.setSoLuong(item.getSoLuong() + 1); // Tăng số lượng lên 1
            holder.binding.tvQuantity.setText(String.valueOf(item.getSoLuong())); // Cập nhật số trên màn hình
            cartChangeListener.onCartChanged(); // Báo cho Activity tính lại tiền
        });

        // --- NÚT TRỪ ---
        holder.binding.btnMinus.setOnClickListener(v -> {
            if (item.getSoLuong() > 0) { // Chỉ cho trừ khi số lượng > 0 (không âm được)
                item.setSoLuong(item.getSoLuong() - 1);
                holder.binding.tvQuantity.setText(String.valueOf(item.getSoLuong()));
                cartChangeListener.onCartChanged(); // Báo cho Activity tính lại tiền
            }
        });
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ItemAdminServiceCardBinding binding;

        public ServiceViewHolder(ItemAdminServiceCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}