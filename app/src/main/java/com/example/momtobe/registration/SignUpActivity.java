package com.example.momtobe.registration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Address;
import com.amplifyframework.datastore.generated.model.Mother;
import com.example.momtobe.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private TextView mLoginPrompt;
    private TextInputLayout mFullName,mNumOfChildren,mEmailAddress,mPassword;
    private EditText mPhoneNumber;
    private Button mImage, mSignUp;
    public static final int REQUEST_CODE = 123;
    private ProgressBar loadingProgressBar;
    public static final String EMAIL = "email";
    private ImageView mImageView;
    private Intent intent;
    private File file;
    private String imageKey, fullName,password,emailAddress, image, phoneNumber, key;
    private int numOfChildren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mLoginPrompt = findViewById(R.id.sign_in_prompt);
        mFullName = findViewById(R.id.register_full_name);
        mNumOfChildren = findViewById(R.id.register_children_no);
        mImage = findViewById(R.id.register_upload_image);
        mImageView = findViewById(R.id.image_view);
//        Check the number
        mPhoneNumber = findViewById(R.id.register_phone_number);
        mEmailAddress = findViewById(R.id.register_email);
        mPassword = findViewById(R.id.register_password);
        mSignUp = findViewById(R.id.btn_register);
        loadingProgressBar = findViewById(R.id.loading);

        fullName = mFullName.getEditText().getText().toString();
        try {
            numOfChildren = Integer.parseInt(mNumOfChildren.getEditText().getText().toString());
            image = mImage.toString();
        }catch (Exception err){

        }

//        Check the number
        phoneNumber = mPhoneNumber.getText().toString();
        emailAddress = mEmailAddress.getEditText().getText().toString();
        password = mPassword.getEditText().getText().toString();

        mPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
                    mSignUp.setEnabled(true);
                }
                return false;
            }
        });
        Intent intent1 = getIntent();
        intent1.setType(intent1.ACTION_SEND);
        if (intent1 != null && intent1.getType() != null || intent1.getData() != null) {
            Uri imageUri = (Uri) intent1.getParcelableExtra(Intent.EXTRA_STREAM);
            mImageView.setImageURI(imageUri);
        }
        mImage.setOnClickListener(view -> {
            uploadPhoto();
        });


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NameValidate() |!PasswordValidation() | !NumberOfChildrenValidate() | !validateEmail())
                {
                    return;
                }
//                loadingProgressBar.setVisibility(View.VISIBLE);

                signUp(mFullName.getEditText().getText().toString(),
                        mEmailAddress.getEditText().getText().toString(),
                        mPassword.getEditText().getText().toString());

            }

        });


        mLoginPrompt.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        });
    }

    private void saveDataInAWS(){
        fullName = mFullName.getEditText().getText().toString();
        numOfChildren = Integer.parseInt(mNumOfChildren.getEditText().getText().toString());
        image = mImage.toString();
//        Check the number
        phoneNumber = mPhoneNumber.getText().toString();
        emailAddress = mEmailAddress.getEditText().getText().toString();

        imageKey = fullName + ".jpg";
        Amplify.Storage.uploadFile(
                imageKey,
                file,
                uploadSuccess -> {
                    Log.i(TAG, "upload image is succeed" + uploadSuccess.getKey());
                    runOnUiThread(() -> {
                        key = uploadSuccess.getKey();
                        Address address = Address.builder()
                                .country("")
                                .city("")
                                .street("")
                                .build();
                        Mother mother = Mother.builder()
                                .name(fullName)
                                .numOfChildren(1)
                                .emailAddress(emailAddress)
                                .phoneNumber(phoneNumber)
                                .image(imageKey)
                                .addressMothersId(address.getId())
                                .build();


                        Amplify.API.mutate(
                                ModelMutation.create(mother),
                                success ->{
                                    Log.i(TAG,"Saved item: "+ success);
                                },
                                error ->{
                                    Log.i(TAG, "Could not save to Database");
                                }
                        );

                    });
                },
                failure -> {
                    Log.e(TAG, "Uploaded failed.", failure);
                    runOnUiThread(() -> {
                        Address address = Address.builder()
                                .country("Jordan")
                                .city("Amman")
                                .street("University of Jordan street")
                                .build();
                        Mother mother = Mother.builder()
                                .name(fullName)
                                .numOfChildren(1)
                                .emailAddress(emailAddress)
                                .phoneNumber(phoneNumber)
                                .addressMothersId(address.getId())
                                .build();


                        Amplify.API.mutate(
                                ModelMutation.create(mother),
                                success ->{
                                    Log.i(TAG,"Saved item: "+ success);
                                },
                                error ->{
                                    Log.i(TAG, "Could not save to Database");
                                }
                        );

                    });
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Log.e(TAG, "onActivityResult: Error getting image from device");
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();
                mImageView.setImageURI(currentUri);
                // Do stuff with the photo/video URI.
                Log.i(TAG, "onActivityResult: the uri is => " + currentUri);

                try {
                    Bitmap bitmap = getBitmapFromUri(currentUri);

                    file = new File(getApplicationContext().getFilesDir(), "image.jpg");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                    os.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }
    private void uploadPhoto() {
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
//          startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

    }


    /**
     * Name Vaildation
     * @return
     */
    private Boolean NameValidate(){
        if(mFullName.getEditText().getText().toString().isEmpty()){
            mFullName.setError("Field Cannot be Empty");
            return false;
        }else if(mNumOfChildren.getEditText().getText().length() > 25){
            mFullName.setError("This Field Cannot be more than 25 character");
            return false;
        }
        else{
            mFullName.setError(null);
            return true;
        }
    }

    /**
     *  Number of children Validation
     * @return
     */
    private Boolean NumberOfChildrenValidate(){
        if(mNumOfChildren.getEditText().getText().toString().isEmpty()){
            mNumOfChildren.setError("Field Cannot be Empty");
            return false;
        }else if(Integer.valueOf(mNumOfChildren.getEditText().getText().toString()) > 10){
                mNumOfChildren.setError("Please add a number less than 10 ");
                return false;
            }
            else{
            mNumOfChildren.setError(null);
            return true;
        }
    }

    /**
     *  Password Validation
     * @return
     */
    private Boolean PasswordValidation(){
        String val = mPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
//                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            mPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            mPassword.setError("Password is too weak");
            return false;
        } else {
            mPassword.setError(null);
            mPassword.setErrorEnabled(false);
            return true;
        }

    }
    private Boolean validateEmail(){
        String val = mEmailAddress.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            mEmailAddress.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            mEmailAddress.setError("Invalid email address");
            return false;
        } else {
            mEmailAddress.setError(null);
            mEmailAddress.setErrorEnabled(false);
            return true;
        }
    }
    private void signUp(String username,String email, String password) {
//

        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .userAttribute(AuthUserAttributeKey.nickname(), username)
                .build();

        Amplify.Auth.signUp(email, password, options,
                result -> {
                    Log.i(TAG, "Result: " + result);
                    saveDataInAWS();
//                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
                    intent.putExtra(EMAIL, email);
                    startActivity(intent);
                    finish();
                },
                error -> {
                    Log.e(TAG, "Sign up failed", error);
                    // show a dialog of the error below

                    // error.getMessage()
                }
        );

    }

}