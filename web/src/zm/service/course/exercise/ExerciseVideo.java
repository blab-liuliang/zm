package zm.service.course.exercise;

import org.json.simple.JSONObject;
import zm.service.course.Lesson;

public class ExerciseVideo extends Exercise{
    private Lesson lesson;              // 课程
    private String url;                 // 视频地址


    public ExerciseVideo(JSONObject jsonObj, Lesson lesson){
        this.type = jsonObj.get("type").toString();
        this.url = jsonObj.get("url").toString();
        this.lesson = lesson;
    }

    public String getUrl(){
        return lesson.getDomain() + lesson.getOssUrl() + url;
    }
}
