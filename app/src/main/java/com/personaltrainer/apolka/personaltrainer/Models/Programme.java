package com.personaltrainer.apolka.personaltrainer.Models;

import com.personaltrainer.apolka.personaltrainer.Exerc;

import java.util.List;

/**
 * Created by Api on 3/8/2018.
 */

public class Programme {
    private String Name;
    private String Description;
    private int RecommendedDays;
    private int RecommendedRestDays;
    private List<Exercise> ExerciseList;

    Programme(String name, String description, int recommendedDays, int recommendedRestDays, List<Exercise> exerciseList){
        this.Name = name;
        this.Description = description;
        this.RecommendedDays = recommendedDays;
        this.RecommendedRestDays = recommendedRestDays;
        this.ExerciseList = exerciseList;
    }

    public String getName(){return this.Name;}
    public String getDescription(){return  this.Description;}
    public int getRecommendedDays(){return this.RecommendedDays;}
    public int getRecommendedRestDays(){return this.RecommendedRestDays;}
    public List<Exercise> getExerciseList(){return this.ExerciseList;}

    public void setName(String name){this.Name = name;}
    public void setDescription(String description){this.Description = description;}
    public void setRecommendedDays(int recommendedDays){this.RecommendedDays = recommendedDays;}
    public void setRecommendedRestDays(int recommendedRestDays){this.RecommendedRestDays = recommendedRestDays;}
    public void setExerciseList(List<Exercise> exerciseList){this.ExerciseList = exerciseList;}

}
