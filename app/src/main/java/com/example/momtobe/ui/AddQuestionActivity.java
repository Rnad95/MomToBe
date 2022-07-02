package com.example.momtobe.ui;

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
import com.amplifyframework.datastore.generated.model.Question;
import com.example.momtobe.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AddQuestionActivity extends AppCompatActivity {
    private static final String TAG = AddExperianceActivity.class.getName();
    private static final int REQUEST_CODE = 1234;
    private ArrayList<Cat> arrayListspinner3;
    private Handler handler1;
    private Spinner spinner3;
    private String imageKey = "" ;
    private String userId ;

    private static final int REQUEST_CODE_SEND = 4567;


    private Uri currentUri=null;
    private String fileName;
    private Uri fileData;
    private ImageView image_Question;
    private Button button;
    private EditText title;
    private EditText description;
    private Question newQuestion;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        arrayListspinner3 = new ArrayList<>();
        button = findViewById(R.id.btn_register);
        image_Question = findViewById(R.id.Image_experiance);
        title = findViewById(R.id.edit_Question_name);
        description = findViewById(R.id.edit_Question_desc);


        spinner3=findViewById(R.id.spinner);


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
        add_Spinner_API_Query();
        image_Question.setOnClickListener(view ->uploadImage() );

        button.setOnClickListener(view -> {
            String title1 = title.getText().toString();
            String description1 = description.getText().toString();



//            for (int i = 0; i < arrayListspinner3.size(); i++) {
//
//                if (arrayListspinner3.get(i).getTitle() == spinner3.getSelectedItem().toString()) {
//
//
//                }
//
//            }
            newQuestion = Question.builder()
                    .title(title1)
                    .description(description1)
                    .featured(false)
                    .motherQuestionsId(userId)
//                            .image(currentUri.toString())
                    .build();
            Amplify.API.mutate(
                    ModelMutation.create(newQuestion),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id Question: "+response );
                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
        });
        Amplify.Auth.fetchUserAttributes(
                attributes ->{
                    userId = attributes.get(0).getValue();
                    Bundle bundle = new Bundle();
                    bundle.putString("data" , "Done");

                    Message message = new Message();
                    message.setData(bundle);
                    handler1.sendMessage(message);
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
    }
    public void add_Spinner_API_Query() {
        Amplify.API.query(
                ModelQuery.list(Cat.class),
                teamsName -> {
                    Log.i(TAG, "add_Spinner_API_Query: "+teamsName);

                    for (Cat category : teamsName.getData()) {
                        arrayListspinner3.add(category);
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
                        Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    },
                    storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}