package com.example.momtobe.remote;

public class Blog {

    private String id ;
    private String title;
    private String content;
    private String author;
    private String imageLink;
    private String category;
    private String date;

    public Blog(String title, String content, String author, String imageLink, String category, String date) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.imageLink = imageLink;
        this.category = category;
        this.date = date;
    }

    public Blog(String id , String title, String content, String author, String imageLink, String category, String date) {
        this.id=id ;
        this.title = title;
        this.content = content;
        this.author = author;
        this.imageLink = imageLink;
        this.category = category;
        this.date = date ;
    }

    public Blog(String title, String content,String imageLink) {
        this.title = title;
        this.content = content;
        this.imageLink = imageLink;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Blog{" +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
