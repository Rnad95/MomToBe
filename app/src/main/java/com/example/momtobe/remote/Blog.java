package com.example.momtobe.remote;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//
//@Data
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
public class Blog {


    private Long id;
    private String title;
    private String Content;
    private String Author;
    private String imageLink;
    private String Category;

    public Blog() {
    }

    public Blog(String title, String content, String author, String imageLink, String category) {
        this.title = title;
        Content = content;
        Author = author;
        this.imageLink = imageLink;
        Category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
//        package com.example.momtobe.remote;
//
//import java.util.List;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//
//@Data
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//public class Blog {
//
//    private Long id;
//    private String title;
//    private String Content;
//    private String Author;
//    private String imageLink;
//    private String Category;
//
//}