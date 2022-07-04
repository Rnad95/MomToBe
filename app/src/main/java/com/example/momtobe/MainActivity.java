package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.Mother;
import com.amplifyframework.datastore.generated.model.Question;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.momtobe.adapter.mainAdapter;
import com.example.momtobe.registration.LoginActivity;

import com.example.momtobe.ui.ProductActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    BottomNavigationView bottomNavigationView;

    private String showEmail;
    private String url ="https://jsonkeeper.com/b/FV5T";
    private String userId;
    private String userName;

    private Handler handler;
    private Handler handler2;
    private Handler handler1;
    private Handler handlerMom;

    RecyclerView recyclerView;

    ListView listView;

    TextView mViewAll;
    TextView mFullName;

    private ImageView mImage;
    private ImageView imageView;

    List<com.example.momtobe.remote.Blog> blogsListTest= new ArrayList<>();;
    ArrayList<String> taskArrayList=new ArrayList<>();

    private Date dateParse;

    Mother mother ;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navToActivity();
        itemsSelector();
        ButtonOnListener();

        setUserInformation();

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

    @Override
    protected void onResume() {
        super.onResume();
        setUserInformation();
    }

    private void fetchUserInformation(){

        Amplify.Auth.fetchUserAttributes(
                attributes ->{
                    userId = attributes.get(0).getValue();
                    userName = attributes.get(2).getValue();
                    Bundle bundle = new Bundle();
                    bundle.putString("data" , userName);

                    Message message = new Message();
                    message.setData(bundle);

                    runOnUiThread(() -> {
                        mFullName.setText(userName);
                    });

                    findMotherAPI(attributes.get(3).getValue());

                    Log.i(TAG, "fetchUserInformation: "+ attributes);
                    Log.i(TAG, "fetchUserInformation: 0 "+ attributes.get(0).getValue());
                    Log.i(TAG, "fetchUserInformation: 1 "+ attributes.get(1).getValue());
                    Log.i(TAG, "fetchUserInformation: 2 "+ attributes.get(2).getValue());
                    Log.i(TAG, "fetchUserInformation: 3 "+ attributes.get(3).getValue());

                    handlerMom =  new Handler(Looper.getMainLooper(), msg->{
                        try {
                            setImage(mother.getImage());
                        }catch (Exception error){

                        }
//                        if(mother.getImage()!=null)
//                        setImage(mother.getImage());


                        return true ;
                    });
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
    }

    void findMotherAPI (String emailId ){
        Log.i(TAG, "findMotherAPI: id ->"+emailId);
        Amplify.API.query(
                ModelQuery.list(Mother.class),
                success->{
                    if(success.hasData())
                    {
                        for (Mother curMother : success.getData())
                        {
                            if(curMother.getEmailAddress().equals(emailId)){
                                Log.i(TAG, "findMotherAPI: mother->"+mother);
                                mother  = curMother;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("data","Done");
                            Message message = new Message();
                            message.setData(bundle);
                            handlerMom.sendMessage(message);
                        }
                    }
                },
                fail->{
                    Log.i(TAG, "onCreate: failed to find mother in database");
                }
        );
    }
    private void setImage(String image) {
        if(image != null) {
            Amplify.Storage.downloadFile(
                    image,
                    new File(getApplicationContext().getFilesDir() + "/" + image + "download.jpg"),
                    result -> {
                        imageView = findViewById(R.id.image_profile);
                        Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());
                        runOnUiThread(() -> Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(imageView));
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }
    }

    private void setUserInformation(){
        fetchUserInformation();
        Amplify.Storage.getUrl("1734345085.jpg",
                success ->{
                        String url = success.getUrl().toString();
                        runOnUiThread(() -> {

                            Glide.with(this).load(url).into(mImage);
                        });

                },
                error -> Log.e(TAG,  "display Failed", error)
        );
    }

    private void setRecyclerViewForQuestion(){
        listView = findViewById(R.id.list_view);
        handler1 = new Handler(
                Looper.getMainLooper(), msg -> {
            ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,taskArrayList);
            listView.setAdapter(adapter);
            listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), CommentActivity_Question.class);
                    String QuestionId=taskArrayList.get(position)+"?";
                    intent.putExtra("QuestionId",QuestionId);
                    startActivity(intent);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
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
            mainAdapter blogCustomAdapter = new mainAdapter(getApplicationContext(),blogsListTest, new mainAdapter.CustomClickListener() {
                @Override
                public void onTaskItemClicked(int position) {
                    Intent intent = new Intent(getApplicationContext(), BlogContentes.class);
                    intent.putExtra("position", blogsListTest.get(position).getId());
                    intent.putExtra("title",  blogsListTest.get(position).getTitle());
                    intent.putExtra("content",blogsListTest.get(position).getContent());
                    intent.putExtra("author", blogsListTest.get(position).getAuthor());
                    intent.putExtra("imageLink",blogsListTest.get(position).getImageLink());
                    intent.putExtra("category" ,blogsListTest.get(position).getCategory());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(blogCustomAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
            return true ;
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)

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
                            String imageLink = t.get("imageLink").toString();
                            String author = t.get("author").toString();
                            String category = t.get("category").toString();
                            String blogId = t.get("blogId").toString();
                            com.example.momtobe.remote.Blog blog = new com.example.momtobe.remote.Blog(blogId,title,content,author,imageLink,category);
                            blogsListTest.add(blog);

//                            Log.i(TAG, "CallAPI: blog from API : "+blog.toString());
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
    private void GetEmail(){
        Bundle bundle = getIntent().getExtras();
        showEmail = bundle.getString("EMAIL");
    }
    private void itemsSelector() {

        mImage = findViewById(R.id.image_profile);
        mFullName = findViewById(R.id.user_full_name);

    }
    private void ButtonOnListener(){
//        mProfileBtn.setOnClickListener(view ->{
//            SentEmailToUserActivity();
//        });
//
//        mLogoutBtn.setOnClickListener(view-> {
//            logout();
//        });
//        mFavoriteBtn.setOnClickListener(view-> {
//            startActivity(new Intent(MainActivity.this,SavedActivity.class));
//        });
        mImage.setOnClickListener(view ->{
            SentEmailToUserActivity();
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                SentEmailToUserActivity();
                return true;
            case R.id.action_favorite:
                startActivity(new Intent(MainActivity.this,SavedActivity.class));

                return true;
            case R.id.action_logout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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