package zm.service.course.exercise;

import org.json.simple.JSONObject;
import zm.service.course.Lesson;

public class ExerciseMarkDown extends Exercise{
    private Lesson lesson;             // 课程
    private String url;           // MD文件地址地址


    public ExerciseMarkDown(JSONObject jsonObj, Lesson lesson){
        this.lesson = lesson;
        this.url = jsonObj.get("url").toString();
    }
}
