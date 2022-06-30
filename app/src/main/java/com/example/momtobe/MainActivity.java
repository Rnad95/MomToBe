package com.example.momtobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Cat;

import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.Question;
import com.example.momtobe.registration.LoginActivity;

import com.example.momtobe.ui.ProductActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    BottomNavigationView bottomNavigationView;
    private Button mProfileBtn;
    private Button mSettingBtn;
    private Button mLogoutBtn;
    private Button mFavoriteBtn;
    private String showEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navToActivity();

        ButtonSelector();
        ButtonOnListener();
        GetEmail();


//        TextView mEmail = findViewById(R.id.main_email);
//        mEmail.setText(showEmail);

    }
    private void SentEmailToUserActivity(){
        Intent intent = new Intent(MainActivity.this, Profile.class);
        intent.putExtra("EMAIL_ADDRESS",showEmail);
        startActivity( intent);
    }
    private void SentEmailToSettingsActivity(){
        Intent intent = new Intent(MainActivity.this, Settings.class);
        intent.putExtra("EMAIL_ADDRESS",showEmail);
        startActivity( intent);
    }

    private void GetEmail(){
        Bundle bundle = getIntent().getExtras();
        showEmail = bundle.getString("EMAIL");
    }

    private void ButtonSelector() {
        mProfileBtn = findViewById(R.id.profile);
        mSettingBtn = findViewById(R.id.setting);
        mLogoutBtn = findViewById(R.id.log_out);
//        mFavoriteBtn = findViewById(R.id.main_favorite);

    }
    private void ButtonOnListener(){
        mProfileBtn.setOnClickListener(view ->{
            SentEmailToUserActivity();
//            navigateToProfile();
        });
        mSettingBtn.setOnClickListener(view ->{
            SentEmailToSettingsActivity();
//            navigateToSetting();
        });

        mLogoutBtn.setOnClickListener(view-> {
            logout();
        });
//        mFavoriteBtn.setOnClickListener(view-> {
//            startActivity(new Intent(MainActivity.this,SavedActivity.class));
//        });
    }
    private void navigateToProfile(){
        Intent intent = new Intent(MainActivity.this,Profile.class);
        startActivity(intent);

    }
    private void navigateToSetting(){
        Intent intent = new Intent(MainActivity.this,Settings.class);
        startActivity(intent);

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
                        startActivity(new Intent(getApplicationContext(),Blog.class));
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
    private void logout() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    authSession();
                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );
    }
    private void authSession() {
        Amplify.Auth.fetchAuthSession(
                result -> Log.i(TAG, "Auth Session" + result.toString()),
                error -> Log.e(TAG, error.toString())
        );
    }

}