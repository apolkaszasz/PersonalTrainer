package com.personaltrainer.apolka.personaltrainer.NavigationBarFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personaltrainer.apolka.personaltrainer.Adapters.PlannedExerciseAdapter;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;
import com.personaltrainer.apolka.personaltrainer.R;

import java.text.DateFormat;
import java.util.Calendar;


public class AdviceFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SharedPreferences sharedPreferences;

    private PlannedExerciseAdapter mPlannedExerciseAdapter;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    private static final String TAG = "AdviceFragment";



    private DateFormat dateFormat;
    private Calendar dateTime;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AdviceFragment() {
    }


    public static AdviceFragment newInstance(String param1, String param2) {
        AdviceFragment fragment = new AdviceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //mFirebaseDatabase = FirebaseDatabase.getInstance();
        //mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("PlannedExercises");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                "userPreferences",0);
        Log.d(TAG,"................."+sharedPreferences.getString("UserID",null));

        dateFormat = DateFormat.getDateInstance();
        dateTime = Calendar.getInstance();



        return inflater.inflate(R.layout.fragment_advices, container, false);
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        TextView advice1 = (TextView)view.findViewById(R.id.textView4);
        advice1.setText( Html.fromHtml("<a href=\"http://www.diyhealthremedy.com/10-foods-to-get-flatter-abs/\">10 Foods To Get Flatter Abs</a>"));
        advice1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView advice2 = (TextView)view.findViewById(R.id.textView5);
        advice2.setText( Html.fromHtml("<a href=\"http://seannal.com/articles/nutrition/cheap-bodybuilding-foods.php\">20 Cheap Bodybuilding Foods On A Budget</a>"));
        advice2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView advice3 = (TextView)view.findViewById(R.id.textView6);
        advice3.setText( Html.fromHtml("<a href=\"https://athleticmuscle.net/post-workout-meals-for-muscle-growth/\">20 Post Workout Meals</a>"));
        advice3.setMovementMethod(LinkMovementMethod.getInstance());
    }

        public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onPause(){
        super.onPause();

        //detachDatabaseReadListener();

    }
    @Override
    public void onResume(){
        super.onResume();

        //attachDatabaseReadListener();

    }
    private void detachDatabaseReadListener(){
        if (mChildEventListener != null){
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener =null;
        }
    }
    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    PlannedExercise plannedExercise = dataSnapshot.getValue(PlannedExercise.class);
                    Log.d(TAG,plannedExercise.getUser());
                    if(plannedExercise.getUser().equals(sharedPreferences.getString("UserID",null))){
                        Log.d(TAG,plannedExercise.getUser());

                        Calendar day5 = Calendar.getInstance();
                        day5.add(Calendar.DATE,-5);
                        Calendar day6 = Calendar.getInstance();
                        day6.add(Calendar.DATE,-6);
                        Calendar day7 = Calendar.getInstance();
                        day7.add(Calendar.DATE,-7);


                        Log.d(TAG,dateFormat.format(plannedExercise.getDate()));

                        if(dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day6.getTime())) || dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day7.getTime()))){
                            //yellow

                        }


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
            };

            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }}
}


