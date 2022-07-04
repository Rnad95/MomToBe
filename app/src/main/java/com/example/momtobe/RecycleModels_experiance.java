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

import java.util.ArrayList;
import java.util.List;

public class RecycleModels_experiance extends RecyclerView.Adapter<RecycleModels_experiance.taskviewsholoder>{
    List<Experience> models;
    CustomClickListener listener;
    Context mContext ;
    String userId;
    public RecycleModels_experiance(Context context ,String userId, ArrayList<Experience> models, CustomClickListener listener) {
        this.models = models;
        this.listener = listener;
        this.mContext = context ;
        this.userId=userId;
    }

    @NonNull
    @Override
    public taskviewsholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTaskView = layoutInflater.inflate(R.layout.experiance_item_layout, parent, false);
        return new taskviewsholoder(listTaskView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull taskviewsholoder holder, int position) {
        holder.title.setText(models.get(position).getTitle());
        holder.description.setText(models.get(position).getDescription());
//        holder.ImageView.setImageURI(models.get(position).getImage());
        holder.ImageView.setImageURI(Uri.parse(mContext.getFilesDir()+ "/" + models.get(position).getImage() + "download.jpg"));
String idItems=models.get(position).getId();
deleteItem(holder,position);
updateItem(holder,position);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class taskviewsholoder extends RecyclerView.ViewHolder {
        private final Button delete;
        private final Button update;
        TextView description;
        TextView title;
        ImageView ImageView;

        public taskviewsholoder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title_comment);
            description=itemView.findViewById(R.id.description);
            ImageView=itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.button3);
            update = itemView.findViewById(R.id.button4);

            itemView.setOnClickListener(view -> listener.taskItemClicked(getAdapterPosition()));

        }
    }

    public interface CustomClickListener {
        void taskItemClicked(int position);
    }
    private void deleteItem(@NonNull taskviewsholoder holder,int position) {
        Log.i("userId" , userId);
        Log.i("userId" , models.get(position).getMotherExperiencesId());
        if (models.get(position).getMotherExperiencesId().equals(userId)) {



            Log.i("userIdPostion" , String.valueOf(position));

            holder.delete.setVisibility(View.VISIBLE);

            holder.delete.setOnClickListener(v -> {

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

        if (models.get(position).getMotherExperiencesId().equals(userId)) {
            holder.update.setVisibility(View.VISIBLE);
            holder.update.setOnClickListener(v -> {
                Intent editProductActivity = new Intent(mContext , updateExperiance_Activity.class);
                editProductActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                editProductActivity.removeExtra("id");
                Log.i("TAG", "updateItem: "+ models.get(position).getId());
                editProductActivity.putExtra("id" ,  models.get(position).getId());
                mContext.startActivity(editProductActivity);
            });
        }
    }


}
