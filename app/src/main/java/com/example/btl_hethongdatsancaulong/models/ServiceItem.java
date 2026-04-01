package com.example.btl_hethongdatsancaulong.models;

public class ServiceItem {
    private String tenDichVu;
    private int giaTien;
    private int hinhAnh; // Lưu id của ảnh (Ví dụ: R.drawable.nuoc_suoi)
    private int soLuong;

    public ServiceItem(String tenDichVu, int giaTien, int hinhAnh) {
        this.tenDichVu = tenDichVu;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
        this.soLuong = 0; // Mặc định số lượng ban đầu là 0
    }

    public String getTenDichVu() { return tenDichVu; }
    public int getGiaTien() { return giaTien; }
    public int getHinhAnh() { return hinhAnh; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}