package com.example.momtobe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Mother;
import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Settings extends AppCompatActivity {

    private static final String TAG = "Settings";
    public static final int REQUEST_CODE = 123;

    private String imageKey = "" ;
    private String showEmail;
    Mother mother ;
    Handler handler;
    Button save_btn;
    ImageButton updateImage ;
    ImageView imageView ;


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
            setSaveButton(mother);
            showImage();

            updateImage = findViewById(R.id.set_change_picture);
            updateImage.setOnClickListener(view -> uploadImage());

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


    public void uploadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
        showImage();

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

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {

        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

    void showImage(){
        imageView = findViewById(R.id.set_profile_picture);
        Bundle bundle = getIntent().getExtras();
        String imageKey = bundle.getString("imageLink");

        if (imageKey != null) {
            setImage(imageKey);
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
                        runOnUiThread(() -> Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(imageView));
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }
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
                    .image(imageKey)
                    .id(mother.getId())
                    .build();

            Log.i(TAG, "setSaveButton: testing ->"+ newMother);
            Amplify.API.mutate(ModelMutation.update(newMother),
                    response -> {
                        Log.i("MyAmplifyApp", "Todo with id: " + response);
                        finish();
                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );


            Intent intent = new Intent(Settings.this,Profile.class);
            intent.putExtra("EMAIL_ADDRESS",showEmail);
            startActivity(intent);

        });
    }
}