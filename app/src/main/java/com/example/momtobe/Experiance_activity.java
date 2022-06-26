package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
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
    public static final String TASK_Array = "taskId";
    private static final String TAG = Experiance_activity.class.getName();
    private Handler handler;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addProduct ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Experience> taskArrayList=new ArrayList<>();
        Intent intent = getIntent();

//        ActionBar actionBar = getSupportActionBar();

//        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiance);

        addProduct = findViewById(R.id.product_add_img);

        addProduct.setOnClickListener(v -> {
            navigateToAddTask();
        });

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

//
//        RecyclerView RecycleTask = findViewById(R.id.Recycle_task);
//        Button add=findViewById(R.id.button2);
//        EditText editTextDetail=findViewById(R.id.editTextTextPersonName);
//
//        handler=new Handler(
//                Looper.getMainLooper(), msg -> {
//            RecycleModels recycleModels = new RecycleModels(taskArrayList, position -> {
//                Toast.makeText(
//                        Experiance_activity.this,
//                        "The item clicked => " + taskArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//                String titleQurey= taskArrayList.get(position).getTitle();
//                String StatusQuery=taskArrayList.get(position).getDescription();
//
//
//
//                startActivity(intent);
//            });
//            RecycleTask.setAdapter(recycleModels);
//            RecycleTask.setHasFixedSize(true);
//            RecycleTask.setLayoutManager(new LinearLayoutManager(this));
//
//            return true;
//
//        }
//        );
//        add.setOnClickListener(view -> {
//
//            Experience newExperience= Experience.builder().
//                    title("Experience").
//                    description(editTextDetail.getText().toString()).
//                    featured(true).
////                  image().
//                    build();
//
//            Amplify.API.mutate(
//                    ModelMutation.create(newExperience),
//                    response -> {
//                        Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getTitle());
//                        handler.sendEmptyMessage(1);
//
//
//                    },
//                    error -> Log.e("MyAmplifyApp", "Create failed", error)
//            );
//
//
//
//        });
//
//        Amplify.API.query(
//                ModelQuery.list(Experience.class),
//                teamsName -> {
//                    for (Experience note : teamsName.getData()) {
//                        taskArrayList.add(note);
//                    }
//
//                    handler.sendEmptyMessage(1);
//                },
//                error -> Log.e(TAG, error.toString())
//        );
//
//
//        Amplify.DataStore.observe(Experience.class,
//                started -> Log.i(TAG, "Observation began."),
//                change -> {Log.i(TAG, change.item().toString());
//
//                    Bundle bundle=new Bundle();
//                    bundle.putString(TASK_Array,change.item().toString());
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
//
//

    }

    public void navigateToAddTask(){
        startActivity(new Intent(this , AddExperianceActivity.class));
    }

}