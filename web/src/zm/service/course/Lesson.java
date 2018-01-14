package zm.service.course;

import org.json.simple.JSONObject;

public class Lesson {

    private String title;

    public Lesson(JSONObject jsonObj){
        this.title = jsonObj.get("title").toString();
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
