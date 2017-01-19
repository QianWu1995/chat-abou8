package com.example.qianwu.chat_abou8;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qianwu.chat_abou8.classes.MovieObj;
import com.example.qianwu.chat_abou8.classes.userSharedPreferences;
import com.example.qianwu.chat_abou8.fragments.MovieList;
import com.example.qianwu.chat_abou8.fragments.likedFragment;
import com.example.qianwu.chat_abou8.fragments.profileSettingFragment;
import com.example.qianwu.chat_abou8.fragments.searchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class master extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Button setting;
    //ImageView SlideRight;
    ImageView ProfilePic;
    TextView Username;
    TextView Useremail;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference root ;
    Vector<String> URLvec = new Vector<String>();
    Vector<MovieObj> mvec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        root = FirebaseDatabase.getInstance().getReference().child("userFav")
                .child(FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getDisplayName());

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Log.d("children",dataSnapshot.getChildren().iterator());

                Map<String, Object> td2 = (HashMap<String,Object>) dataSnapshot.getValue();
                Set set = td2.entrySet();
                Iterator iterator = set.iterator();

                while(iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry)iterator.next();
                    Log.d("mentry21",mentry.getKey().toString());
                    Log.d("mentry1",mentry.toString().substring(9,mentry.toString().length()));
                    URLvec.add(mentry.toString().substring(9,mentry.toString().length()));
                    Log.d("mentry",mentry.toString().substring(5,8));
                    Log.d("gitgit","key is: "+ mentry.getKey());


                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setSupportActionBar(toolbar);

        mFirebaseAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        Username = (TextView)hView.findViewById(R.id.userNameNav);
        ProfilePic = (ImageView)hView.findViewById(R.id.profilePic);
        Useremail = (TextView)hView.findViewById(R.id.userEmailAddress);
        //SlideRight = (ImageView)hView.findViewById(R.id.slideright);
        Username.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
        Useremail.setText(mFirebaseAuth.getCurrentUser().getEmail());
        //Picasso.with(getApplicationContext()).load(mFirebaseAuth.getCurrentUser().getPhotoUrl()).into(ProfilePic);
        navigationView.setNavigationItemSelectedListener(this);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
    class MyLoadingTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private ProgressDialog progressDialog;
        Vector<MovieObj> mvec;
        searchFragment fragment; //= searchFragment.newInstance("","",null);


        @Override
        protected void onPreExecute() {
            mvec = new Vector<MovieObj>();
            //progressDialog = new ProgressDialog(MyLoadingTask.class);
            //progressDialog.setMessage("one sec");
            //progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for(int i = 780;i<796;++i){
                try{
                    MovieObj movieObj = new MovieObj(i+"");
                    mvec.add(movieObj);
                }
                catch (Exception e){
                    Log.d("Exception caught","MovieObj initialization");

                }

            }
            Log.d("done2","yes");
            Log.d("size of array2",mvec.size()+"");

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            //progressDialog.dismiss();
            fragment = searchFragment.newInstance("","",mvec);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainmasterview,fragment).commit();

            //Start other Activity or do whatever you want
        }
    }

    class MyLoadingTaskMovieList extends AsyncTask<Void, Void, Void> {
        private Context context;
        private ProgressDialog progressDialog;
        Vector<MovieObj> mvec2;
        MovieList fragment; //= searchFragment.newInstance("","",null);


        @Override
        protected void onPreExecute() {
            mvec2 = new Vector<MovieObj>();

        }

        @Override
        protected Void doInBackground(Void... params) {
            for(int i = 792;i<801;++i){
                try{
                    MovieObj movieObj = new MovieObj(i+"");
                    //Log.d("movie name",movieObj.title);
                    mvec2.add(movieObj);
                }
                catch (Exception e){
                    Log.d("Exception caught","MovieObj initialization");

                }

            }
            Log.d("done2","yes");
            Log.d("size of array2",mvec2.size()+"");

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            //progressDialog.dismiss();
            fragment = MovieList.newInstance(mvec2);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainmasterview,fragment).commit();

            //Start other Activity or do whatever you want
        }
    }


    Vector<MovieObj> favMovie(){
        Log.d("root","");

        return null;



    }

    class ObjBuilding extends AsyncTask<String, Void, Void> {
        private Context context;
        private ProgressDialog progressDialog;

        likedFragment fragment; //= searchFragment.newInstance("","",null);
        MovieObj m;


        @Override
        protected void onPreExecute() {



        }

        @Override
        protected Void doInBackground(String... params) {

            try{
                m = new MovieObj(params[0]);

            }
            catch (Exception r){

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            mvec.add(m);

        }
    }

    class MylikedLoading extends AsyncTask<Void, Void, Void> {
        private Context context;
        private ProgressDialog progressDialog;

        likedFragment fragment; //= searchFragment.newInstance("","",null);
        MovieObj m;

        @Override
        protected void onPreExecute() {
            mvec = new Vector<MovieObj>();

        }

        @Override
        protected Void doInBackground(Void... params) {

            for(int i = 0;i<URLvec.size();++i ){
                m = null;
                try{
                    Log.d("URLVEC",URLvec.elementAt(i));
                    m = new MovieObj(URLvec.elementAt(i));
                    Thread.sleep(100);

                }catch (Exception e){


                }
                mvec.add(m);




            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            //progressDialog.dismiss();
            fragment = likedFragment.newInstance(mvec);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainmasterview,fragment).commit();
            //progressDialog.dismiss();
            //Start other Activity or do whatever you want
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            new MyLoadingTaskMovieList().execute(null,null,null);
            //SlideRight.getBackground().setAlpha(0);
        } else if (id == R.id.nav_gallery) {
            profileSettingFragment fragment = new profileSettingFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainmasterview,fragment).commit();

        } else if (id == R.id.nav_slideshow) {//movie liked

            new MylikedLoading().execute(null,null,null);



        } else if (id == R.id.nav_manage) {
            //search


            new MyLoadingTask().execute(null,null,null);


        } else if (id == R.id.nav_share) {//log out
            Intent backToLogin =new Intent(this, LoginActivity.class);
            userSharedPreferences.setPrefPassWord(master.this,"");
            userSharedPreferences.setUserName(master.this,"");
            startActivity(backToLogin);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
