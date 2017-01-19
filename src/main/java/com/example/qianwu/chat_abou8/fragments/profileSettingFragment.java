package com.example.qianwu.chat_abou8.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.qianwu.chat_abou8.R;
import com.example.qianwu.chat_abou8.SignupActivity;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class profileSettingFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    EditText oldpassword;
    EditText newpasswordretype;
    EditText newpasword;
    EditText newname;
    Button update;


    private FirebaseAuth mFirebaseAuth;
    private Firebase mFirebase;
    private FirebaseUser mFirebaseUser;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public profileSettingFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profileSettingFragment newInstance(String param1, String param2) {
        profileSettingFragment fragment = new profileSettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onClick(View view) {
        if(view == update){

            mFirebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
            final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newname.getText().toString()).setPhotoUri(Uri.parse("http://image.tmdb.org/t/p/original//rZd0y1X1Gw4t5B3f01Qzj8DYY66.jpg"))
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
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_profile_setting, container, false);
        // Inflate the layout for this fragment
        oldpassword = (EditText)view.findViewById(R.id.OldPassword);
        newpasword = (EditText)view.findViewById(R.id.newpassword);
        newpasswordretype = (EditText)view.findViewById(R.id.newpasswordRetype);
        newname = (EditText)view.findViewById(R.id.newuserName);
        update = (Button)view.findViewById(R.id.UpdateAccount);

        update.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


}
