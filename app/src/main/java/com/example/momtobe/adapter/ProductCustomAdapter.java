package com.example.momtobe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Product;
import com.bumptech.glide.Glide;
import com.example.momtobe.R;

import java.io.File;
import java.util.List;

public class ProductCustomAdapter extends RecyclerView.Adapter<ProductCustomAdapter.CustomHoleder> {
    private static final String TAG = "ProductCustomAdapter";
    List<Product> productDataList ;
    CustomClickListener listener;
    Context mContext ;


    public ProductCustomAdapter(Context context , List<Product> productDataList, CustomClickListener listener) {
        this.productDataList = productDataList;
        this.listener = listener;
        this.mContext = context ;
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
//        holder.productQuantity.setText(productDataList.get(position).getQuantity().toString());
        holder.productImage.setImageURI(Uri.parse(mContext.getFilesDir()+ "/" + productDataList.get(position).getImage() + "download.jpg"));

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

            itemView.setOnClickListener(v -> listener.onTaskItemClicked(getAdapterPosition()));
        }

    }

    public interface CustomClickListener{
        void onTaskItemClicked(int position);
    }
}
