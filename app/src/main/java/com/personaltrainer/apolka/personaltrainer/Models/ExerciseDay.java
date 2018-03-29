package com.personaltrainer.apolka.personaltrainer.Models;

import java.util.List;

public class ExerciseDay {

    private String dayId;
    private List<String> exerciseList;

    ExerciseDay(){}
    ExerciseDay(String dayId, List<String> exerciseList){
        this.dayId = dayId;
        this.exerciseList = exerciseList;
    }

    public String getDayId(){return this.dayId;}
    public List<String> getExerciseList(){return  this.exerciseList;}

    public void setDayId(String dayId){this.dayId = dayId;}
    public void setExerciseList(List<String> exerciseList){this.exerciseList = exerciseList;}
}
