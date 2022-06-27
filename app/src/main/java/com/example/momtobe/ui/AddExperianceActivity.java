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
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Category;
import com.amplifyframework.datastore.generated.model.Experience;
import com.example.momtobe.R;

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
    private ArrayList<Category> arrayListspinner3;
    private Handler handler1;
    private Spinner spinner3;

    private static final int REQUEST_CODE_SEND = 4567;


    private Uri currentUri=null;
    private String fileName;
    private Uri fileData;
    private ImageView image_experiance;
    private Button button;
    private EditText title;
    private EditText description;
    private Experience newExperience;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experiance);
         arrayListspinner3 = new ArrayList<>();
        button = findViewById(R.id.btn_register);
        image_experiance = findViewById(R.id.Image_experiance);
        title = findViewById(R.id.edit_Experiance_name);
        description = findViewById(R.id.edit_Experiance_desc);


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
image_experiance.setOnClickListener(view ->pictureUpload() );

        button.setOnClickListener(view -> {
            String title1 = title.getText().toString();
            String description1 = description.getText().toString();

            String state = spinner3.getSelectedItem().toString();


            for (int i = 0; i < arrayListspinner3.size(); i++) {

                if (arrayListspinner3.get(i).getTitle() == spinner3.getSelectedItem().toString()) {

                    newExperience = Experience.builder()
                            .title(title1)
                            .description(description1)
                            .featured(false)
                            .image(currentUri.toString())
                             .build();

                }

            }
            Amplify.API.mutate(
                    ModelMutation.create(newExperience),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getTitle());



                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );





        });





    }


    public void add_Spinner_API_Query() {
        Amplify.API.query(
                ModelQuery.list(Category.class),
                teamsName -> {
                    for (Category note : teamsName.getData()) {
                        arrayListspinner3.add(note);
                    }
                    handler1.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, error.toString())
        );


    }
    private void pictureUpload() {

        Intent intent1=new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        startActivityForResult(intent1, REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        File file1 = new File(data.getData().getPath());
        fileName = file1.getName();
        fileData = data.getData();
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            Log.e(TAG, "onActivityResult: Error getting image from device");
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_SEND:
                // Get photo picker response for single select.
                currentUri = data.getData();

                // Do stuff with the photo/video URI.
                Log.i(TAG, "onActivityResult: the uri is => " + currentUri);

                try {
                    Bitmap bitmap = getBitmapFromUri(currentUri);

                    File file = new File(getApplicationContext().getFilesDir(), "image1.jpg");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();

                    // upload to s3
                    // uploads the file
                    Amplify.Storage.uploadFile(
                            "image.jpg",
                            file,
                            result -> Log.i(TAG, "Successfully uploaded: " + result.getKey()),
                            storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

}