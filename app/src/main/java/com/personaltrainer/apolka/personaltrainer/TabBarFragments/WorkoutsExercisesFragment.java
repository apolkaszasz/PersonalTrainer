package com.personaltrainer.apolka.personaltrainer.TabBarFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personaltrainer.apolka.personaltrainer.Adapters.ExerciseAdapter;
import com.personaltrainer.apolka.personaltrainer.DetailActivities.ExerciseItem;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.R;

import java.util.ArrayList;
import java.util.List;


public class WorkoutsExercisesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WorkoutFragment";

    private SharedPreferences sharedPreferences;

    private String mParam1;
    private String mParam2;

    private String muscleFilter;

    private OnFragmentInteractionListener mListener;


    private GridView gridView;
    private ExerciseAdapter mExerciseAdapter;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;



    public WorkoutsExercisesFragment() {
    }




    public static WorkoutsExercisesFragment newInstance(String param1, String param2) {
        WorkoutsExercisesFragment fragment = new WorkoutsExercisesFragment();
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

        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                "userPreferences",0);
        Log.d(TAG,"................."+sharedPreferences.getString("UserID",null));

        muscleFilter = sharedPreferences.getString("muscle", null);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Exercises");


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

        gridView = (GridView)getView().findViewById(R.id.mygridview);

//      TODO Matyas: lasd magyarazat az ExerciseItem-ben
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise exercise = (Exercise) adapterView.getAdapter().getItem(i);

                startActivity(ExerciseItem.getStartIntent(getContext(), exercise));

            }
        });

        List<Exercise> exercises = new ArrayList<>();
        mExerciseAdapter = new ExerciseAdapter(this, R.layout.item_exercise, exercises);
        gridView.setAdapter(mExerciseAdapter);



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

    @Override
    public void onPause(){
        super.onPause();

        detachDatabaseReadListener();
        mExerciseAdapter.clear();
    }
    @Override
    public void onResume(){
        super.onResume();

        attachDatabaseReadListener();

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
                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                    if (muscleFilter!=null){
                        if (exercise.getMuscle().equals(muscleFilter)){
                            mExerciseAdapter.add(exercise);
                        }
                    }
                    else{
                        mExerciseAdapter.add(exercise);
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
