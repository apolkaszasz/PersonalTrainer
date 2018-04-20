package com.personaltrainer.apolka.personaltrainer.Models;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;


public class PlannedExercise {

    private String exerciseName;
    private Date date;
    private int plannedRepetitions;
    private int plannedSets;
    private int plannedRepetitionsToIncreaseWith;
    private boolean isPartOfaProgram;
    private FirebaseUser user;

    public PlannedExercise(){};
    public PlannedExercise(String exercise, Date date, int plannedRepetitions, int plannedSets, int plannedRepetitionsToIncreaseWith,boolean isPartOfaProgram, FirebaseUser user){
        this.exerciseName = exercise;
        this.date = date;
        this.plannedRepetitions = plannedRepetitions;
        this.plannedSets = plannedSets;
        this.plannedRepetitionsToIncreaseWith = plannedRepetitionsToIncreaseWith;
        this.isPartOfaProgram = isPartOfaProgram;
        this.user = user;
    }

    public String getExercise(){return this.exerciseName;}
    public Date getDate(){return  this.date;}
    public int getPlannedRepetitions(){return this.plannedRepetitions;}
    public int getPlannedSets(){return this.plannedSets;}
    public int getPlannedRepetitionsToIncreaseWith(){return this.plannedRepetitionsToIncreaseWith;}
    public boolean getIsPartOfaProgram(){return this.isPartOfaProgram;}
    public FirebaseUser getUser(){return this.user;}


    public void setExercise(String exercise){this.exerciseName = exercise;}
    public void setDate(Date date){this.date =date;}
    public void setPlannedRepetitions(int plannedRepetitions){this.plannedRepetitions = plannedRepetitions;}
    public void setPlannedSets(int plannedSets){this.plannedSets = plannedSets;}
    public void setPlannedRepetitionsToIncreaseWith(int plannedRepetitionsToIncreaseWith){this.plannedRepetitionsToIncreaseWith = plannedRepetitionsToIncreaseWith;}
    public void setIsPartOfaProgram(boolean isPartOfaProgram){this.isPartOfaProgram = isPartOfaProgram;}
    public void setUser(FirebaseUser user){this.user = user;}
}
