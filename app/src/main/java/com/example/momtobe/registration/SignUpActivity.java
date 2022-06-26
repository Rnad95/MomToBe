package com.example.momtobe.registration;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.momtobe.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private TextView mLoginPrompt;
    private TextInputLayout mFullName,mNumOfChildren,mEmailAddress,mPassword;
    TextInputEditText mPhoneNumber;
    private Button mImage;
    private Button mSignUp;
    private ProgressBar loadingProgressBar;
    public static final String EMAIL = "email";
    public static String USERNAME = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mLoginPrompt = findViewById(R.id.sign_up_prompt);
        mFullName = findViewById(R.id.register_full_name);
        mNumOfChildren = findViewById(R.id.register_children_no);
        mImage = findViewById(R.id.register_upload_image);
//        Check the number
        mPhoneNumber = findViewById(R.id.register_phone_number);
        mEmailAddress = findViewById(R.id.register_email);
        mPassword = findViewById(R.id.register_password);
        mSignUp = findViewById(R.id.btn_register);
        loadingProgressBar = findViewById(R.id.loading);


        String fullName = mFullName.getEditText().getText().toString();
        String numOfChildren = mNumOfChildren.getEditText().getText().toString();
        String image = mImage.toString();
//        Check the number
        String phoneNumber = mPhoneNumber.getText().toString();
        String emailAddress =  mEmailAddress.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();

        mPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
                    mSignUp.setEnabled(true);
                }
                return false;
            }
        });


        mLoginPrompt.setOnClickListener(view -> {
            Intent navigateToSignUpIntent = new Intent(this, LoginActivity.class);
            navigateToSignUpIntent.putExtra("data",fullName);
            startActivity(navigateToSignUpIntent);


        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NameValidate() |!PasswordValidation() | !NumberOfChildrenValidate() | !validateEmail())
                {
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                signUp(mFullName.getEditText().getText().toString(),
                        mEmailAddress.getEditText().getText().toString(),
                        mPassword.getEditText().getText().toString());
            }
        });

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
                    Log.i(TAG, "Result: " + result.toString());
                    loadingProgressBar.setVisibility(View.INVISIBLE);

                    Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
                    intent.putExtra(EMAIL, email);
                    startActivity(intent);

                    finish();
                },
                error -> {
                    Log.e(TAG, "Sign up failed", error);
                    // show a dialog of the error below
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Sign Up Failed, Please try again", Toast.LENGTH_SHORT).show();

                    // error.getMessage()
                }
        );

    }

}