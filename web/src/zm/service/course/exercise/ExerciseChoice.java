package zm.service.course.exercise;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExerciseChoice extends Exercise {

    private String question;    // 问题
    private List<String> options = new ArrayList<>();
    private String answer;      // 答案
    private String hint;        // 提示

    public ExerciseChoice(JSONObject jsonObj){
        type = jsonObj.get("type").toString();
        question = jsonObj.get("question").toString();
        answer = jsonObj.get("answer").toString();
        hint = jsonObj.get("hint").toString();

        JSONArray optionsJA = (JSONArray) jsonObj.get("options");
        for( Object obj : optionsJA){
            String optionStr = (String) obj;

            options.add( optionStr);
        }
    }

    @Override
    public String getType(){
        return type;
    }

    public String getQuestion(){
        return question;
    }

    public List<String> getOptions(){
        return options;
    }

    public String getAnswer(){
        return answer;
    }

    public String getHint() {
        return hint;
    }
}
