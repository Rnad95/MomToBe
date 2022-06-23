package com.example.momtobe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momtobe.data.BlogDetails;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder> {

    List<BlogDetails> blogsList;

    public Adapter(List<BlogDetails> blogsList) {
        this.blogsList = blogsList;
    }

    public Adapter() {
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listBlogView = layoutInflater.inflate(R.layout.menu_blogs,parent,false);
        CustomViewHolder customViewHolder = new CustomViewHolder(listBlogView);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setText(blogsList.get(position).getTitle());
        holder.desc.setText(blogsList.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        return blogsList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.blog_title);
            desc = itemView.findViewById(R.id.blog_desc);

        }

    }


}
