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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.momtobe.adapter.BlogCustomAdapter;
import com.example.momtobe.adapter.mainAdapter;
import com.example.momtobe.registration.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainDesignActivity extends AppCompatActivity {
    private final String TAG = MainDesignActivity.class.getSimpleName();
    private String url ="https://e717393f5555dc.lhrtunnel.link/blogs";
    Handler handler;
    mainAdapter adapter;
    RecyclerView recyclerView;
    TextView mViewAll;
    List<com.amplifyframework.datastore.generated.model.Blog> blogs;
    List<com.example.momtobe.remote.Blog> blogsListTest= new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_design);
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

        setRecyclerView();
    }

    private void setRecyclerView(){

        handler = new Handler(Looper.getMainLooper() , msg -> {
            recyclerView = findViewById(R.id.main2_recycler_view);
            mainAdapter blogCustomAdapter = new mainAdapter(getApplicationContext(),blogsListTest, new mainAdapter.CustomClickListener() {
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
                            String imageLink = t.get("imageLink").toString();
                            com.example.momtobe.remote.Blog blog = new com.example.momtobe.remote.Blog(title,content,imageLink);
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

}