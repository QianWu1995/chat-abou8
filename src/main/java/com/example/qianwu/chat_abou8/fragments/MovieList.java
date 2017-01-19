package com.example.qianwu.chat_abou8.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qianwu.chat_abou8.R;
import com.example.qianwu.chat_abou8.classes.MovieObj;
import com.example.qianwu.chat_abou8.classes.RecyclerItemClickListener;
import com.example.qianwu.chat_abou8.classes.RecyclerViewAdapterSearchView;

import java.util.Vector;


public class MovieList extends Fragment {
    RecyclerView gridview;
    private Vector<MovieObj> movieObjVector  = null;

    public MovieList(){


    }

    public static MovieList newInstance(Vector<MovieObj> movieObjs) {
        MovieList fragment = new MovieList();
        Bundle args = new Bundle();

        args.putSerializable("obj",movieObjs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            movieObjVector = (Vector<MovieObj>) getArguments().getSerializable("obj");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("amount of objs",movieObjVector.size()+"");

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        gridview = (RecyclerView)view.findViewById(R.id.recycler_view_movielist);
        final RecyclerViewAdapterSearchView Adapter = new RecyclerViewAdapterSearchView(getActivity(),movieObjVector);
        gridview.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        gridview.setAdapter(Adapter);

        gridview.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), gridview ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                // do whatever
                Log.d("position",""+position);
                FragmentManager manager = getFragmentManager();

                movieDetail Detail = movieDetail.newInstance(position,movieObjVector);
                manager.beginTransaction().replace(R.id.mainmasterview,Detail).commit();


            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));




        return view;

    }





}
