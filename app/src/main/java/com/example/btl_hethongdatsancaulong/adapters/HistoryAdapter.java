package com.example.btl_hethongdatsancaulong.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_hethongdatsancaulong.databinding.ItemHistoryCardBinding;
import com.example.btl_hethongdatsancaulong.models.LichDat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<LichDat> listLichDat;

    public HistoryAdapter(List<LichDat> listLichDat) {
        this.listLichDat = listLichDat;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryCardBinding binding = ItemHistoryCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        LichDat lich = listLichDat.get(position);

        // --- ĐÂY LÀ LÚC XÓA SỔ HARDCODE VÀ BƠM DỮ LIỆU THẬT VÀO ---
        holder.binding.tvHistoryName.setText(lich.getTenSan());
        holder.binding.tvHistoryDate.setText("Ngày: " + lich.getNgayDat());
        holder.binding.tvHistoryTime.setText("Giờ: " + lich.getThoiGian());
        holder.binding.tvHistoryPrice.setText("Tổng: " + lich.getTongTien());
        holder.binding.tvHistoryStatus.setText(lich.getTrangThai());

        // Mẹo nhỏ: Đổi màu chữ Trạng thái cho sinh động
        if (lich.getTrangThai().equalsIgnoreCase("Đã hủy")) {
            holder.binding.tvHistoryStatus.setTextColor(Color.parseColor("#D32F2F")); // Đỏ
            holder.binding.tvHistoryStatus.setBackgroundColor(Color.parseColor("#FFEBEE")); // Nền đỏ nhạt
        } else {
            holder.binding.tvHistoryStatus.setTextColor(Color.parseColor("#4CAF50")); // Xanh
            holder.binding.tvHistoryStatus.setBackgroundColor(Color.parseColor("#E8F5E9")); // Nền xanh nhạt
        }
    }

    @Override
    public int getItemCount() {
        return listLichDat.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        ItemHistoryCardBinding binding;

        public HistoryViewHolder(ItemHistoryCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}