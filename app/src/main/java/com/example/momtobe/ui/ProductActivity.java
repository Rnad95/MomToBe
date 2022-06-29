package com.example.momtobe.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
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
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    FloatingActionButton addProduct ;
    private List<Product> myProducts;

    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        navigationBar();
//        navToActivity();
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



    @Override
    protected void onResume() {

        myProducts = new ArrayList<>();
        Handler handler = new Handler(Looper.getMainLooper() , msg -> {
            RecyclerView recyclerView = findViewById(R.id.product_archive_recycler);

            ProductCustomAdapter customRecyclerView = new ProductCustomAdapter(myProducts, new ProductCustomAdapter.CustomClickListener() {
                @Override
                public void onTaskItemClicked(int position) {
                    Intent productDetailActivity = new Intent(getApplicationContext() , ProductDetailsActivity.class);
                    productDetailActivity.removeExtra("id");
                    productDetailActivity.putExtra("id" ,  myProducts.get(position).getId().toString());
                    startActivity(productDetailActivity);
                }
            });

            recyclerView.setAdapter(customRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            return true ;
        });


        Amplify.API.query(
                ModelQuery.list(Product.class),
                response -> {

                    for (Product product : response.getData()) {
                        myProducts.add(product);
                    }
                    Log.i("tasks" , myProducts.toString()) ;
                    Bundle bundle = new Bundle();
                    bundle.putString("data" , "Done");
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);

                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );





        super.onResume();
    }


    public void navigationBar() {
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.market_page);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.exp_page:
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
                        startActivity(new Intent(getApplicationContext(), Question_avtivity.class));
                        overridePendingTransition(0, 0);
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