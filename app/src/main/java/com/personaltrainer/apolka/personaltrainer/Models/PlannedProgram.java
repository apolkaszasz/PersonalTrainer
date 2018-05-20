package com.personaltrainer.apolka.personaltrainer.Models;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class PlannedProgram implements Serializable{
    private String programName;
    private Date startDate;
    private String userId;

    public PlannedProgram(){}
    public PlannedProgram(String program, Date startDate,   String  user){
        this.programName = program;
        this.startDate = startDate;

        this.userId = user;
    }

    public String getProgram(){return this.programName;}
    public Date getStartDate(){return this.startDate;}
    public String getUser(){return this.userId;}

    public void setProgram(String program){this.programName = program;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}
    public void setUser(String  user) {this.userId = user;}
}
