package com.personaltrainer.apolka.personaltrainer.Models;

import java.io.Serializable;
import java.util.List;

public class ExerciseDay implements Serializable {

    private int dayId;
    private List<String> exercises;

    ExerciseDay(){}
    ExerciseDay(int dayId, List<String> exerciseList){
        this.dayId = dayId;
        this.exercises = exerciseList;
    }

    public int getDayId(){return this.dayId;}
    public List<String> getExercises(){return  this.exercises;}

    public void setDayId(int dayId){this.dayId = dayId;}
    public void setExercises(List<String> exercises){this.exercises = exercises;}
}
