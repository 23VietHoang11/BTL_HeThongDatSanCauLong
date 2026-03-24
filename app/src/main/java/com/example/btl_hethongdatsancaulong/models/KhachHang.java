package com.example.btl_hethongdatsancaulong.models;

public class KhachHang {
    private String id;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String matKhau;

    // Constructor trống cần thiết cho Firebase/Database sau này
    public KhachHang() {}

    public KhachHang(String hoTen, String soDienThoai, String email, String matKhau) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.matKhau = matKhau;
    }

    // Các hàm Getters / Setters
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}
