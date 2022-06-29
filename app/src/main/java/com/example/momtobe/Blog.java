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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.momtobe.SavedActivity;
import com.example.momtobe.adapter.BlogCustomAdapter;
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

public class Blog extends AppCompatActivity {
    private final String TAG = SavedActivity.class.getSimpleName();
    Handler handler;
    BlogCustomAdapter adapter;
    RecyclerView recyclerView;
    List<com.amplifyframework.datastore.generated.model.Blog> blogs;
    List<com.example.momtobe.remote.Blog> blogsListTest= new ArrayList<>();;
    private RequestQueue queue;
    private RequestQueue mQueue;
    private String url ="https://jsonkeeper.com/b/Y854";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        mQueue = Volley.newRequestQueue(this);

        try {
            CallAPI();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler = new Handler(Looper.getMainLooper() , msg -> {
            recyclerView = findViewById(R.id.blog_archive_recycler);
            BlogCustomAdapter blogCustomAdapter = new BlogCustomAdapter(blogsListTest, new BlogCustomAdapter.CustomClickListener() {
                @Override
                public void onTaskItemClicked(int position) {
                    Intent intent = new Intent(getApplicationContext(), BlogContentes.class);
                    intent.putExtra("title",blogsListTest.get(position).getTitle());
                    intent.putExtra("content",blogsListTest.get(position).getContent());
                    intent.putExtra("author",blogsListTest.get(position).getAuthor());
                    intent.putExtra("imageLink",blogsListTest.get(position).getImageLink());
                    intent.putExtra("category",blogsListTest.get(position).getCategory());

                    Bundle bundle = new Bundle();
                    intent.putExtra("motherEmail",bundle.getString("EMAIL_ADDRESS"));

                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(blogCustomAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            return true ;
        });

    }

    private void CallAPI() throws IOException {
        url = "https://jsonkeeper.com/b/Y854";
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
                            com.example.momtobe.remote.Blog blog = new com.example.momtobe.remote.Blog(title,content,author,imageLink,category);
                            blogsListTest.add(blog);
                            Log.i(TAG, "CallAPI: Author: "+blog.getAuthor());
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

}


