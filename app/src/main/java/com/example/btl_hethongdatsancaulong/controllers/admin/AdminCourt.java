package com.example.btl_hethongdatsancaulong.models;

public class AdminCourt {
    private String id;
    private String tenSan;
    private String giaTien;
    private String trangThai;

    public AdminCourt(String id, String tenSan, String giaTien, String trangThai) {
        this.id = id;
        this.tenSan = tenSan;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
    }

    public String getId() { return id; }
    public String getTenSan() { return tenSan; }
    public String getGiaTien() { return giaTien; }
    public String getTrangThai() { return trangThai; }
}