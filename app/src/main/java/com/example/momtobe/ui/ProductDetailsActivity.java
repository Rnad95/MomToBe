package com.example.momtobe.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.example.momtobe.CommentActivity_Eperiance;
import com.example.momtobe.R;
import com.example.momtobe.adapter.ProductCommentCustomAdapter;
import com.example.momtobe.adapter.ProductCustomAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String TAG = ProductDetailsActivity.class.getSimpleName();
    private String title, desc, price , quantity , userId , phoneNumber , imageKey , callUserId , userEmail;

    ArrayList<Comment> commentArrayList = new ArrayList<>() ;

    private ImageView imageView;

    private TextView productTitle;
    private TextView productDesc;
    private TextView productPrice;
    private TextView productQuantity;

    private Button callBtn ;
    private Button productAddCommentBtn;

    private EditText productCommentEditText ;

    private RecyclerView commentRecyclerView ;
    private Handler handler1;
    private Handler handler2;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        // Declare Constant
        declareConstant();

        productAddCommentBtn.setOnClickListener(v -> {
            addComment();
        });



    }

    @Override
    protected void onResume() {
        setDetails();
        super.onResume();
    }

    public void setDetails(){
        Intent intent = getIntent();
        handler = new Handler(Looper.getMainLooper(), msg -> {

            productTitle.setText(title);
            productDesc.setText(desc);
            productPrice.setText(price);
            productQuantity.setText(quantity);
            if (imageKey != null) {
                setImage(imageKey);
            }

            callBtn.setOnClickListener(v -> {
                startCallIntent();
            });

            RecyclerView recyclerView = findViewById(R.id.product_comment_recyclier_view);

            Log.i("userIdAdded" , userId);
            ProductCommentCustomAdapter customRecyclerView = new ProductCommentCustomAdapter(commentArrayList, new ProductCommentCustomAdapter.CustomClickListener() {
                @Override
                public void onTaskItemClicked(int position) {
                    Log.i(TAG , "This is comment");
                    Toast.makeText(ProductDetailsActivity.this, "this item is clicked", Toast.LENGTH_SHORT).show();
                    setDetails();
                }
            } , userId );

            recyclerView.setAdapter(customRecyclerView);


            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));



            return true ;

        });

       getUserId();

        if (!commentArrayList.isEmpty()) commentArrayList.clear();

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
                    commentArrayList.addAll(response.getData().getComments());
                    callUserId = response.getData().getMotherProductsId() ;

                    Bundle bundle = new Bundle();

                    Message message = new Message();
                    message.setData(bundle);
                    handler1.sendMessage(message);

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
                        runOnUiThread(() -> Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(imageView));
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
        callBtn = findViewById(R.id.product_call_btn);
        productAddCommentBtn = findViewById(R.id.product_comment_add_btn);
        productCommentEditText = findViewById(R.id.product_comment_add_comment_edit);
        commentRecyclerView = findViewById(R.id.product_comment_recyclier_view);
    }

    public void startCallIntent(){
        Log.i("callId" , callUserId);
        Amplify.API.query(
                ModelQuery.get(Mother.class, callUserId),
                response -> {
                    Log.i(TAG, (response.getData()).getPhoneNumber());

                    phoneNumber = response.getData().getPhoneNumber();

                    runOnUiThread(() -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL) ;
                        intent.setData(Uri.parse("tel:" + response.getData().getPhoneNumber()));
                        startActivity(intent);
                    });

                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );

    }

    public void addComment(){
        Intent intent = getIntent();
        String commentContent= productCommentEditText.getText().toString();

        Comment newComment = Comment.builder()
                .content(commentContent)
                .experienceCommentsId("000")
                .productCommentsId(intent.getStringExtra("id"))
                .motherCommentsId(userId)
                .questionCommentsId("000")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newComment),
                response -> Log.i("MyAmplifyApp", "Product comment is added: "+response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

        setDetails();
        productCommentEditText.setText("");
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

        handler1 = new Handler(Looper.getMainLooper() , msg -> {

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

            return true ;
        });




    }

}