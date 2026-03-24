package com.example.btl_hethongdatsancaulong.controllers;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_hethongdatsancaulong.R;

public class FeaturedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);

        // 1. Tìm các nút "ĐẶT LỊCH NGAY" màu vàng trên giao diện bằng ID
        TextView btnBook1 = findViewById(R.id.btnBookNowCard1);
        TextView btnBook2 = findViewById(R.id.btnBookNowCard2);
        TextView btnBook3 = findViewById(R.id.btnBookNowCard3);

        // 2. Tạo một hàm dùng chung để bắt sự kiện click
        View.OnClickListener bookClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi bấm vào bất kỳ nút nào, đều gọi hàm hiển thị Dialog
                showChooseBookingTypeDialog();
            }
        };

        // 3. Gán hàm bắt sự kiện click cho cả 3 nút
        btnBook1.setOnClickListener(bookClickListener);
        btnBook2.setOnClickListener(bookClickListener);
        btnBook3.setOnClickListener(bookClickListener);
    }

    /**
     * Hàm dùng để khởi tạo và hiển thị Dialog chọn hình thức đặt (xanh hồng)
     * Sử dụng layout: dialog_choose_booking_type.xml
     */
    private void showChooseBookingTypeDialog() {
        // Tạo một đối tượng Dialog mới, sử dụng Context của Activity này
        final Dialog dialog = new Dialog(FeaturedActivity.this);

        // Yêu cầu Dialog không hiển thị tiêu đề mặc định của hệ thống
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Gán file layout XML (cái dialog xanh hồng) vào làm giao diện cho Dialog
        // Nhớ đổi tên ID cho đúng với file XML của bạn nhé (ví dụ: dialog_choose_booking_type)
        dialog.setContentView(R.layout.activity_choose_booking);

        // RẤT QUAN TRỌNG: Thiết lập nền của cửa sổ Dialog thành trong suốt.
        // Điều này giúp cho các góc bo tròn (radian) và hiệu ứng tỏa sáng của khung nền hiện ra đúng thiết kế.
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // --- Xử lý sự kiện bấm nút X để đóng dialog ---
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        if (btnClose != null) {
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss(); // Đóng dialog
                }
            });
        }

        // --- Bạn có thể viết thêm code xử lý khi bấm vào card xanh hoặc hồng ở đây ---
        // Ví dụ: Tìm ConstraintLayout card màu xanh và bắt sự kiện click để Intent chuyển sang màn hình Bảng giờ.

        // Hiển thị Dialog lên màn hình
        dialog.show();
    }
}