package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BlogContentes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_contentes);

//        setTitleText();
//        setTitleContent();
//        setSaveBtn();

    }

    void setTitleText (){
        Bundle bundle = getIntent().getExtras();
        TextView title = findViewById(R.id.BlogTitle);
        String titleText =  bundle.getString("Blog_Title");
        title.setText(titleText);
    }

    void setTitleContent (){
        Bundle bundle = getIntent().getExtras();
        TextView content = findViewById(R.id.BlogContent);
        String contentText =  bundle.getString("Blog_Content");
        content.setText(contentText);
    }

    void setSaveBtn (){
        Button save_btn = findViewById(R.id.save_blog);
        save_btn.setOnClickListener(view->{

        });

    }

}