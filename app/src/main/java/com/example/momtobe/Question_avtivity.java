package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

import android.widget.CheckBox;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Cat;
import com.amplifyframework.datastore.generated.model.Question;

import com.amplifyframework.datastore.generated.model.QuestionCategories;
import com.example.momtobe.ui.ProductActivity;


import com.example.momtobe.ui.AddQuestionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Question_avtivity extends AppCompatActivity {

    public static final String QUESTION_Array = "questionId";
    private static final String TAG = Question_avtivity.class.getName();
    private Handler handler;
    FloatingActionButton addQuestion;
    public static final String Questionid = "questionId";
    private String userId  =  "" ;
    private Handler handler1;



    BottomNavigationView bottomNavigationView;
    private ArrayList<Question> taskArrayList;
    private RecyclerView recycleTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_avtivity);
        taskArrayList = new ArrayList<>();

        recycleTask = findViewById(R.id.Recycle_Question);
        navToActivities();



        DrawerLayout drawerLayout=findViewById(R.id.drawerlayout);
        findViewById(R.id.imageMenu).setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);

        });

        NavigationView navigationView=findViewById(R.id.NavigationView);
        navigationView.setItemIconTintList(null);


        setHandler();



        CheckBox dualcamera1=findViewById(R.id.checkBox);
        CheckBox dualcamera2=findViewById(R.id.checkBox2);
        CheckBox dualcamera3=findViewById(R.id.checkBox3);
        CheckBox dualcamera4=findViewById(R.id.checkBox4);
        CheckBox dualcamera5=findViewById(R.id.checkBox5);
        CheckBox dualcamera6=findViewById(R.id.checkBox6);


        Button filter=findViewById(R.id.filter);
        AtomicInteger m= new AtomicInteger();
        filter.setOnClickListener(view -> {
            taskArrayList.removeAll(taskArrayList);
            if(dualcamera1.isChecked())
            {
                m.getAndIncrement(); // you can save this as checked somewhere
                Toast.makeText(this, "checkBox:"+m, Toast.LENGTH_SHORT).show();
                Amplify.API.query(
                        ModelQuery.get(Cat.class,"95a17e76-c15e-4e01-9ffc-0fdccd247a9c"),
                        teamsName -> {
                            for ( QuestionCategories question: teamsName.getData().getQuestions()) {
                                taskArrayList.add(question.getQuestion());
                            }
                            Log.i(TAG, "onCreate filter by ayyaub 1: "+teamsName.getData().getQuestions());
                            handler.sendEmptyMessage(1);
                        },
                        error -> Log.e(TAG, error.toString())
                );

            }
            if(dualcamera2.isChecked())
            {
                m.getAndIncrement(); // you can save this as checked somewhere
                Toast.makeText(this, "checkBox2:"+m, Toast.LENGTH_SHORT).show();

                Amplify.API.query(
                        ModelQuery.get(Cat.class,"c5a552ed-a35a-405b-84c3-4cf9b384a99d"),
                        teamsName -> {
                            for ( QuestionCategories question: teamsName.getData().getQuestions()) {
                                taskArrayList.add(question.getQuestion());
                            }
                            Log.i(TAG, "onCreate filter by ayyaub 2: "+teamsName.getData().getQuestions());
                            handler.sendEmptyMessage(1);
                        },
                        error -> Log.e(TAG, error.toString())
                );

            }
            if(dualcamera3.isChecked())
            {
                m.getAndIncrement(); // you can save this as checked somewhere
                Toast.makeText(this, "checkBox3:"+m, Toast.LENGTH_SHORT).show();
            }
            if(dualcamera4.isChecked())
            {
                m.getAndIncrement();
                Toast.makeText(this, "checkBox3:"+m, Toast.LENGTH_SHORT).show();
            }
            if(dualcamera5.isChecked())
            {
                m.getAndIncrement();
                Toast.makeText(this, "checkBox4:"+m, Toast.LENGTH_SHORT).show();
            }
            if(dualcamera6.isChecked())
            {
                m.getAndIncrement();
                Toast.makeText(this, "checkBox5:"+m, Toast.LENGTH_SHORT).show();
            }

            setHandler();
        });


        FloatingActionButton addQuestion = findViewById(R.id.Question_add_img);

        addQuestion.setOnClickListener(v ->{

            Intent intent1=new Intent(this, AddQuestionActivity.class);
            startActivity(intent1);

        } );
    }



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
    public void setHandler(){
        handler=new Handler(
                Looper.getMainLooper(), msg -> {
            RecycleModels_Question recycleModels = new RecycleModels_Question(getApplicationContext(),userId,taskArrayList, position -> {
                Toast.makeText(
                        Question_avtivity.this,
                        "The item clicked => " + taskArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), CommentActivity_Question.class);
                String QuestionId=taskArrayList.get(position).getId();
                intent.putExtra(Questionid,QuestionId);
                startActivity(intent);

            });
            recycleTask.setAdapter(recycleModels);
            recycleTask.setHasFixedSize(true);
            recycleTask.setLayoutManager(new LinearLayoutManager(this));
            return true;

        }
        );
        handler1 = new Handler(Looper.getMainLooper() , msg -> {

            Amplify.Auth.fetchUserAttributes(
                    attributes ->{
                        userId = attributes.get(0).getValue();

                        Bundle bundle = new Bundle();
                        bundle.putString("data" , "Done");
                        Message message = new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);
                    },
                    error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
            );


            return true ;
        });

        Amplify.API.query(
                ModelQuery.list(Question.class),
                teamsName -> {
                    for (Question question : teamsName.getData()) {
                        taskArrayList.add(question);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("data" , "Done");
                    Message message = new Message();
                    message.setData(bundle);
                    handler1.sendMessage(message);
                },
                error -> Log.e(TAG, error.toString())
        );


    }
}