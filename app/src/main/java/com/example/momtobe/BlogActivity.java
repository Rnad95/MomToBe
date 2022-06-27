package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.momtobe.ui.ProductActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class BlogActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        setRecyclerView();

        /**
         * bottom Navigation Bar
         */
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.blogs_page);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.exp_page:
                        startActivity(new Intent(getApplicationContext(),Experiance_activity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.blogs_page:
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

    void setRecyclerView (){
        ArrayList<BlogActivity> blogActivities = new ArrayList<>();
        //bring blogs from API
        Handler handler = new Handler(Looper.getMainLooper() , msg -> { // this works when i send the message in the findTasksAPI() function

            ListView tasksList = findViewById(R.id.blog_archive_recycler);
            ArrayAdapter<BlogActivity> taskArrayAdapter = new ArrayAdapter<BlogActivity>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_2,
                    android.R.id.text1,
                    blogActivities
            ) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView title = (TextView) view.findViewById(android.R.id.text1);

                    title.setText(blogActivities.get(position).getTitle());

                    return view;
                }
            };
            tasksList.setAdapter(taskArrayAdapter);

            tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent taskIntent = new Intent(getApplicationContext(), BlogActivity.class);

                    taskIntent.putExtra("blog", (Parcelable) blogActivities.get(i));
                    startActivity(taskIntent);
                }
            });
            return true ; //for the handler
        });
    }



}