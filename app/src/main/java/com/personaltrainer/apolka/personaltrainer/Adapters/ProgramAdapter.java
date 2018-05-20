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
import com.personaltrainer.apolka.personaltrainer.Models.Program;
import com.personaltrainer.apolka.personaltrainer.NavigationBarFragments.MyPlansFragment;
import com.personaltrainer.apolka.personaltrainer.R;
import com.personaltrainer.apolka.personaltrainer.TabBarFragments.ProgramsFragment;
import com.personaltrainer.apolka.personaltrainer.TabBarFragments.WorkoutsExercisesFragment;

import java.util.List;

/**
 * Created by Api on 4/9/2018.
 */

public class ProgramAdapter extends ArrayAdapter<Program> {


    public ProgramAdapter(@NonNull ProgramsFragment context, int resource, List<Program> objects) {
        super(context.getActivity(), resource, objects);
    }
    public ProgramAdapter(@NonNull MyPlansFragment context, int resource, List<Program> objects) {
        super(context.getActivity(), resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.grid_item_program, parent, false);

        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewProgram);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.txtNameProgram);



        Program program = getItem(position);

        nameTextView.setVisibility(View.VISIBLE);

        if(program.getPhotoUrl() !=null){
            Glide.with(imageView.getContext())
                    .load(program.getPhotoUrl())
                    .into(imageView);
        }
        else
            imageView.setImageResource(R.drawable.nopicture);

        nameTextView.setText(program.getName());

        return convertView;
    }
}
