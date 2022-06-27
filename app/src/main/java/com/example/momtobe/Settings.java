package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Mother;

public class Settings extends AppCompatActivity {

    private static final String TAG = "Settings";
    private String showEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        GetEmail();

//        int motherId = 0 ; // only to avoid errors
////        motherId = getIntent().getStringExtra(ID);
//
//        Amplify.API.query(
//                ModelQuery.list(Mother.class),
//                success->{
//                    if(success.hasData())
//                    {
//                        for (Mother mother : success.getData())
//                        {
//                            if(mother.getId().equals(motherId)){
//                                showImage();
//                                uploadImage(mother);
//                                setSaveButton(mother);
//                            }
//                        }
//                    }
//                },
//                fail->{
//                    Log.i(TAG, "onCreate: failed to find mother in database");
//                }
//        );

    }

    private void GetEmail(){
        Bundle bundle = getIntent().getExtras();
        showEmail = bundle.getString("EMAIL");
    }

    void showImage(){  //TODO waiting for s3 -> hamze

    }

    void uploadImage (Mother mother){  //TODO waiting for s3 -> hamze and photo picker


    }

    void setSaveButton (Mother mother){
        Button save_btn = findViewById(R.id.set_save_btn);
        save_btn.setOnClickListener(view->{
            //check delete from mohammed Ghanem , there's an update.
            EditText name = findViewById(R.id.set_mother_name);
            EditText phone = findViewById(R.id.set_phone);
            EditText numberOfChildren = findViewById(R.id.set_children_number);


            int numOfChi = Integer.valueOf(numberOfChildren.getText().toString());

            Mother newMother = Mother.builder()
                    .name(name.getText().toString().equals(null)?mother.getName():name.getText().toString())
                    .numOfChildren(numberOfChildren.getText()==null? mother.getNumOfChildren():numOfChi)
                    .phoneNumber(phone.getText().toString()==null? mother.getPhoneNumber():phone.getText().toString())
                    .id(mother.getId())
                    .build();

            Amplify.API.mutate(ModelMutation.update(newMother),
                    response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

        });
    }
}