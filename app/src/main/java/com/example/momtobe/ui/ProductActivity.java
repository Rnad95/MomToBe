package com.example.momtobe.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.amplifyframework.datastore.generated.model.Product;
import com.example.momtobe.Blog;
import com.example.momtobe.Experiance_activity;
import com.example.momtobe.MainActivity;

import com.example.momtobe.Question_avtivity;
import com.example.momtobe.R;
import com.example.momtobe.adapter.ProductCustomAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    FloatingActionButton addProduct ;

    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        navToActivity();
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



        addProduct = findViewById(R.id.product_add_img);

        addProduct.setOnClickListener(v -> {
            navigateToAddTask();
        });


    }


    public void navigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.exp_page:
                        startActivity(new Intent(getApplicationContext(), Experiance_activity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.blogs_page:
                        startActivity(new Intent(getApplicationContext(), Blog.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.market_page:
                        startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.question_page:
                        return true;

                }
                return false;
            }
        });

    }


    public void navigateToAddTask(){
        startActivity(new Intent(this , AddProductActivity.class));
    }



}