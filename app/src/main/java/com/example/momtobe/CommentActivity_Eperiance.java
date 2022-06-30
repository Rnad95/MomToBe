package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.amplifyframework.datastore.generated.model.Experience;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class CommentActivity_Eperiance extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getName();
    private Handler handler;

    private String COMMENT_Array="Comment Array";
    private Comment newComment;
    private Experience Experiance_item;
    private ImageView imageViewComment;
    private String imageKey;
    private String experianceName;
    private String MotherExperiences;

    private ArrayList<Comment> taskArrayList;
    private RecyclerView recycleTask;
    private EditText comment;
    private TextView titleComment;
    private TextView descriptionComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_eperiance);
        taskArrayList = new ArrayList<>();
        comment = findViewById(R.id.editTextTextPersonName);
        Button send=findViewById(R.id.button2);
        recycleTask = findViewById(R.id.Recycle_CommentExperiance);
        imageViewComment = findViewById(R.id.imageViewCommentExperiance);
        titleComment = findViewById(R.id.titleCommentExperiance);
        descriptionComment = findViewById(R.id.descriptionCommentExperiance);

        Intent intent1 = getIntent();
        experianceName = intent1.getStringExtra(Experiance_activity.experianceName);
        MotherExperiences = intent1.getStringExtra(Experiance_activity.MotherExperiences);



        Log.i(TAG, "onCreate: experianceName"+ experianceName);

        send.setOnClickListener(view -> {
            String content= comment.getText().toString();
            newComment = Comment.builder()
                    .content(content)
                    .experienceCommentsId(Experiance_item.getId())
                    .productCommentsId("ppppppp")
                    .motherCommentsId(MotherExperiences)
                    .questionCommentsId("qqqqqqqqqqqq")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newComment),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id Question: "+response.getData());
                        taskArrayList.add(response.getData());

                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
            setDetails();
        });


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
                            Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(imageViewComment);
                        });
                    },
                    error -> Log.e(TAG, "Download Failure", error)
            );
        }
    }
    @Override
    protected void onResume() {
        setDetails();
        super.onResume();
    }
public void setDetails(){
    handler=new Handler(
            Looper.getMainLooper(), msg -> {
        imageKey=Experiance_item.getImage();
        if (imageKey != null) {
            setImage(imageKey);
        }

        titleComment.setText(Experiance_item.getTitle());
        descriptionComment.setText(Experiance_item.getDescription());


        RecycleModel_comment recycleModels = new RecycleModel_comment(taskArrayList, position -> {
            Toast.makeText(
                    CommentActivity_Eperiance.this,
                    "The item clicked => " + taskArrayList.get(position).getContent(), Toast.LENGTH_SHORT).show();
        });
        recycleTask.setAdapter(recycleModels);
        recycleTask.setHasFixedSize(true);
        recycleTask.setLayoutManager(new LinearLayoutManager(this));
        return true;

    }
    );
    Amplify.API.query(
            ModelQuery.get(Experience.class,experianceName),
            teamsName -> {
                Log.i(TAG, "onCreate: get comment"+ teamsName.getData().getComments());
                for (Comment Comment : teamsName.getData().getComments()) {
                    taskArrayList.add(Comment);
                }
                Log.i(TAG, "onCreate:for Query Experiance detail " +teamsName.getData());
                Experiance_item = teamsName.getData();
                Bundle bundle1=new Bundle();
                bundle1.putString(COMMENT_Array,"DONE");
                Message message1=new Message();
                message1.setData(bundle1);
                handler.sendMessage(message1);
            },
            error -> Log.e(TAG, error.toString())
    );


}
}