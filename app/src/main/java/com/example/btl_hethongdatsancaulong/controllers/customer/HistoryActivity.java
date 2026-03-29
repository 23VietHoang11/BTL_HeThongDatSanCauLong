package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.adapters.HistoryAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityHistoryBinding;
import com.example.btl_hethongdatsancaulong.models.LichDat;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Cài đặt nút Quay lại (Back)
        binding.btnBackHistory.setOnClickListener(v -> finish());

        // 2. Tạo dữ liệu giả để test (Mock Data)
        List<LichDat> danhSachLich = new ArrayList<>();
        danhSachLich.add(new LichDat("Sân Cầu Lông Mỹ Đình", "26/03/2026", "18:00 - 20:00", "250.000đ", "Thành công"));
        danhSachLich.add(new LichDat("Sân Cầu Lông Xuân Thủy", "25/03/2026", "17:00 - 19:00", "200.000đ", "Đã hủy"));
        danhSachLich.add(new LichDat("Sân Cầu Lông ĐHSP", "20/03/2026", "19:00 - 21:00", "180.000đ", "Thành công"));
        danhSachLich.add(new LichDat("Sân Cầu Lông Cầu Giấy", "15/03/2026", "06:00 - 08:00", "150.000đ", "Thành công"));

        // 3. Đổ dữ liệu vào RecyclerView thông qua Adapter
        HistoryAdapter adapter = new HistoryAdapter(danhSachLich);
        binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewHistory.setAdapter(adapter);
    }
}