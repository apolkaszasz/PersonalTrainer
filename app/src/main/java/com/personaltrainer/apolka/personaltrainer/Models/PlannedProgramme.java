package com.personaltrainer.apolka.personaltrainer.Models;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.List;

/**
 * Created by Api on 3/8/2018.
 */

public class PlannedProgramme {
    private Programme Programme;
    private Date StartDate;
    private int PlannedDays;
    private int PlannedRestDays;
    private List<PlannedExercise> PlannedExerciseList;
    private FirebaseUser User;

    PlannedProgramme(Programme programme, Date startDate, int plannedDays, int plannedRestDays, List<PlannedExercise> plannedExerciseList, FirebaseUser user){
        this.Programme = programme;
        this.StartDate = startDate;
        this.PlannedDays = plannedDays;
        this.PlannedRestDays = plannedRestDays;
        this.PlannedExerciseList = plannedExerciseList;
        this.User = user;
    }

    public Programme getProgramme(){return this.Programme;}
    public Date getStartDate(){return this.StartDate;}
    public int getPlannedDays(){return this.PlannedDays;}
    public int getPlannedRestDays(){return this.PlannedRestDays;}
    public List<PlannedExercise> getPlannedExerciseList(){return this.PlannedExerciseList;}
    public FirebaseUser getUser(){return this.User;}

    public void setProgramme(Programme programme){this.Programme = programme;}
    public void setStartDate(Date startDate) {this.StartDate = startDate;}
    public void setPlannedDays(int plannedDays) {this.PlannedDays = plannedDays;}
    public void setPlannedRestDays(int plannedRestDays) {this.PlannedRestDays = plannedRestDays;}
    public void setPlannedExerciseList(List<PlannedExercise> plannedExerciseList) {this.PlannedExerciseList = plannedExerciseList;}
    public void setUser(FirebaseUser user) {this.User = user;}
}
