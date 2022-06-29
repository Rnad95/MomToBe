package com.example.momtobe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Question;

import java.util.ArrayList;
import java.util.List;

public class RecycleModels_Question extends RecyclerView.Adapter<RecycleModels_Question.taskviewsholoder>{
    List<Question> models;
    CustomClickListener listener;
    Context mContext ;
    public RecycleModels_Question(Context context ,ArrayList<Question> models, CustomClickListener listener) {
        this.models = models;
        this.listener = listener;
        this.mContext = context ;
    }

    @NonNull
    @Override
    public taskviewsholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTaskView = layoutInflater.inflate(R.layout.task_item_layout_question, parent, false);
        return new taskviewsholoder(listTaskView, listener);
    }
//public void getActivity(){
//
//
//}
    @Override
    public void onBindViewHolder(@NonNull taskviewsholoder holder, int position) {
        holder.title.setText(models.get(position).getTitle());
        holder.description.setText(models.get(position).getDescription());
//        holder.Question_image.setImageURI(models.get(position).getImage());
        holder.Question_image.setImageURI(Uri.parse(mContext.getFilesDir()+ "/" + models.get(position).getImage() + "download.jpg"));


    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class taskviewsholoder extends RecyclerView.ViewHolder {
        TextView description;
        TextView title;

ImageView Question_image;
        public taskviewsholoder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);

            Question_image=itemView.findViewById(R.id.imageView2);
            itemView.setOnClickListener(view -> listener.taskItemClicked(getAdapterPosition()));

        }
    }

    public interface CustomClickListener {
        void taskItemClicked(int position);
    }
}
