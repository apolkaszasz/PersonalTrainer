package com.personaltrainer.apolka.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.personaltrainer.apolka.personaltrainer.Adapters.ExerciseAdapter;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.ExerciseDay;
import com.personaltrainer.apolka.personaltrainer.Models.Program;
import com.personaltrainer.apolka.personaltrainer.TabBarFragments.WorkoutsExercisesFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramItem extends AppCompatActivity {

    private static final String TAG = "ProgramItem";



    private TextView programName;
    private TextView programDescription;
    private TextView programDurationInWeeks;
    private TextView programWorkoutdays;
    private FloatingActionButton addButton;

    private List<String> dayList;
    private List<String> workoutDayList;

    private List<String> exerciseKeylist;

    private Program program;

    private GridView mExerciseGridView;
    private ExerciseAdapter mExerciseAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    private static final String PROGRAM_OBJ_KEY = "Program_Obj_Key";
    public static Intent getStartIntent(Context context, Program program) {
        return new Intent(context, ProgramItem.class)
                .putExtra(PROGRAM_OBJ_KEY, program);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_item);

        Intent i = getIntent();
        program = (Program) i.getSerializableExtra(PROGRAM_OBJ_KEY);

        dayList = new ArrayList<String>();
        workoutDayList = new ArrayList<String>();

        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");
        dayList.add("Sunday");

        for (int j = 0; j < 7; j++) {
            if (program.getDaysListPerWeek().get(j) == 1) {
                workoutDayList.add(dayList.get(j));
            }
        }

        programName = (TextView) findViewById(R.id.ProgramNameTextView);
        programName.setText(program.getName());

        programDescription = (TextView) findViewById(R.id.ProgramDescriptionValue);
        programDescription.setText(program.getDescription());

        programDurationInWeeks = (TextView) findViewById(R.id.ProgramDurationInWeeksTextViewValue);
        programDurationInWeeks.setText(program.getDurationInWeeks() + "");

        programWorkoutdays = (TextView) findViewById(R.id.ProgramWorkoutDaysTextViewValue);
        programWorkoutdays.setText(TextUtils.join(", ", workoutDayList));

        addButton = (FloatingActionButton)findViewById(R.id.AddThisProgramToPlansFloatingButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                newPlannedProgram();
            }}
        );

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Exercises").child("");


        Spinner spinner = (Spinner) findViewById(R.id.DaysSpinner);
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, workoutDayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                populateCurrentDaysExercisesListView(dataAdapter.getItem(position));
                attachDatabaseReadListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

                populateCurrentDaysExercisesListView(dataAdapter.getItem(0));
                attachDatabaseReadListener();
            }
        });





    }
    private void populateCurrentDaysExercisesListView(String day){
        exerciseKeylist = new ArrayList<>();
        switch (day){
            case "Monday":
                exerciseKeylist = program.getExercisesByDayId(0);
                break;
            case "Tuesday":
                exerciseKeylist = program.getExercisesByDayId(1);
                break;
            case "Wednesday":
                exerciseKeylist = program.getExercisesByDayId(2);
                break;
            case "Thursday":
                exerciseKeylist = program.getExercisesByDayId(3);
                break;
            case "Friday":
                exerciseKeylist = program.getExercisesByDayId(4);
                break;
            case "Saturday":
                exerciseKeylist = program.getExercisesByDayId(5);
                break;
            case "Sunday":
                exerciseKeylist = program.getExercisesByDayId(6);
                break;
        }



        mExerciseGridView = (GridView) findViewById(R.id.CurrentdayExerciseGridView);
        mExerciseGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise exercise = (Exercise) adapterView.getAdapter().getItem(i);
                startActivity(ExerciseItem.getStartIntent(view.getContext(), exercise));
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

    private void newPlannedProgram(){
        startActivity(NewPlannedProgram.getStartIntent(this, program));

    }

}
