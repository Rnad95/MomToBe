package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Blog;
import com.amplifyframework.datastore.generated.model.Mother;
import com.amplifyframework.datastore.generated.model.UserBlogs;
import com.bumptech.glide.Glide;

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
    private Blog blog;
    private TextView authorNameView;
    private ImageButton save_btn;
    private String userId;
    private String author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_contentes);

        setData();

//        setBlogImage();
        setSaveBtn();
//        removeSave();
        readContent();
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
        save_btn.setOnClickListener(view->{
            Bundle bundle = new Bundle();
            email =  bundle.getString("motherEmail") ;
            index = bundle.getInt("blogId");
            author = bundle.getString("author");
            image = bundle.getString("imageLink");
            Log.i(TAG, "setSaveBtn: AUTHOR: " + author);
            blog = Blog.builder().title(title)
                    .description(content)
                    .autherName("author")
                    .featured(true)
                    .image(image)
                    .build();
            Amplify.API.mutate(
                    ModelMutation.create(blog),
                    success ->{
                        Log.i(TAG,"Saved blog: "+ success);
                    },
                    error ->{
                        Log.i(TAG, "Could not save to Database");
                    }
            );



            Amplify.API.query(ModelQuery.list(Mother.class),
                    success->{
                        if(success.hasData())
                            for(Mother mother : success.getData()){
                                {
                                    if(mother.getEmailAddress().equals(email)){
                                        UserBlogs userBlogs = UserBlogs.builder().mother(mother).blog(blog).build();
                                        mother.getBlogs().add(userBlogs);
                                        blog.getMothers().add(userBlogs);

                                        Amplify.API.mutate(
                                                ModelMutation.create(userBlogs),
                                                successBlogUser ->{
                                                    Log.i(TAG,"Saved blog: "+ successBlogUser);
                                                },
                                                error ->{
                                                    Log.i(TAG, "Could not save to Database");
                                                }
                                        );

                                    }
                                }
                            }
                    },
                    fail->{
                    });
        });
    }
    void removeSave (){
        Amplify.API.query(ModelQuery.list(Mother.class),
                foundMother ->{
                    for(Mother mother : foundMother.getData()){
//                    if(mother.getEmailAddress().equals(email))
                        {
                            Amplify.API.query(ModelQuery.list(Blog.class),
                                    foundBlog->{
                                        for(Blog blog : foundBlog.getData())
                                        {
//                                        if(blog.getId().equals(blogId))
                                            {
                                                UserBlogs relation =  UserBlogs.builder().mother(mother).blog(blog).build();
                                                Amplify.API.mutate(ModelMutation.delete(relation),
                                                        response -> {
                                                            Log.i("MyAmplifyApp", "Deleted ");
                                                            finish();
                                                        },
                                                        error -> Log.e("MyAmplifyApp", "delete failed", error)
                                                );
                                            }
                                        }

                                    },
                                    notFoundBlog->{
                                        Log.e("MyAmplifyApp", "delete failed", notFoundBlog);
                                    });
                        }
                    }


                },
                notFound ->{
                    Log.e("MyAmplifyApp", "delete failed", notFound);

                }
        );
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

}