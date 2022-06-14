package com.example.maltatourguide.ui.cities.photos;

public class PhotoModel {
    private int id;
    private String originalUrl;
    private final String mediumUrl;

    public PhotoModel(int id, String originalUrl, String mediumUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.mediumUrl = mediumUrl;
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

    public String getMediumUrl() {
        return mediumUrl;
    }

}
