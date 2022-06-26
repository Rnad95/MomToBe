package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.momtobe.adapter.BlogCustomAdapter;
import com.example.momtobe.data.BlogDetails;

import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {

    List<BlogDetails> blogs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

    }


}