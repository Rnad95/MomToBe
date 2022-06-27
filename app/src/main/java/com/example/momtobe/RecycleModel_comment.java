package com.example.momtobe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Experience;

import java.util.ArrayList;
import java.util.List;

public class RecycleModel_comment extends RecyclerView.Adapter<RecycleModel_comment.taskviewsholoder> {
    List<Experience> models;
    RecycleModel_comment.CustomClickListener listener;

    public RecycleModel_comment(ArrayList<Experience> models, RecycleModel_comment.CustomClickListener listener) {
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecycleModel_comment.taskviewsholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTaskView = layoutInflater.inflate(R.layout.comment_item_layout, parent, false);
        return new taskviewsholoder(listTaskView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull taskviewsholoder holder, int position) {
        holder.title.setText(models.get(position).getTitle());

    }


    @Override
    public int getItemCount() {
        return models.size();
    }


    class taskviewsholoder extends RecyclerView.ViewHolder {
        private final TextView title;
        TextView description;




        public taskviewsholoder(@NonNull View listTaskView, RecycleModel_comment.CustomClickListener listener) {
            super(listTaskView);
            title = itemView.findViewById(R.id.title);
        }
    }

    public interface CustomClickListener {
        void taskItemClicked(int position);
    }

}
