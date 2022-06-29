package com.example.momtobe;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Experience;
import com.amplifyframework.datastore.generated.model.Question;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecycleModels extends RecyclerView.Adapter<RecycleModels.taskviewsholoder>{
    List<Experience> models;
    CustomClickListener listener;
    Context mContext ;
    public RecycleModels(Context context ,ArrayList<Experience> models, CustomClickListener listener) {
        this.models = models;
        this.listener = listener;
        this.mContext = context ;
    }

    @NonNull
    @Override
    public taskviewsholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTaskView = layoutInflater.inflate(R.layout.task_item_layout, parent, false);
        return new taskviewsholoder(listTaskView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull taskviewsholoder holder, int position) {
        holder.title.setText(models.get(position).getTitle());
        holder.description.setText(models.get(position).getDescription());
//        holder.ImageView.setImageURI(models.get(position).getImage());
        holder.ImageView.setImageURI(Uri.parse(mContext.getFilesDir()+ "/" + models.get(position).getImage() + "download.jpg"));



    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class taskviewsholoder extends RecyclerView.ViewHolder {
        TextView description;
        TextView title;
        ImageView ImageView;

        public taskviewsholoder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            ImageView=itemView.findViewById(R.id.image);
            itemView.setOnClickListener(view -> listener.taskItemClicked(getAdapterPosition()));

        }
    }

    public interface CustomClickListener {
        void taskItemClicked(int position);
    }
//    private void setImage(String image) {
//        if(image != null) {
//            Amplify.Storage.downloadFile(
//                    image,
//                    new File(getApplicationContext().getFilesDir() + "/" + image + "download.jpg"),
//                    result -> {
//                        Log.i(TAG, "The root path is: " + getApplicationContext().getFilesDir());
//                        Log.i(TAG, "Successfully downloaded: " + result.getFile().getName());
//                        runOnUiThread(() -> {
//                            Glide.with(getApplicationContext()).load(result.getFile().getPath()).into(imageView);
//                        });
//                    },
//                    error -> Log.e(TAG, "Download Failure", error)
//            );
//        }
//    }

}
