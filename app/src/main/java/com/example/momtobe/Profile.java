package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Mother;
import com.bumptech.glide.Glide;

import java.io.File;


public class Profile extends AppCompatActivity {
    private static final String TAG = "profile";
    Mother mother ;
    Handler handler ;
    Handler handlerId ;
    String imageKey;
    private String motherEmail;
    private String emailId;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        handlerId =  new Handler(Looper.getMainLooper(),msg->{
            Log.i(TAG, "onCreate: handlerId ->" + emailId);
            findMotherAPI(emailId);
            return true ;
        });

        handler =  new Handler(Looper.getMainLooper(), msg->{
            setMotherInfo();
            return true ;
        });

        setFavBtn();
        setSettingsBtn();




    }

    void findMotherAPI (String emailId ){
        Log.i(TAG, "findMotherAPI: id ->"+emailId);
        Amplify.API.query(
                ModelQuery.list(Mother.class),
                success->{
                    if(success.hasData())
                    {
                        for (Mother curMother : success.getData())
                        {
                            if(curMother.getEmailAddress().equals(emailId)){
                                Log.i(TAG, "findMotherAPI: mother->"+mother);
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


    void setMotherInfo(){

        Log.i(TAG, "setMotherInfo: 100 ->" + mother);
        TextView mMotherName = findViewById(R.id.pro_mother_name);
        TextView mMotherPhone = findViewById(R.id.pro_mother_phone);
        TextView mMotherNumberOfChildren = findViewById(R.id.pro_mother_number_of_children);
        if (mother != null) {
        mMotherName.setText("Mother Name : "+mother.getName());
        mMotherPhone.setText("Mother Phone Number : "+mother.getPhoneNumber().toString());
        mMotherNumberOfChildren.setText("Mother Number Of Children : "+mother.getNumOfChildren().toString());

        Log.i(TAG, "setMotherInfo: imageKey ->" + mother.getImage());

            setImage(mother.getImage());
        }
    }

    private void setImage(String image) {
        if(image != null) {
            Amplify.Storage.downloadFile(
                    image,
                    new File(getApplicationContext().getFilesDir() + "/" + image + "download.jpg"),
                    result -> {
                        imageView = findViewById(R.id.pro_profile_picture);
                        Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());
                        runOnUiThread(() -> Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(imageView));
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }
    }



    void setFavBtn(){
        Button favBtn = findViewById(R.id.pro_my_fav_blogs);
        favBtn.setOnClickListener(view->{
            Intent intent = new Intent(Profile.this,SavedActivity.class);
            intent.putExtra("EMAIL_ADDRESS",motherEmail);
            startActivity(intent);
        });
    }

    void setSettingsBtn () {
        Button settingsBtn =  findViewById(R.id.pro_settings_btn);
        settingsBtn.setOnClickListener(view->{
            Intent intent = new Intent(Profile.this,Settings.class);
            intent.putExtra("EMAIL_ADDRESS",motherEmail);
            startActivity(intent);
        });

    }


}