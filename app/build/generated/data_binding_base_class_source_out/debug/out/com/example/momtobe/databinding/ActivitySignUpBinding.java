// Generated by view binder compiler. Do not edit!
package com.example.momtobe.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.momtobe.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignUpBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnRegister;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final TextInputLayout registerChildrenNo;

  @NonNull
  public final CountryCodePicker registerCountryCode;

  @NonNull
  public final TextInputLayout registerEmail;

  @NonNull
  public final TextInputLayout registerFullName;

  @NonNull
  public final TextInputLayout registerPassword;

  @NonNull
  public final TextInputLayout registerPhoneConstraint;

  @NonNull
  public final TextInputEditText registerPhoneNumber;

  @NonNull
  public final Button registerUploadImage;

  @NonNull
  public final TextView signUpPrompt;

  private ActivitySignUpBinding(@NonNull LinearLayout rootView, @NonNull Button btnRegister,
      @NonNull ImageView imageView, @NonNull ProgressBar loading,
      @NonNull TextInputLayout registerChildrenNo, @NonNull CountryCodePicker registerCountryCode,
      @NonNull TextInputLayout registerEmail, @NonNull TextInputLayout registerFullName,
      @NonNull TextInputLayout registerPassword, @NonNull TextInputLayout registerPhoneConstraint,
      @NonNull TextInputEditText registerPhoneNumber, @NonNull Button registerUploadImage,
      @NonNull TextView signUpPrompt) {
    this.rootView = rootView;
    this.btnRegister = btnRegister;
    this.imageView = imageView;
    this.loading = loading;
    this.registerChildrenNo = registerChildrenNo;
    this.registerCountryCode = registerCountryCode;
    this.registerEmail = registerEmail;
    this.registerFullName = registerFullName;
    this.registerPassword = registerPassword;
    this.registerPhoneConstraint = registerPhoneConstraint;
    this.registerPhoneNumber = registerPhoneNumber;
    this.registerUploadImage = registerUploadImage;
    this.signUpPrompt = signUpPrompt;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_register;
      Button btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.image_view;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);
      if (loading == null) {
        break missingId;
      }

      id = R.id.register_children_no;
      TextInputLayout registerChildrenNo = ViewBindings.findChildViewById(rootView, id);
      if (registerChildrenNo == null) {
        break missingId;
      }

      id = R.id.register_country_code;
      CountryCodePicker registerCountryCode = ViewBindings.findChildViewById(rootView, id);
      if (registerCountryCode == null) {
        break missingId;
      }

      id = R.id.register_email;
      TextInputLayout registerEmail = ViewBindings.findChildViewById(rootView, id);
      if (registerEmail == null) {
        break missingId;
      }

      id = R.id.register_full_name;
      TextInputLayout registerFullName = ViewBindings.findChildViewById(rootView, id);
      if (registerFullName == null) {
        break missingId;
      }

      id = R.id.register_password;
      TextInputLayout registerPassword = ViewBindings.findChildViewById(rootView, id);
      if (registerPassword == null) {
        break missingId;
      }

      id = R.id.register_phone_constraint;
      TextInputLayout registerPhoneConstraint = ViewBindings.findChildViewById(rootView, id);
      if (registerPhoneConstraint == null) {
        break missingId;
      }

      id = R.id.register_phone_number;
      TextInputEditText registerPhoneNumber = ViewBindings.findChildViewById(rootView, id);
      if (registerPhoneNumber == null) {
        break missingId;
      }

      id = R.id.register_upload_image;
      Button registerUploadImage = ViewBindings.findChildViewById(rootView, id);
      if (registerUploadImage == null) {
        break missingId;
      }

      id = R.id.sign_up_prompt;
      TextView signUpPrompt = ViewBindings.findChildViewById(rootView, id);
      if (signUpPrompt == null) {
        break missingId;
      }

      return new ActivitySignUpBinding((LinearLayout) rootView, btnRegister, imageView, loading,
          registerChildrenNo, registerCountryCode, registerEmail, registerFullName,
          registerPassword, registerPhoneConstraint, registerPhoneNumber, registerUploadImage,
          signUpPrompt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}