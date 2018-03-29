package com.personaltrainer.apolka.personaltrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.TextView;

import com.personaltrainer.apolka.personaltrainer.Models.Exercise;

public class ExerciseItem extends AppCompatActivity {

    private static final String TAG = "ExerciseItem";
    private TextView NameTextView;
    private TextView MuscleTextView;
    private TextView RecRepTextViewNumber;
    private TextView RecSetsTextViewNumber;
    private TextView DescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_item);

        Intent i = getIntent();
        Exercise exercise = (Exercise) i.getSerializableExtra("EcerciseObject");

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

    }
}
