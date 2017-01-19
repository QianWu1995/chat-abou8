package com.example.qianwu.chat_abou8.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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


public class likedFragment extends Fragment {
    RecyclerView mView2;
    Vector<MovieObj> mObjVector;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



    // TODO: Rename and change types and number of parameters
    public static likedFragment newInstance(Vector<MovieObj> movieObjs) {
        likedFragment fragment = new likedFragment();
        Bundle args = new Bundle();

        args.putSerializable("likedVec",movieObjs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mObjVector = (Vector<MovieObj>) getArguments().getSerializable("likedVec");
            Log.d("size of mObjVec",mObjVector.size()+"");
        }
        else{
            Log.d("vector","is empty");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked, container, false);
        mView2 = (RecyclerView)view.findViewById(R.id.recycler_view_movieliked);
        final RecyclerViewAdapterSearchView Adapter = new RecyclerViewAdapterSearchView(getActivity(),mObjVector);
        mView2.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        mView2.setAdapter(Adapter);
        // Inflate the layout for this fragment
        mView2.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mView2 ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.d("position",""+position);
                        FragmentManager manager = getFragmentManager();

                        movieDetail Detail = movieDetail.newInstance(position,mObjVector);
                        manager.beginTransaction().replace(R.id.mainmasterview,Detail).commit();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                }));
        return view;
    }


}
