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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.Switch;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Question;

import com.example.momtobe.ui.ProductActivity;


import com.example.momtobe.ui.AddQuestionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;

public class Question_avtivity extends AppCompatActivity {

    public static final String TASK_Array = "taskId";
    private static final String TAG = Question_avtivity.class.getName();
    private Handler handler;
    FloatingActionButton addProduct ;
    Switch switchAB;
    Switch simpleSwitch1, simpleSwitch2,simpleSwitch3,simpleSwitch4,simpleSwitch5,simpleSwitch6;


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Question> taskArrayList=new ArrayList<>();


        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_avtivity);
         navToActivities();
        RecyclerView RecycleTask = findViewById(R.id.Recycle_task);

        DrawerLayout drawerLayout=findViewById(R.id.drawerlayout);
        findViewById(R.id.imageMenu).setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);

        });

NavigationView navigationView=findViewById(R.id.NavigationView);
        navigationView.setItemIconTintList(null);

        handler=new Handler(
                Looper.getMainLooper(), msg -> {
            RecycleModels_Question recycleModels = new RecycleModels_Question(taskArrayList, position -> {
                Toast.makeText(
                        Question_avtivity.this,
                        "The item clicked => " + taskArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                String titleQurey= taskArrayList.get(position).getTitle();
                String StatusQuery=taskArrayList.get(position).getDescription();



                startActivity(intent);
            });
            RecycleTask.setAdapter(recycleModels);
            RecycleTask.setHasFixedSize(true);
            RecycleTask.setLayoutManager(new LinearLayoutManager(this));

            return true;

        }
        );
        addProduct = findViewById(R.id.product_add_img);

        addProduct.setOnClickListener(v -> {
            navigateToAddTask();
        });



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

        simpleSwitch1 =  findViewById(R.id.switch1);
        simpleSwitch2 =  findViewById(R.id.switch2);
        simpleSwitch3 =  findViewById(R.id.switch3);
        simpleSwitch4 =  findViewById(R.id.switch4);
        simpleSwitch5 =  findViewById(R.id.switch5);
        simpleSwitch6 =  findViewById(R.id.switch6);
        Button filter=findViewById(R.id.filter);
        filter.setOnClickListener(view -> {
        String statusSwitch1, statusSwitch2,statusSwitch3,statusSwitch4,statusSwitch5,statusSwitch6;
                if (simpleSwitch1.isChecked()){
                    statusSwitch1 = simpleSwitch1.getTextOn().toString();
                    Toast.makeText(getApplicationContext(), "Switch1 on"+statusSwitch1, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onCreate statusSwitch1: "+statusSwitch1);
                }
                if (simpleSwitch2.isChecked()) {
                    statusSwitch2 = simpleSwitch2.getTextOn().toString();
                    Toast.makeText(getApplicationContext(), "Switch2 on"+statusSwitch2, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onCreate statusSwitch2: "+statusSwitch2);
                }
                if (simpleSwitch3.isChecked()) {
                    statusSwitch3 = simpleSwitch3.getTextOn().toString();
                    Toast.makeText(getApplicationContext(), "Switch3 on"+statusSwitch3, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onCreate statusSwitch3: "+statusSwitch3);
                }
                if (simpleSwitch4.isChecked()) {
                    statusSwitch4 = simpleSwitch4.getTextOn().toString();
                    Toast.makeText(getApplicationContext(), "Switch4 on"+statusSwitch4, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onCreate statusSwitch4: "+statusSwitch4);
                }
                if (simpleSwitch5.isChecked()){
                    statusSwitch5 = simpleSwitch5.getTextOn().toString();
                    Toast.makeText(getApplicationContext(), "Switch5 on"+statusSwitch5, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onCreate statusSwitch5: "+statusSwitch5);
                }
                if (simpleSwitch6.isChecked()){
                    statusSwitch6 = simpleSwitch6.getTextOn().toString();
                    Toast.makeText(getApplicationContext(), "Switch6 on"+statusSwitch6, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onCreate statusSwitch6: "+statusSwitch6);
                }
        });

}
    public void navigateToAddTask(){
        startActivity(new Intent(this , AddQuestionActivity.class));
    }

//    public ScanResult getAllMemos() {
//
//        Map<String, AttributeValue> expressionAttributeValues =
//                new HashMap<String, AttributeValue>();
//        expressionAttributeValues.put(":val", new AttributeValue().withS("thumbnail"));
//
//
//        ScanRequest scanRequest = new ScanRequest()
//                .withTableName(DB_NAME)
//                .withFilterExpression("contains(imgName,:val)")
//                .withExpressionAttributeValues(expressionAttributeValues);
//        return util.getAmazonDynamoDBClient(getActivity()).scan(scanRequest);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation_menu_bar, menu);
//
//        final MenuItem searchItem = menu.findItem(R.id.app_bar_switch);
//        final SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
//
//        return true;
//    }


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
                        startActivity(new Intent(getApplicationContext(), BlogActivity.class));
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