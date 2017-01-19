package com.example.qianwu.chat_abou8;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import butterknife.Bind;
//import com.example.qianwu.chatting4real.classes.MovieObj;
import com.example.qianwu.chat_abou8.classes.userSharedPreferences;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    private Button registerButton;
    private Button userAlreadyHaveAccount;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextPassword_retype;
    private EditText userName;

    private String email;
    private String password;
    private String usernameStr;
    private ProgressDialog registingDialog;
    private FirebaseAuth mFirebaseAuth;
    private Firebase mFirebase;
    private FirebaseUser mFirebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_signup);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebase.setAndroidContext(this);
        mFirebase = new Firebase("https://chataboutit-52307.firebaseio.com/Users");



        registingDialog = new ProgressDialog(this);
        registerButton = (Button) findViewById(R.id.signUpButtoninSignup);
        mEditTextEmail = (EditText) findViewById(R.id.signUpEmail);

        mEditTextPassword = (EditText) findViewById(R.id.password);
        mEditTextPassword_retype = (EditText) findViewById(R.id.reTypePassword);
        userAlreadyHaveAccount = (Button) findViewById(R.id.ifUserIsRegisted);
        userName = (EditText) findViewById(R.id.userName);
        registerButton.setOnClickListener(this);
        userAlreadyHaveAccount.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == registerButton){

            requestUser();

        }

        if(view == userAlreadyHaveAccount){

            Intent loginActivityIntent = new Intent(this, LoginActivity.class);
            startActivity(loginActivityIntent);
        }
    }

    private void requestUser(){
        email = mEditTextEmail.getText().toString().trim();
        password = mEditTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please enter your email",Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please enter your pasword",Toast.LENGTH_SHORT).show();
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this,"Account created",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(SignupActivity.this,"Something went wring, please try again",Toast.LENGTH_SHORT).show();

                }
            }
        });
        usernameStr = userName.getText().toString();
        Log.d("usernameInSignup",usernameStr);
        //mFirebaseUser.updateProfile(n)


        registingDialog.setMessage("One moment");
        registingDialog.show();


        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //loginDialog.dismiss();

                if (task.isSuccessful()) {
                    mFirebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
                    final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(usernameStr)
                            .build();
                    mFirebaseUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Userprofile", "User profile updated.");
                                    }
                                }
                            });

                    userSharedPreferences.setUserName(SignupActivity.this, email);
                    userSharedPreferences.setPrefPassWord(SignupActivity.this, password);

                    Toast.makeText(SignupActivity.this,"Login Succeeded",Toast.LENGTH_SHORT).show();

                    finish();

                    Intent master = new Intent(SignupActivity.this, master.class);
                    startActivity(master);

                }

                //Toast.makeText(this,"Your password is wrong.",Toast.LENGTH_LONG).show();
            }
        });

        //registingDialog.dismiss();

    }
}

