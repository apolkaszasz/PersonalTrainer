package com.personaltrainer.apolka.personaltrainer.NavigationBarFragments;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personaltrainer.apolka.personaltrainer.Adapters.ExerciseAdapter;
import com.personaltrainer.apolka.personaltrainer.Adapters.PlannedExerciseAdapter;
import com.personaltrainer.apolka.personaltrainer.ExerciseItem;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;
import com.personaltrainer.apolka.personaltrainer.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MyPlansFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button SelectedDate;
    private GridView mExerciseGridView;
    private ExerciseAdapter mExerciseAdapter;
    private List<PlannedExercise> mPlannedExerciseList;
    private List<String> keyList;
    private List<String> exerciseKeyList;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mExercisesDatabaseReference;
    private DatabaseReference mPlannedExercisesDatabaseReference;
    private ChildEventListener mChildEventListener;

    private DateFormat dateFormat;
    private Calendar dateTime;


    private SharedPreferences sharedPreferences;

    private static final String TAG = "MyPlansFragment";



    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyPlansFragment() {
    }

    public static MyPlansFragment newInstance(String param1, String param2) {
        MyPlansFragment fragment = new MyPlansFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                "userPreferences",0);
        Log.d(TAG,"................."+sharedPreferences.getString("UserID",null));


        View view = inflater.inflate(R.layout.fragment_my_plans, container, false);

        dateFormat = DateFormat.getDateInstance();
        dateTime = Calendar.getInstance();
        SelectedDate = (Button) view.findViewById(R.id.dateButton);

        Log.d(TAG,"text /......"+SelectedDate.getText().toString());

        Log.d(TAG,"the date is :....."+dateFormat.format(dateTime.getTime()));
        SelectedDate.setText(dateFormat.format(dateTime.getTime()).toString());

        Log.d(TAG,"text /......"+SelectedDate.getText().toString());

        SelectedDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onclickdatePicker();
            }
        });


        mPlannedExerciseList = new ArrayList<>();
        keyList = new ArrayList<>();
        exerciseKeyList = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance();



        mPlannedExercisesDatabaseReference =  mFirebaseDatabase.getReference().child("PlannedExercises").child("");



        mExerciseGridView=(GridView)view.findViewById(R.id.myplansExercisesGrid);


        return inflater.inflate(R.layout.fragment_my_plans, container, false);
    }

    private void createTodaysExerciseKEYList(){
        List<PlannedExercise> aux = new ArrayList<>();
        Log.d(TAG,"hereeeeeeeeeeeeeeeeeeee" + mPlannedExerciseList.size());
        for(int i=0;i<mPlannedExerciseList.size();i++){
            Log.d(TAG,"date>>>>>>"+dateFormat.format(mPlannedExerciseList.get(i).getDate()));
            Log.d(TAG,"date>>>>TODAY>>"+dateFormat.format(dateTime.getTime()));
            if(dateFormat.format(mPlannedExerciseList.get(i).getDate()).equals(dateFormat.format(dateTime.getTime()))){
                Log.d(TAG,"......................."+mPlannedExerciseList.get(i).getExercise());
                aux.add(mPlannedExerciseList.get(i));
            }
        };
        mPlannedExerciseList = aux;

    }

    private void populateCurrentDaysExercisesListView(){

        List<Exercise> exerciseListForAdapter = new ArrayList<>();
        mExerciseAdapter = new ExerciseAdapter(this, R.layout.item_exercise, exerciseListForAdapter);

        mExerciseGridView.setAdapter(mExerciseAdapter);


    }


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateTime.set(Calendar.YEAR,year);
            dateTime.set(Calendar.MONTH,month);
            dateTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            SelectedDate.setText(dateFormat.format(dateTime.getTime()));
        }
    };

    private void onclickdatePicker(){
        new DatePickerDialog(getActivity(), d ,dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();

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
    public void onPause(){
        super.onPause();


        detachDatabaseReadListener();
        mExerciseAdapter.clear();

    }
    @Override
    public void onResume(){
        super.onResume();




    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

        //beolvassa a Planned Exercise-kat az adott dátumnak megfelelően
        attachDatabaseReadListener();


        //elkésziti a kulcs listát
        createTodaysExerciseKEYList();




        mExercisesDatabaseReference = mFirebaseDatabase.getReference().child("Exercises").child("");

        //beolvassa azokat az Exercise-ket, amelyek szerepelnek az adott nap kulcslistáján
        attachDatabaseReadListener2();



        //megjeleniti az adott napon szereplő Planned Exercise-k gyakorlatait képpel, grid view-ben
        populateCurrentDaysExercisesListView();


    }

    private void detachDatabaseReadListener(){
        if (mChildEventListener != null){
            mExercisesDatabaseReference.removeEventListener(mChildEventListener);
            mPlannedExercisesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener =null;
        }
    }
    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    if (dataSnapshot.getValue(Exercise.class).getName()!=null){
                        for(int i=0;i<keyList.size();i++){
                            if(dataSnapshot.getKey().equals(keyList.get(i))){
                                Exercise exercise = dataSnapshot.getValue(Exercise.class);
                                Log.d(TAG,"child ......"+exercise.getName());
                                exerciseKeyList.add(dataSnapshot.getKey());
                                mExerciseAdapter.add(exercise);

                            }

                        }

                    }
                    else{
                        PlannedExercise plannedExercise = dataSnapshot.getValue(PlannedExercise.class);
                        Log.d(TAG,"Planned exercise child is........."+plannedExercise.getExercise());
                        mPlannedExerciseList.add(plannedExercise);
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

            mPlannedExercisesDatabaseReference.addChildEventListener(mChildEventListener);
            //mExercisesDatabaseReference.addChildEventListener(mChildEventListener);
            mChildEventListener = null;
        }}
    private void attachDatabaseReadListener2() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    if (dataSnapshot.getValue(Exercise.class).getName()!=null){
                        for(int i=0;i<keyList.size();i++){
                            if(dataSnapshot.getKey().equals(keyList.get(i))){
                                Exercise exercise = dataSnapshot.getValue(Exercise.class);
                                Log.d(TAG,"child ......"+exercise.getName());
                                exerciseKeyList.add(dataSnapshot.getKey());
                                mExerciseAdapter.add(exercise);

                            }

                        }

                    }
                    else{
                        PlannedExercise plannedExercise = dataSnapshot.getValue(PlannedExercise.class);
                        Log.d(TAG,"Planned exercise child is........."+plannedExercise.getExercise());
                        mPlannedExerciseList.add(plannedExercise);
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

            //mPlannedExercisesDatabaseReference.addChildEventListener(mChildEventListener);
            mExercisesDatabaseReference.addChildEventListener(mChildEventListener);
            mChildEventListener = null;
        }}


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
