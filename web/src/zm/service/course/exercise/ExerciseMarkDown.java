package zm.service.course.exercise;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import zm.service.course.Courses;
import zm.service.course.Lesson;
import zm.service.course.Markdown2Html;

public class ExerciseMarkDown extends Exercise{

    private Lesson lesson;              // 课程
    private String url;                 // MD文件地址地址
    private String md;                  // MD文件内容
    private String html;                // html

    public ExerciseMarkDown(JSONObject jsonObj, Lesson lesson){
        this.type = jsonObj.get("type").toString();
        this.lesson = lesson;
        this.url = jsonObj.get("url").toString();
        this.md = Courses.getInst().getMarkDown( lesson.getCourseName(), lesson.getUnitName(), lesson.getLessonName(), url).getContent();
        this.html = Markdown2Html.getInst().toHtml( this.md);
    }

    public String getUrl(){
        return url;
    }

    public String getHtml(){
        return html;
    }
}
