package com.example.btl_hethongdatsancaulong.controllers.admin;

public class AdminCourt {
    private String id;
    private String tenSan;
    private String giaTien;
    private String trangThai;
    private int hinhAnh; // DÙNG int ĐỂ LƯU ẢNH DRAWABLE TĨNH

    public AdminCourt(String id, String tenSan, String giaTien, String trangThai, int hinhAnh) {
        this.id = id;
        this.tenSan = tenSan;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
    }

    public String getId() { return id; }
    public String getTenSan() { return tenSan; }
    public String getGiaTien() { return giaTien; }
    public String getTrangThai() { return trangThai; }
    public int getHinhAnh() { return hinhAnh; } // Trả về ID ảnh
}