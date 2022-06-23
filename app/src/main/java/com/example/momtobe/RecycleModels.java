package com.example.momtobe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Experience;
import com.amplifyframework.datastore.generated.model.Question;

import java.util.ArrayList;
import java.util.List;

public class RecycleModels extends RecyclerView.Adapter<RecycleModels.taskviewsholoder>{
    List<Experience> models;
    CustomClickListener listener;

    public RecycleModels(ArrayList<Experience> models, CustomClickListener listener) {
        this.models = models;
        this.listener = listener;
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
}
