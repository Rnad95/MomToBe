package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Blog;
import com.amplifyframework.datastore.generated.model.Mother;
import com.bumptech.glide.Glide;
import com.example.momtobe.ui.ProductActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BlogContentes extends AppCompatActivity {

    private static final String TAG = "blogContent";
    BottomNavigationView bottomNavigationView;
    private final MediaPlayer mp = new MediaPlayer();
    private String email;
    private Handler handler;
    private List<com.example.momtobe.remote.Blog> blogsList= new ArrayList<>();;
    private String url ="https://jsonkeeper.com/b/WAVV";
    private int index;
    private String title;
    private TextView contentView;
    private String authorName;
    private String content;
    private String image;
    private String category;
    private String blogId;
    private Blog blog;
    private TextView authorNameView;
    private ImageButton save_btn;
    private String emailId;
    Handler handlerId ;
    Mother mother ;
    List <String> blogIds = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_contentes);


        Amplify.Auth.fetchUserAttributes(
                attributes ->{
                    emailId = attributes.get(3).getValue();
                    Bundle bundle = new Bundle();
                    bundle.putString("data" , "Done");

                    Message message = new Message();
                    message.setData(bundle);
                    handlerId.sendMessage(message);
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

        handlerId =  new Handler(Looper.getMainLooper(), msg->{
//            Log.i(TAG, "onCreate: handlerId ->" + emailId);
            findMotherAPI();
            return true ;
        });

        handler =  new Handler(Looper.getMainLooper(), msg->{
            setSaveBtn();
            return true ;
        });
        save_btn = findViewById(R.id.favorite_blog);

        setData();

        readContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSaveBtn();
    }

    void findMotherAPI (){
        Log.i(TAG, "findMotherAPI: id ->"+emailId);
        Amplify.API.query(
                ModelQuery.list(Mother.class),
                success->{
                    if(success.hasData())
                    {
                        for (Mother curMother : success.getData())
                        {
                            if(curMother.getEmailAddress().equals(emailId)){
//                                Log.i(TAG, "findMotherAPI: mother->"+mother);
                                mother  = curMother;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("data","Done");
                            Message message = new Message();
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }
                },
                fail->{
                    Log.i(TAG, "onCreate: failed to find mother in database");
                }
        );
    }

    void setData(){
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        TextView titleView = findViewById(R.id.BlogTitle);
        titleView.setText(title);

        content = bundle.getString("content");
        contentView = findViewById(R.id.BlogContent);
        contentView.setText(content);

        authorName = bundle.getString("author");
        authorNameView = findViewById(R.id.author_name);
        authorNameView.setText(authorName);

        image = bundle.getString("imageLink");
        ImageView imageView = findViewById(R.id.blogImage);
        Glide.with(this).load(image).into(imageView);

    }

    void setSaveBtn (){

        save_btn = findViewById(R.id.favorite_blog);
        save_btn.setOnClickListener(view-> {

            Bundle bundle = getIntent().getExtras();
            blogId = bundle.getString("position");

            Log.i(TAG, "setSaveBtn: blogId ->" +blogId);
            if(mother.getFaveBlogs().contains(blogId))
            {
                blogIds.clear();
                blogIds.addAll(mother.getFaveBlogs());
                blogIds.remove(blogId);
            }
            else
            {
                blogIds.clear();
                blogIds.addAll(mother.getFaveBlogs());
                blogIds.add(blogId);

            }

            Mother newMother = Mother.builder()
                    .name(mother.getName())
                    .numOfChildren(mother.getNumOfChildren())
                    .emailAddress(mother.getEmailAddress())
                    .phoneNumber(mother.getPhoneNumber())
                    .image(mother.getImage())
                    .faveBlogs(blogIds)
                    .id(mother.getId())
                    .build();

//            Log.i(TAG, "setSaveButton: testing ->"+ newMother);
            Amplify.API.mutate(ModelMutation.update(newMother),
                    response -> {
                        Log.i("MyAmplifyApp", "Todo with id: " + response);
                        runOnUiThread(()->{
                            if(response.getData().getFaveBlogs().contains(blogId))
                            {
                                int color = Color.parseColor("#000000");
                                save_btn.setColorFilter(color);
                            }
                            else
                            {
                                int color = Color.parseColor("#FFFFFF");
                                save_btn.setColorFilter(color);
                            }
                        });
                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

            Log.i(TAG, "setSaveBtn: favBlogs ->"+mother.getFaveBlogs());
        });


    }




    void readContent (){
        ImageButton sound = findViewById(R.id.sound_on_btn);
        sound.setOnClickListener(view -> {
            TextView content = findViewById(R.id.BlogContent);
            String textToRead = content.getText().toString();
            Amplify.Predictions.convertTextToSpeech(
                    textToRead,
                    result -> {
                        playAudio(result.getAudioData());
                        Log.i(TAG, "soundOn: "+result);
                    },
                    error -> Log.e("MyAmplifyApp", "Conversion failed", error)
            );
        });
    }
    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
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