package com.example.momtobe.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.momtobe.MainActivity;
import com.example.momtobe.R;
import com.example.momtobe.remote.Blog;

import java.util.List;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.CustomHoleder> {

        List<Blog> blogList;
        CustomClickListener listener;
        Context context ;

        public mainAdapter(List<Blog> blogList) {
            this.blogList = blogList;
        }
//
//        public mainAdapter(List<Blog> blogList, mainAdapter.CustomClickListener listener) {
//            this.blogList = blogList;
//            this.listener = listener;
//        }
    public mainAdapter(Context context,List<Blog> blogList, mainAdapter.CustomClickListener listener) {
        this.blogList = blogList;
        this.listener = listener;
        this.context = context;
    }

        @NonNull
        @Override
        public mainAdapter.CustomHoleder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItemView = layoutInflater.inflate(R.layout.recycler_view_main_exp ,parent , false);
            return new mainAdapter.CustomHoleder(listItemView, listener);
        }
        @Override
        public void onBindViewHolder(@NonNull mainAdapter.CustomHoleder holder, int position) {
            holder.blogTitle.setText(blogList.get(position).getTitle());
            holder.blogAuthor.setText(blogList.get(position).getAuthor());
            holder.blogContent.setText(blogList.get(position).getContent());
            String url = blogList.get(position).getImageLink().toString();
            Glide.with(context).load(url).into(holder.blogImage);

             }
        @Override
        public int getItemCount() {
            return blogList.size();
        }
        static class CustomHoleder extends RecyclerView.ViewHolder {

            TextView blogTitle ;
            ImageView blogImage ;
            TextView blogAuthor;
            TextView blogContent;

            mainAdapter.CustomClickListener listener ;
            public CustomHoleder(@NonNull View itemView , mainAdapter.CustomClickListener listener) {
                super(itemView);
                this.listener = listener;
                blogTitle = itemView.findViewById(R.id.mother_title);
                blogImage = itemView.findViewById(R.id.mother_image);
                blogAuthor = itemView.findViewById(R.id.mother_Author);
                blogContent = itemView.findViewById(R.id.mother_content);
                itemView.setOnClickListener(v -> listener.onTaskItemClicked(getAdapterPosition()));
            }
        }
        public interface CustomClickListener{
            void onTaskItemClicked(int position);
        }

}
