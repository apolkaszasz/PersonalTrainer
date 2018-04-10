package com.personaltrainer.apolka.personaltrainer.Models;

import java.util.List;

public class ExerciseDay {

    private String dayId;
    private List<String> exercises;

    ExerciseDay(){}
    ExerciseDay(String dayId, List<String> exerciseList){
        this.dayId = dayId;
        this.exercises = exerciseList;
    }

    public String getDayId(){return this.dayId;}
    public List<String> getExercises(){return  this.exercises;}

    public void setDayId(String dayId){this.dayId = dayId;}
    public void setExercises(List<String> exercises){this.exercises = exercises;}
}
