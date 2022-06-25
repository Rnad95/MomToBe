package com.example.momtobe.ui;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.amplifyframework.datastore.generated.model.Product;
import com.example.momtobe.R;
import com.example.momtobe.adapter.ProductCustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    FloatingActionButton addProduct ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

//        ArrayList<Product> arrayList = new ArrayList<>();
//
//        Product product ;
//
//
//        product = Product.builder()
//                .title("Shoes")
//                .price(100.00)
//                .description("The best product")
//                .featured(false)
//                .build();
//


//        arrayList.add(product);

        // Navigate to add product activity

        addProduct = findViewById(R.id.product_add_img);

        addProduct.setOnClickListener(v -> {
            navigateToAddTask();
        });


    }


    public void navigateToAddTask(){
        startActivity(new Intent(this , AddProductActivity.class));
    }

}