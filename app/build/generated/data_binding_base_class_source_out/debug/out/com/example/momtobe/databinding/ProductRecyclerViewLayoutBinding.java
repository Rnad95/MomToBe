// Generated by view binder compiler. Do not edit!
package com.example.momtobe.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.momtobe.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ProductRecyclerViewLayoutBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView productArchiveDesc;

  @NonNull
  public final ImageView productArchiveImg;

  @NonNull
  public final TextView productArchivePrice;

  @NonNull
  public final TextView productArchiveQuantity;

  @NonNull
  public final TextView productArchiveStatus;

  @NonNull
  public final TextView productArchiveTitle;

  private ProductRecyclerViewLayoutBinding(@NonNull CardView rootView,
      @NonNull TextView productArchiveDesc, @NonNull ImageView productArchiveImg,
      @NonNull TextView productArchivePrice, @NonNull TextView productArchiveQuantity,
      @NonNull TextView productArchiveStatus, @NonNull TextView productArchiveTitle) {
    this.rootView = rootView;
    this.productArchiveDesc = productArchiveDesc;
    this.productArchiveImg = productArchiveImg;
    this.productArchivePrice = productArchivePrice;
    this.productArchiveQuantity = productArchiveQuantity;
    this.productArchiveStatus = productArchiveStatus;
    this.productArchiveTitle = productArchiveTitle;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ProductRecyclerViewLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProductRecyclerViewLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.product_recycler_view_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProductRecyclerViewLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.product_archive_desc;
      TextView productArchiveDesc = ViewBindings.findChildViewById(rootView, id);
      if (productArchiveDesc == null) {
        break missingId;
      }

      id = R.id.product_archive_img;
      ImageView productArchiveImg = ViewBindings.findChildViewById(rootView, id);
      if (productArchiveImg == null) {
        break missingId;
      }

      id = R.id.product_archive_price;
      TextView productArchivePrice = ViewBindings.findChildViewById(rootView, id);
      if (productArchivePrice == null) {
        break missingId;
      }

      id = R.id.product_archive_quantity;
      TextView productArchiveQuantity = ViewBindings.findChildViewById(rootView, id);
      if (productArchiveQuantity == null) {
        break missingId;
      }

      id = R.id.product_archive_status;
      TextView productArchiveStatus = ViewBindings.findChildViewById(rootView, id);
      if (productArchiveStatus == null) {
        break missingId;
      }

      id = R.id.product_archive_title;
      TextView productArchiveTitle = ViewBindings.findChildViewById(rootView, id);
      if (productArchiveTitle == null) {
        break missingId;
      }

      return new ProductRecyclerViewLayoutBinding((CardView) rootView, productArchiveDesc,
          productArchiveImg, productArchivePrice, productArchiveQuantity, productArchiveStatus,
          productArchiveTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}