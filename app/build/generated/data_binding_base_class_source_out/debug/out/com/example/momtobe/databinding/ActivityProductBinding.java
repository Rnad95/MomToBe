// Generated by view binder compiler. Do not edit!
package com.example.momtobe.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.momtobe.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProductBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavigator;

  @NonNull
  public final Button btnProductSearch;

  @NonNull
  public final LinearLayout layoutSearch;

  @NonNull
  public final FloatingActionButton productAddImg;

  @NonNull
  public final RecyclerView productArchiveRecycler;

  @NonNull
  public final TextInputEditText productArchiveSearch;

  private ActivityProductBinding(@NonNull RelativeLayout rootView,
      @NonNull BottomNavigationView bottomNavigator, @NonNull Button btnProductSearch,
      @NonNull LinearLayout layoutSearch, @NonNull FloatingActionButton productAddImg,
      @NonNull RecyclerView productArchiveRecycler,
      @NonNull TextInputEditText productArchiveSearch) {
    this.rootView = rootView;
    this.bottomNavigator = bottomNavigator;
    this.btnProductSearch = btnProductSearch;
    this.layoutSearch = layoutSearch;
    this.productAddImg = productAddImg;
    this.productArchiveRecycler = productArchiveRecycler;
    this.productArchiveSearch = productArchiveSearch;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProductBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProductBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_product, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProductBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_navigator;
      BottomNavigationView bottomNavigator = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigator == null) {
        break missingId;
      }

      id = R.id.btn_product_search;
      Button btnProductSearch = ViewBindings.findChildViewById(rootView, id);
      if (btnProductSearch == null) {
        break missingId;
      }

      id = R.id.layout_search;
      LinearLayout layoutSearch = ViewBindings.findChildViewById(rootView, id);
      if (layoutSearch == null) {
        break missingId;
      }

      id = R.id.product_add_img;
      FloatingActionButton productAddImg = ViewBindings.findChildViewById(rootView, id);
      if (productAddImg == null) {
        break missingId;
      }

      id = R.id.product_archive_recycler;
      RecyclerView productArchiveRecycler = ViewBindings.findChildViewById(rootView, id);
      if (productArchiveRecycler == null) {
        break missingId;
      }

      id = R.id.product_archive_search;
      TextInputEditText productArchiveSearch = ViewBindings.findChildViewById(rootView, id);
      if (productArchiveSearch == null) {
        break missingId;
      }

      return new ActivityProductBinding((RelativeLayout) rootView, bottomNavigator,
          btnProductSearch, layoutSearch, productAddImg, productArchiveRecycler,
          productArchiveSearch);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}