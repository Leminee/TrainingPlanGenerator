package gui;

public class ShowPlanController {


    private String muscleGroup;
    private String exercise;
    private int numberSet;
    private int numberRepetition;

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public void setNumberSet(int numberSet) {
        this.numberSet = numberSet;
    }

    public void setNumberRepetition(int numberRepetition) {
        this.numberRepetition = numberRepetition;
    }


    public ShowPlanController(){


    }

   public String getMuscleGroup() {
        return muscleGroup;
    }

    public String getExercise() {
        return exercise;
    }

    public int getNumberSet() {
        return numberSet;
    }

    public int getNumberRepetition() {
        return numberRepetition;
    }


}