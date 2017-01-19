package com.example.qianwu.chat_abou8.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.qianwu.chat_abou8.R;
import com.example.qianwu.chat_abou8.classes.MovieObj;
import com.example.qianwu.chat_abou8.classes.RecyclerItemClickListener;
import com.example.qianwu.chat_abou8.classes.RecyclerViewAdapterSearchView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class searchFragment extends Fragment implements View.OnClickListener{
    //ListView resultList;
    //LinearLayout mLinearLayout;
    RecyclerView mRecyclerView;
    SearchView mSearchView;
    LayoutInflater layoutInflater;
    private Vector<MovieObj> movieObjVector  = null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static searchFragment newInstance(String param1, String param2,Vector<MovieObj> movieObjs) {
        searchFragment fragment = new searchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putSerializable("obj",movieObjs);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View v) {
        if(v == mRecyclerView){
           Log.d("linear","yes");

        }

    }
    class MyLoadingTaskMovieList extends AsyncTask<String, Void, Void> {
        private Context context;
        private ProgressDialog progressDialog;
        Vector<MovieObj> mvec;
        MovieList fragment; //= searchFragment.newInstance("","",null);


        @Override
        protected void onPreExecute() {
            mvec = new Vector<MovieObj>();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading result from your input");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {

            movieObjVector =  searchFromKeyWord(params[0]);
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            mRecyclerView.setAdapter(new RecyclerViewAdapterSearchView(getActivity(),movieObjVector));
            progressDialog.dismiss();
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            movieObjVector = (Vector<MovieObj>) getArguments().getSerializable("obj");
        }


    }

    JSONObject mJSONObject;
    int numOfObj;
    String json;
    public Vector<MovieObj> searchFromKeyWord(String keys){



        final Vector<MovieObj> movieObjVector = new Vector<MovieObj>();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("http://api.themoviedb.org/3/search/movie?query=" + keys + "&api_key=5565bb94f87424577ef39f24d901ff06").build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d("FAIL","TRUE");
            }
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        Log.v("TAG", jsonData);
                        json = jsonData;
                        Log.v("TAG"+"123",json);
                        try{
                            mJSONObject = new JSONObject(json);
                            JSONArray movieList = mJSONObject.getJSONArray("results");
                            numOfObj = movieList.length();
                            Log.d("numofObj",numOfObj+"");
                            for(int i = 0; i < numOfObj; ++i){
                                JSONObject curObj = movieList.getJSONObject(i);
                                MovieObj tempMovie = new MovieObj(curObj.getString("id"));
                                movieObjVector.add(tempMovie);
                            }

                        }
                        catch (Exception e){
                            Log.d("Jiba","error!!!!cannot initialize obecjts vector");
                        }
                    }
                    else{
                        //alertUserAboutError();

                    }
                }
                catch (IOException e) {
                    Log.e("TAG", "Exception caught: ", e);
                }
                    /*catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }*/
            }
        });
        return movieObjVector;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("size of array!!!!!!!!",movieObjVector.size()+"");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), ));//这里用线性宫格显示 类似于grid view
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        mRecyclerView.setAdapter(new RecyclerViewAdapterSearchView(getActivity(),movieObjVector));
        //layoutInflater = LayoutInflater.from(getActivity());z
        mSearchView = (SearchView)view.findViewById(R.id.movieSearchView);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
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

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                try{
                    // build the vector here
                    movieObjVector = null;
                     new MyLoadingTaskMovieList().execute(query);
                    Log.d("related movie",movieObjVector.size()+"");
                    //mSearchView.clearFocus();
                    /*movieObjVector =  searchFromKeyWord(query);

                    try{
                        Thread.sleep(2000);
                    }
                    catch (Exception e){

                    }


                    mRecyclerView.setAdapter(new RecyclerViewAdapterSearchView(getActivity(),movieObjVector));*/

                }
                catch (Exception e){
                    Log.d("no results", "yes");
                }

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event

}
