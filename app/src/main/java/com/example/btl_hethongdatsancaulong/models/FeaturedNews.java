package com.example.btl_hethongdatsancaulong.models;

public class FeaturedNews {
    private String title;
    private String content;
    private int imageResource; // Dùng ảnh trong folder drawable
    private String date;

    public FeaturedNews(String title, String content, int imageResource, String date) {
        this.title = title;
        this.content = content;
        this.imageResource = imageResource;
        this.date = date;
    }

    // Getter
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getImageResource() { return imageResource; }
    public String getDate() { return date; }
}