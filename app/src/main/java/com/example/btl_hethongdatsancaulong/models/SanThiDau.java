package com.example.btl_hethongdatsancaulong.models;

public class SanThiDau {
    private String tenSan;
    private String diaChi;
    private String thoiGian;
    // Dùng int để lưu ID ảnh từ thư mục drawable/mipmap
    private int hinhAnh;

    // Cập nhật Constructor để nhận thêm hinhAnh
    public SanThiDau(String tenSan, String diaChi, String thoiGian, int hinhAnh) {
        this.tenSan = tenSan;
        this.diaChi = diaChi;
        this.thoiGian = thoiGian;
        this.hinhAnh = hinhAnh;
    }

    public String getTenSan() { return tenSan; }
    public String getDiaChi() { return diaChi; }
    public String getThoiGian() { return thoiGian; }
    // Thêm Getter để lấy ảnh ra dùng bên Adapter
    public int getHinhAnh() { return hinhAnh; }
}