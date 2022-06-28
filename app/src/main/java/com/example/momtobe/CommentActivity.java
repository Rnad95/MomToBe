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

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getName();
    private Handler handler;
    private Handler handler1;
    private String COMMENT_Array="Comment Array";
    private Comment newComment;
    private Question question_item;
    private ImageView imageViewComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ArrayList<Comment> taskArrayList=new ArrayList<>();
        EditText comment=findViewById(R.id.editTextTextPersonName);
        Button send=findViewById(R.id.button2);
        RecyclerView RecycleTask = findViewById(R.id.Recycle_Comment);
        imageViewComment = findViewById(R.id.imageViewComment);
        TextView titleComment=findViewById(R.id.titleComment);
        TextView descriptionComment=findViewById(R.id.descriptionComment);

        Intent intent1 = getIntent();
        String Questionid= intent1.getStringExtra(Question_avtivity.Questionid);
        handler=new Handler(
                Looper.getMainLooper(), msg -> {
            RecycleModel_comment recycleModels = new RecycleModel_comment(taskArrayList, position -> {
                Toast.makeText(
                        CommentActivity.this,
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
//            imageViewComment.setImageURI(question_item.getImage());
            titleComment.setText(question_item.getTitle());
            descriptionComment.setText(question_item.getDescription());
            return true;

        }
        );
        Amplify.API.query(
                ModelQuery.get(Question.class,Questionid),
                teamsName -> {
                    for (Comment Comment : teamsName.getData().getComments()) {
                        taskArrayList.add(Comment);
                    }
                    question_item = teamsName.getData();
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
                    .experienceCommentsId("sssssss")
                    .productCommentsId("ppppppp")
                    .motherCommentsId("mmmmmmmmmmm")
                    .questionCommentsId("qqqqqqqqqqqq")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newComment),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id Question: "+response );
                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
        });




    }
}