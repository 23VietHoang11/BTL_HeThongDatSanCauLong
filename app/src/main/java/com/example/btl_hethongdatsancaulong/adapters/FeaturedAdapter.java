package com.example.btl_hethongdatsancaulong.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_hethongdatsancaulong.databinding.ItemFeaturedCardBinding;
import com.example.btl_hethongdatsancaulong.models.FeaturedNews;
import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    private final List<FeaturedNews> newsList;
    private final OnItemClickListener listener;

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(FeaturedNews news);
    }

    public FeaturedAdapter(List<FeaturedNews> newsList, OnItemClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeaturedCardBinding binding = ItemFeaturedCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FeaturedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        FeaturedNews news = newsList.get(position);
        holder.bind(news, listener);
    }

    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
    }

    static class FeaturedViewHolder extends RecyclerView.ViewHolder {
        private final ItemFeaturedCardBinding binding;

        public FeaturedViewHolder(ItemFeaturedCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FeaturedNews news, OnItemClickListener listener) {
            binding.tvNewsTitle.setText(news.getTitle());
            binding.tvNewsDate.setText(news.getDate());
            binding.ivNewsThumb.setImageResource(news.getImageResource());

            // Xử lý khi nhấn vào nút "Chi tiết"
            binding.btnNewsDetail.setOnClickListener(v -> listener.onItemClick(news));

            // Hoặc xử lý khi nhấn vào cả item
            binding.getRoot().setOnClickListener(v -> listener.onItemClick(news));
        }
    }
}