package com.example.momtobe.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Comment;
import com.amplifyframework.datastore.generated.model.Mother;
import com.amplifyframework.datastore.generated.model.Product;
import com.example.momtobe.R;
import com.example.momtobe.ui.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductCommentCustomAdapter extends RecyclerView.Adapter<ProductCommentCustomAdapter.taskviewsholoder> {
    String userId , commentUserName;
    List<Comment> productCommentList;
    CustomClickListener listener;

    public ProductCommentCustomAdapter(ArrayList<Comment> models, CustomClickListener listener , String userId  ) {
        this.productCommentList = models;
        this.listener = listener;
        this.userId = userId ;
    }

    @NonNull
    @Override
    public taskviewsholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTaskView = layoutInflater.inflate(R.layout.comment_item_layout, parent, false);
        return new taskviewsholoder(listTaskView, listener );
    }

    @Override
    public void onBindViewHolder(@NonNull taskviewsholoder holder, int position) {

        holder.title.setText(productCommentList.get(position).getContent());

        setCommentUserName(holder , position);

        setDeleteBtn(holder , position);

    }



    @Override
    public int getItemCount() {
        return productCommentList.size();
    }


    class taskviewsholoder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView userName;

        private final Button deleteButton;


        public taskviewsholoder(@NonNull View listTaskView, CustomClickListener listener ) {
            super(listTaskView);
            title = itemView.findViewById(R.id.comment_title);
            userName = itemView.findViewById(R.id.comment_username);
            deleteButton = itemView.findViewById(R.id.comment_delete_btn) ;

            itemView.setOnClickListener(v -> listener.onTaskItemClicked(getAdapterPosition()));
        }
    }

    public interface CustomClickListener {
        void onTaskItemClicked(int position);
    }

    public void setCommentUserName(@NonNull taskviewsholoder holder, int position){
        Handler handler = new Handler(Looper.getMainLooper(), msg -> {
            holder.userName.setText(commentUserName);
            return true ;
        });

        Amplify.API.query(
                ModelQuery.get(Mother.class, productCommentList.get(position).getMotherCommentsId()),
                response -> {
                    commentUserName = response.getData().getName() ;
                    Bundle bundle = new Bundle();
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );

    }


    public void setDeleteBtn(@NonNull taskviewsholoder holder, int position){
        Log.i("UserAuthId" , userId) ;
        Log.i("UserCommentId" , productCommentList.get(position).getMotherCommentsId().toString()) ;
        if (productCommentList.get(position).getMotherCommentsId().equals(userId)) {

            holder.deleteButton.setVisibility(View.VISIBLE);

            holder.deleteButton.setOnClickListener(v -> {

                Amplify.API.mutate(ModelMutation.delete(productCommentList.get(position)) ,
                        success -> {
                            Log.i("Comment", "Delete item API");

                        } ,
                        error -> Log.e("TAG", "Could not save item to DataStore", error)
                );
                Log.i("CommentDel" , productCommentList.get(position).toString());
                Log.i("CommentDelPos" , String.valueOf(position));
                Log.i("CommentBef" , productCommentList.toString());
                productCommentList.remove(position);
                notifyItemRemoved(position);
            });

        }
    }




}