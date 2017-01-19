package com.example.qianwu.chat_abou8.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qianwu.chat_abou8.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by qianwu on 2017-01-09.
 */
public class recyclerViewAdapter extends BaseAdapter {
    Context m;
    Vector<MovieObj> mObjArrayList;

    public recyclerViewAdapter(Context context, Vector<MovieObj> movieObjArrayList) {
        m = context;
        mObjArrayList = movieObjArrayList;
    }

    @Override
    public int getCount() {
        return mObjArrayList.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MovieObj currentMovie = mObjArrayList.elementAt(i);


        if (view == null) {

            final LayoutInflater inflater = LayoutInflater.from(m);

            view = inflater.inflate(R.layout.book, null);

        }

        final ImageView imageView = (ImageView)view.findViewById(R.id.imageView4);
        final TextView textView = (TextView)view.findViewById(R.id.textView5);
        Picasso.with(m).load(currentMovie.getMoivepic()).into(imageView);
        textView.setText(currentMovie.title);

        return view;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
