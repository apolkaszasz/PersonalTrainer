package com.personaltrainer.apolka.personaltrainer.Models;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.List;


public class PlannedProgram {
    private String programName;
    private Date startDate;
    private List<ExerciseDay> plannedStarterExerciseList;
    private FirebaseUser user;

    public PlannedProgram(String program, Date startDate,  List<ExerciseDay> plannedStarterExerciseList, FirebaseUser user){
        this.programName = program;
        this.startDate = startDate;
        this.plannedStarterExerciseList = plannedStarterExerciseList;
        this.user = user;
    }

    public String getProgram(){return this.programName;}
    public Date getStartDate(){return this.startDate;}
    public List<ExerciseDay> getPlannedStarterExerciseList(){return this.plannedStarterExerciseList;}
    public FirebaseUser getUser(){return this.user;}

    public void setProgram(String program){this.programName = program;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}
    public void setPlannedStarterExerciseList(List<ExerciseDay> plannedStarterExerciseList) {this.plannedStarterExerciseList = plannedStarterExerciseList;}
    public void setUser(FirebaseUser user) {this.user = user;}
}
