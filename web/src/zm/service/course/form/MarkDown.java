package zm.service.course.form;

import org.springframework.web.bind.annotation.ModelAttribute;
import zm.service.course.Courses;

public class MarkDown {
    private String content;
    private String courseName;
    private String unitName;
    private String lessonName;
    private String markdownName;

    public MarkDown(){
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName){
        this.unitName = unitName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName){
        this.lessonName = lessonName;
    }

    public String getMarkdownName() {
        return markdownName;
    }

    public void setMarkdownName(String markdownName){
        this.markdownName = markdownName;
    }

    public void uploadToOss(){
        Courses.getInst().putMarkDown( courseName, unitName, markdownName, content);
        Courses.getInst().evicitLessonCache( courseName, unitName, lessonName);
    }
}
