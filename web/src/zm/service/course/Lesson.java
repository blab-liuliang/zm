package zm.service.course;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import zm.service.course.exercise.Exercise;
import zm.service.course.exercise.ExerciseChoice;
import zm.service.course.exercise.ExerciseMarkDown;
import zm.service.course.exercise.ExerciseVideo;

import java.util.ArrayList;
import java.util.List;

public class Lesson {

    private String title;
    private String desc;
    private String domain;      // 域名
    private String ossUrl;      // OSS存储课程地址

    List<Exercise> exercises = new ArrayList<>();

    public Lesson(){
    }

    public void setData(JSONObject jsonObj){
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
            else if( type.equals("video")){
                Exercise exe = new ExerciseVideo( exerciseJO, this);
                this.exercises.add( exe);
            }
            else if( type.equals("markdown")){
                Exercise exe = new ExerciseMarkDown( exerciseJO, this);
                this.exercises.add(exe);
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

    public void setDomain(String domain){
        this.domain = domain;
    }

    public String getDomain(){
        return this.domain;
    }

    public void setOssUrl(String url) {
        this.ossUrl = url;
    }

    public String getOssUrl(){
        return ossUrl;
    }

    public List<Exercise> getExercises(){
        return this.exercises;
    }
}
