package com.example.qianwu.chat_abou8.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qianwu.chat_abou8.R;
import com.example.qianwu.chat_abou8.master;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.Vector;

/**
 * Created by qianwu on 2017-01-11.
 */
public class RecyclerViewAdapterSearchView extends RecyclerView.Adapter<RecyclerViewAdapterSearchView.MovieViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private Vector<MovieObj> movies;

    public RecyclerViewAdapterSearchView (Context context,Vector<MovieObj> movieObjs) {
        movies = movieObjs;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.book, parent, false);

        // Return a new holder instance
        MovieViewHolder viewHolder = new MovieViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieObj movieObj = movies.elementAt(position);
        TextView tempTextView = holder.nameTextView;
        tempTextView.setText(movieObj.title);
        final ImageView tempImageView = holder.MovieTitle;
              Picasso.with(mContext).load(movieObj.getMoivepic()).into(tempImageView);



        /*Picasso.with(mContext)
                .load(movieObj.getMoivepic())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(tempImageView);*/

        /*try{URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            tempImageView.setImageBitmap(bmp);
        }catch (Exception e){

        }*/


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView MovieTitle;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MovieViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.textView5);
            MovieTitle = (ImageView) itemView.findViewById(R.id.imageView4);
        }
    }

    public static class A1Q1 extends java.lang.Object{
        A1Q1(){//Constructor


        }

        static private int countPositive(int[] elements){

            return elements.length;
        }
        static void main(java.lang.String[] args){
            //do something here

        }



    }



}
