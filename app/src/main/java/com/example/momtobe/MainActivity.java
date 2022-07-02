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

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.Question;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.momtobe.adapter.mainAdapter;
import com.example.momtobe.registration.LoginActivity;

import com.example.momtobe.ui.ProductActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    BottomNavigationView bottomNavigationView;
    private Button mProfileBtn;
    private Button mSettingBtn;
    private Button mLogoutBtn;
    private Button mFavoriteBtn;
    private String showEmail;
    private String url ="https://jsonkeeper.com/b/WAVV";
    Handler handler;
    mainAdapter adapter;
    RecyclerView recyclerView;
    ListView listView;
    TextView mViewAll;
    List<com.amplifyframework.datastore.generated.model.Blog> blogs;
    List<com.example.momtobe.remote.Blog> blogsListTest= new ArrayList<>();;
    ArrayList<String> taskArrayList=new ArrayList<>();
    private Handler handler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navToActivity();

        itemsSelector();
        ButtonOnListener();
//        GetEmail();


//        TextView mEmail = findViewById(R.id.main_email);
//        mEmail.setText(showEmail);

        mViewAll = findViewById(R.id.view_all_blogs);
        mViewAll.setOnClickListener(view -> {
            Intent intent = new Intent(this, Blog.class);
            startActivity(intent);
        });

        try {
            CallAPI();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setRecyclerViewForBlogs();
        getQuestions();
        setRecyclerViewForQuestion();

    }
    private void setRecyclerViewForQuestion(){
        listView = findViewById(R.id.list_view);
        handler1 = new Handler(
                Looper.getMainLooper(), msg -> {
            ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,taskArrayList);
            listView.setAdapter(adapter);
            return true;

        }
        );

    }
    private void getQuestions(){
        Amplify.API.query(
                ModelQuery.list(Question.class),
                teamsName -> {
                    for (Question question : teamsName.getData()) {
                        taskArrayList.add(question.getDescription());
                    }

                    handler1.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, error.toString())
        );
    }
    private void setRecyclerViewForBlogs(){

        handler = new Handler(Looper.getMainLooper() , msg -> {
            recyclerView = findViewById(R.id.main2_recycler_view);
            mainAdapter blogCustomAdapter = new mainAdapter(blogsListTest, new mainAdapter.CustomClickListener() {
                @Override
                public void onTaskItemClicked(int position) {
                    Intent intent = new Intent(getApplicationContext(), BlogContentes.class);
                    intent.putExtra("title",blogsListTest.get(position).getTitle());
                    intent.putExtra("content",blogsListTest.get(position).getContent());
                    intent.putExtra("author",blogsListTest.get(position).getAuthor());
                    intent.putExtra("imageLink",blogsListTest.get(position).getImageLink());
                    intent.putExtra("category",blogsListTest.get(position).getCategory());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(blogCustomAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
            return true ;
        });
    }
    private void CallAPI() throws IOException {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONObject("_embedded").getJSONArray("blogs");

                        Gson gson = new Gson();
                        String json = gson.toJson(jsonArray);
                        List<JsonObject> arrayList = new ArrayList();
                        arrayList = gson.fromJson(jsonArray.toString(),ArrayList.class);

                        for (int i = 0; i < arrayList.toArray().length; i++) {
                            Log.i(TAG, "CallAPI: SIZE =>"+ arrayList.size());
                            Object getrow = arrayList.get(i);
                            LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;
                            String title = t.get("title").toString();
                            String content = t.get("content").toString();

                            com.example.momtobe.remote.Blog blog = new com.example.momtobe.remote.Blog(title,content);
                            blogsListTest.add(blog);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("data" , "Done");
                        Message message = new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);



                    } catch (JSONException e) {
                        Log.e(TAG, "CallAPI: FROM CATCH ");
                    }
                },
                error -> {
                    Log.e(TAG, "CallAPI: ", error);
                }
        );
        queue.add(jsonObjectRequest);
    }
    private void SentEmailToUserActivity(){
        Intent intent = new Intent(MainActivity.this, Profile.class);
        intent.putExtra("EMAIL_ADDRESS",showEmail);
        startActivity( intent);
    }
    private void SentEmailToSettingsActivity(){
        Intent intent = new Intent(MainActivity.this, Settings.class);
        intent.putExtra("EMAIL_ADDRESS",showEmail);
        startActivity( intent);
    }
    private void GetEmail(){
        Bundle bundle = getIntent().getExtras();
        showEmail = bundle.getString("EMAIL");
    }
    private void itemsSelector() {
        mProfileBtn = findViewById(R.id.profile);
        mLogoutBtn = findViewById(R.id.log_out);
        mFavoriteBtn = findViewById(R.id.main_favorite);


    }
    private void ButtonOnListener(){
        mProfileBtn.setOnClickListener(view ->{
            SentEmailToUserActivity();
        });

        mLogoutBtn.setOnClickListener(view-> {
            logout();
        });
        mFavoriteBtn.setOnClickListener(view-> {
            startActivity(new Intent(MainActivity.this,SavedActivity.class));
        });
    }
    private void navigateToProfile(){
        Intent intent = new Intent(MainActivity.this,Profile.class);
        startActivity(intent);

    }
    private void navigateToSetting(){
        Intent intent = new Intent(MainActivity.this,Settings.class);
        startActivity(intent);

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
    private void logout() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    authSession();
                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );
    }
    private void authSession() {
        Amplify.Auth.fetchAuthSession(
                result -> Log.i(TAG, "Auth Session" + result.toString()),
                error -> Log.e(TAG, error.toString())
        );
    }

}