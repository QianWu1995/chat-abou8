package com.example.qianwu.chat_abou8.classes;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.qianwu.chat_abou8.R;
import com.squareup.picasso.Picasso;
import com.example.qianwu.chat_abou8.classes.DownloadThread;
import java.util.Random;

/**
 * Created by qianwu on 2016-12-31.
 */
public class imageAdapter extends BaseAdapter {
    private Context mContext;
    public
    MovieObj movieObj1;
    public
    MovieObj movieObj2;
    public
    MovieObj movieObj3;
    public
    MovieObj movieObj4;
    public
    MovieObj movieObj5;
    public
    MovieObj movieObj6;
    public
    MovieObj movieObj7;
    public
    MovieObj movieObj8;
    public
    MovieObj movieObj9;
    public DownloadThread movieObjDownloadThread;


    public imageAdapter(Context c) {
        /*movieObjDownloadThread = new DownloadThread();
        movieObjDownloadThread.run();

        Random rand = new Random();

        int  i = rand.nextInt(50) + 1;

        try{
            movieObj1 = new MovieObj("star",0);
            movieObj2 = new MovieObj(i+"");
            movieObj3 = new MovieObj(i+5+"");
            movieObj4 = new MovieObj(i+15+"");
            movieObj5 = new MovieObj(i+25+"");
            movieObj6 = new MovieObj(i+35+"");
            movieObj7 = new MovieObj(i+45+"");
            movieObj8 = new MovieObj(i+55+"");
            movieObj9 = new MovieObj(i+65+"");
            Log.d("download","alldone");

        }
        catch (Exception e){

            Log.d("havent","yes");
        }*/

        mContext = c;
    }

    public int getCount() {
        return 12;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void initializeObjects(){
        new Thread(new Runnable() {
            public void run() {

                Random rand = new Random();

                int  i = rand.nextInt(50) + 1;

                try{
                    //movieObj1 = new MovieObj("star",0);
                    movieObj2 = new MovieObj(i+"");
                    movieObj3 = new MovieObj(i+5+"");
                    movieObj4 = new MovieObj(i+15+"");
                    movieObj5 = new MovieObj(i+25+"");
                    movieObj6 = new MovieObj(i+35+"");
                    movieObj7 = new MovieObj(i+45+"");
                    movieObj8 = new MovieObj(i+55+"");
                    movieObj9 = new MovieObj(i+65+"");
                    Log.d("download","alldone");

                }
                catch (Exception e){

                    Log.d("havent","yes");
                }

            }
        }).start();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(330, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }
        if(position == 0)
            Picasso.with(mContext).load(movieObj1.getMoivepic()).into(imageView);
        if(position == 1)
            Picasso.with(mContext).load(movieObj2.getMoivepic()).into(imageView);
        if(position == 2)
            Picasso.with(mContext).load(movieObj3.getMoivepic()).into(imageView);
        if(position == 3)
            Picasso.with(mContext).load(movieObj4.getMoivepic()).into(imageView);
        if(position == 4)
            Picasso.with(mContext).load(movieObj5.getMoivepic()).into(imageView);
        if(position == 5)
            Picasso.with(mContext).load(movieObj6.getMoivepic()).into(imageView);



            /*if(position == 1)movieObj2 = new MovieObj("551");
            if(position == 2) movieObj3 = new MovieObj("552");
            if(position == 3)movieObj4 = new MovieObj("553");
            if(position == 4)movieObj5 = new MovieObj("554");
            if(position == 5)movieObj6 = new MovieObj("555");*/

        Log.d("succeed","yes");







        //return imageView;
        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images

}
