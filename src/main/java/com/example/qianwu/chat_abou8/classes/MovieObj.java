package com.example.qianwu.chat_abou8.classes;

/**
 * Created by qianwu on 2016-12-31.
 */

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieObj {
    public String title,language,tagline,overview,posterpath;
    String exampleURL = "https://api.themoviedb.org/3/movie/550?api_key=5565bb94f87424577ef39f24d901ff06";
    String upperUrl = "https://api.themoviedb.org/3/movie/";
    String lowerUrl = "?api_key=5565bb94f87424577ef39f24d901ff06";
    String searchBynameUpper = "http://api.themoviedb.org/3/search/movie?query=";
    String searchBynameLower = "&api_key=5565bb94f87424577ef39f24d901ff06";
    String exampleURL2 = "https://api.forecast.io/forecast/be9edfe86e0f859c1537df864bc09467/37.8267,-122.423";
    String exampleURL3 = "http://image.tmdb.org/t/p/original//8gEXmIzw1tDnBfOaCFPimkNIkmm.jpg";
    String exampleURL4 = "http://image.tmdb.org/t/p/original///jrLLRe7pctP1aFDRuD9mt59YGHX.jpg";
    String uppler = "http://image.tmdb.org/t/p/original/";
    String objurl;
    double rating,popularity;
    final int BarelySeen = 3;
    final int HaveSomeFans = 5;
    final int hot = 6;
    final int superhot = 8;
    public int numOfObj;

    HttpURLConnection connection;
    String TAG = "DEBUG";
    public String json = "";
    public JSONObject mJSONObject;

    public String getUrl(){
        return objurl;

    }

    public String getMoivepic(){
        String url = uppler+posterpath;
        Log.d("TAG",url);

        return url;

    }


    public MovieObj(String url) throws MalformedURLException,JSONException {


        objurl = url;

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder().url(upperUrl + url + lowerUrl).build();

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
                    //Response response = call.execute();
                    if (response.isSuccessful()) {

                        try{
                            Log.d("jsonData is",jsonData);
                            mJSONObject = new JSONObject(jsonData);
                            title = mJSONObject.getString("title");
                            posterpath = mJSONObject.getString("poster_path");
                            overview = mJSONObject.getString("overview");
                        }

                        catch (Exception e){
                            Log.d("exception caught","1");
                        }


                    }
                    else{
                    }
                }
                catch (IOException e) {

                    Log.d("exception caught","2");
                }

            }
        });

    }

    public Vector<MovieObj> searchFromKeyWord(String keys){

        objurl = keys;

        final Vector<MovieObj> movieObjVector = new Vector<MovieObj>();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(searchBynameUpper + keys + searchBynameLower).build();
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
                        Log.v(TAG, jsonData);
                        json = jsonData;
                        Log.v(TAG+"123",json);
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
                    Log.e(TAG, "Exception caught: ", e);
                }
                    /*catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }*/
            }
        });
        return movieObjVector;
    }



}
