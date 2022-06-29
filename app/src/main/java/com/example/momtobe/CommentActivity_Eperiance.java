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

import java.util.ArrayList;

public class CommentActivity_Eperiance extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getName();
    private Handler handler;
    private Handler handler1;
    private String COMMENT_Array="Comment Array";
    private Comment newComment;
    private Experience Experiance_item;
    private ImageView imageViewComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_eperiance);
        ArrayList<Comment> taskArrayList=new ArrayList<>();
        EditText comment=findViewById(R.id.editTextTextPersonName);
        Button send=findViewById(R.id.button2);
        RecyclerView RecycleTask = findViewById(R.id.Recycle_CommentExperiance);
        imageViewComment = findViewById(R.id.imageViewCommentExperiance);
        TextView titleComment=findViewById(R.id.titleCommentExperiance);
        TextView descriptionComment=findViewById(R.id.descriptionCommentExperiance);

        Intent intent1 = getIntent();
        String experianceName= intent1.getStringExtra(Experiance_activity.experianceName);
        handler=new Handler(
                Looper.getMainLooper(), msg -> {
            RecycleModel_comment recycleModels = new RecycleModel_comment(taskArrayList, position -> {
                Toast.makeText(
                        CommentActivity_Eperiance.this,
                        "The item clicked => " + taskArrayList.get(position).getContent(), Toast.LENGTH_SHORT).show();
            });
            RecycleTask.setAdapter(recycleModels);
            RecycleTask.setHasFixedSize(true);
            RecycleTask.setLayoutManager(new LinearLayoutManager(this));
            return true;

        }
        );
        handler1=new Handler(
                Looper.getMainLooper(), msg -> {
//            imageViewComment.setImageURI(Uri.parse(Experiance_item.getImage()));
            titleComment.setText(Experiance_item.getTitle());
            descriptionComment.setText(Experiance_item.getDescription());
            return true;

        }
        );
        Log.i(TAG, "onCreate: experianceName"+experianceName);
        Amplify.API.query(
                ModelQuery.get(Experience.class,experianceName),
                teamsName -> {
                    for (Comment Comment : teamsName.getData().getComments()) {
                        taskArrayList.add(Comment);
                    }
                    Log.i(TAG, "onCreate:for Query Experiance detail " +teamsName.getData());
                    Experiance_item = teamsName.getData();
                    Bundle bundle1=new Bundle();
                    bundle1.putString(COMMENT_Array,"DONE");
                    Message message1=new Message();
                    message1.setData(bundle1);
                    handler1.sendMessage(message1);
                },
                error -> Log.e(TAG, error.toString())
        );

        Amplify.DataStore.observe(Comment.class,
                started -> Log.i(TAG, "Observation began."),
                change -> {Log.i(TAG, change.item().toString());

                    Bundle bundle=new Bundle();
                    bundle.putString(COMMENT_Array,change.item().toString());
                    Message message=new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                },
                failure -> Log.e(TAG, "Observation failed.", failure),
                () -> Log.i(TAG, "Observation complete.")
        );

        send.setOnClickListener(view -> {
            String content=comment.getText().toString();
            newComment = Comment.builder()
                    .content(content)
                    .experienceCommentsId(Experiance_item.getId())
                    .productCommentsId("ppppppp")
                    .motherCommentsId("mmmmmmmmmmm")
                    .questionCommentsId("qqqqqqqqqqqq")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newComment),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id Question: "+response.getData());
                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
        });



    }
}