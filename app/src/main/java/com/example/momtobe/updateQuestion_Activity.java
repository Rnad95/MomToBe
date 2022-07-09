package com.example.momtobe;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import com.amplifyframework.datastore.generated.model.Question;
import com.amplifyframework.datastore.generated.model.QuestionCategories;
import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class updateQuestion_Activity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;
    private final String TAG = updateQuestion_Activity.class.getName();
    private ArrayList<Cat> arrayListspinner3;
    private Button update;
    private ImageView image_question_update;
    private EditText title_question_update;
    private EditText description_question_update;
    private Spinner spinner3_question_update;
    private Handler handler1;
    private String editTitle;
    private String editDesc;
    private String editImageKey;
    private Question newQuestion;
    private List<QuestionCategories> questionCat;
    private String idQuestion;
    private String userId;
    private List<QuestionCategories> questionCategories1;
    private String imageKey;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);
        declare_find();
        add_Spinner_API_Query();
        setHandler1();
        setDetails();


    }

    public void declare_find() {

        arrayListspinner3 = new ArrayList<>();
        update = findViewById(R.id.btn_register_Question_update);
        image_question_update = findViewById(R.id.Image_Question_update);
        title_question_update = findViewById(R.id.edit_Question_name_update);
        description_question_update = findViewById(R.id.edit_Question_desc_update);
        spinner3_question_update = findViewById(R.id.spinner_Question_update);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setHandler1(){

        handler1 = new Handler(
                Looper.getMainLooper(), msg -> {
            ArrayList<String> spinnerlist = (ArrayList<String>) arrayListspinner3.stream().map(index -> {
                return index.getTitle();
            }).collect(Collectors.toList());

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerlist);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3_question_update.setAdapter(arrayAdapter);

            return true;

        }
        );
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDetails() {

        Intent intent=getIntent();
        idQuestion = intent.getStringExtra("idQuestion");

        Handler handler = new Handler(Looper.getMainLooper(), msg -> {
            title_question_update.setText(editTitle);
            description_question_update.setText(editDesc);
            if (editImageKey != null) {
                setImage(editImageKey);
            }

            questionCategories1 = questionCat.stream().filter(index->index.getQuestion().getId().equals(idQuestion)).collect(Collectors.toList());

            // button that is take the update data and save it in aws
            update.setOnClickListener(view -> updateProduct());
            return true;

        });

        Amplify.API.query(
                ModelQuery.get(Question.class, intent.getStringExtra("idQuestion")),
                response -> {
                    Log.i(TAG, (response.getData()).getTitle());
                    editTitle = response.getData().getTitle();
                    editDesc = response.getData().getDescription();
                    editImageKey = response.getData().getImage();
                    newQuestion = response.getData();
                    questionCat = response.getData().getCategories();

                    Bundle bundle = new Bundle();
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);


                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );

    }
    public void updateProduct(){

        Handler handler2 = new Handler(Looper.getMainLooper() , msg -> {
         String   title1 = title_question_update.getText().toString();
         String   description1 = description_question_update.getText().toString();


           Question newQuestion1 = Question.builder()
                    .title(title1)
                    .description(description1)
                    .featured(false)
                    .id(idQuestion)
                    .image(imageKey)
                   .motherQuestionsId(userId)
                    .build();



            Amplify.API.mutate(
                    ModelMutation.update(newQuestion1),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with title: " + response.getData());},
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );

            startActivity(new Intent(getApplicationContext() , Question_avtivity.class));

            return true ;
        });
        Amplify.Auth.fetchUserAttributes(
                attributes ->{
                    userId = attributes.get(0).getValue();
                    handler2.sendEmptyMessage(1);
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );


        for (int i = 0; i < arrayListspinner3.size(); i++) {
            if (arrayListspinner3.get(i).getTitle() == spinner3_question_update.getSelectedItem().toString()) {
                String idCat=arrayListspinner3.get(i).getId();

                QuestionCategories questionCategories=QuestionCategories
                        .builder()
                        .question(newQuestion)
                        .cat(arrayListspinner3.get(i))
                        .id(questionCategories1.get(0).getId())
                        .build();
                Amplify.API.mutate(
                        ModelMutation.update(questionCategories),
                        response -> {
                            Log.i("MyAmplifyApp", "Added Todo with  categrey Experiance id: " + response.getData().getId());},
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );

            }

        }


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
                        Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    },
                    storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void setImage(String image) {
        if(image != null) {
            Amplify.Storage.downloadFile(
                    image,
                    new File(getApplicationContext().getFilesDir() + "/" + image + "download.jpg"),
                    result -> {
                        Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());
                        runOnUiThread(() -> Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(image_question_update));
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }
    }


}