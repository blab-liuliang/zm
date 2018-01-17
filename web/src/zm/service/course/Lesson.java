package zm.service.course;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zm.service.course.exercise.Exercise;
import zm.service.course.exercise.ExerciseChoice;

import java.util.ArrayList;
import java.util.List;

public class Lesson {

    private String title;
    private String desc;

    List<Exercise> exercises = new ArrayList<>();

    public Lesson(JSONObject jsonObj){
        this.title = jsonObj.get("title").toString();
        this.desc = jsonObj.get("desc").toString();

        // parse exercises
       JSONArray exercisesJA = (JSONArray) jsonObj.get("exercises");
       for( Object obj : exercisesJA){
           JSONObject exerciseJO = (JSONObject) obj;

           String type = exerciseJO.get("type").toString();
           if ( type.equals("choice")){
               Exercise exe = new ExerciseChoice( exerciseJO);
               this.exercises.add( exe);
           }
       }
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return this.desc;
    }

    public List<Exercise> getExercises(){
        return this.exercises;
    }
}
