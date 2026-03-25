package com.example.btl_hethongdatsancaulong.models;

public class SanThiDau {
    private String tenSan;
    private String diaChi;
    private String thoiGian;
    // Tạm dùng String hoặc int để lưu ID ảnh (sau này lấy từ API tính sau)

    public SanThiDau(String tenSan, String diaChi, String thoiGian) {
        this.tenSan = tenSan;
        this.diaChi = diaChi;
        this.thoiGian = thoiGian;
    }

    public String getTenSan() { return tenSan; }
    public String getDiaChi() { return diaChi; }
    public String getThoiGian() { return thoiGian; }
}