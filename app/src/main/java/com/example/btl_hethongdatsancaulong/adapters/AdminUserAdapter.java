package com.example.btl_hethongdatsancaulong.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_hethongdatsancaulong.databinding.ItemAdminUserCardBinding;
import com.example.btl_hethongdatsancaulong.models.AdminUser;
import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.UserViewHolder> {

    private List<AdminUser> listUser;

    public AdminUserAdapter(List<AdminUser> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminUserCardBinding binding = ItemAdminUserCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        AdminUser user = listUser.get(position);

        holder.binding.tvUserName.setText(user.getTenKhach());
        holder.binding.tvUserPhone.setText("📞 " + user.getSoDienThoai());
        holder.binding.tvUserSpent.setText("Đã chi tiêu: " + user.getTongChiTieu());
        holder.binding.tvUserStatus.setText(user.getTrangThai());

        // Đổi màu chữ theo trạng thái
        if (user.getTrangThai().equals("Hoạt động")) {
            holder.binding.tvUserStatus.setTextColor(Color.parseColor("#2E7D32"));
            holder.binding.tvUserStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
        } else {
            holder.binding.tvUserStatus.setTextColor(Color.parseColor("#D32F2F"));
            holder.binding.tvUserStatus.setBackgroundColor(Color.parseColor("#FFEBEE"));
        }
    }

    @Override
    public int getItemCount() { return listUser.size(); }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ItemAdminUserCardBinding binding;
        public UserViewHolder(ItemAdminUserCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}