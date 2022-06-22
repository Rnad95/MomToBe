package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setSaveButton();
    }
    void setSaveButton (){
        Button save_btn = findViewById(R.id.set_save_btn);
        save_btn.setOnClickListener(view->{

        });
    }
}