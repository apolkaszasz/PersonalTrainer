package com.personaltrainer.apolka.personaltrainer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.personaltrainer.apolka.personaltrainer.Models.Exercise;

import java.util.List;

/**
 * Created by Api on 3/15/2018.
 */

public class ExerciseAdapter extends ArrayAdapter<Exercise> {


    public ExerciseAdapter(@NonNull WorkoutsExercisesFragment context, int resource, List<Exercise> objects) {
        super(context.getActivity(), resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_exercise, parent, false);

        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.NameTextView);
        TextView muscleTextView = (TextView) convertView.findViewById(R.id.MuscleTextView);


        Exercise exercise = getItem(position);

        nameTextView.setVisibility(View.VISIBLE);
        muscleTextView.setVisibility(View.VISIBLE);
        nameTextView.setText(exercise.getName());
        muscleTextView.setText(exercise.getMuscle().toString());

        return convertView;
    }
}
