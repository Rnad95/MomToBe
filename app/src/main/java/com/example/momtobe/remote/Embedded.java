package com.example.momtobe.remote;


import java.util.List;

public class Embedded {
    List<Blog> blogs;

    public Embedded() {
    }

    public Embedded(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
