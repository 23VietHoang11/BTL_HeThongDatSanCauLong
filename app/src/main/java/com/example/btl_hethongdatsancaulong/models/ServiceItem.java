package com.example.btl_hethongdatsancaulong.models;

public class ServiceItem {
    private String tenDichVu;
    private int giaTien; // Dùng kiểu số nguyên (int) để dễ nhân chia cộng trừ
    private int soLuong; // Số lượng khách đang chọn (Mặc định là 0)

    public ServiceItem(String tenDichVu, int giaTien) {
        this.tenDichVu = tenDichVu;
        this.giaTien = giaTien;
        this.soLuong = 0; // Mới mở lên thì chưa mua gì cả
    }

    public String getTenDichVu() { return tenDichVu; }
    public int getGiaTien() { return giaTien; }
    public int getSoLuong() { return soLuong; }

    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}