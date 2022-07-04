package com.example.momtobe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Experience;
import com.amplifyframework.datastore.generated.model.Question;

import java.util.ArrayList;
import java.util.List;

public class RecycleModels_Question extends RecyclerView.Adapter<RecycleModels_Question.taskviewsholoder>{
    List<Question> models;
    CustomClickListener listener;
    Context mContext ;
    String userId;
    public RecycleModels_Question(Context context , String userId, ArrayList<Question> models, RecycleModels_Question.CustomClickListener listener) {
        this.models = models;
        this.listener = listener;
        this.mContext = context ;
        this.userId=userId;
    }

    @NonNull
    @Override
    public taskviewsholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTaskView = layoutInflater.inflate(R.layout.question_item_layout, parent, false);
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
        deleteItem(holder,position);
        updateItem(holder,position);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    private void deleteItem(@NonNull taskviewsholoder holder, int position) {
        Log.i("userId" , userId);
        Log.i("userId" , models.get(position).getMotherQuestionsId());
        if (models.get(position).getMotherQuestionsId().equals(userId)) {



            Log.i("userIdPostion" , String.valueOf(position));

            holder.delete_btn.setVisibility(View.VISIBLE);

            holder.delete_btn.setOnClickListener(v -> {

                Amplify.API.mutate(ModelMutation.delete(models.get(position)) ,
                        success -> {
                            Log.i("Comment", "Delete item API");

                        } ,
                        error -> Log.e("TAG", "Could not save item to DataStore", error)
                );

                models.remove(position);
                notifyItemRemoved(position);
            });

        }

    }
    private void updateItem(@NonNull taskviewsholoder holder, int position) {

        if (models.get(position).getMotherQuestionsId().equals(userId)) {
            holder.update_btn.setVisibility(View.VISIBLE);
            holder.update_btn.setOnClickListener(v -> {
                Intent editProductActivity = new Intent(mContext , updateQuestion_Activity.class);
                editProductActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                editProductActivity.removeExtra("id");
                Log.i("TAG", "updateItem: "+ models.get(position).getId());
                editProductActivity.putExtra("idQuestion" ,  models.get(position).getId());
                mContext.startActivity(editProductActivity);
            });
        }
    }
    class taskviewsholoder extends RecyclerView.ViewHolder {
        TextView description;
        TextView title;
        TextView delete_btn;
        TextView update_btn;


        ImageView Question_image;
        public taskviewsholoder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title_Question);
            description=itemView.findViewById(R.id.description);

            Question_image=itemView.findViewById(R.id.imageView2);
            delete_btn=itemView.findViewById(R.id.delete_btn);
            update_btn=itemView.findViewById(R.id.update_btn);


            itemView.setOnClickListener(view -> listener.taskItemClicked(getAdapterPosition()));

        }
    }

    public interface CustomClickListener {
        void taskItemClicked(int position);
    }
}