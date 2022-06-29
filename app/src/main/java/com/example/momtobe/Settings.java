package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
    Mother mother ;
    Handler handler;
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bundle bundle = getIntent().getExtras();
        showEmail = bundle.getString("EMAIL_ADDRESS");
        save_btn = findViewById(R.id.set_save_btn);

        findMotherAPI();
        handler =  new Handler(Looper.getMainLooper() , msg -> {
            Log.i(TAG, "onCreate: 35 mother -> "+mother);
//            showImage();
//            uploadImage(mother);
            setSaveButton(mother);

         return true ;
        });



    }

    void findMotherAPI (){
        Amplify.API.query(
                ModelQuery.list(Mother.class),
                success->{
                    if(success.hasData())
                    {
                        for (Mother curMother : success.getData())
                        {
                            if(curMother.getEmailAddress().equals(showEmail)){

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



    void showImage(){  //TODO waiting for s3 -> hamze
    }

    void uploadImage (Mother mother){  //TODO waiting for s3 -> hamze and photo picker
    }

    void setSaveButton (Mother mother){
        save_btn.setOnClickListener(view->{

            EditText name = findViewById(R.id.set_mother_name);
            EditText phone = findViewById(R.id.set_phone);
            EditText numberOfChildren = findViewById(R.id.set_children_number);


            int numOfChildrenInt = Integer.parseInt(numberOfChildren.getText().toString());
            String phoneString = phone.getText().toString();
            String nameString = name.getText().toString();

            Mother newMother = Mother.builder()
                    .name(nameString)
                    .numOfChildren(numOfChildrenInt)
                    .emailAddress(showEmail)
                    .phoneNumber(phoneString)
                    .id(mother.getId())
                    .build();

            Log.i(TAG, "setSaveButton: ayah testing ->"+ newMother);
            Amplify.API.mutate(ModelMutation.update(newMother),
                    response -> Log.i("MyAmplifyApp", "Todo with id: " + response),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );


            Intent intent = new Intent(Settings.this,Profile.class);
            intent.putExtra("EMAIL_ADDRESS",showEmail);
            startActivity(intent);

        });
    }
}