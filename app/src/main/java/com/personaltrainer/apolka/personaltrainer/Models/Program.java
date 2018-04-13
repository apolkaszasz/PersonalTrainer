package com.personaltrainer.apolka.personaltrainer.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Program  implements Serializable {
    private String Name;
    private String Description;
    private int DurationInWeeks;
    private List<Integer> DaysListPerWeek;  //[0,1,1,1,0,1,1] for ex
    private List<ExerciseDay> exerciseList;
    private String PhotoUrl;

    public Program(){}
    public Program(String name, String description, int durationInWeeks, List<Integer> daysListPerWeek, List<ExerciseDay> exerciseList){
        this.Name = name;
        this.Description = description;
        this.DurationInWeeks = durationInWeeks;
        this.DaysListPerWeek = daysListPerWeek;
        this.exerciseList = exerciseList;


    }

    public String getName(){return this.Name;}
    public String getDescription(){return  this.Description;}
    public int getDurationInWeeks(){return this.DurationInWeeks;}
    public List<Integer> getDaysListPerWeek(){return this.DaysListPerWeek;}
    public List<ExerciseDay> getExerciseList(){return this.exerciseList;}
    public List<String> getExercisesByDayId(int dayId){
        for(ExerciseDay exday:this.exerciseList){
            if (exday.getDayId() == dayId){
                return exday.getExercises();
            }
        }
        return new ArrayList<>();
    }
    public String getPhotoUrl(){return this.PhotoUrl;}

    public void setName(String name){this.Name = name;}
    public void setDescription(String description){this.Description = description;}
    public void setDurationInWeeks(int durationInWeeks){this.DurationInWeeks = durationInWeeks;}
    public void setDaysListPerWeek(List<Integer> daysListPerWeek){this.DaysListPerWeek = daysListPerWeek;}
    public void setExerciseList(List<ExerciseDay> exerciseList){this.exerciseList = exerciseList;}
    public void setPhotoUrl(String photoUrl){this.PhotoUrl = photoUrl;}

}

