package com.personaltrainer.apolka.personaltrainer;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.grid_item_exercise, parent, false);

        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.txtName);
        TextView muscleTextView = (TextView) convertView.findViewById(R.id.txtMuscle);


        Exercise exercise = getItem(position);

        nameTextView.setVisibility(View.VISIBLE);
        muscleTextView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.q3varo58nxkz);
        nameTextView.setText(exercise.getName());
        muscleTextView.setText(exercise.getMuscle().toString());

        return convertView;
    }
}
