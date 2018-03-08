package com.personaltrainer.apolka.personaltrainer.Models;

/**
 * Created by Api on 3/8/2018.
 */

public class Exercise {

    private String Name;
    private Muscle Muscle;
    private int RecommendedRepetitions;
    private int RecommendedSets;
    private int RecommendedRepetitionsToIncreaseWith;
    private String Description;

    Exercise(String name, Muscle muscle, int recommendedRepetitions, int recommendedSets, int recommendedRepetitionsToIncreaseWith, String description){
        this.Name = name;
        this.Muscle = muscle;
        this.RecommendedRepetitions = recommendedRepetitions;
        this.RecommendedSets = recommendedSets;
        this.RecommendedRepetitionsToIncreaseWith = recommendedRepetitionsToIncreaseWith;
        this.Description = description;

    }

    public String getName(){return this.Name;}
    public Muscle getMuscle(){return this.Muscle;}
    public int getRecommendedRepetitions(){return this.RecommendedRepetitions;}
    public int getRecommendedSets(){return this.RecommendedSets;}
    public int getRecommendedRepetitionsToIncreaseWith(){return this.RecommendedRepetitionsToIncreaseWith;}
    public String getDescription(){return this.Description;}

    public void setName(String name){this.Name = name;}
    public void setMuscle(Muscle muscle){this.Muscle = muscle;}
    public void setRecommendedRepetitions(int recommendedRepetitions){this.RecommendedRepetitions = recommendedRepetitions;}
    public void setRecommendedSets(int recommendedSets){this.RecommendedSets = recommendedSets;}
    public void setRecommendedRepetitionsToIncreaseWith(int recommendedRepetitionsToIncreaseWith){this.RecommendedRepetitionsToIncreaseWith = recommendedRepetitionsToIncreaseWith;}
    public void setDescription(String description){this.Description = description;}






}
