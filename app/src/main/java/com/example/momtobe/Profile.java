package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


public class Profile extends AppCompatActivity {
    private static final String TAG = "profile";
    Mother mother ;
    Handler handler ;
    private String motherEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        motherEmail = bundle.getString("EMAIL_ADDRESS");

        findMotherAPI();
        setFavBtn(mother);
        setSettingsBtn();

//        Log.i(TAG, "onCreate 39 : motherName-> "+mother.getName());
        handler =  new Handler(Looper.getMainLooper(), msg->{
            setMotherInfo(mother);
            return true ;
        });

    }

    void setMotherImage(Mother mother){  //TODO waiting for s3 -> hamze
        ImageView motherImage = findViewById(R.id.pro_profile_picture);

    }
    void findMotherAPI (){
        Amplify.API.query(
                ModelQuery.list(Mother.class),
                success->{
                    if(success.hasData())
                    {
                        for (Mother curMother : success.getData())
                        {
                            if(curMother.getEmailAddress().equals(motherEmail)){

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

    void setMotherInfo(Mother mother){

        TextView mMotherName = findViewById(R.id.pro_mother_name);
        TextView mMotherPhone = findViewById(R.id.pro_mother_phone);
        TextView mMotherNumberOfChildren = findViewById(R.id.pro_mother_number_of_children);


        mMotherName.setText("Mother Name : "+mother.getName());
        mMotherPhone.setText("Mother Phone Number : "+mother.getPhoneNumber().toString());
        mMotherNumberOfChildren.setText("Mother Number Of Children : "+mother.getNumOfChildren().toString());

    }

    void setFavBtn(Mother mother){
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