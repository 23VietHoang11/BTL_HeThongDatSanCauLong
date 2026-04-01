package com.example.btl_hethongdatsancaulong.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_hethongdatsancaulong.databinding.ItemVenueCardBinding;
import com.example.btl_hethongdatsancaulong.models.SanThiDau;
import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueViewHolder> {

    private List<SanThiDau> listSan;
    private OnItemClickListener listener;

    public interface OnItemClickListener { void onBookClick(SanThiDau san); }

    public VenueAdapter(List<SanThiDau> listSan, OnItemClickListener listener) {
        this.listSan = listSan;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VenueViewHolder(ItemVenueCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        SanThiDau san = listSan.get(position);
        holder.binding.tvVenueName.setText(san.getTenSan());
        holder.binding.tvVenueAddress.setText(san.getDiaChi());
        holder.binding.tvVenueTime.setText(san.getThoiGian());

        // --- ĐÃ CẬP NHẬT Ở ĐÂY ---
        // 1. Gắn ảnh cho hình nền to (img_cover)
        holder.binding.imgCover.setImageResource(san.getHinhAnh());

        // 2. THÊM DÒNG NÀY: Gắn ảnh y hệt cho logo nhỏ (img_venue_logo)
        holder.binding.imgVenueLogo.setImageResource(san.getHinhAnh());

        holder.binding.btnBook.setOnClickListener(v -> listener.onBookClick(san));
    }

    @Override public int getItemCount() { return listSan.size(); }

    public static class VenueViewHolder extends RecyclerView.ViewHolder {
        ItemVenueCardBinding binding;
        public VenueViewHolder(ItemVenueCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}