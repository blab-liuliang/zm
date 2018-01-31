package zm.service.course.form;

import org.springframework.web.bind.annotation.ModelAttribute;

public class MarkDown {
    private String content;

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
