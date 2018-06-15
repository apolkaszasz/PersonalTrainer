package com.personaltrainer.apolka.personaltrainer.NavigationBarFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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


public class ResultsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences sharedPreferences;

    private static final String TAG = "ResultsFragment";

    private PlannedExerciseAdapter mPlannedExerciseAdapter;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;


    private ImageView fullBody;
    private ImageView abs;
    private ImageView abductors;
    private ImageView biceps;
    private ImageView calves;
    private ImageView chest;
    private ImageView feet;
    private ImageView forearms;
    private ImageView glutes;
    private ImageView hamstrings;
    private ImageView hands;
    private ImageView lats;
    private ImageView neck;
    private ImageView obliques;
    private ImageView quads;
    private ImageView shoulders;
    private ImageView triceps;
    private ImageView upperBack;



    private DateFormat dateFormat;
    private Calendar dateTime;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ResultsFragment() {
    }

    public static ResultsFragment newInstance(String param1, String param2) {
        ResultsFragment fragment = new ResultsFragment();
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

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("PlannedExercises");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                "userPreferences",0);
        Log.d(TAG,"................."+sharedPreferences.getString("UserID",null));


        dateFormat = DateFormat.getDateInstance();
        dateTime = Calendar.getInstance();

        abductors = (ImageView)view.findViewById(R.id.imageViewAbductorsR);
        abs = (ImageView)view.findViewById(R.id.imageViewAbsR);
        biceps = (ImageView)view.findViewById(R.id.imageViewBicepsR);
        calves = (ImageView)view.findViewById(R.id.imageViewCalvesR);
        chest = (ImageView)view.findViewById(R.id.imageViewChestR);
        feet = (ImageView)view.findViewById(R.id.imageViewFeetR);
        forearms = (ImageView)view.findViewById(R.id.imageViewForearmsR);
        glutes = (ImageView)view.findViewById(R.id.imageViewGlutesR);
        hamstrings = (ImageView)view.findViewById(R.id.imageViewHamstringsR);
        hands = (ImageView)view.findViewById(R.id.imageViewHandsR);
        lats = (ImageView)view.findViewById(R.id.imageViewLatsR);
        neck = (ImageView)view.findViewById(R.id.imageViewNeckR);
        obliques = (ImageView)view.findViewById(R.id.imageViewObliquesR);
        quads = (ImageView)view.findViewById(R.id.imageViewQuadsR);
        shoulders = (ImageView)view.findViewById(R.id.imageViewShouldersR);
        triceps = (ImageView)view.findViewById(R.id.imageViewTricepsR);
        upperBack = (ImageView)view.findViewById(R.id.imageViewUpperBackR);

        fullBody = (ImageView)view.findViewById(R.id.imageViewFullBodyR);

        abductors.clearColorFilter();
        abs.clearColorFilter();
        biceps.clearColorFilter();
        calves.clearColorFilter();
        chest.clearColorFilter();
        feet.clearColorFilter();
        forearms.clearColorFilter();
        glutes.clearColorFilter();
        hamstrings.clearColorFilter();
        hands.clearColorFilter();
        lats.clearColorFilter();
        neck.clearColorFilter();
        obliques.clearColorFilter();
        quads.clearColorFilter();
        shoulders.clearColorFilter();
        triceps.clearColorFilter();
        upperBack.clearColorFilter();


        //upperBack.setColorFilter(Color.rgb(204,0,0));


        return view;
    }

    @Override
    public void onPause(){
        super.onPause();

        detachDatabaseReadListener();

    }
    @Override
    public void onResume(){
        super.onResume();

        attachDatabaseReadListener();

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
                        if (plannedExercise.getIsCompleted() ==1) {


                            Log.d(TAG, plannedExercise.getUser());
                            Calendar day1 = Calendar.getInstance();
                            day1.add(Calendar.DATE, 0);
                            Calendar day2 = Calendar.getInstance();
                            day2.add(Calendar.DATE, -1);
                            Calendar day3 = Calendar.getInstance();
                            day3.add(Calendar.DATE, -2);
                            Calendar day4 = Calendar.getInstance();
                            day4.add(Calendar.DATE, -3);
                            Calendar day5 = Calendar.getInstance();
                            day5.add(Calendar.DATE, -4);
                            Calendar day6 = Calendar.getInstance();
                            day6.add(Calendar.DATE, -5);
                            Calendar day7 = Calendar.getInstance();
                            day7.add(Calendar.DATE, -6);


                            Log.d(TAG, dateFormat.format(plannedExercise.getDate()));
                            Log.d(TAG, dateFormat.format(day1.getTime()));

                            if (dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day6.getTime())) || dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day7.getTime()))) {
                                //yellow
                                Log.d(TAG, plannedExercise.getUser());
                                if (plannedExercise.getExercise().startsWith("abs")) {
                                    abs.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("abd")) {
                                    abductors.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("bic")) {
                                    biceps.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("cal")) {
                                    calves.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("che")) {
                                    chest.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("fee")) {
                                    feet.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("for")) {
                                    forearms.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("glu")) {
                                    glutes.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("ham")) {
                                    hamstrings.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("han")) {
                                    hands.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("lat")) {
                                    lats.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("nec")) {
                                    neck.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("obl")) {
                                    obliques.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("qua")) {
                                    quads.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("sho")) {
                                    shoulders.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("tri")) {
                                    triceps.setColorFilter(Color.rgb(255, 255, 153));
                                }
                                if (plannedExercise.getExercise().startsWith("upp")) {
                                    upperBack.setColorFilter(Color.rgb(255, 255, 153));
                                }
                            }
                            if (dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day4.getTime())) || dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day5.getTime()))) {
                                //orange
                                if (plannedExercise.getExercise().startsWith("abs")) {
                                    abs.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("abd")) {
                                    abductors.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("bic")) {
                                    biceps.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("cal")) {
                                    calves.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("che")) {
                                    chest.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("fee")) {
                                    feet.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("for")) {
                                    forearms.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("glu")) {
                                    glutes.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("ham")) {
                                    hamstrings.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("han")) {
                                    hands.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("lat")) {
                                    lats.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("nec")) {
                                    neck.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("obl")) {
                                    obliques.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("qua")) {
                                    quads.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("sho")) {
                                    shoulders.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("tri")) {
                                    triceps.setColorFilter(Color.rgb(255, 102, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("upp")) {
                                    upperBack.setColorFilter(Color.rgb(255, 102, 0));
                                }

                            }
                            if (dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day1.getTime())) || dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day2.getTime())) || dateFormat.format(plannedExercise.getDate()).equals(dateFormat.format(day3.getTime()))) {
                                //red
                                if (plannedExercise.getExercise().startsWith("abs")) {
                                    abs.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("abd")) {
                                    abductors.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("bic")) {
                                    biceps.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("cal")) {
                                    calves.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("che")) {
                                    chest.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("fee")) {
                                    feet.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("for")) {
                                    forearms.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("glu")) {
                                    glutes.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("ham")) {
                                    hamstrings.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("han")) {
                                    hands.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("lat")) {
                                    lats.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("nec")) {
                                    neck.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("obl")) {
                                    obliques.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("qua")) {
                                    quads.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("sho")) {
                                    shoulders
                                            .setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("tri")) {
                                    triceps.setColorFilter(Color.rgb(204, 0, 0));
                                }
                                if (plannedExercise.getExercise().startsWith("upp")) {
                                    upperBack.setColorFilter(Color.rgb(204, 0, 0));
                                }
                            }
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
