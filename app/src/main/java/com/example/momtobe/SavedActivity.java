package com.example.momtobe;
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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.momtobe.adapter.ProductCustomAdapter;
import com.example.momtobe.api.BlogAPIService;
import com.example.momtobe.remote.Embedded;
import com.example.momtobe.ui.ProductDetailsActivity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.var;
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
    List<com.example.momtobe.remote.Blog> blogsListTest= new ArrayList<>();;
    private RequestQueue queue;
    private RequestQueue mQueue;
    private String url ="https://jsonkeeper.com/b/MKEL";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        mQueue = Volley.newRequestQueue(this);


        try {
            CallAPI();
        } catch (IOException e) {
            e.printStackTrace();
        }
         handler = new Handler(Looper.getMainLooper() , msg -> {
            recyclerView = findViewById(R.id.saved_recycler_view);
            BlogCustomAdapter blogCustomAdapter = new BlogCustomAdapter(getApplicationContext(),blogsListTest, new BlogCustomAdapter.CustomClickListener() {
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
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                        Log.e(TAG, "CallAPI: ERROR FROM CATCH => "+e.getMessage());
                    }
                },
                error -> {
                    Log.e(TAG, "CallAPI: ",error );
                }
        );
        queue.add(jsonObjectRequest);
    }


}