package com.personaltrainer.apolka.personaltrainer.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.personaltrainer.apolka.personaltrainer.Models.PlannedExercise;
import com.personaltrainer.apolka.personaltrainer.NavigationBarFragments.MyPlansFragment;
import com.personaltrainer.apolka.personaltrainer.DetailActivities.NewPlannedExercise;

import java.util.List;

/**
 * Created by Api on 3/25/2018.
 */

public class PlannedExerciseAdapter extends ArrayAdapter<PlannedExercise>{

    public PlannedExerciseAdapter(MyPlansFragment context, int resource, List<PlannedExercise> objects){
        super(context.getActivity(),resource,objects);
    }

    public PlannedExerciseAdapter(NewPlannedExercise context, int resource, List<PlannedExercise> objects){
        super(context,resource,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

       if(convertView == null) {
           //convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_planned_exercise, parent, false);

       }



       return convertView;
    }

}
