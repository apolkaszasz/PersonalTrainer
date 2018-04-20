package com.personaltrainer.apolka.personaltrainer.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Program  implements Serializable {
    private String name;
    private String description;
    private int durationInWeeks;
    private List<Integer> daysListPerWeek;  //[0,1,1,1,0,1,1] for ex
    private List<ExerciseDay> exerciseList;
    private String photoUrl;

    public Program(){}
    public Program(String name, String description, int durationInWeeks, List<Integer> daysListPerWeek, List<ExerciseDay> exerciseList){
        this.name = name;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
        this.daysListPerWeek = daysListPerWeek;
        this.exerciseList = exerciseList;


    }

    public String getName(){return this.name;}
    public String getDescription(){return  this.description;}
    public int getDurationInWeeks(){return this.durationInWeeks;}
    public List<Integer> getDaysListPerWeek(){return this.daysListPerWeek;}
    public List<ExerciseDay> getExerciseList(){return this.exerciseList;}
    public List<String> getExercisesByDayId(int dayId){
        for(ExerciseDay exday:this.exerciseList){
            if (exday.getDayId() == dayId){
                return exday.getExercises();
            }
        }
        return new ArrayList<>();
    }
    public String getPhotoUrl(){return this.photoUrl;}

    public void setName(String name){this.name = name;}
    public void setDescription(String description){this.description = description;}
    public void setDurationInWeeks(int durationInWeeks){this.durationInWeeks = durationInWeeks;}
    public void setDaysListPerWeek(List<Integer> daysListPerWeek){this.daysListPerWeek = daysListPerWeek;}
    public void setExerciseList(List<ExerciseDay> exerciseList){this.exerciseList = exerciseList;}
    public void setPhotoUrl(String photoUrl){this.photoUrl = photoUrl;}

}

