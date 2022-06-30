package com.example.momtobe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Comment;
import com.example.momtobe.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCommentCustomAdapter extends RecyclerView.Adapter<ProductCommentCustomAdapter.taskviewsholoder> {
    List<Comment> productCommentList;
    CustomClickListener listener;

    public ProductCommentCustomAdapter(ArrayList<Comment> models, CustomClickListener listener) {
        this.productCommentList = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public taskviewsholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTaskView = layoutInflater.inflate(R.layout.comment_item_layout, parent, false);
        return new taskviewsholoder(listTaskView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull taskviewsholoder holder, int position) {
        holder.title.setText(productCommentList.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return productCommentList.size();
    }


    class taskviewsholoder extends RecyclerView.ViewHolder {
        private final TextView title;


        public taskviewsholoder(@NonNull View listTaskView, CustomClickListener listener) {
            super(listTaskView);
            title = itemView.findViewById(R.id.title);
        }
    }

    public interface CustomClickListener {
        void taskItemClicked(int position);
    }

}