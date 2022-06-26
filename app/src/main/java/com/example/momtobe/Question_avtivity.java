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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.Question;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Question_avtivity extends AppCompatActivity {

    public static final String TASK_Array = "taskId";
    private static final String TAG = Question_avtivity.class.getName();
    private Handler handler;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Question> taskArrayList=new ArrayList<>();
        Intent intent = getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_avtivity);
        navToActivities();

//        RecyclerView RecycleTask = findViewById(R.id.Recycle_task);
//        Button add=findViewById(R.id.button2);
//        EditText editTextDetail=findViewById(R.id.editTextTextPersonName);
//
//        handler=new Handler(
//                Looper.getMainLooper(), msg -> {
//            RecycleModels_Question recycleModels = new RecycleModels_Question(taskArrayList, position -> {
//                Toast.makeText(
//                        Question_avtivity.this,
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
//add.setOnClickListener(view -> {
//
//    Question newQuestion= Question.builder().
//            title("Question").
//            description(editTextDetail.getText().toString()).
//            featured(true).
//            build();
//
//    Amplify.API.mutate(
//            ModelMutation.create(newQuestion),
//            response -> {
//                Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getTitle());
//                handler.sendEmptyMessage(1);
//
//
//            },
//            error -> Log.e("MyAmplifyApp", "Create failed", error)
//    );
//
//
//
//        });
//
//        Amplify.API.query(
//                ModelQuery.list(Question.class),
//                teamsName -> {
//                    for (Question note : teamsName.getData()) {
//                        taskArrayList.add(note);
//                    }
//
//                    handler.sendEmptyMessage(1);
//                },
//                error -> Log.e(TAG, error.toString())
//        );
//
//
//        Amplify.DataStore.observe(Question.class,
//                started -> Log.i(TAG, "Observation began."),
//                change -> {Log.i(TAG, change.item().toString());
//
//                    Bundle bundle=new Bundle();
//                    bundle.putString(TASK_Array,change.item().toString());
//
//    Message message=new Message();
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//
//
//},
//        failure -> Log.e(TAG, "Observation failed.", failure),
//        () -> Log.i(TAG, "Observation complete.")
//        );
//

        }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.taskdetail, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        switch (item.getItemId()) {
//            case R.id.action_mainpage:
//                navigateToMain();
//                return true;
//            case R.id.action_Settings:
//                navigateToSettings();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void navToActivities(){

        /**
         * bottom Navigation Bar
         */
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.question_page);
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
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.question_page:
                        return true;
                }
                return false;
            }
        });
    }
}