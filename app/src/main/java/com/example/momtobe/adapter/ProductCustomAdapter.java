package com.example.momtobe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.amplifyframework.datastore.generated.model.Product;
import com.bumptech.glide.Glide;
import com.example.momtobe.R;
import com.example.momtobe.ui.EditProductActivity;
import com.example.momtobe.ui.ProductDetailsActivity;

import java.io.File;
import java.util.List;

public class ProductCustomAdapter extends RecyclerView.Adapter<ProductCustomAdapter.CustomHoleder> {
    private static final String TAG = "ProductCustomAdapter";
    List<Product> productDataList ;
    CustomClickListener listener;
    Context mContext ;
    String userId;


    public ProductCustomAdapter(Context context , List<Product> productDataList , String userId, CustomClickListener listener) {
        this.productDataList = productDataList;
        this.listener = listener;
        this.mContext = context ;
        this.userId = userId ;
    }

    @NonNull
    @Override
    public CustomHoleder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.product_recycler_view_layout ,parent , false);
        return new CustomHoleder(listItemView, listener);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomHoleder holder, int position) {

        // Set Image
        //        holder.productImage.setText(productDataList.get(position).getImage());
        holder.productTitle.setText(productDataList.get(position).getTitle());
        holder.productDescription.setText(productDataList.get(position).getDescription());
        holder.productPrice.setText(productDataList.get(position).getPrice().toString());
        holder.productQuantity.setText(productDataList.get(position).getQuantity().toString());
        holder.productImage.setImageURI(Uri.parse(mContext.getFilesDir()+ "/" + productDataList.get(position).getImage() + "download.jpg"));

        setDeleteBtn(holder , position);
        setUpdateBtn(holder , position);


    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    static class CustomHoleder extends RecyclerView.ViewHolder {

        TextView productTitle ;
        TextView productPrice;
        TextView productDescription;
        TextView productQuantity;
        TextView productDelete;
        TextView productUpdate;


        ImageView productImage ;

        CustomClickListener listener ;
        public CustomHoleder(@NonNull View itemView , CustomClickListener listener) {
            super(itemView);
            this.listener = listener;
            productTitle = itemView.findViewById(R.id.product_archive_title);
            productPrice = itemView.findViewById(R.id.product_archive_price);
            productDescription = itemView.findViewById(R.id.product_archive_desc);
            productQuantity = itemView.findViewById(R.id.product_archive_quantity);
            productImage = itemView.findViewById(R.id.product_archive_img);
            productUpdate = itemView.findViewById(R.id.product_archive_update_btn);
            productDelete = itemView.findViewById(R.id.product_archive_delete_btn);

            itemView.setOnClickListener(v -> listener.onTaskItemClicked(getAdapterPosition()));
        }

    }

    public interface CustomClickListener{
        void onTaskItemClicked(int position);
    }


    public void setDeleteBtn(@NonNull CustomHoleder holder, int position){

        if (productDataList.get(position).getMotherProductsId().equals(userId)) {

            Log.i("userId" , userId);
            Log.i("userId" , productDataList.get(position).getMotherProductsId());
            Log.i("userIdPostion" , String.valueOf(position));

            holder.productDelete.setVisibility(View.VISIBLE);

            holder.productDelete.setOnClickListener(v -> {

                Amplify.API.mutate(ModelMutation.delete(productDataList.get(position)) ,
                        success -> {
                            Log.i("Comment", "Delete item API");

                        } ,
                        error -> Log.e("TAG", "Could not save item to DataStore", error)
                );

                productDataList.remove(position);
                notifyItemRemoved(position);
            });

        }

    }

    public void setUpdateBtn(@NonNull CustomHoleder holder, int position){

        if (productDataList.get(position).getMotherProductsId().equals(userId)) {
            holder.productUpdate.setVisibility(View.VISIBLE);
            holder.productUpdate.setOnClickListener(v -> {
                Intent editProductActivity = new Intent(mContext , EditProductActivity.class);
                editProductActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                editProductActivity.removeExtra("id");
                editProductActivity.putExtra("id" ,  productDataList.get(position).getId());
                mContext.startActivity(editProductActivity);
            });
        }
    }


}
