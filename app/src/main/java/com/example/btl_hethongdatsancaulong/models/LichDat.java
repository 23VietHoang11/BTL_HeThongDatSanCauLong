package com.example.btl_hethongdatsancaulong.models;

public class LichDat {
    private String tenSan;
    private String ngayDat;
    private String thoiGian;
    private String tongTien;
    private String trangThai; // Ví dụ: "Thành công", "Đã hủy"

    public LichDat(String tenSan, String ngayDat, String thoiGian, String tongTien, String trangThai) {
        this.tenSan = tenSan;
        this.ngayDat = ngayDat;
        this.thoiGian = thoiGian;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getTenSan() { return tenSan; }
    public String getNgayDat() { return ngayDat; }
    public String getThoiGian() { return thoiGian; }
    public String getTongTien() { return tongTien; }
    public String getTrangThai() { return trangThai; }
}