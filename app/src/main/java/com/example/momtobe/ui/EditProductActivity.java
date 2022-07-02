package com.example.momtobe.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Comment;
import com.amplifyframework.datastore.generated.model.Mother;
import com.amplifyframework.datastore.generated.model.Product;
import com.bumptech.glide.Glide;
import com.example.momtobe.R;
import com.example.momtobe.adapter.ProductCommentCustomAdapter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class EditProductActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 123;
    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    private String editTitle, editDesc, editPrice , editQuantity , userId  , editImageKey , userEmail;

    ArrayList<Comment> commentArrayList = new ArrayList<>() ;

    private ImageView editImageView;

    private EditText editProductTitle;
    private EditText editProductDesc;
    private EditText editProductPrice;
    private EditText editProductQuantity;

    private Button saveBtn ;

    private Handler handler2;
    private Handler handler1;
    private Handler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Declare Constant
        declareConst();

        // Set prev details
        setDetails();


        // Upload Image
        editImageView.setOnClickListener(view -> uploadImage());


        // Update Image
        saveBtn.setOnClickListener(view -> updateProduct());


    }


    public void updateProduct(){
         handler1 = new Handler(Looper.getMainLooper() , msg -> {


            Product product = Product.builder()
                    .title(editProductTitle.getText().toString())
                    .price(Double.parseDouble(editProductPrice.getText().toString()))
                    .description(editProductDesc.getText().toString())
                    .featured(false)
                    .image(editImageKey)
                    .quantity(Integer.parseInt(editProductQuantity.getText().toString()))
                    .motherProductsId(userId)
                    .id(getIntent().getStringExtra("id"))
                    .build() ;


            Amplify.API.mutate(ModelMutation.update(product) ,
                    success -> Log.i(TAG, "Saved item API") ,
                    error -> Log.e(TAG, "Could not save item to DataStore", error)
            );

            startActivity(new Intent(getApplicationContext() , ProductActivity.class));

            return true ;
        });

      getUserId();

    }

    public void setDetails() {
        Intent intent = getIntent();

        Handler handler = new Handler(Looper.getMainLooper(), msg -> {

            editProductTitle.setText(editTitle);
            editProductDesc.setText(editDesc);
            editProductPrice.setText(editPrice);
            editProductQuantity.setText(editQuantity);
            if (editImageKey != null) {
                    setImage(editImageKey);
            }


            return true;

        });

        Amplify.API.query(
                ModelQuery.get(Product.class, intent.getStringExtra("id")),
                response -> {
                    Log.i(TAG, (response.getData()).getTitle());

                    editTitle = response.getData().getTitle();
                    editDesc = response.getData().getDescription();
                    editPrice = response.getData().getPrice().toString();
                    if (response.getData().getQuantity() == null) editQuantity = "0";
                    else editQuantity = response.getData().getQuantity().toString();
                    editImageKey = response.getData().getImage();


                    Bundle bundle = new Bundle();


                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);


                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
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
                        runOnUiThread(() -> Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(editImageView));
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.close();

            // upload to s3
            // uploads the file
            Amplify.Storage.uploadFile(
                    currentUriStr,
                    file,
                    result -> {
                        Log.i(TAG, "Successfully uploaded: " + result.getKey()) ;
                        editImageKey = result.getKey();
                        runOnUiThread(() -> {
                            setImage(editImageKey);
                        });
                        Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    },
                    storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void declareConst(){
        editProductTitle = findViewById(R.id.edit_edit_product_name);
        editProductDesc = findViewById(R.id.edit_edit_product_desc);
        editProductPrice = findViewById(R.id.edit_edit_product_price);
        editProductQuantity = findViewById(R.id.edit_edit_product_quantity);
        editImageView = findViewById(R.id.edit_avatar);
        saveBtn = findViewById(R.id.edit_btn_add_product);
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
                        handler1.sendMessage(message);
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