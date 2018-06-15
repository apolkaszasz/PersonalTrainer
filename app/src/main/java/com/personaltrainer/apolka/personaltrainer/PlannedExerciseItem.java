package com.personaltrainer.apolka.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;

import java.text.DateFormat;
import java.util.Calendar;

public class PlannedExerciseItem extends YouTubeBaseActivity {

    private Exercise exercise;
    private PlannedExercise plannedExercise ;
    private static final String TAG = "PlannedExerciseItem";
    private static final String EXERCISE_OBJ_KEY = "Exercise_Obj_Key";
    private static final String PLANNED_EXERCISE_OBJ_KEY = "Planned_exxercise_Obj_key";

    private String key;

    private TextView NameTextView;
    private TextView MuscleTextView;
    private TextView RepTextViewNumber;
    private TextView SetsTextViewNumber;
    private TextView DescriptionTextView;
    private CheckBox completedCheckBox;
    private Button okButton;


    private DateFormat dateFormat;
    private Calendar dateTime;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mPlannedExercisesDatabaseReference;
    private ChildEventListener mChildEventListener;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;


    public static Intent getStartIntent(Context context, Exercise exercise, PlannedExercise plannedExercise) {
        return new Intent(context, PlannedExerciseItem.class)
                .putExtra(EXERCISE_OBJ_KEY,exercise)
                .putExtra(PLANNED_EXERCISE_OBJ_KEY, plannedExercise);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_exercise_item);

        Intent i  = getIntent();




        dateFormat = DateFormat.getDateInstance();
        dateTime = Calendar.getInstance();


        mFirebaseDatabase = FirebaseDatabase.getInstance();


        mPlannedExercisesDatabaseReference = mFirebaseDatabase.getReference().child("PlannedExercises").child("");

        attachDatabaseReadListener();



        exercise = (Exercise)i.getSerializableExtra(EXERCISE_OBJ_KEY);
        plannedExercise = (PlannedExercise)i.getSerializableExtra(PLANNED_EXERCISE_OBJ_KEY);
        Log.d(TAG,"......"  + exercise.getName() + "   "+ plannedExercise.getExercise());
        //Log.d(TAG,"......"  + exercise.getName() + "   "+ plannedExercise.getExercise());
        //Log.d(TAG,"......"  + exercise.getName() + "   "+ plannedExercise.getExercise());

        NameTextView = (TextView)findViewById(R.id.ExerciseNameTextViewMyPlans);
        NameTextView.setText(exercise.getName());

        MuscleTextView =(TextView)findViewById(R.id.ExerceMuscleTextViewMyPlans);
        MuscleTextView.setText(exercise.getMuscle());

        okButton = (Button)findViewById(R.id.okButtonExercise);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update the planned exercise

                if (completedCheckBox.isEnabled()==false){
                    finish();

                }else{
                    if (key == null){
                        Log.d(TAG, plannedExercise.toString()+"...............................................................");
                        PlannedExercise newPlannedExercise = new PlannedExercise(plannedExercise.getExercise(), plannedExercise.getDate(), plannedExercise.getPlannedRepetitions(), plannedExercise.getPlannedSets(),plannedExercise.getPlannedRepetitionsToIncreaseWith(),true, plannedExercise.getUser(),plannedExercise.getIsCompleted());
                        key = newPlannedExercise.getExercise()+newPlannedExercise.getUser()+Calendar.getInstance().getTime().toString();
                        if (newPlannedExercise.getIsPartOfaProgram() == true && newPlannedExercise.getIsCompleted() ==1){
                            mPlannedExercisesDatabaseReference.child(key).setValue(newPlannedExercise);
                            Toast.makeText(PlannedExerciseItem.this, "Planned exercise's status changed!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            finish();
                        }
                        //mPlannedExercisesDatabaseReference.child(key).setValue(newPlannedExercise);
                    }
                    else{
                        mPlannedExercisesDatabaseReference.child(key).setValue(plannedExercise);

                        Toast.makeText(PlannedExerciseItem.this, "Planned exercise's status changed!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }

            }
        });


        completedCheckBox = (CheckBox)findViewById(R.id.checkBox);
        if (plannedExercise.getIsCompleted() == 1){
            Log.d(TAG,"........."+plannedExercise.getIsCompleted());
            completedCheckBox.setChecked(true);
            completedCheckBox.setEnabled(false);
        }
        completedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if (isChecked == true){

                    plannedExercise.setCompleted(1);

                }
                else{
                    plannedExercise.setCompleted(0);
                }
            }
        });

        RepTextViewNumber=(TextView)findViewById(R.id.ExerciseRecRepNumberMyPlans);
        RepTextViewNumber.setText(plannedExercise.getPlannedRepetitions()+"");

        SetsTextViewNumber = (TextView)findViewById(R.id.ExerciseRecSetsNumberMyPlans);
        SetsTextViewNumber.setText(plannedExercise.getPlannedSets()+"");

        DescriptionTextView = (TextView)findViewById(R.id.DescriptionValueMyPlans);
        DescriptionTextView.setText(exercise.getDescription());

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeVideo);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(exercise.getVideoUrl());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.initialize("AIzaSyDHwoaS9Go7vu-fXFAtIBepN7qPa9C0B2o",onInitializedListener);

    }

    private void detachDatabaseReadListener(){
        if (mChildEventListener != null){

            mPlannedExercisesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener =null;
        }
    }

    private void attachDatabaseReadListener(){
        if(mChildEventListener == null){
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    PlannedExercise pE = dataSnapshot.getValue(PlannedExercise.class);
                    if (pE.getUser().equals(plannedExercise.getUser())){

                        if(pE.getIsPartOfaProgram() == true){
                            if(dateFormat.format(pE.getDate()).equals(dateFormat.format(plannedExercise.getDate()))){
                                if(pE.getExercise().equals(plannedExercise.getExercise())){
                                    if (pE.getPlannedRepetitions() == plannedExercise.getPlannedRepetitions() && pE.getIsPartOfaProgram()==plannedExercise.getIsPartOfaProgram() && pE.getPlannedSets() ==plannedExercise.getPlannedSets() && pE.getPlannedRepetitionsToIncreaseWith() == plannedExercise.getPlannedRepetitionsToIncreaseWith()){

                                        if (pE.getIsCompleted() == 1){
                                            completedCheckBox.setChecked(true);
                                            completedCheckBox.setEnabled(false);
                                        }
                                        Log.d(TAG, "here.......00");
                                        key = dataSnapshot.getKey();

                                    }
                                }
                            }

                        }else{
                            if(dateFormat.format(pE.getDate()).equals(dateFormat.format(plannedExercise.getDate()))){
                                if(pE.getExercise().equals(plannedExercise.getExercise())){
                                    if (pE.getPlannedRepetitions() == plannedExercise.getPlannedRepetitions() && pE.getIsPartOfaProgram()==plannedExercise.getIsPartOfaProgram() && pE.getPlannedSets() ==plannedExercise.getPlannedSets() && pE.getPlannedRepetitionsToIncreaseWith() == plannedExercise.getPlannedRepetitionsToIncreaseWith()){

                                        Log.d(TAG, "here.......00");
                                        key = dataSnapshot.getKey();

                                    }
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
            mChildEventListener = null;
        }
    }
    @Override
    public void onPause(){
        super.onPause();


        detachDatabaseReadListener();

    }
    @Override
    public void onResume(){
        super.onResume();


    }
}
