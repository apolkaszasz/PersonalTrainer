package com.personaltrainer.apolka.personaltrainer.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;

import java.util.List;

/**
 * Created by Api on 3/25/2018.
 */

public class PlannedExerciseAdapter extends ArrayAdapter<PlannedExercise>{

    public PlannedExerciseAdapter(Fragment context,int resource, List<PlannedExercise> objects){
        super(context.getActivity(),resource,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

       if(convertView == null) {
           //convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_planned_exercise, parent, false);

       }



       return convertView;
    }

}
