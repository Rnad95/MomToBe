package com.example.momtobe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momtobe.R;
import com.example.momtobe.remote.Blog;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class BlogCustomAdapter extends RecyclerView.Adapter<BlogCustomAdapter.CustomHoleder> {

    List<Blog> blogList;
    CustomClickListener listener;

    public BlogCustomAdapter(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public BlogCustomAdapter(List<Blog> blogList, CustomClickListener listener) {
        this.blogList = blogList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CustomHoleder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.reciclerview_content ,parent , false);
        return new CustomHoleder(listItemView, listener);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomHoleder holder, int position) {
        holder.blogTitle.setText(blogList.get(position).getTitle());
        holder.blogDescription.setText(blogList.get(position).getContent());
        holder.blogAuthorName.setText(blogList.get(position).getAuthor());
    }
    @Override
    public int getItemCount() {
        return blogList.size();
    }
    static class CustomHoleder extends RecyclerView.ViewHolder {

        TextView blogTitle ;
        TextView blogDescription;
        //            TextView blogImage ;
        TextView blogAuthorName;

        CustomClickListener listener ;
        public CustomHoleder(@NonNull View itemView , CustomClickListener listener) {
            super(itemView);
            this.listener = listener;
            blogTitle = itemView.findViewById(R.id.blog_archive_title);
            blogDescription = itemView.findViewById(R.id.blog_archive_desc);
            blogAuthorName = itemView.findViewById(R.id.blog_archive_autherName);
            itemView.setOnClickListener(v -> listener.onTaskItemClicked(getAdapterPosition()));
        }
    }
    public interface CustomClickListener{
        void onTaskItemClicked(int position);
    }
}
