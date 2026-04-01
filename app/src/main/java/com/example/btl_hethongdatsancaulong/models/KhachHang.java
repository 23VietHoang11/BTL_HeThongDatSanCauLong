package com.example.btl_hethongdatsancaulong.models;

public class KhachHang {
    private String id;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String matKhau;

    // Constructor trống cần thiết cho Firebase
    public KhachHang() {}

    public KhachHang(String hoTen, String soDienThoai, String email, String matKhau) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.matKhau = matKhau;
    }

    // Các hàm Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}
