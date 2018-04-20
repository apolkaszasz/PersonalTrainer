package com.personaltrainer.apolka.personaltrainer.Models;

import java.io.Serializable;


public class Exercise implements Serializable {

    private String name;
    private String muscle;
    private int recommendedRepetitions;
    private int recommendedSets;
    private int recommendedRepetitionsToIncreaseWith;
    private String description;
    private String photoUrl;

    public Exercise(){};
    public Exercise(String name, String muscle, int recommendedRepetitions, int recommendedSets, int recommendedRepetitionsToIncreaseWith, String description, String photoUrl){
        this.name = name;
        this.muscle = muscle;
        this.recommendedRepetitions = recommendedRepetitions;
        this.recommendedSets = recommendedSets;
        this.recommendedRepetitionsToIncreaseWith = recommendedRepetitionsToIncreaseWith;
        this.description = description;
        this.photoUrl = photoUrl;

    }

    public String getName(){return this.name;}
    public String getMuscle(){return this.muscle;}
    public int getRecommendedRepetitions(){return this.recommendedRepetitions;}
    public int getRecommendedSets(){return this.recommendedSets;}
    public int getRecommendedRepetitionsToIncreaseWith(){return this.recommendedRepetitionsToIncreaseWith;}
    public String getDescription(){return this.description;}
    public String getPhotoUrl(){return this.photoUrl;}


    public void setName(String name){this.name = name;}
    public void setMuscle(String muscle){this.muscle = muscle;}
    public void setRecommendedRepetitions(int recommendedRepetitions){this.recommendedRepetitions = recommendedRepetitions;}
    public void setRecommendedSets(int recommendedSets){this.recommendedSets = recommendedSets;}
    public void setRecommendedRepetitionsToIncreaseWith(int recommendedRepetitionsToIncreaseWith){this.recommendedRepetitionsToIncreaseWith= recommendedRepetitionsToIncreaseWith;}
    public void setDescription(String description){this.description = description;}
    public void setPhotoUrl(String photoUrl){this.photoUrl = photoUrl;}





}
