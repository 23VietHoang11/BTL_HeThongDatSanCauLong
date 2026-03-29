package com.example.btl_hethongdatsancaulong.models;

public class AdminBooking {
    private String maDon;
    private String tenKhachHang;
    private String soDienThoai;
    private String tenSan;
    private String thoiGian;
    private String tongTien;
    private String trangThai;

    public AdminBooking(String maDon, String tenKhachHang, String soDienThoai, String tenSan, String thoiGian, String tongTien, String trangThai) {
        this.maDon = maDon;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.tenSan = tenSan;
        this.thoiGian = thoiGian;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getMaDon() { return maDon; }
    public String getTenKhachHang() { return tenKhachHang; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getTenSan() { return tenSan; }
    public String getThoiGian() { return thoiGian; }
    public String getTongTien() { return tongTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; } // Hàm này để Admin cập nhật trạng thái
}