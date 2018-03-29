package com.personaltrainer.apolka.personaltrainer.Models;

import java.io.Serializable;


public class Exercise implements Serializable {

    private String Name;
    private String Muscle;
    private int RecommendedRepetitions;
    private int RecommendedSets;
    private int RecommendedRepetitionsToIncreaseWith;
    private String Description;
    private String PhotoUrl;

    public Exercise(){};
    public Exercise(String name, String muscle, int recommendedRepetitions, int recommendedSets, int recommendedRepetitionsToIncreaseWith, String description, String photoUrl){
        this.Name = name;
        this.Muscle = muscle;
        this.RecommendedRepetitions = recommendedRepetitions;
        this.RecommendedSets = recommendedSets;
        this.RecommendedRepetitionsToIncreaseWith = recommendedRepetitionsToIncreaseWith;
        this.Description = description;
        this.PhotoUrl = photoUrl;

    }

    public String getName(){return this.Name;}
    public String getMuscle(){return this.Muscle;}
    public int getRecommendedRepetitions(){return this.RecommendedRepetitions;}
    public int getRecommendedSets(){return this.RecommendedSets;}
    public int getRecommendedRepetitionsToIncreaseWith(){return this.RecommendedRepetitionsToIncreaseWith;}
    public String getDescription(){return this.Description;}
    public String getPhotoUrl(){return this.PhotoUrl;}


    public void setName(String name){this.Name = name;}
    public void setMuscle(String muscle){this.Muscle = muscle;}
    public void setRecommendedRepetitions(int recommendedRepetitions){this.RecommendedRepetitions = recommendedRepetitions;}
    public void setRecommendedSets(int recommendedSets){this.RecommendedSets = recommendedSets;}
    public void setRecommendedRepetitionsToIncreaseWith(int recommendedRepetitionsToIncreaseWith){this.RecommendedRepetitionsToIncreaseWith = recommendedRepetitionsToIncreaseWith;}
    public void setDescription(String description){this.Description = description;}
    public void setPhotoUrl(String photoUrl){this.PhotoUrl = photoUrl;}





}
