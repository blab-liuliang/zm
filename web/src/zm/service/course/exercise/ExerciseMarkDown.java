package zm.service.course.exercise;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import zm.service.course.Courses;
import zm.service.course.Lesson;

public class ExerciseMarkDown extends Exercise{

    private Lesson lesson;              // 课程
    private String url;                 // MD文件地址地址
    private String html;                // html

    public ExerciseMarkDown(JSONObject jsonObj, Lesson lesson){
        this.type = jsonObj.get("type").toString();
        this.lesson = lesson;
        this.url = jsonObj.get("url").toString();

        WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
        this.html = Courses.inst().getMarkDown( lesson.getOssUrl() + url);
    }

    public String getUrl(){
        return url;
    }

    public String getHtml(){
        return html;
    }
}
