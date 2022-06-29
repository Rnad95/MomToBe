package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Experience;

import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.Question;

import com.example.momtobe.ui.ProductActivity;

import com.example.momtobe.ui.AddExperianceActivity;
import com.example.momtobe.ui.AddProductActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class Experiance_activity extends AppCompatActivity {
    private static final String TAG = Experiance_activity.class.getName();
    
    private static final String EXPERIANCE_Array ="weasm";
    private Handler handler;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addExperince;
    private RecyclerView recycleExperince;
    public static final String experianceName = "ExperianceName";
    public static final String MotherExperiences = "motherExperiences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        Intent intent = getIntent();

//        ActionBar actionBar = getSupportActionBar();

//        actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiance);
        ArrayList<Experience> taskArrayList=new ArrayList<>();

        addExperince = findViewById(R.id.Question_add_img);

        addExperince.setOnClickListener(v ->{

            Intent intent1=new Intent(this,AddExperianceActivity.class);
            startActivity(intent1);

        } );

        /**
         * bottom Navigation Bar
         */
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.exp_page);
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
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.question_page:
                        startActivity(new Intent(getApplicationContext(), Question_avtivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        recycleExperince = findViewById(R.id.Recycle_Comment);


        handler=new Handler(
                Looper.getMainLooper(), msg -> {
            RecycleModels recycleModels = new RecycleModels(getApplicationContext(),taskArrayList, position -> {
                Toast.makeText(
                        Experiance_activity.this,
                        "The item clicked => " + taskArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),CommentActivity_Eperiance.class);
                String experianceId=taskArrayList.get(position).getId();
                String MotherExperiencesId=taskArrayList.get(position).getMotherExperiencesId();
                intent.putExtra(experianceName,experianceId);
                intent.putExtra(MotherExperiences,MotherExperiencesId);
                startActivity(intent);
            });
            recycleExperince.setAdapter(recycleModels);
            recycleExperince.setHasFixedSize(true);
            recycleExperince.setLayoutManager(new LinearLayoutManager(this));
            return true;
        }
        );
       
    
 
        Amplify.API.query(
                ModelQuery.list(Experience.class),
                teamsName -> {
                    for (Experience experince : teamsName.getData()) {
                        taskArrayList.add(experince);
                    }

                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, error.toString())
        );

//        Amplify.DataStore.observe(Experience.class,
//                started -> Log.i(TAG, "Observation began."),
//                change -> {Log.i(TAG, change.item().toString());
//
//                    Bundle bundle=new Bundle();
//                    bundle.putString(EXPERIANCE_Array,change.item().toString());
//
//                    Message message=new Message();
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//
//
//                },
//                failure -> Log.e(TAG, "Observation failed.", failure),
//                () -> Log.i(TAG, "Observation complete.")
//        );

    }
}