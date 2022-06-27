package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Cat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navToActivity();
//        Cat Cat= com.amplifyframework.datastore.generated.model.Cat.builder()
//                .title("ayyoub-1")
//                .description("sdsadddddddddddgfance")
//                .build();
//        Cat Cat1=com.amplifyframework.datastore.generated.model.Cat.builder()
//                .title("ayyoub-2")
//                .description("sdsadddddddddddgfance")
//                .build();
//
//        Amplify.API.mutate(
//                ModelMutation.create(Cat),
//                    response -> {
//                        Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId());
//                        },
//                    error -> Log.e("MyAmplifyApp", "Create failed", error)
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(Cat1),
//                    response -> {
//                        Amplify.API.query(
//                                ModelQuery.list(Cat.class,com.amplifyframework.datastore.generated.model.Cat.TITLE.contains("ayyoub")),
//                                res -> {
//                                    Log.i("TAG", "onCreate: " +res);
//                                },
//                                error->{
//                                    Log.e("TAG", "onCreate: ",error );
//                                }
//                        );
//                        Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId());
//                        },
//                    error -> Log.e("MyAmplifyApp", "Create failed", error)
//        );


    }

    private void navToActivity(){

        /**
         * bottom Navigation Bar
         */
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home_page);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home_page:
                        return true;
                    case R.id.exp_page:
                        startActivity(new Intent(getApplicationContext(),Experiance_activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.blogs_page:
                        startActivity(new Intent(getApplicationContext(),Blog.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.market_page:
                        startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.question_page:
                        startActivity(new Intent(getApplicationContext(), Question_avtivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

    }


}