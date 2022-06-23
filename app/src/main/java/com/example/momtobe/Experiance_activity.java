package com.example.momtobe;

import androidx.appcompat.app.ActionBar;
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
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Experience;
import com.amplifyframework.datastore.generated.model.Question;

import java.util.ArrayList;

public class Experiance_activity extends AppCompatActivity {
    public static final String TASK_Array = "taskId";
    private static final String TAG = Experiance_activity.class.getName();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Experience> taskArrayList=new ArrayList<>();
        Intent intent = getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiance);
        RecyclerView RecycleTask = findViewById(R.id.Recycle_task);
        Button add=findViewById(R.id.button2);
        EditText editTextDetail=findViewById(R.id.editTextTextPersonName);

        handler=new Handler(
                Looper.getMainLooper(), msg -> {
            RecycleModels recycleModels = new RecycleModels(taskArrayList, position -> {
                Toast.makeText(
                        Experiance_activity.this,
                        "The item clicked => " + taskArrayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                String titleQurey= taskArrayList.get(position).getTitle();
                String StatusQuery=taskArrayList.get(position).getDescription();



                startActivity(intent);
            });
            RecycleTask.setAdapter(recycleModels);
            RecycleTask.setHasFixedSize(true);
            RecycleTask.setLayoutManager(new LinearLayoutManager(this));

            return true;

        }
        );
        add.setOnClickListener(view -> {

            Experience newExperience= Experience.builder().
                    title("Experience").
                    description(editTextDetail.getText().toString()).
                    featured(true).
//                  image().
                    build();

            Amplify.API.mutate(
                    ModelMutation.create(newExperience),
                    response -> {
                        Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getTitle());
                        handler.sendEmptyMessage(1);


                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );



        });

        Amplify.API.query(
                ModelQuery.list(Experience.class),
                teamsName -> {
                    for (Experience note : teamsName.getData()) {
                        taskArrayList.add(note);
                    }

                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, error.toString())
        );


        Amplify.DataStore.observe(Experience.class,
                started -> Log.i(TAG, "Observation began."),
                change -> {Log.i(TAG, change.item().toString());

                    Bundle bundle=new Bundle();
                    bundle.putString(TASK_Array,change.item().toString());

                    Message message=new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);


                },
                failure -> Log.e(TAG, "Observation failed.", failure),
                () -> Log.i(TAG, "Observation complete.")
        );



    }
}