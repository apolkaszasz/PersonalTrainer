package com.personaltrainer.apolka.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personaltrainer.apolka.personaltrainer.Adapters.ExerciseAdapter;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedProgram;
import com.personaltrainer.apolka.personaltrainer.Models.Program;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlannedProgramItem extends AppCompatActivity {

    private static final String TAG = "PlannedProggramItem";

    private Program program;
    private PlannedProgram plannedProgram;

    private TextView programName;
    private TextView programDescription;
    private TextView programDurationInWeeks;
    private TextView programStartDate;


    private List<String> exerciseKeylist;

    private DateFormat dateFormat;
    private Calendar dateTime;

    private int week;


    private GridView mExerciseGridView;
    private ExerciseAdapter mExerciseAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    private static final String PROGRAM_OBJ_KEY = "Program_Obj_Key";
    private static final String PLANNED_PROGRAM_OBJ_KEY = "Planned_program_Obj_key";
    private static final String DATE_SELECTED = "Date_key";



    public static Intent getStartIntent(Context context, Program program, PlannedProgram plannedProgram, Calendar dateTime) {
        return new Intent(context, PlannedProgramItem.class)
                .putExtra(PROGRAM_OBJ_KEY,program)
                .putExtra(PLANNED_PROGRAM_OBJ_KEY, plannedProgram)
                .putExtra(DATE_SELECTED, dateTime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_program_item);


        Intent i  = getIntent();
        program = (Program)i.getSerializableExtra(PROGRAM_OBJ_KEY);
        plannedProgram = (PlannedProgram)i.getSerializableExtra(PLANNED_PROGRAM_OBJ_KEY);
        dateTime = (Calendar)i.getSerializableExtra(DATE_SELECTED);

        Log.d(TAG,"..." + program.getName() +"  " + plannedProgram.getProgram());


        programName = (TextView) findViewById(R.id.ProgramNameTextViewMyPlans);
        programName.setText(program.getName());

        programDescription = (TextView) findViewById(R.id.ProgramDescriptionValueMyPlans);
        programDescription.setText(program.getDescription());

        programDurationInWeeks = (TextView) findViewById(R.id.ProgramDurationInWeeksTextViewValueMyPlans);
        programDurationInWeeks.setText(program.getDurationInWeeks() + "");

        programStartDate = (TextView) findViewById(R.id.ProgramStartDateTextViewValueMyPlans);
        dateFormat = DateFormat.getDateInstance();
        programStartDate.setText(dateFormat.format(plannedProgram.getStartDate()));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Exercises").child("");

        exerciseKeylist = new ArrayList<>();
        int days = program.getDurationInWeeks()*7;
        for (int day=0;day<days;day++){
            Calendar dayCurrent = Calendar.getInstance();
            dayCurrent.setTime(plannedProgram.getStartDate());
            dayCurrent.add(Calendar.DATE,day);
            if(dateFormat.format(dayCurrent.getTime()).equals(dateFormat.format(dateTime.getTime()))){

                exerciseKeylist = program.getExercisesByDayId(day%7);
                week = day /7;
                Log.d(TAG,"....weekss" + week);
            }
        }


        pupulateExerciseList();

    }
    private void pupulateExerciseList(){
        mExerciseGridView = (GridView) findViewById(R.id.CurrentdayExerciseGridViewMyPlans);
        mExerciseGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise exercise = (Exercise) adapterView.getAdapter().getItem(i);
                PlannedExercise pe = new PlannedExercise();
                pe.setPlannedRepetitions(week * exercise.getRecommendedRepetitionsToIncreaseWith() + exercise.getRecommendedRepetitions());
                pe.setPlannedSets(exercise.getRecommendedSets());
                startActivity(PlannedExerciseItem.getStartIntent(view.getContext(), exercise,pe));
            }
        });

        List<Exercise> exerciseListForAdapter = new ArrayList<>();
        mExerciseAdapter = new ExerciseAdapter(this, R.layout.item_exercise, exerciseListForAdapter);

        mExerciseGridView.setAdapter(mExerciseAdapter);

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
                    for(String key:exerciseKeylist){
                        if(dataSnapshot.getKey().equals(key)){
                            Exercise exercise = dataSnapshot.getValue(Exercise.class);
                            Log.d(TAG,"ammmmmmmmm");
                            mExerciseAdapter.add(exercise);
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
            mChildEventListener = null;
        }}

}
