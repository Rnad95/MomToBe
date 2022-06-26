package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Mother;

public class Profile extends AppCompatActivity {
    private static final String TAG = "profile";
    Mother mother ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int motherId = 0 ; // only to avoid errors
//        motherId = getIntent().getStringExtra(ID);

        Amplify.API.query(
                ModelQuery.list(Mother.class),
                success->{
                    if(success.hasData())
                    {
                        for (Mother mother : success.getData())
                        {
                            if(mother.getId().equals(motherId)){
                                setMotherInfo(mother);
                                setMotherImage(mother);
                                setRecyclerFragment(mother);
                            }
                        }
                    }
                },
                fail->{
                    Log.i(TAG, "onCreate: failed to find mother in database");
                }
        );
    }

    void setMotherImage(Mother mother){  //TODO waiting for s3 -> hamze
        ImageView motherImage = findViewById(R.id.pro_profile_picture);

    }

    void setMotherInfo(Mother mother){

        TextView motherName = findViewById(R.id.pro_mother_name);
        TextView motherPhone = findViewById(R.id.pro_mother_phone);
        TextView motherNumberOfChildren = findViewById(R.id.pro_mother_number_of_children);

        motherName.setText(mother.getName());
        motherPhone.setText(mother.getPhoneNumber());
        motherNumberOfChildren.setText(mother.getNumOfChildren());

    }

    void setRecyclerFragment(Mother mother){
        
    }

}