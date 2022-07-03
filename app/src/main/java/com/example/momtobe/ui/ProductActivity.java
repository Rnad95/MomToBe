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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Mother;
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
import java.util.Locale;
import java.util.stream.Collectors;

public class ProductActivity extends AppCompatActivity {
    FloatingActionButton addProduct ;
    private List<Product> myProducts;
    private List<Product> myList;
    private String userId , userEmail ;


    private BottomNavigationView bottomNavigationView;

    private EditText search ;

    private Handler handler;
    private Handler handler1;
    private Handler handler2;

    private Button searchBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        navigationBar();

        declareConstant();



        addProduct.setOnClickListener(v -> {
            navigateToAddProduct();
        });

        searchBtn.setOnClickListener(v -> {
            searchFunction();
        });

    }



    @Override
    protected void onResume() {

        myProducts = new ArrayList<>();
        myList = new ArrayList<>();


        handler = new Handler(Looper.getMainLooper() , msg -> {
            if (myList.isEmpty()) myList = myProducts ;
            RecyclerView recyclerView = findViewById(R.id.product_archive_recycler);
            ProductCustomAdapter customRecyclerView = new ProductCustomAdapter(getApplicationContext() , myList , userId, new ProductCustomAdapter.CustomClickListener() {
                @Override
                public void onTaskItemClicked(int position) {
                    Intent productDetailActivity = new Intent(getApplicationContext() , ProductDetailsActivity.class);
                    productDetailActivity.removeExtra("id");
                    productDetailActivity.putExtra("id" ,  myList.get(position).getId().toString());
                    productDetailActivity.putExtra("userId" , userId);
                    startActivity(productDetailActivity);
                }

            });

            recyclerView.setAdapter(customRecyclerView);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));



            return true ;
        });


        handler1 = new Handler(Looper.getMainLooper() , msg -> {

            getUserId();

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
                    handler1.sendMessage(message);

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


    public void navigateToAddProduct(){
        startActivity(new Intent(this , AddProductActivity.class));
    }

    public void declareConstant(){
        addProduct = findViewById(R.id.product_add_img);
        search = findViewById(R.id.product_archive_search);
        searchBtn = findViewById(R.id.btn_product_search);

    }

    public void searchFunction(){
                String text = search.getText().toString();

                myList = myProducts.stream().filter(index -> index.getTitle().toLowerCase().contains(text)).collect(Collectors.toList()) ;

                Log.i("My Product" , myList.toString()) ;

                Bundle bundle = new Bundle();
                bundle.putString("data" , "Done");
                Message message = new Message();
                message.setData(bundle);
                handler1.sendMessage(message);
    }

    public void getUserId(){

        handler2 = new Handler(Looper.getMainLooper() , msg -> {
            Amplify.API.query(
                    ModelQuery.list(Mother.class, Mother.EMAIL_ADDRESS.contains(userEmail)),
                    response -> {
                        for (Mother mother : response.getData()) {
                            userId =  mother.getId() ;
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("data" , "Done");
                        Message message = new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);
                    },
                    error -> Log.e("MyAmplifyApp", "Query failure", error)
            );

            return true ;
        });


        Amplify.Auth.fetchUserAttributes(
                    attributes ->{
                        Log.i("UserEmail" , attributes.toString());
                        userEmail = attributes.get(3).getValue();
                        Bundle bundle = new Bundle();
                        bundle.putString("data" , "Done");
                        Message message = new Message();
                        message.setData(bundle);
                        handler2.sendMessage(message);
                    },
                    error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

    }

}