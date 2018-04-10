package com.personaltrainer.apolka.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.personaltrainer.apolka.personaltrainer.Models.Exercise;
import com.personaltrainer.apolka.personaltrainer.Models.ExerciseDay;
import com.personaltrainer.apolka.personaltrainer.Models.Program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramItem extends AppCompatActivity {

    private static final String TAG = "ProgramItem";



    private TextView programName;
    private TextView programDescription;
    private TextView programDurationInWeeks;
    private TextView programWorkoutdays;


    private static final String PROGRAM_OBJ_KEY = "Program_Obj_Key";
    public static Intent getStartIntent(Context context, Program program) {
        return new Intent(context, ProgramItem.class)
                .putExtra(PROGRAM_OBJ_KEY, program);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_item);

        Intent i = getIntent();
        Program program = (Program) i.getSerializableExtra(PROGRAM_OBJ_KEY);

        List<String> dayList =  new ArrayList<String>();
        List<String> workoutDayList =  new ArrayList<String>();

        dayList.add("Monday");
        dayList.add("Tuesday");
        dayList.add("Wednesday");
        dayList.add("Thursday");
        dayList.add("Friday");
        dayList.add("Saturday");
        dayList.add("Sunday");

        for (int j =0;j<7;j++){
            if (program.getDaysListPerWeek().get(j)==1){
                workoutDayList.add(dayList.get(j));
            }
        }

        programName = (TextView)findViewById(R.id.ProgramNameTextView);
        programName.setText(program.getName());

        programDescription = (TextView)findViewById(R.id.ProgramDescriptionValue);
        programDescription.setText(program.getDescription());

        programDurationInWeeks = (TextView)findViewById(R.id.ProgramDurationInWeeksTextViewValue);
        programDurationInWeeks.setText(program.getDurationInWeeks()+"");

        programWorkoutdays = (TextView)findViewById(R.id.ProgramWorkoutDaysTextViewValue);
        programWorkoutdays.setText(TextUtils.join(", ",workoutDayList));

        Log.d(TAG, "...........Name is: "+program.getName());
        Log.d(TAG,"Description is: "+program.getDescription());
        Log.d(TAG, "Duration in weeks"+program.getDurationInWeeks());
        List<Integer> daysListPerWeek = program.getDaysListPerWeek();
        List<ExerciseDay> exerciseList= program.getExerciseList();
        Log.d(TAG, "Days: "+daysListPerWeek.get(0)+" "+daysListPerWeek.get(1)+" "+daysListPerWeek.get(2)+" "+daysListPerWeek.get(3)+" "
        +daysListPerWeek.get(4)+" "+daysListPerWeek.get(5)+" "+daysListPerWeek.get(6)+" ");

        Log.d(TAG,"Day "+exerciseList.get(0).getDayId()+"ex 1 "+exerciseList.get(0).getExercises().get(0));
    }
}
