package com.example.momtobe.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Product;
import com.example.momtobe.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = AddProductActivity.class.getSimpleName();
    public static final int REQUEST_CODE = 123;
    private String imageKey = "" ;

    private ImageView productImage;
    private Button addProduct ;
    private EditText addTitle ;
    private EditText addDesc ;
    private EditText addQuantity ;
    private EditText addPrice ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Declare const
        declareConst();


        // Upload Image


        productImage.setOnClickListener(view -> uploadImage());

        // AWS Add product api

        addProduct();

    }


    public void addProduct(){

        Amplify.Auth.fetchUserAttributes(
                attributes -> Log.i("AuthDemo", "User attributes = " + attributes.get(0).getValue().toString()),
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
//        Product product = Product.builder()
//                .title(addTitle.getText().toString())
//                .price(addPrice.getText().toString())
//                .description(addDesc.getText().toString())
//                .featured(false)
//                .image(imageKey)
//                .motherProductsId()

//        Amplify.API.mutate(ModelMutation.create(item) ,
//                    success -> Log.i(TAG, "Saved item API") ,
//                    error -> Log.e(TAG, "Could not save item to DataStore", error)
//            );
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


    private void handleSendImage(Intent intent){
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            imageS3upload(imageUri);
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

    public void declareConst(){
        addTitle = findViewById(R.id.edit_Experiance_name);
        addDesc = findViewById(R.id.edit_Experiance_desc);
        addPrice = findViewById(R.id.edit_product_price);
        addQuantity = findViewById(R.id.edit_product_quantity);
        productImage = findViewById(R.id.avatar);
    }
}