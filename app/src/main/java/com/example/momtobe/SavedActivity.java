package com.example.momtobe;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Mother;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.momtobe.adapter.BlogCustomAdapter;
import com.example.momtobe.adapter.ProductCustomAdapter;
import com.example.momtobe.api.BlogAPIService;
import com.example.momtobe.remote.Embedded;
import com.example.momtobe.ui.ProductActivity;
import com.example.momtobe.ui.ProductDetailsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SavedActivity extends AppCompatActivity {

    private final String TAG = SavedActivity.class.getSimpleName();
    private Handler handlerMom , handler;
    private BlogCustomAdapter adapter;
    private Date dateParse;
    private RecyclerView recyclerView;
    private Mother mother;
    private String emailId;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<com.example.momtobe.remote.Blog> blogsListTest  = new ArrayList<>();
    private ArrayList<com.example.momtobe.remote.Blog> favBlogsList = new ArrayList<>();
    private List<String > blogsIdList= new ArrayList<>();;
    private RequestQueue queue;
    private RequestQueue mQueue;
    private String url ="https://jsonkeeper.com/b/FV5T";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        mQueue = Volley.newRequestQueue(this);
//        navToActivity();
        
        
        try {
            CallAPI();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//
        Bundle bundle = getIntent().getExtras();
        emailId = bundle.getString("EMAIL_ADDRESS");
        blogsIdList = bundle.getStringArrayList("FAV_BLOGS");


        handler =  new Handler(Looper.getMainLooper(), msg->{
            Log.i(TAG, "onCreate: HERE");
            findMotherAPI();
            return true ;
        });



        handlerMom = new Handler(Looper.getMainLooper(),msg-> {
            favBlogsList.clear();
            Log.i(TAG, "onCreate: handlerMom-> saved" + blogsIdList.get(0));
            Log.i(TAG, "onCreate: handlerMom-> " + blogsListTest.size());
            for(String id : blogsIdList){
                for(int i=0 ; i<blogsListTest.size();i++){
                    Log.i(TAG, "onCreate: blog -> "+blogsListTest.get(i).getId());

                    if(blogsListTest.get(i).getId().equals(id)){
                        favBlogsList.add(blogsListTest.get(i));
                        Log.i(TAG, "onCreate: blog -> chosen -> "+blogsListTest.get(i).getId());
                    }
                }
            }

            setRecyclerView ();

            return true ;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        findMotherAPI();
    }

    void setRecyclerView (){
        recyclerView = findViewById(R.id.saved_recycler_view);
            BlogCustomAdapter blogCustomAdapter = new BlogCustomAdapter(getApplicationContext(),favBlogsList, new BlogCustomAdapter.CustomClickListener() {
                @Override
                public void onTaskItemClicked(int position) {
                    Intent intent = new Intent(getApplicationContext(), BlogContentes.class);
                    intent.putExtra("position", favBlogsList.get(position).getId());
                    intent.putExtra("title",  favBlogsList.get(position).getTitle());
                    intent.putExtra("content",favBlogsList.get(position).getContent());
                    intent.putExtra("author", favBlogsList.get(position).getAuthor());
                    intent.putExtra("imageLink",favBlogsList.get(position).getImageLink());
                    intent.putExtra("category" ,favBlogsList.get(position).getCategory());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(blogCustomAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                            String imageLink = t.get("imageLink").toString();
                            String author = t.get("author").toString();
                            String category = t.get("category").toString();
                            String date = t.get("date").toString();

                            String blogId = t.get("blogId").toString();
                            com.example.momtobe.remote.Blog blog = new com.example.momtobe.remote.Blog(blogId,title,content,author,imageLink,category,date);

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


    void findMotherAPI (){
        Amplify.API.query(
                ModelQuery.list(Mother.class),
                success->{
                    Log.i(TAG, "findMotherAPI: 217 -> "+emailId);
                    if(success.hasData())
                    {
                        for (Mother curMother : success.getData())
                        {
                            if(curMother.getEmailAddress().equals(emailId)){

                                mother  = curMother;

                                Log.i(TAG, "onCreate: mother before the loop "+mother);

                                Bundle bundle = new Bundle();
                                bundle.putString("data","Done");
                                Message message = new Message();
                                message.setData(bundle);
                                handlerMom.sendMessage(message);
                                break ;

                            }

                        }

                    }
                },
                fail->{
                    Log.i(TAG, "onCreate: failed to find mother in database");
                }
        );
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
                        startActivity(new Intent(getApplicationContext(), com.example.momtobe.Blog.class));
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