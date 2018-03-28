package com.personaltrainer.apolka.personaltrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.personaltrainer.apolka.personaltrainer.Models.Exercise;

public class ExerciseItem extends AppCompatActivity {

    private static final String TAG = "ExerciseItem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_item);

        Intent i = getIntent();
        Exercise exercise = (Exercise) i.getSerializableExtra("EcerciseObject");
        Log.d(TAG,"................"+exercise.getName());

    }
}
