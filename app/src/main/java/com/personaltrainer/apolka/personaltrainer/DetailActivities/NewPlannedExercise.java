package com.personaltrainer.apolka.personaltrainer.DetailActivities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;
import com.personaltrainer.apolka.personaltrainer.R;

import java.text.DateFormat;
import java.util.Calendar;

public class NewPlannedExercise extends AppCompatActivity {

    private static final String TAG = "NewPlannedExercise";
    private static final String EXERCISE_OBJ_KEY = "Exercise_Obj_Key";
    private Exercise exercise;
    private PlannedExercise newExercise;
    private String exerciseKey;
    private String userId;

    private SharedPreferences sharedPreferences;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private DatabaseReference mMessagesDatabaseReference2;
    private ChildEventListener mChildEventListener;


    private TextView exercisenameTextView;
    private TextView muscleTextView;
    private EditText repetitionsEditText;
    private EditText setsEdittext;
    private TextView dateTextView;
    private Button add;

    private DateFormat dateFormat;
    private Calendar dateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_planned_exercise);


        Intent i = getIntent();
        exercise = (Exercise)i.getSerializableExtra(EXERCISE_OBJ_KEY);

        Log.d(TAG,"start intent ....."+exercise.getName());
        sharedPreferences = this.getSharedPreferences(
                "userPreferences",0);
        Log.d(TAG,"................."+sharedPreferences.getString("UserID",null));


        userId= sharedPreferences.getString("UserID",null);


        exercisenameTextView = (TextView)findViewById(R.id.NewExerciseNameTextView);
        exercisenameTextView.setText(exercise.getName());

        muscleTextView = (TextView)findViewById(R.id.NewExerceMuscleTextView);
        muscleTextView.setText(exercise.getMuscle());

        repetitionsEditText = (EditText) findViewById(R.id.NewExerciseRecRepNumber);
        repetitionsEditText.setHint(exercise.getRecommendedRepetitions()+"");

        setsEdittext = (EditText) findViewById(R.id.NewExerciseRecSetsNumber);
        setsEdittext.setHint(exercise.getRecommendedSets()+"");

        dateFormat = DateFormat.getDateInstance();
        dateTime = Calendar.getInstance();
        dateTextView = (TextView)findViewById(R.id.dateTextViewValue);
        dateTextView.setText(dateFormat.format(dateTime.getTime()));
        dateTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                onclickdatePicker();
                }
        });







        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Exercises");


        add = (Button)findViewById(R.id.addExerciseToMyPlans);
        add.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {

                        int repetitions;
                        if(repetitionsEditText.getText().toString().length()!=0){
                            repetitions = Integer.parseInt( repetitionsEditText.getText().toString() );
                        }
                        else{
                            repetitions = exercise.getRecommendedRepetitions();
                        }
                        int sets;
                        if(setsEdittext.getText().toString().length()!=0){
                            sets = Integer.parseInt(setsEdittext.getText().toString());
                        }
                        else{
                            sets = exercise.getRecommendedSets();
                        }



                        PlannedExercise plannedExercise = new PlannedExercise(exerciseKey, dateTime.getTime(),repetitions,sets,0,false,userId,0);
                        mMessagesDatabaseReference2 = mFirebaseDatabase.getReference();
                        mMessagesDatabaseReference2.child("PlannedExercises").child(exerciseKey+userId+Calendar.getInstance().getTime().toString()).setValue(plannedExercise);


                        Toast.makeText(NewPlannedExercise.this, "Exercise added to MyPlans!", Toast.LENGTH_LONG).show();
                        finish();

                    }}
        );
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateTime.set(Calendar.YEAR,year);
            dateTime.set(Calendar.MONTH,month);
            dateTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            Log.d(TAG, "............"+dateTime.get(Calendar.DAY_OF_WEEK));

            dateTextView.setText(dateFormat.format(dateTime.getTime()));
        }
    };

    private void onclickdatePicker(){
        new DatePickerDialog(this, d ,dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();

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
    public static Intent getStartIntent(Context context, Exercise exercise) {
        return new Intent(context, NewPlannedExercise.class)
                .putExtra(EXERCISE_OBJ_KEY, exercise);
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

                    Exercise exerciseL = dataSnapshot.getValue(Exercise.class);
                    Log.d(TAG,"............"+exerciseL.getName());
                    if(exerciseL.getName()!=null){
                        if (exerciseL.getName().equals(exercise.getName())){
                            exerciseKey = dataSnapshot.getKey();
                            Log.d(TAG,"exercise key is ............."+exerciseKey);
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
