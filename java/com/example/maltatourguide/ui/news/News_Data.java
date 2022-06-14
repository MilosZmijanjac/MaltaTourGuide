package com.example.maltatourguide.ui.news;

class News_Data {

    private String title;
    private final String author;
    private final String url;
    private final String imageURL;
    private String description;
    private final String publishedAt;

   public News_Data(String title, String author, String url,String imageUrl,String description,String publishedAt) {
       this.title = title;
       this.author= author;
       this.url = url;
       this.imageURL = imageUrl;
       this.description = description;
       this.publishedAt = publishedAt;

   }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

}
