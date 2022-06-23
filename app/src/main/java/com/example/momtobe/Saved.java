package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.momtobe.data.BlogDetails;

import java.util.ArrayList;
import java.util.List;

public class Saved extends AppCompatActivity {

    List<BlogDetails> blogs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        /**
         * Recycler View
         */
        init_data();
        RecyclerView recyclerView = findViewById(R.id.saved_recycler_view);
        Adapter adapter = new Adapter(blogs);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void init_data(){
        blogs.add(new BlogDetails("Baby age", "Babies stuff"));
        blogs.add(new BlogDetails("Baby age", "Babies stuff"));
        blogs.add(new BlogDetails("Baby age", "Babies stuff"));
        blogs.add(new BlogDetails("Baby age", "Babies stuff"));
        blogs.add(new BlogDetails("Baby age", "Babies stuff"));
        blogs.add(new BlogDetails("Baby age", "Babies stuff"));
        blogs.add(new BlogDetails("Baby age", "Babies stuff"));

    }
}