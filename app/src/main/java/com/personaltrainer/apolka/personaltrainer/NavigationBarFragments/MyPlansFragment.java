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
import android.view.MotionEvent;
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
import com.personaltrainer.apolka.personaltrainer.Adapters.ProgramAdapter;
import com.personaltrainer.apolka.personaltrainer.ExerciseItem;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedProgram;
import com.personaltrainer.apolka.personaltrainer.Models.Program;
import com.personaltrainer.apolka.personaltrainer.PlannedExerciseItem;
import com.personaltrainer.apolka.personaltrainer.PlannedProgramItem;
import com.personaltrainer.apolka.personaltrainer.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyPlansFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button SelectedDate;
    private GridView mExerciseGridView;
    private GridView mProgramGridView;
    private ExerciseAdapter mExerciseAdapter;
    private ProgramAdapter mProgramAdapter;
    private Map<String, Program> allPrograms;
    private Map<String, Exercise> allExercises;
    private List<PlannedExercise> mPlannedExerciseList;
    private List<PlannedProgram> mPlannedProgramList;
    private List<String> keyList;
    private List<String> pKeyList;

    private Button updateButton;



    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mExercisesDatabaseReference;
    private DatabaseReference mPlannedExercisesDatabaseReference;
    private DatabaseReference mProgramsDatabaseReference;
    private DatabaseReference mPlannedProgamsDatabaseReference;
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

        return inflater.inflate(R.layout.fragment_my_plans, container, false);
    }



    private void createTodaysExerciseKEYList(){
        List<PlannedExercise> aux = new ArrayList<>();
        keyList.clear();
        Log.d(TAG,"hereeeeeeeeeeeeeeeeeeee" + mPlannedExerciseList.size());
        for(int i=0;i<mPlannedExerciseList.size();i++){
            Log.d(TAG,"date>>>>>>"+dateFormat.format(mPlannedExerciseList.get(i).getDate()));
            Log.d(TAG,"date>>>>TODAY>>"+dateFormat.format(dateTime.getTime()));
            if(dateFormat.format(mPlannedExerciseList.get(i).getDate()).equals(dateFormat.format(dateTime.getTime()))){
                Log.d(TAG,"......................."+mPlannedExerciseList.get(i).getExercise());
                aux.add(mPlannedExerciseList.get(i));
                keyList.add(mPlannedExerciseList.get(i).getExercise());
            }
        };
        mPlannedExerciseList = aux;

    }
    private void createTodaysProgramKEYList(){
        List<PlannedProgram> aux = new ArrayList<>();
        if(mProgramAdapter!=null){
            mProgramAdapter.clear();
        }
        pKeyList.clear();
        Log.d(TAG,"hereeeeeeeeeeeeeeeeeeee" + mPlannedProgramList.size());
        for(int i=0;i<mPlannedProgramList.size();i++){
            Log.d(TAG,"date>>>>>>"+dateFormat.format(mPlannedProgramList.get(i).getStartDate()));
            Log.d(TAG,"date>>>>TODAY>>"+dateFormat.format(dateTime.getTime()));
            int days = allPrograms.get(mPlannedProgramList.get(i).getProgram()).getDurationInWeeks()*7;
            for (int day=0;day<days;day++){
                Calendar dayCurrent = Calendar.getInstance();
                dayCurrent.setTime(mPlannedProgramList.get(i).getStartDate());
                dayCurrent.add(Calendar.DATE,day);
                if(dateFormat.format(dayCurrent.getTime()).equals(dateFormat.format(dateTime.getTime()))){

                    //a program érvényes a détumon
                    List<Integer> workoutDays = allPrograms.get(mPlannedProgramList.get(i).getProgram()).getDaysListPerWeek();
                    Log.d(TAG,workoutDays+"");
                    Log.d(TAG,day%7+"");
                    if (workoutDays.get(day%7)==1){
                        //a kivalasztott datum workout day a program szerint
                        aux.add(mPlannedProgramList.get(i));
                        pKeyList.add(mPlannedProgramList.get(i).getProgram());
                        mProgramAdapter.add(allPrograms.get(mPlannedProgramList.get(i).getProgram()));
                    }

                }

            }

        };
        mPlannedProgramList = aux;




    }

    private void populateCurrentDaysExercisesListView(){

        List<Exercise> exerciseListForAdapter = new ArrayList<>();
        mExerciseAdapter = new ExerciseAdapter(this, R.layout.item_exercise, exerciseListForAdapter);

        mExerciseGridView.setAdapter(mExerciseAdapter);


    }
    private void populateCurrentDaysProgramsListView(){


        mProgramGridView.setAdapter(mProgramAdapter);


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
        if (mProgramAdapter!=null){
            mProgramAdapter.clear();
        }
        if (mExerciseAdapter!=null){
            mExerciseAdapter.clear();
        }



    }
    @Override
    public void onResume(){
        super.onResume();




    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

        List<Program> programListForAdapter = new ArrayList<>();
        mProgramAdapter = new ProgramAdapter(this, R.layout.grid_item_program, programListForAdapter);


        updateButton = (Button)view.findViewById(R.id.updateExlist);
        updateButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {


                    //elkésziti a kulcs listát
                    createTodaysExerciseKEYList();
                    createTodaysProgramKEYList();

                }


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d(TAG,"itt vagzpokl........." + keyList.size());


                    mExercisesDatabaseReference = mFirebaseDatabase.getReference().child("Exercises").child("");

                    //beolvassa azokat az Exercise-ket, amelyek szerepelnek az adott nap kulcslistáján
                    attachDatabaseReadListener2();



                    //megjeleniti az adott napon szereplő Planned Exercise-k gyakorlatait képpel, grid view-ben
                    populateCurrentDaysExercisesListView();
                    populateCurrentDaysProgramsListView();

                }



                return  true;
            }
        });

        dateFormat = DateFormat.getDateInstance();
        dateTime = Calendar.getInstance();
        SelectedDate = (Button)view.findViewById(R.id.dateButton);

        SelectedDate.setText(dateFormat.format(dateTime.getTime()).toString());

        SelectedDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onclickdatePicker();
                if (mExerciseAdapter!=null){
                    mExerciseAdapter.clear();
                }
                if(mProgramAdapter!=null){
                    mProgramAdapter.clear();
                }

                pKeyList.clear();
                keyList.clear();
                mPlannedExerciseList.clear();
                mPlannedProgramList.clear();
                allPrograms.clear();
                allExercises.clear();

                attachDatabaseReadListener();

                //exerciseupdate(getView());
            }
        });




        mPlannedExerciseList = new ArrayList<>();
        mPlannedProgramList = new ArrayList<>();
        pKeyList = new ArrayList<>();
        keyList = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance();



        mPlannedExercisesDatabaseReference =  mFirebaseDatabase.getReference().child("PlannedExercises").child("");
        mProgramsDatabaseReference = mFirebaseDatabase.getReference().child("Programs").child("");
        mPlannedProgamsDatabaseReference = mFirebaseDatabase.getReference().child("PlannedPrograms");



        mExerciseGridView=(GridView)view.findViewById(R.id.myplansExercisesGrid);
        mProgramGridView = (GridView)view.findViewById(R.id.myplansProgramsGrid);

        mExerciseGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise exercise = (Exercise) adapterView.getAdapter().getItem(i);
                PlannedExercise plannedExercise = new PlannedExercise();
                Log.d(TAG,""+ allExercises.size()+" .....");
                for (Map.Entry<String, Exercise> entry : allExercises.entrySet())
                {
                    Log.d(TAG,"bbbbbbbbbbb");
                    if (entry.getValue().getName().equals(exercise.getName())){
                        String keyy = entry.getKey();
                        for (int j=0;j<mPlannedExerciseList.size();j++){
                            if( mPlannedExerciseList.get(j).getExercise().equals(keyy)){
                                plannedExercise = mPlannedExerciseList.get(j);
                                Log.d(TAG,"........................" + plannedExercise.getExercise());
                            }
                        }
                    }
                }
                Log.d(TAG, "APOLKA SZASZ ...>>> .."+plannedExercise.getExercise()+" "+plannedExercise.getUser());
                startActivity(PlannedExerciseItem.getStartIntent(getContext(), exercise,plannedExercise));


            }
        });
        mProgramGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Program program = (Program) adapterView.getAdapter().getItem(i);
                PlannedProgram plannedProgram = new PlannedProgram();

                for (Map.Entry<String, Program> entry : allPrograms.entrySet())
                {
                    if (entry.getValue().getName().equals(program.getName())){
                        String keyy = entry.getKey();
                        for (int j=0;j<mPlannedProgramList.size();j++){
                            if( mPlannedProgramList.get(j).getProgram().equals(keyy)){
                                plannedProgram = mPlannedProgramList.get(j);

                            }
                        }
                    }
                }
                startActivity(PlannedProgramItem.getStartIntent(getContext(), program,plannedProgram, dateTime));


            }
        });


        allPrograms = new HashMap<String,Program>();
        allExercises = new HashMap<String,Exercise>();
        //beolvassa a Planned Exercise-kat az adott dátumnak megfelelően
        attachDatabaseReadListener();

        //exerciseupdate(view);

    }


    private void detachDatabaseReadListener(){
        if (mChildEventListener != null){
            mExercisesDatabaseReference.removeEventListener(mChildEventListener);
            mPlannedExercisesDatabaseReference.removeEventListener(mChildEventListener);
            mPlannedProgamsDatabaseReference.removeEventListener(mChildEventListener);
            mProgramsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener =null;
        }
    }
    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    if(dataSnapshot.getKey().startsWith("program")){
                        if(dataSnapshot.getValue(Program.class).getName()!=null){
                            Program program  = dataSnapshot.getValue(Program.class);
                            String keyy = dataSnapshot.getKey();
                            allPrograms.put(keyy,program);
                        }
                        else{
                            PlannedProgram plannedProgram = dataSnapshot.getValue(PlannedProgram.class);
                            mPlannedProgramList.add(plannedProgram);
                        }
                    }
                    else{
                        if (dataSnapshot.getValue(Exercise.class).getName()!=null){
                            for(int i=0;i<keyList.size();i++){
                                if(dataSnapshot.getKey().equals(keyList.get(i))){
                                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                                    Log.d(TAG,"child ......"+exercise.getName());
                                    mExerciseAdapter.add(exercise);
                                    allExercises.put(dataSnapshot.getKey(),exercise);

                                }

                            }

                        }
                        else{
                            PlannedExercise plannedExercise = dataSnapshot.getValue(PlannedExercise.class);
                            if (plannedExercise.equals(null)){

                            }else{
                                Log.d(TAG,"Planned exercise child is........."+plannedExercise.getExercise());
                                if (plannedExercise.getUser().equals(sharedPreferences.getString("UserID",null))){
                                    mPlannedExerciseList.add(plannedExercise);
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

            mPlannedExercisesDatabaseReference.addChildEventListener(mChildEventListener);
            //mExercisesDatabaseReference.addChildEventListener(mChildEventListener);
            mProgramsDatabaseReference.addChildEventListener(mChildEventListener);
            mPlannedProgamsDatabaseReference.addChildEventListener(mChildEventListener);
            mChildEventListener = null;
        }}
    private void attachDatabaseReadListener2() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if(dataSnapshot.getKey().startsWith("program")){}
                    else {

                        if (dataSnapshot.getValue(Exercise.class).getName() != null) {
                            for (int i = 0; i < keyList.size(); i++) {
                                if (dataSnapshot.getKey().equals(keyList.get(i))) {
                                    //Log.d(TAG,dataSnapshot.getKey()+ "...." + keyList.get(i));
                                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                                    Log.d(TAG, "child ......" + exercise.getName());
                                    mExerciseAdapter.add(exercise);

                                    allExercises.put(dataSnapshot.getKey(),exercise);
                                }

                            }

                        } else {
                            PlannedExercise plannedExercise = dataSnapshot.getValue(PlannedExercise.class);
                            Log.d(TAG, "Planned exercise child is........." + plannedExercise.getExercise());
                            if (plannedExercise.getUser().equals(sharedPreferences.getString("UserID",null))){
                                if(plannedExercise.getIsPartOfaProgram()==false){
                                    mPlannedExerciseList.add(plannedExercise);
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
