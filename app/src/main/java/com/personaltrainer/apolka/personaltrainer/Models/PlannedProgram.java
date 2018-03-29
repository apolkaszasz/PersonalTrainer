package com.personaltrainer.apolka.personaltrainer.Models;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.List;


public class PlannedProgram {
    private Program Program;
    private Date StartDate;
    private int PlannedDays;
    private int PlannedRestDays;
    private List<PlannedExercise> PlannedExerciseList;
    private FirebaseUser User;

    public PlannedProgram(Program program, Date startDate, int plannedDays, int plannedRestDays, List<PlannedExercise> plannedExerciseList, FirebaseUser user){
        this.Program = program;
        this.StartDate = startDate;
        this.PlannedDays = plannedDays;
        this.PlannedRestDays = plannedRestDays;
        this.PlannedExerciseList = plannedExerciseList;
        this.User = user;
    }

    public Program getProgram(){return this.Program;}
    public Date getStartDate(){return this.StartDate;}
    public int getPlannedDays(){return this.PlannedDays;}
    public int getPlannedRestDays(){return this.PlannedRestDays;}
    public List<PlannedExercise> getPlannedExerciseList(){return this.PlannedExerciseList;}
    public FirebaseUser getUser(){return this.User;}

    public void setProgram(Program program){this.Program = program;}
    public void setStartDate(Date startDate) {this.StartDate = startDate;}
    public void setPlannedDays(int plannedDays) {this.PlannedDays = plannedDays;}
    public void setPlannedRestDays(int plannedRestDays) {this.PlannedRestDays = plannedRestDays;}
    public void setPlannedExerciseList(List<PlannedExercise> plannedExerciseList) {this.PlannedExerciseList = plannedExerciseList;}
    public void setUser(FirebaseUser user) {this.User = user;}
}
