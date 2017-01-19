package com.example.qianwu.chat_abou8.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qianwu.chat_abou8.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public class roomFragment extends Fragment implements View.OnClickListener{
    TextView chatBubble;
    ImageView sendImageButton;
    EditText UserInputArea;
    LayoutInflater layoutInflater;
    LinearLayout mLinearLayout;
    TextView firstBubble;

    private String roomName;
    private String temp_key;
    private DatabaseReference root ;
    private Query mQuery;
    private FirebaseAuth mAuth;
    private Vector<String> chatHistory;
    private String username;
    private String message;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public roomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment roomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static roomFragment newInstance(String param1, String param2) {
        roomFragment fragment = new roomFragment();
        Bundle args = new Bundle();
        args.putString("ROOMNAME", param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();



    }

    @Override
    public void onClick(View v) {



        if(v == sendImageButton){


            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            DatabaseReference message_root = root.child(root.push().getKey()+currentDateTimeString);
            Map<String,Object> map2 = new HashMap<String, Object>();
            map2.put("name",username);
            map2.put("msg",UserInputArea.getText().toString());

            message_root.updateChildren(map2);

            View ChatBubbleLayout = layoutInflater.inflate(R.layout.chatbubble, null);


            TextView m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);

            //UNCOMMENT FOLLOWING TWO LINES
            m.setText(UserInputArea.getText().toString());
            mLinearLayout.addView(ChatBubbleLayout);


            //firstBubble.setText("hello from the other side");

        }

    }
    ValueEventListener postListener = new ValueEventListener() {
        String curUser;


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            //Post post = dataSnapshot.getValue(Post.class);
            Map<String, Object> td2 = (HashMap<String,Object>) dataSnapshot.getValue();
            if(dataSnapshot.hasChildren() == false){
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                DatabaseReference message_root = root.child(root.push().getKey()+currentDateTimeString);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                map2.put("msg","Welcome! You are the first one here! Go send the first message!");

                message_root.updateChildren(map2);
                td2 = map2;

                View ChatBubbleLayout = layoutInflater.inflate(R.layout.chatbubble2, null);


                TextView m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);
                m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);
                m.setText("Welcome! You are the first one here! Go send the first message!");
                mLinearLayout.addView(ChatBubbleLayout);
                return;
            }


            Map<String, Object> td = new TreeMap<String, Object>(td2);
            Set set = td.entrySet();
            Iterator iterator = set.iterator();

            while(iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry)iterator.next();
                Log.d("mentry",mentry.toString());
                Log.d("ddddd1","key is: "+ mentry.getKey() + " & Value is: ");
                Log.d("ddddd",mentry.toString());
                String []messageArray = mentry.toString().split("msg=");
                curUser = messageArray[0].split("name=")[1].substring(0,messageArray[0].split("name=")[1].length()-2);
                Log.d("curuser is",curUser);
                Log.d("ddddd2",messageArray[messageArray.length-1]);
                message = messageArray[messageArray.length-1].substring(0,messageArray[messageArray.length-1].length()-1);
                Log.d("ddddd3",message);
                if(chatHistory == null){
                    Log.d("ddddd4","null");
                }
                if(message!=""){
                chatHistory.add(message);
                }
            }
            if(curUser == username){
                Log.d("not same user is ",username);
                View ChatBubbleLayout = layoutInflater.inflate(R.layout.chatbubble2, null);


                TextView m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);
                m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);
                m.setText(message);
                mLinearLayout.addView(ChatBubbleLayout);


            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.d("OnCancelled", "loadPost:onCancelled", databaseError.toException());
            // ...
        }


    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //populate();



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_search) {
            Log.d("setting pressed","yes");
            View ChatBubbleLayout = layoutInflater.inflate(R.layout.chatbubble, null);
            TextView m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);
            int i = 0;
            while(i<=chatHistory.size()-1){
                ChatBubbleLayout = layoutInflater.inflate(R.layout.chatbubble, null);


                m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);
                m.setText(chatHistory.elementAt(i));
                mLinearLayout.addView(ChatBubbleLayout);
                i++;

            }
        }*/


        return super.onOptionsItemSelected(item);
    }

    void populate(){
        int i = 0;
        while(i<=chatHistory.size()-1){
            View ChatBubbleLayout = layoutInflater.inflate(R.layout.chatbubble, null);
            TextView m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);
            ChatBubbleLayout = layoutInflater.inflate(R.layout.chatbubble, null);


            m = (TextView) ChatBubbleLayout.findViewById(R.id.textViewFirstBubble);
            m.setText(chatHistory.elementAt(i));
            mLinearLayout.addView(ChatBubbleLayout);
            i++;

        }


    }
    public class MyTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... Params) {

            populate();
            return null;
        }



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment]
        mAuth = FirebaseAuth.getInstance();
        username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        Log.d("userEmail",mAuth.getCurrentUser().getEmail());
        if(mAuth.getCurrentUser().getDisplayName() != null){
        Log.d("userName",mAuth.getCurrentUser().getDisplayName());}
        chatHistory = new Vector<String>();
        Log.d("Current room name",getArguments().getString("ROOMNAME"));
        root = FirebaseDatabase.getInstance().getReference().child("MovieChatDataSet").child(getArguments().getString("ROOMNAME"));
        //root2 = new Firebase("https://chataboutit-52307.firebaseio.com/fight_club");
        mQuery = root.orderByKey();

        View view = inflater.inflate(R.layout.fragment_room, container, false);
        firstBubble = (TextView)view.findViewById(R.id.textViewFirstBubble);
        //chatBubble = (TextView)view.findViewById(R.id.textViewFirstBubble);
        sendImageButton = (ImageView) view.findViewById(R.id.sendMessageButton);
        UserInputArea = (EditText)view.findViewById(R.id.EditMessageText);
        mLinearLayout = (LinearLayout)view.findViewById(R.id.genericLinearLayout);

        //populate = (Button)view.findViewById(R.id.populate);

        //root.addValueEventListener(postListener);
        mQuery.addValueEventListener(postListener);
        //populate.setOnClickListener(this);
        //chatBubble.setOnClickListener(this);
        sendImageButton.setOnClickListener(this);
        //settings.setOnClickListener(this);
        layoutInflater = LayoutInflater.from(getActivity());




        new MyTask().execute();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    // TODO: Rename method, update argument and hook method into UI event



}
