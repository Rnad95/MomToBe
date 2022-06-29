package com.example.momtobe.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momtobe.R;
import com.example.momtobe.remote.Blog;

import java.util.List;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.CustomHoleder> {

        List<Blog> blogList;
        CustomClickListener listener;

        public mainAdapter(List<Blog> blogList) {
            this.blogList = blogList;
        }

        public mainAdapter(List<Blog> blogList, mainAdapter.CustomClickListener listener) {
            this.blogList = blogList;
            this.listener = listener;
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
//            Uri uri = Uri.parse(blogList.get(position).getImageLink());
            holder.blogImage.setImageURI(Uri.parse(blogList.get(position).getImageLink()));
        }
        @Override
        public int getItemCount() {
            return blogList.size();
        }
        static class CustomHoleder extends RecyclerView.ViewHolder {

            TextView blogTitle ;
            ImageView blogImage ;

            mainAdapter.CustomClickListener listener ;
            public CustomHoleder(@NonNull View itemView , mainAdapter.CustomClickListener listener) {
                super(itemView);
                this.listener = listener;
                blogTitle = itemView.findViewById(R.id.mother_title);
                blogImage = itemView.findViewById(R.id.mother_image);
                itemView.setOnClickListener(v -> listener.onTaskItemClicked(getAdapterPosition()));
            }
        }
        public interface CustomClickListener{
            void onTaskItemClicked(int position);
        }

}
