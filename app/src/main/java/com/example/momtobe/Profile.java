package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Mother;

public class Profile extends AppCompatActivity {
    Mother mother ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        String motherUsername = getIntent().getStringExtra(USERNAME);

        setMotherInfo();
        setMotherImage();
        setRecycler();
        setButtons();
    }

    void setMotherImage(){
        ImageView motherImage = findViewById(R.id.pro_profile_picture);


    }

    void setMotherInfo(){
        TextView motherName = findViewById(R.id.pro_mother_name);
        TextView motherEmail = findViewById(R.id.pro_mother_email);
        TextView motherPhone = findViewById(R.id.pro_mother_phone);
        TextView motherNumberOfChildren = findViewById(R.id.pro_mother_number_of_children);





    }

    void setRecycler(){
        RecyclerView recyclerView = findViewById(R.id.pro_list);


    }
    void setButtons(){
        RecyclerView recyclerView = findViewById(R.id.pro_list);

        Button myFavBlogs = findViewById(R.id.pro_fav_blogs);
        myFavBlogs.setOnClickListener(view->{

        });

        Button myExperience= findViewById(R.id.pro_mother_experience);
        myExperience.setOnClickListener(view->{

        });

        Button myProducts= findViewById(R.id.pro_mother_products);
        myProducts.setOnClickListener(view->{

        });

        Button myQuestions = findViewById(R.id.pro_mother_questions);
        myQuestions.setOnClickListener(view->{

        });

    }
}