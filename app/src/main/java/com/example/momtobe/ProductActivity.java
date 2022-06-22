package com.example.momtobe;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amplifyframework.datastore.generated.model.Product;
import com.example.momtobe.adapter.ProductCustomAdapter;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

    }
}