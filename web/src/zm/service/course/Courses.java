package zm.service.course;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.google.gson.Gson;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class Courses {
    private String endPoint = "oss-cn-shenzhen.aliyuncs.com"; //"oss-cn-shenzhen-internal.aliyuncs.com";
    private String bucketName = "alab-web";
    private String coursesLocation = "zm/courses/";
    private String accessKeyId = "LTAIn09uY98LyNVH";
    private String accessKeySecret = "U0FSBe9oFiaha98iRTVEPdZywQaFuC";


    public Courses(){
    }

    /***
     * 获取课程列表
     */

    /***
     * 获取单元描述信息
     * @param courseName
     * @return
     */
    @Cacheable(value = "courses")
    public List<UnitMeta> getUnitMetas(String courseName){
        // 获取课程配置文件
        OSSClient oss = new OSSClient( endPoint, accessKeyId, accessKeySecret);

        String rootURL = "http://" + bucketName + "." + endPoint + "/";
        String courseLocation = coursesLocation + courseName + "/";
        List<OSSObjectSummary> objSummarys = oss.listObjects( bucketName, courseLocation).getObjectSummaries();

        List<UnitMeta> unitMetas = new ArrayList<>();

        for(OSSObjectSummary summary : objSummarys){
            String key = summary.getKey();
            if( key.endsWith("/") && key.length() > courseLocation.length()){
                String substr = summary.getKey().substring( courseLocation.length());
                String unitName = substr.substring( 0, substr.length()-1);

                UnitMeta unitMeta = new UnitMeta();
                unitMeta.setName(unitName);
                unitMeta.setIcon(rootURL + summary.getKey() + "icon.png");
                unitMeta.setLink(String.format("unit?course=%s&unit=%s", courseName, unitName));

                unitMetas.add(unitMeta);
            }
        }

        return unitMetas;
    }

    // 获取单元图标链接地址
    public String getUnitIconUrl(String courseName, String unitName){

        String rootURL = "http://" + bucketName + "." + endPoint + "/";
        String unitLocation = coursesLocation + courseName + "/" + unitName + "/";

        return rootURL + unitLocation + "icon.png";
    }

    /**
     * 获取单元内课程信息
     */
    @Cacheable(value = "courses")
    public List<LessonMeta> getLessonMetas(String courseName, String unitName){

        // 获取课程配置文件
        OSSClient oss = new OSSClient( endPoint, accessKeyId, accessKeySecret);

        String rootURL = "http://" + bucketName + "." + endPoint + "/";
        String unitLocation = coursesLocation + courseName + "/" + unitName + "/";
        List<OSSObjectSummary> objSummarys = oss.listObjects( bucketName, unitLocation).getObjectSummaries();

        List<LessonMeta> lessonMetas = new ArrayList<>();

        for(OSSObjectSummary summary : objSummarys){
            String key = summary.getKey();
            if( key.endsWith("lesson") && key.length() > unitLocation.length()){
                String lessonName = summary.getKey().substring( unitLocation.length());
                String lessonNameSplits[] = lessonName.split("\\.");

                LessonMeta lessonMeta = new LessonMeta();
                lessonMeta.setName(lessonNameSplits[1]);
                //lessonMeta.setIcon(rootURL + summary.getKey() + "icon.png");
                lessonMeta.setLink(String.format("lesson?course=%s&unit=%s&lesson=%s", courseName, unitName, lessonName));

                lessonMetas.add(lessonMeta);
            }
        }

        return lessonMetas;
    }


    /**
     * 获取课程内容
     */
    @Cacheable(value = "courses")
    public Lesson getLesson(String courseName, String unitName, String lessonName){

        // 获取课程配置文件
        OSSClient oss = new OSSClient( endPoint, accessKeyId, accessKeySecret);

        String rootURL = "http://" + bucketName + "." + endPoint + "/";
        String lessonLocation = coursesLocation + courseName + "/" + unitName + "/" + lessonName;

        OSSObject obj = oss.getObject(bucketName, lessonLocation);
        InputStream inputStream = obj.getObjectContent();

        Lesson lesson = null;

        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse( new InputStreamReader(inputStream, "UTF-8"));

            lesson = new Lesson(jsonObject);

            return lesson;

        } catch (java.io.UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (java.io.IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }


        return lesson;
    }
}
