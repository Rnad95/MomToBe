package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Blog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.momtobe.adapter.BlogCustomAdapter;
import com.example.momtobe.api.BlogAPIService;
import com.example.momtobe.remote.Embedded;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SavedActivity extends AppCompatActivity {

    private final String TAG = SavedActivity.class.getSimpleName();
    Handler handler;
    BlogCustomAdapter adapter;
    RecyclerView recyclerView;
    List<Blog> blogs;
    List<com.example.momtobe.remote.Blog> blogsListTest;
    private RequestQueue queue;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        try {
            CallAPI();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        addBlogs();
//        SetHandler();

    }
    private void addBlogs(){

//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
//        blogsListTest.add(new com.example.momtobe.remote.Blog("Babies","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque","Author","https://els-jbs-prod-cdn.jbs.elsevierhealth.com/cms/attachment/bdc3172e-40b6-482f-8f3f-654ec0d50e96/fx1.jpg","Infant development"));
    }
    @Override
    protected void onResume() {
        super.onResume();
//        getBlogs();
    }
    private void getBlogs(){
            Amplify.API.query(
                    ModelQuery.list(Blog.class),
                    success -> {
                        blogs = new ArrayList<>();

                        if (success.hasData()) {
                            for (Blog blog : success.getData()) {
                                blogs.add(blog);
                            }
                        }
                        Log.i(TAG, "Blogs list => " + blogs);

                        Bundle bundle = new Bundle();
                        bundle.putString("BlogsList", success.toString());

                        Message message = new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);

                        runOnUiThread(() -> {
                            recyclerView.setAdapter(adapter);
                        });
                    },
                    error -> Log.e(TAG, error.toString(), error)
            );

        }
    private void SetHandler(){
        handler = new Handler(Looper.getMainLooper(), msg -> {
            recyclerView = findViewById(R.id.saved_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new BlogCustomAdapter(
                    blogs, position -> {
                Intent intent = new Intent(this, BlogContentes.class);
                intent.putExtra("title",blogs.get(position).getTitle());
                intent.putExtra("description",blogs.get(position).getDescription());
                intent.putExtra("authorName",blogs.get(position).getAutherName());
                intent.putExtra("imageUrl",blogs.get(position).getImage());
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
            return true;
        });

    }
    private void CallAPI() throws IOException {


        String url = "https://39146b37273509.lhrtunnel.link/blogs";

        mQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                            Log.i(TAG, "onResponse: SUCCESS"+response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        mQueue.add(request);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    String[] embedded =  gson.fromJson(response,String[].class);
                    Log.i(TAG, "CallAPI: "+response.toString() );
                }
                , error -> {
            Log.i(TAG, "CallAPI: " + error.getMessage());
        });

        queue.add(stringRequest);

    }


}