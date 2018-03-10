package com.personaltrainer.apolka.personaltrainer.Models;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by Api on 3/8/2018.
 */

public class PlannedExercise {
    private Exercise Exercise;
    private Date Date;
    private int PlannedRepetitions;
    private int PlannedSets;
    private int PlannedRepetitionsToIncreaseWith;
    private FirebaseUser User;

    public PlannedExercise(Exercise exercise, Date date, int plannedRepetitions, int plannedSets, int plannedRepetitionsToIncreaseWith, FirebaseUser user){
        this.Exercise = exercise;
        this.Date = date;
        this.PlannedRepetitions = plannedRepetitions;
        this.PlannedSets = plannedSets;
        this.PlannedRepetitionsToIncreaseWith = plannedRepetitionsToIncreaseWith;
        this.User = user;
    }

    public Exercise getExercise(){return this.Exercise;}
    public Date getDate(){return  this.Date;}
    public int getPlannedRepetitions(){return this.PlannedRepetitions;}
    public int getPlannedSets(){return this.PlannedSets;}
    public int getPlannedRepetitionsToIncreaseWith(){return this.PlannedRepetitionsToIncreaseWith;}
    public FirebaseUser getUser(){return this.User;}


    public void setExercise(Exercise exercise){this.Exercise = exercise;}
    public void setDate(Date date){this.Date =date;}
    public void setPlannedRepetitions(int plannedRepetitions){this.PlannedRepetitions = plannedRepetitions;}
    public void setPlannedSets(int plannedSets){this.PlannedSets = plannedSets;}
    public void setPlannedRepetitionsToIncreaseWith(int plannedRepetitionsToIncreaseWith){this.PlannedRepetitionsToIncreaseWith = plannedRepetitionsToIncreaseWith;}
    public void setUser(FirebaseUser user){this.User = user;}
}
