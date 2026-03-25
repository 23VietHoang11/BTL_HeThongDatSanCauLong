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

    // Interface để bắt sự kiện khi bấm nút "ĐẶT LỊCH"
    public interface OnItemClickListener {
        void onBookClick(SanThiDau san);
    }
    private OnItemClickListener listener;

    public VenueAdapter(List<SanThiDau> listSan, OnItemClickListener listener) {
        this.listSan = listSan;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVenueCardBinding binding = ItemVenueCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new VenueViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        SanThiDau san = listSan.get(position);

        // Đổ dữ liệu vào các thẻ TextView trong item_venue_card.xml
        holder.binding.tvVenueName.setText(san.getTenSan());
        holder.binding.tvVenueAddress.setText(san.getDiaChi());
        holder.binding.tvVenueTime.setText(san.getThoiGian());

        // Bắt sự kiện bấm nút Đặt lịch
        holder.binding.btnBook.setOnClickListener(v -> listener.onBookClick(san));
    }

    @Override
    public int getItemCount() {
        return listSan.size();
    }

    public static class VenueViewHolder extends RecyclerView.ViewHolder {
        ItemVenueCardBinding binding;

        public VenueViewHolder(ItemVenueCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}