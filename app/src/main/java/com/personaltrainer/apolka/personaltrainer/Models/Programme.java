package com.personaltrainer.apolka.personaltrainer.Models;

import com.personaltrainer.apolka.personaltrainer.Exerc;

import java.util.List;

/**
 * Created by Api on 3/8/2018.
 */

public class Programme {
    private String Name;
    private String Description;
    private int DurationInWeeks;
    private int[] DaysListPerWeek;  //[0,1,1,1,0,1,1] for ex
    private String[][] ExerciseList;

    public Programme(String name, String description, int durationInWeeks, int[] daysListPerWeek, String[][] exerciseList){
        this.Name = name;
        this.Description = description;
        this.DurationInWeeks = durationInWeeks;
        this.DaysListPerWeek = daysListPerWeek;
        this.ExerciseList = exerciseList;
    }

    public String getName(){return this.Name;}
    public String getDescription(){return  this.Description;}
    public int getDurationInWeeks(){return this.DurationInWeeks;}
    public int[] getDaysListPerWeek(){return this.DaysListPerWeek;}
    public String[][] getExerciseList(){return this.ExerciseList;}

    public void setName(String name){this.Name = name;}
    public void setDescription(String description){this.Description = description;}
    public void setDurationInWeeks(int durationInWeeks){this.DurationInWeeks = durationInWeeks;}
    public void setDaysListPerWeek(int[] daysListPerWeek){this.DaysListPerWeek = daysListPerWeek;}
    public void setExerciseList(String[][] exerciseList){this.ExerciseList = exerciseList;}

}
