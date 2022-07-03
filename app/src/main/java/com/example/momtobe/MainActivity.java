package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;


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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    RecyclerView recyclerViewQuestion;
    TextView mViewAll, mFullName;
    private ImageView imageView, mImage;
    List<com.example.momtobe.remote.Blog> blogsListTest= new ArrayList<>();
    ArrayList<Question> questionList = new ArrayList<>();
    private Handler handler1, handlerMom,handler2, handler;
    private Date dateParse;
    private String userId, userName, email, imageKey, showEmail, url ="https://jsonkeeper.com/b/MKEL";
    Mother mother ;
    CircleImageView profileImage;
    public static final String QuestionId = "questionId";

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
//        popMenuView();
        setRecyclerViewForBlogs();
        getQuestions();
        setRecyclerViewForQuestion();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
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
                    email = attributes.get(3).getValue();
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
                            }catch (Exception err){

                            }
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
        Amplify.API.query(
                ModelQuery.list(Mother.class),
                getSuccess -> {
                    for (Mother mother : getSuccess.getData()) {
                        if (mother.getEmailAddress().equals(email)) {
                            imageKey = mother.getName();
                            Log.i(TAG, "setUserInformation: +" + imageKey);
                        }
                    }

                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, error.toString())
        );

        Amplify.Storage.getUrl(imageKey+".jpg",
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
        recyclerViewQuestion = findViewById(R.id.recycler_view_question);
        handler1 = new Handler(
                Looper.getMainLooper(), msg -> {
            HomeQuestionAdapter homeQuestionAdapter = new HomeQuestionAdapter(getApplicationContext(),questionList, position -> {

                Intent intent=new Intent(getApplicationContext(),CommentActivity.class);
                String QuestionId=questionList.get(position).getId();
                intent.putExtra(QuestionId,QuestionId);
                startActivity(intent);

            });
            recyclerViewQuestion.setAdapter(homeQuestionAdapter);
            recyclerViewQuestion.setHasFixedSize(true);
            recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(this));
            return true;

        }
        );

    }
    private void getQuestions(){
        Amplify.API.query(
                ModelQuery.list(Question.class),
                teamsName -> {
                    for (Question question : teamsName.getData()) {
                        questionList.add(question);
                    }

                    handler1.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, error.toString())
        );
    }
    private void setRecyclerViewForBlogs(){

        handler = new Handler(Looper.getMainLooper() , msg -> {
            recyclerView = findViewById(R.id.main2_recycler_view);
            mainAdapter blogCustomAdapter = new mainAdapter(getApplicationContext(),
                    blogsListTest,
                    new mainAdapter.CustomClickListener() {
                @Override
                public void onTaskItemClicked(int position) {

                    Intent intent = new Intent(getApplicationContext(), BlogContentes.class);
                    intent.putExtra("title",blogsListTest.get(position).getTitle());
                    intent.putExtra("content",blogsListTest.get(position).getContent());
                    intent.putExtra("author",blogsListTest.get(position).getAuthor());
                    intent.putExtra("imageLink",blogsListTest.get(position).getImageLink());
                    intent.putExtra("category",blogsListTest.get(position).getCategory());
                    Log.i(TAG, "onTaskItemClicked: "+blogsListTest.get(position).getTitle());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(blogCustomAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL,
                    false));
            return true ;
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CallAPI() throws IOException {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = response.getJSONObject("_embedded").getJSONArray("blogs");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Gson gson = new Gson();
                        String json = gson.toJson(jsonArray);
                        List<JsonObject> arrayList = new ArrayList();
                        arrayList = gson.fromJson(jsonArray.toString(),ArrayList.class);

                        for (int i = 0; i < arrayList.toArray().length; i++) {
                            Object getrow = arrayList.get(i);
                            LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;
                            String date = t.get("date").toString();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
                            String title = t.get("title").toString();
                            String content = t.get("content").toString();
                            String imageLink = t.get("imageLink").toString();
                            try {
                                dateParse = simpleDateFormat.parse(date);
                                String afterDate = "2022-06-01";
                                if(dateParse.after(simpleDateFormat.parse(afterDate))){
                                com.example.momtobe.remote.Blog blog = new com.example.momtobe.remote.Blog(title,content,imageLink);
                                blogsListTest.add(blog);
                                    Log.i(TAG, "CallAPI: SUCCESS FROM DATA");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("data" , "Done");
                        Message message = new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);


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
    private void itemsSelector() {

        mImage = findViewById(R.id.image_profile);
        mFullName = findViewById(R.id.user_full_name);
        profileImage = findViewById(R.id.image_profile);

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