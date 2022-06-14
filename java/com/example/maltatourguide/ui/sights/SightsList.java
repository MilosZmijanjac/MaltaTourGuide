package com.example.maltatourguide.ui.sights;

public class SightsList {
    private final int id;
    private final String name;
    private final String image;
    private final String description;
    private final String longitude;
    private final String latitude;

    public SightsList(int id, String name, String image, String description, String longitude, String latitude) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
