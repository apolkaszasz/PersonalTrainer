package com.personaltrainer.apolka.personaltrainer.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.NavigationBarFragments.MyPlansFragment;
import com.personaltrainer.apolka.personaltrainer.PlannedProgramItem;
import com.personaltrainer.apolka.personaltrainer.ProgramItem;
import com.personaltrainer.apolka.personaltrainer.R;
import com.personaltrainer.apolka.personaltrainer.TabBarFragments.WorkoutsExercisesFragment;

import java.util.List;



public class ExerciseAdapter extends ArrayAdapter<Exercise> {


    public ExerciseAdapter(@NonNull WorkoutsExercisesFragment context, int resource, List<Exercise> objects) {
        super(context.getActivity(), resource, objects);
    }
    public ExerciseAdapter(@NonNull MyPlansFragment context, int resource, List<Exercise> objects) {
        super(context.getActivity(), resource, objects);
    }
    public ExerciseAdapter(@NonNull ProgramItem context, int resource, List<Exercise> objects) {
        super(context, resource, objects);
    }
    public ExerciseAdapter(@NonNull PlannedProgramItem context, int resource, List<Exercise> objects) {
        super(context, resource, objects);
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
        if(exercise.getPhotoUrl() !=null){
            Glide.with(imageView.getContext())
                    .load(exercise.getPhotoUrl())
                    .into(imageView);
        }
        else
            imageView.setImageResource(R.drawable.ikon);

        nameTextView.setText(exercise.getName());
        muscleTextView.setText(exercise.getMuscle().toString());

        return convertView;
    }
}
