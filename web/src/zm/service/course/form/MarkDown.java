package zm.service.course.form;

import org.springframework.web.bind.annotation.ModelAttribute;
import zm.service.course.Courses;

public class MarkDown {
    private String content;
    private String lessonUrl;       // 课程地址
    private String url;             // 相对地址

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getLessonUrl(){
        return lessonUrl;
    }

    public void setLessonUrl(String lessonUrl){
        this.lessonUrl = lessonUrl;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void uploadToOss(){
        Courses.getInst().putObjectString( lessonUrl + url, content);
    }
}
