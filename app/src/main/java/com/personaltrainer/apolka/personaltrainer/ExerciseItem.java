package com.personaltrainer.apolka.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;

import static android.provider.Contacts.SettingsColumns.KEY;
import static java.security.AccessController.getContext;

public class ExerciseItem extends YouTubeBaseActivity {

    private static final String TAG = "ExerciseItem";
    private static final String EXERCISE_OBJ_KEY = "Exercise_Obj_Key";
    private TextView NameTextView;
    private TextView MuscleTextView;
    private TextView RecRepTextViewNumber;
    private TextView RecSetsTextViewNumber;
    private TextView DescriptionTextView;
    private FloatingActionButton addButton;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    private Exercise exercise;
    public static Intent getStartIntent(Context context, Exercise exercise) {
        return new Intent(context, ExerciseItem.class)
                .putExtra(EXERCISE_OBJ_KEY, exercise);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_item);


        Intent i = getIntent();
        exercise = (Exercise)i.getSerializableExtra(EXERCISE_OBJ_KEY);


        NameTextView = (TextView)findViewById(R.id.ExerciseNameTextView);
        NameTextView.setText(exercise.getName());

        MuscleTextView =(TextView)findViewById(R.id.ExerceMuscleTextView);
        MuscleTextView.setText(exercise.getMuscle());

        RecRepTextViewNumber=(TextView)findViewById(R.id.ExerciseRecRepNumber);
        RecRepTextViewNumber.setText(exercise.getRecommendedRepetitions()+"");

        RecSetsTextViewNumber = (TextView)findViewById(R.id.ExerciseRecSetsNumber);
        RecSetsTextViewNumber.setText(exercise.getRecommendedSets()+"");

        DescriptionTextView = (TextView)findViewById(R.id.DescriptionValue);
        DescriptionTextView.setText(exercise.getDescription());

        addButton = (FloatingActionButton)findViewById(R.id.AddThisWorkoutToPlansFloatingButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                newPlannedExercise();
            }}
        );

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeVideo2);
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
    private void newPlannedExercise(){
        startActivity(NewPlannedExercise.getStartIntent(this, exercise));

    }


}
