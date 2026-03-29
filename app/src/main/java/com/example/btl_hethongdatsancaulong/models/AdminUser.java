package com.example.btl_hethongdatsancaulong.models;

public class AdminUser {
    private String tenKhach;
    private String soDienThoai;
    private String tongChiTieu;
    private String trangThai;

    public AdminUser(String tenKhach, String soDienThoai, String tongChiTieu, String trangThai) {
        this.tenKhach = tenKhach;
        this.soDienThoai = soDienThoai;
        this.tongChiTieu = tongChiTieu;
        this.trangThai = trangThai;
    }

    public String getTenKhach() { return tenKhach; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getTongChiTieu() { return tongChiTieu; }
    public String getTrangThai() { return trangThai; }
}