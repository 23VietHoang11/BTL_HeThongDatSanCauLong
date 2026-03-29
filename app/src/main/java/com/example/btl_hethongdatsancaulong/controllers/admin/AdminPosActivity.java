package com.example.btl_hethongdatsancaulong.controllers.admin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.adapters.AdminServiceAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityAdminPosBinding;
import com.example.btl_hethongdatsancaulong.models.ServiceItem;
import java.util.ArrayList;
import java.util.List;

public class AdminPosActivity extends AppCompatActivity {

    private ActivityAdminPosBinding binding;
    private List<ServiceItem> menuBanHang;
    private AdminServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackPos.setOnClickListener(v -> finish());

        // 1. Lên Menu các mặt hàng
        menuBanHang = new ArrayList<>();
        menuBanHang.add(new ServiceItem("Nước Lọc Aquafina", 10000));
        menuBanHang.add(new ServiceItem("Revive Chanh Muối", 15000));
        menuBanHang.add(new ServiceItem("Bò Húc (Redbull)", 20000));
        menuBanHang.add(new ServiceItem("Trà Đá (Ca)", 10000));
        menuBanHang.add(new ServiceItem("Ống Cầu Lông Vinastar", 180000));
        menuBanHang.add(new ServiceItem("Thuê Vợt (Buổi)", 30000));

        // 2. Setup Adapter và nhận tín hiệu (Callback) từ Adapter gửi về
        adapter = new AdminServiceAdapter(menuBanHang, new AdminServiceAdapter.OnCartChangeListener() {
            @Override
            public void onCartChanged() {
                tinhTongTien(); // Mỗi lần khách bấm +/- là gọi hàm này
            }
        });

        binding.recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewServices.setAdapter(adapter);

        // 3. Xử lý nút Lên Đơn
        binding.btnCreateOrder.setOnClickListener(v -> {
            int tongTien = tinhTongTien();
            if (tongTien == 0) {
                Toast.makeText(this, "Chưa chọn mặt hàng nào!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "🎉 Lên đơn thành công! Tổng thu: " + String.format("%,d đ", tongTien), Toast.LENGTH_LONG).show();
                finish(); // Lên đơn xong thì thoát trang
            }
        });
    }

    // Hàm tính toán tổng tiền vòng lặp
    private int tinhTongTien() {
        int tong = 0;
        for (ServiceItem item : menuBanHang) {
            tong += (item.getGiaTien() * item.getSoLuong()); // Lấy giá x số lượng
        }
        // Cập nhật lên màn hình
        binding.tvPosTotalPrice.setText(String.format("%,d đ", tong));
        return tong;
    }
}