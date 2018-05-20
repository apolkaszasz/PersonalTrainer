package com.personaltrainer.apolka.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;

public class PlannedExerciseItem extends AppCompatActivity {

    private Exercise exercise;
    private PlannedExercise plannedExercise ;
    private static final String TAG = "PlannedExerciseItem";
    private static final String EXERCISE_OBJ_KEY = "Exercise_Obj_Key";
    private static final String PLANNED_EXERCISE_OBJ_KEY = "Planned_exxercise_Obj_key";

    private TextView NameTextView;
    private TextView MuscleTextView;
    private TextView RepTextViewNumber;
    private TextView SetsTextViewNumber;
    private TextView DescriptionTextView;

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

        exercise = (Exercise)i.getSerializableExtra(EXERCISE_OBJ_KEY);
        plannedExercise = (PlannedExercise)i.getSerializableExtra(PLANNED_EXERCISE_OBJ_KEY);
        Log.d(TAG,"......"  + exercise.getName() + "   "+ plannedExercise.getExercise());
        //Log.d(TAG,"......"  + exercise.getName() + "   "+ plannedExercise.getExercise());
        //Log.d(TAG,"......"  + exercise.getName() + "   "+ plannedExercise.getExercise());

        NameTextView = (TextView)findViewById(R.id.ExerciseNameTextViewMyPlans);
        NameTextView.setText(exercise.getName());

        MuscleTextView =(TextView)findViewById(R.id.ExerceMuscleTextViewMyPlans);
        MuscleTextView.setText(exercise.getMuscle());

        RepTextViewNumber=(TextView)findViewById(R.id.ExerciseRecRepNumberMyPlans);
        RepTextViewNumber.setText(plannedExercise.getPlannedRepetitions()+"");

        SetsTextViewNumber = (TextView)findViewById(R.id.ExerciseRecSetsNumberMyPlans);
        SetsTextViewNumber.setText(plannedExercise.getPlannedSets()+"");

        DescriptionTextView = (TextView)findViewById(R.id.DescriptionValueMyPlans);
        DescriptionTextView.setText(exercise.getDescription());
    }
}
