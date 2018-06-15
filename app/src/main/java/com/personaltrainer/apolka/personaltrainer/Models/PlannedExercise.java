package com.personaltrainer.apolka.personaltrainer.Models;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.Date;


public class PlannedExercise  implements Serializable {

    private String exerciseName;
    private Date date;
    private int plannedRepetitions;
    private int plannedSets;
    private int plannedRepetitionsToIncreaseWith;
    private boolean isPartOfaProgram;
    private int isCompleted;
    private String userId;

    public PlannedExercise(){};
    public PlannedExercise(String exercise, Date date, int plannedRepetitions, int plannedSets, int plannedRepetitionsToIncreaseWith,boolean isPartOfaProgram, String userId,int isCompleted){
        this.exerciseName = exercise;
        this.date = date;
        this.plannedRepetitions = plannedRepetitions;
        this.plannedSets = plannedSets;
        this.plannedRepetitionsToIncreaseWith = plannedRepetitionsToIncreaseWith;
        this.isPartOfaProgram = isPartOfaProgram;
        this.isCompleted = isCompleted;
        this.userId = userId;
    }

    public String getExercise(){return this.exerciseName;}
    public Date getDate(){return  this.date;}
    public int getPlannedRepetitions(){return this.plannedRepetitions;}
    public int getPlannedSets(){return this.plannedSets;}
    public int getPlannedRepetitionsToIncreaseWith(){return this.plannedRepetitionsToIncreaseWith;}
    public boolean getIsPartOfaProgram(){return this.isPartOfaProgram;}
    public int getIsCompleted(){return this.isCompleted;}
    public String getUser(){return this.userId;}


    public void setExercise(String exercise){this.exerciseName = exercise;}
    public void setDate(Date date){this.date =date;}
    public void setPlannedRepetitions(int plannedRepetitions){this.plannedRepetitions = plannedRepetitions;}
    public void setPlannedSets(int plannedSets){this.plannedSets = plannedSets;}
    public void setPlannedRepetitionsToIncreaseWith(int plannedRepetitionsToIncreaseWith){this.plannedRepetitionsToIncreaseWith = plannedRepetitionsToIncreaseWith;}
    public void setIsPartOfaProgram(boolean isPartOfaProgram){this.isPartOfaProgram = isPartOfaProgram;}
    public void setCompleted(int isCompleted){this.isCompleted = isCompleted;}
    public void setUser(String user){this.userId = user;}
}
