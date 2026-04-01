package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_hethongdatsancaulong.databinding.ItemAdminServiceCardBinding;
import com.example.btl_hethongdatsancaulong.models.ServiceItem;

import java.util.List;

public class AdminServiceAdapter extends RecyclerView.Adapter<AdminServiceAdapter.ServiceViewHolder> {

    private List<ServiceItem> listService;
    private OnCartChangeListener listener;

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    public AdminServiceAdapter(List<ServiceItem> listService, OnCartChangeListener listener) {
        this.listService = listService;
        this.listener = listener;
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

        holder.binding.tvServiceName.setText(item.getTenDichVu());
        holder.binding.tvServicePrice.setText(String.format("%,d đ", item.getGiaTien()));
        holder.binding.tvServiceQuantity.setText(String.valueOf(item.getSoLuong()));

        // --- HIỂN THỊ ẢNH ---
        holder.binding.ivServiceThumb.setImageResource(item.getHinhAnh());

        // Xử lý nút Trừ
        holder.binding.btnDecrease.setOnClickListener(v -> {
            if (item.getSoLuong() > 0) {
                item.setSoLuong(item.getSoLuong() - 1);
                holder.binding.tvServiceQuantity.setText(String.valueOf(item.getSoLuong()));
                if (listener != null) listener.onCartChanged();
            }
        });

        // Xử lý nút Cộng
        holder.binding.btnIncrease.setOnClickListener(v -> {
            item.setSoLuong(item.getSoLuong() + 1);
            holder.binding.tvServiceQuantity.setText(String.valueOf(item.getSoLuong()));
            if (listener != null) listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return listService != null ? listService.size() : 0;
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ItemAdminServiceCardBinding binding;
        public ServiceViewHolder(ItemAdminServiceCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}