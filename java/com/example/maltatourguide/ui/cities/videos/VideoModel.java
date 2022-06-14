package com.example.maltatourguide.ui.cities.videos;

public class VideoModel {
    private int id;
    private String originalUrl,imageUrl;

    public VideoModel(int id, String originalUrl, String imageUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
