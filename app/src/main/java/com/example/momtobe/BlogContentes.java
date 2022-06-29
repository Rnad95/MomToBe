package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Blog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BlogContentes extends AppCompatActivity {

    private static final String TAG = "blogContent";
    private final MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_contentes);

        setData();



//        setBlogImage();
//        setSaveBtn();

        readContent();
    }

    void setData(){
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        TextView titleView = findViewById(R.id.BlogTitle);
        titleView.setText(title);

        String content = bundle.getString("content");
        TextView contentView = findViewById(R.id.BlogContent);
        contentView.setText(content);

        String authorName = bundle.getString("author");
        TextView authorNameView = findViewById(R.id.author_name);
        authorNameView.setText(authorName);


    }
    void setBlogImage (){
        ImageView image = findViewById(R.id.blogImage);

    }


    void setSaveBtn (){
        Button save_btn = findViewById(R.id.save_blog);
        save_btn.setOnClickListener(view->{

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



}