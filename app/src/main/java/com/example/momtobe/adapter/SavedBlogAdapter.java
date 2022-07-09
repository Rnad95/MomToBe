package com.example.momtobe.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Mother;
import com.amplifyframework.datastore.generated.model.Product;
import com.bumptech.glide.Glide;
import com.example.momtobe.R;
import com.example.momtobe.remote.Blog;
import com.example.momtobe.ui.EditProductActivity;

import java.util.ArrayList;
import java.util.List;

public class SavedBlogAdapter extends RecyclerView.Adapter<SavedBlogAdapter.CustomHoleder>{
    List<Blog> blogList ;
    SavedBlogAdapter.CustomClickListener listener;
    ArrayList <String> blogIds = new ArrayList<>();
    Context context;
    Mother mother;

    public SavedBlogAdapter(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public SavedBlogAdapter(Context context,Mother mother ,List<Blog> blogList, SavedBlogAdapter.CustomClickListener listener) {
        this.blogList = blogList;
        this.listener = listener;
        this.context = context;
        this.mother = mother;
    }

    @NonNull
    @Override
    public SavedBlogAdapter.CustomHoleder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.activity_blog_items ,parent , false);
        return new SavedBlogAdapter.CustomHoleder(listItemView, listener);
    }
    @Override
    public void onBindViewHolder(@NonNull SavedBlogAdapter.CustomHoleder holder, int position) {
        Log.d("TAG", "onBindViewHolder: " + blogList);
        holder.blogTitle.setText(blogList.get(position).getTitle());
        holder.blogDescription.setText(blogList.get(position).getContent().substring(0,300) + "...");
        holder.blogAuthorName.setText(blogList.get(position).getAuthor());
        holder.blogDate.setText(blogList.get(position).getDate());
        String url = blogList.get(position).getImageLink().toString();
        Glide.with(context).load(url).into(holder.blogImage);
        deleteFav(holder , position);

    }
    @Override
    public int getItemCount() {
        return blogList.size();
    }
    static class CustomHoleder extends RecyclerView.ViewHolder {

        TextView  blogTitle ;
        TextView  blogDescription;
        ImageView blogImage ;
        TextView  blogAuthorName;
        TextView blogDate;
        ImageButton savedButton;


        SavedBlogAdapter.CustomClickListener listener ;
        public CustomHoleder(@NonNull View itemView , SavedBlogAdapter.CustomClickListener listener) {
            super(itemView);
            this.listener = listener;
            blogTitle = itemView.findViewById(R.id.saved_blog_archive_title);
            blogDescription = itemView.findViewById(R.id.saved_blog_archive_desc);
            blogAuthorName = itemView.findViewById(R.id.saved_blog_archive_autherName);
            blogImage = itemView.findViewById(R.id.saved_blog_favorite_img);
            blogDate = itemView.findViewById(R.id.saved_blog_archive_date);
            savedButton = itemView.findViewById(R.id.saved_favorite_blog);


            itemView.setOnClickListener(v -> listener.onTaskItemClicked(getAdapterPosition()));
        }
    }


    public interface CustomClickListener{
        void onTaskItemClicked(int position);
    }
    public void deleteFav(@NonNull SavedBlogAdapter.CustomHoleder holder, int position){
        holder.savedButton.setOnClickListener(view->{
            blogIds.clear();
            blogIds.addAll(mother.getFaveBlogs());
            blogIds.remove(blogList.get(position).getId());

            Mother newMother = Mother.builder()
                    .name(mother.getName())
                    .numOfChildren(mother.getNumOfChildren())
                    .emailAddress(mother.getEmailAddress())
                    .phoneNumber(mother.getPhoneNumber())
                    .image(mother.getImage())
                    .faveBlogs(blogIds)
                    .id(mother.getId())
                    .build();

            Amplify.API.mutate(ModelMutation.update(newMother),
                    response -> {
                        Log.i("MyAmplifyApp", "Todo with id: " + response);
                    },
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
            blogList.remove(position);
            notifyItemRemoved(position);

        });


    }
}
