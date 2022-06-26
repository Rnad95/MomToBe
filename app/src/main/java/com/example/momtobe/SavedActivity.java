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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Blog;
import com.example.momtobe.adapter.BlogCustomAdapter;
import com.example.momtobe.api.BlogAPIService;
import com.example.momtobe.data.BlogDetails;
import com.example.momtobe.remote.Embedded;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SavedActivity extends AppCompatActivity {

    private final String TAG = SavedActivity.class.getSimpleName();
    Handler handler;
    BlogCustomAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Blog> blogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        CallAPI();
        SetHandler();

    }
    @Override
    protected void onResume() {
        super.onResume();
        getBlogs();
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
    private void CallAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BlogAPIService service = retrofit.create(BlogAPIService.class);

        Call<Embedded> allBlogs = service.getBlogList();
        allBlogs.enqueue(new Callback<Embedded>() {
            @Override
            public void onResponse(Call<Embedded> call, Response<Embedded> response) {
                Embedded blog = response.body();
                Log.i(TAG, "onResponse: "+blog.getBlogs());
            }

            @Override
            public void onFailure(Call<Embedded> call, Throwable t) {
                Log.i(TAG, "onFailed: "+t.getMessage());

            }
        });

//
//        Call<Blog> data =service.getOneBlog("1");
//        data.enqueue(new Callback<Blog>() {
//            @Override
//            public void onResponse(Call<Blog> call, Response<Blog> response) {
//                Blog blog = response.body();
//                Log.i(TAG, "onResponse: One Item "+blog.getTitle());
//            }
//
//            @Override
//            public void onFailure(Call<Blog> call, Throwable t) {
//                Log.i(TAG, "onFailed: One Item "+t.getMessage());
//            }
//        });

    }

}