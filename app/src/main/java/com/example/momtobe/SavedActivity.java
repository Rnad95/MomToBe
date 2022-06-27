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
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Blog;
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
    private String url ="https://54c776b6e8bb9b.lhrtunnel.link/blogs/1";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        tv = findViewById(R.id.saved_data);
        try {
            CallAPI();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        SetHandler();

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


        url = "https://54c776b6e8bb9b.lhrtunnel.link/blogs/1";

        mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                            Log.i(TAG, "onResponse: SUCCESS"+response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String title = response.getString("title");
                    String content = response.getString("content");
                    String author = response.getString("author");
                    tv.setText("title: "+title+"\n "+content+" \n Author "+author);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("error ");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);



    }


}