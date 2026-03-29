package com.example.btl_hethongdatsancaulong.models;

public class AdminStaff {
    private String tenNhanVien;
    private String soDienThoai;
    private String caLamViec;
    private double mucLuong; // THÊM BIẾN NÀY

    public AdminStaff(String tenNhanVien, String soDienThoai, String caLamViec, int mucLuong) {
        this.tenNhanVien = tenNhanVien;
        this.soDienThoai = soDienThoai;
        this.caLamViec = caLamViec;
        this.mucLuong = mucLuong;
    }

    // Các hàm Getter lấy dữ liệu
    public String getTenNhanVien() { return tenNhanVien; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getCaLamViec() { return caLamViec; }
    public double getMucLuong() { return mucLuong; }

    public void setCaLamViec(String caLamViec) { this.caLamViec = caLamViec; }
    public void setMucLuong(int mucLuong) { this.mucLuong = mucLuong; }
}