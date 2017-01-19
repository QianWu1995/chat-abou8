package com.example.qianwu.chat_abou8.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.qianwu.chat_abou8.R;
import com.example.qianwu.chat_abou8.classes.MovieObj;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class movieDetail extends Fragment implements View.OnClickListener{
    ScrollView movieDescription;
    ImageView MovieTopicImage;
    TextView movieDescriptionText;
    TextView titleText;
    MovieObj movieObj;
    Button chat;
    ImageButton like;

    private DatabaseReference root ;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieObj = ((Vector<MovieObj>)(getArguments().getSerializable("movieobjvec"))).elementAt(getArguments().getInt("pos"));
        //new MyLoadingTaskMovieDetail().execute(null,null,null);
    }

    public static movieDetail newInstance(int param1,Vector<MovieObj> movieObj22) {

        movieDetail fragment = new movieDetail();
        Bundle args = new Bundle();
        args.putInt("pos", param1);
        args.putSerializable("movieobjvec",movieObj22);
        fragment.setArguments(args);

        return fragment;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //URL=getArguments().getString("MOVIEURL");

        //Log.d("URLDEBUG",URL);
        View view =  inflater.inflate(R.layout.fragment_movie_detail, container, false);
        movieDescription = (ScrollView)view.findViewById(R.id.scrollView);
        movieDescriptionText = (TextView)view.findViewById(R.id.movieDescription);
        titleText = (TextView)view.findViewById(R.id.movieTopic);
        MovieTopicImage = (ImageView)view.findViewById(R.id.moviePic);
        chat = (Button)view.findViewById(R.id.gochat);
        //populate = (Button)view.findViewById(R.id.populate);
        like = (ImageButton)view.findViewById(R.id.likeButton);
        like.setOnClickListener(this);
        //populate.setOnClickListener(this);
        chat.setOnClickListener(this);

        root = FirebaseDatabase.getInstance().getReference().child("userFav").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        populate();



        return view;

    }




    public void populate(){

        titleText.setText(movieObj.title);
        Log.d("overview",movieObj.overview);
        Log.d("moviename",movieObj.title);

        movieDescriptionText.setText(movieObj.overview);
        Picasso.with(getActivity()).load(movieObj.getMoivepic()).into(MovieTopicImage);

    }

    @Override
    public void onClick(View v) {

        if(v == chat){

            roomFragment mroomFragment;
            mroomFragment = roomFragment.newInstance(titleText.getText().toString(),"");
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.mainmasterview,mroomFragment).commit();
        }
        if(v == like){

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            DatabaseReference message_root = root.child(movieObj.getUrl());

            Log.d("movienameLiked",movieObj.title);
            Map<String,Object> map2 = new HashMap<String, Object>();
            //map2.put("moviename",movieObj.title);
            map2.put("movieURL",movieObj.getUrl());
            Log.d("movienameLiked",movieObj.title);
            Log.d("movienameLikedURL",movieObj.getUrl());
            message_root.updateChildren(map2);

        }
    }


}
