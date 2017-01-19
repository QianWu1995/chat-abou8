package com.example.qianwu.chat_abou8;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.qianwu.chat_abou8.classes.userSharedPreferences;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private Button loginButon;
    private TextView signup;
    private ProgressDialog loginDialog;
    private StorageReference mStorageReference;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginButon = (Button) findViewById(R.id.signinButton);
        signup = (TextView) findViewById(R.id.signupButton);
        loginButon.setOnClickListener(this);
        signup.setOnClickListener(this);
        loginDialog = new ProgressDialog(this);

        /*if(login()){


        }*/
        if(userSharedPreferences.getUserName(LoginActivity.this).length() != 0)
        {
            // call autoLogin Activity
            userlogin(userSharedPreferences.getUserName(LoginActivity.this),userSharedPreferences.getPrefPassWord(LoginActivity.this));
        }
        else
        {
            // Stay at the current activity.
        }
    }

    @Override
    public void onClick(View view) {
        if(view == loginButon){
            userlogin(username.getText().toString().trim(),password.getText().toString().trim());
        }

        if(view == signup){
            Intent mainActivityIntent = new Intent(this, SignupActivity.class);
            startActivity(mainActivityIntent);
        }
    }

    private void userlogin(String usernamestr, String passwordstr){
        final String userFIN = usernamestr;
        final String passwordFIN = passwordstr;

        if(TextUtils.isEmpty(usernamestr)){
            Toast.makeText(this,"please enter your email :)",Toast.LENGTH_LONG).show();

        }
        if(TextUtils.isEmpty(passwordstr)){
            Toast.makeText(this,"please enter your password :)",Toast.LENGTH_LONG).show();

        }

        // if everything is good we try to login

        mFirebaseAuth.signInWithEmailAndPassword(usernamestr,passwordstr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loginDialog.dismiss();

                if (task.isSuccessful()) {

                    userSharedPreferences.setUserName(LoginActivity.this, userFIN);
                    userSharedPreferences.setPrefPassWord(LoginActivity.this, passwordFIN);

                    Toast.makeText(LoginActivity.this,"Login Succeeded",Toast.LENGTH_SHORT).show();

                    finish();

                    Intent master = new Intent(LoginActivity.this, master.class);
                    startActivity(master);

                }

                //Toast.makeText(this,"Your password is wrong.",Toast.LENGTH_LONG).show();
            }
        });
    }

}

