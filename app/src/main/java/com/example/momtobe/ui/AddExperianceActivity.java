package com.example.momtobe.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Cat;
import com.amplifyframework.datastore.generated.model.Experience;
import com.amplifyframework.datastore.generated.model.ExperienceCategories;
import com.amplifyframework.datastore.generated.model.Mother;
import com.bumptech.glide.Glide;
import com.example.momtobe.Experiance_activity;
import com.example.momtobe.R;
import com.example.momtobe.adapter.ProductCustomAdapter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AddExperianceActivity extends AppCompatActivity {
    private static final String TAG = AddExperianceActivity.class.getName();
    private static final int REQUEST_CODE = 1234;
    private ArrayList<Cat> arrayListspinner3;
    private Handler handler , handler1 , handler2;
    private Spinner spinner3;
    private String imageKey = "" ;
    private String userId , userEmail ;


    private ImageView image_experiance;
    private Button button;
    private EditText title;
    private EditText description;
    private Experience newExperience;



    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experiance);
         arrayListspinner3 = new ArrayList<>();
        button = findViewById(R.id.btn_register_experiance);
        image_experiance = findViewById(R.id.Image_Experiance);
        title = findViewById(R.id.edit_Experiance_name);
        description = findViewById(R.id.edit_Experiance_desc);
        spinner3=findViewById(R.id.spinner_exeriance);


        image_experiance.setOnClickListener(view ->{
            uploadImage();

        });

        setHandler1();

        add_Spinner_API_Query();



        button.setOnClickListener(view -> {

            handler = new Handler(Looper.getMainLooper() , msg -> {

                String title1 = title.getText().toString();
                String description1 = description.getText().toString();




                newExperience = Experience.builder()
                        .title(title1)
                        .description(description1)
                        .featured(false)
                        .image(imageKey)
                        .motherExperiencesId(userId)
                        .build();



                Amplify.API.mutate(
                        ModelMutation.create(newExperience),
                        response -> {
                            Log.i("MyAmplifyApp", "Added Todo with title: " + response.getData().getTitle());},
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );

                for (int i = 0; i < arrayListspinner3.size(); i++) {

                    if (arrayListspinner3.get(i).getTitle() == spinner3.getSelectedItem().toString()) {
                        ExperienceCategories experienceCategories=ExperienceCategories
                                .builder()
                                .cat(arrayListspinner3.get(i))
                                .experience(newExperience).build();
                        Amplify.API.mutate(
                                ModelMutation.create(experienceCategories),
                                response -> {
                                    Log.i("MyAmplifyApp", "Added Todo with  categrey Experiance id: " + response.getData().getId());},
                                error -> Log.e("MyAmplifyApp", "Create failed", error)
                        );

                    }

                }


                startActivity(new Intent(this , Experiance_activity.class));

                return true ;
            });

            getUserId();

        });


    }


    public void add_Spinner_API_Query() {
        Amplify.API.query(
                ModelQuery.list(Cat.class),
                teamsName -> {
                    for (Cat note : teamsName.getData()) {
                        arrayListspinner3.add(note);
                    }

                    handler1.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, error.toString())
        );


    }
    public void uploadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            Log.e(TAG, "onActivityResult: Error getting image from device");
            return;
        }

        switch(requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();

                // Upload image to S3
                imageS3upload(currentUri);

                return;
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {

        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }
    private void imageS3upload(Uri currentUri){
        Bitmap bitmap = null;
        String currentUriStr = String.valueOf(currentUri.getLastPathSegment())  + ".jpg";
        Log.i("CurrentURI" , currentUriStr);
        try {
            bitmap = getBitmapFromUri(currentUri);
            File file = new File(getApplicationContext().getFilesDir(), currentUriStr );
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

            // upload to s3
            // uploads the file
            Amplify.Storage.uploadFile(
                    currentUriStr,
                    file,
                    result -> {
                        Log.i(TAG, "Successfully uploaded: " + result.getKey()) ;
                        imageKey = result.getKey();
                        runOnUiThread(() -> {
                            setImage(imageKey);
                        });
                        Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    },
                    storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setHandler1(){

        handler1 = new Handler(
                Looper.getMainLooper(), msg -> {
            ArrayList<String> spinnerlist = (ArrayList<String>) arrayListspinner3.stream().map(index -> {
                return index.getTitle();
            }).collect(Collectors.toList());

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerlist);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3.setAdapter(arrayAdapter);

            return true;

        }
        );
    }

    private void setImage(String image) {
        if(image != null) {
            Amplify.Storage.downloadFile(
                    image,
                    new File(getApplicationContext().getFilesDir() + "/" + image + "download.jpg"),
                    result -> {
                        Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());
                        runOnUiThread(() -> Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(image_experiance));
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }
    }

    public void getUserId(){
        handler2 = new Handler(Looper.getMainLooper() , msg -> {
            Amplify.API.query(
                    ModelQuery.list(Mother.class, Mother.EMAIL_ADDRESS.contains(userEmail)),
                    response -> {
                        for (Mother mother : response.getData()) {
                            userId =  mother.getId() ;
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("data" , "Done");
                        Message message = new Message();
                        message.setData(bundle);
                        handler.sendMessage(message);
                    },
                    error -> Log.e("MyAmplifyApp", "Query failure", error)
            );

            return true ;
        });


        Amplify.Auth.fetchUserAttributes(
                attributes ->{
                    Log.i("UserEmail" , attributes.toString());
                    userEmail = attributes.get(3).getValue();
                    Bundle bundle = new Bundle();
                    bundle.putString("data" , "Done");
                    Message message = new Message();
                    message.setData(bundle);
                    handler2.sendMessage(message);
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

    }
}