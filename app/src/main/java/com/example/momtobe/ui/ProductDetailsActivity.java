package com.example.momtobe.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Product;
import com.bumptech.glide.Glide;
import com.example.momtobe.R;

import java.io.File;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    private String title, desc, price , quantity;
    private ImageView imageView;
    private String imageKey;


    private TextView productTitle;
    private TextView productDesc;
    private TextView productPrice;
    private TextView productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        // Declare Constant
        declareConstant();



        // Set Details
        setDetails();


        }


        public void setDetails(){
            Intent intent = getIntent();
            Handler handler = new Handler(Looper.getMainLooper(), msg -> {

                productTitle.setText(title);
                productDesc.setText(desc);
                productPrice.setText(price);
                productQuantity.setText(quantity);
                if (imageKey != null) {
                    setImage(imageKey);
                }

                return true;
            });

            Amplify.API.query(
                    ModelQuery.get(Product.class, intent.getStringExtra("id")),
                    response -> {
                        Log.i(TAG, (response.getData()).getTitle());

                        title = response.getData().getTitle();
                        desc = response.getData().getDescription();
                        price = response.getData().getPrice().toString();
                        if(response.getData().getQuantity() == null) quantity = "0";
                        else quantity = response.getData().getQuantity().toString();
                        imageKey = response.getData().getImage();


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
                            runOnUiThread(() -> {
                                Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(imageView);
                            });
                        },
                        error -> Log.e(TAG, "Download Failure", error)
                );
            }
        }

        private void declareConstant(){

            productTitle = findViewById(R.id.product_details_text_title);
            productDesc = findViewById(R.id.product_details_text_description);
            productPrice = findViewById(R.id.product_details_price);
            productQuantity = findViewById(R.id.product_details_quantity);
            imageView = findViewById(R.id.product_details_img);
        }

}