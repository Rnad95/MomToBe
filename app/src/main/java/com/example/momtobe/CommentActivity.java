package com.example.momtobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.amplifyframework.datastore.generated.model.Question;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getName();
    private Handler handler;

    private String COMMENT_Array="Comment Array";
    private Comment newComment;
    private Question question_item;
    private ImageView imageViewComment;
    private String imageKey;
    private ArrayList<Comment> taskArrayList;
    private EditText comment;
    private Button send;
    private RecyclerView recycleTask;
    private TextView titleComment;
    private TextView descriptionComment;
    private String questionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        taskArrayList = new ArrayList<>();
        comment = findViewById(R.id.editTextTextPersonName);
        send = findViewById(R.id.button2);
        recycleTask = findViewById(R.id.Recycle_Comment);
        imageViewComment = findViewById(R.id.imageViewComment);
        titleComment = findViewById(R.id.titleComment);
        descriptionComment = findViewById(R.id.descriptionComment);

        Intent intent1 = getIntent();
        questionid = intent1.getStringExtra(Question_avtivity.Questionid);

        send.setOnClickListener(view -> {
            String content= comment.getText().toString();
            newComment = Comment.builder()
                    .content(content)
                    .experienceCommentsId("sssssss")
                    .productCommentsId("ppppppp")
                    .motherCommentsId(question_item.getMotherQuestionsId())
                    .questionCommentsId(questionid)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newComment),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id Question: "+response );
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
    public void setDetails(){
        handler=new Handler(
                Looper.getMainLooper(), msg -> {

            imageKey=question_item.getImage();
            if (imageKey != null) {
                setImage(imageKey);
            }
            titleComment.setText(question_item.getTitle());
            descriptionComment.setText(question_item.getDescription());
            RecycleModel_comment recycleModels = new RecycleModel_comment(taskArrayList, position -> {
                Toast.makeText(
                        CommentActivity.this,
                        "The item clicked => " + taskArrayList.get(position).getContent(), Toast.LENGTH_SHORT).show();
            });
            recycleTask.setAdapter(recycleModels);
            recycleTask.setHasFixedSize(true);
            recycleTask.setLayoutManager(new LinearLayoutManager(this));
            return true;

        }
        );

        Amplify.API.query(
                ModelQuery.get(Question.class,questionid),
                teamsName -> {
                    for (Comment Comment : teamsName.getData().getComments()) {
                        taskArrayList.add(Comment);
                    }
                    question_item = teamsName.getData();
                    Bundle bundle1=new Bundle();
                    bundle1.putString(COMMENT_Array,"DONE");
                    Message message1=new Message();
                    message1.setData(bundle1);
                    handler.sendMessage(message1);
                },
                error -> Log.e(TAG, error.toString())
        );

    }
    @Override
    protected void onResume() {
        setDetails();
        super.onResume();
    }

}