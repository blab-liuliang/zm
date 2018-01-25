package zm.service.course;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import zm.service.course.exercise.Exercise;
import zm.service.course.exercise.ExerciseChoice;
import zm.service.course.exercise.ExerciseMarkDown;
import zm.service.course.exercise.ExerciseVideo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class Courses {

    private static Courses inst = null;

    private String endPoint = "oss-cn-shenzhen.aliyuncs.com"; //"oss-cn-shenzhen-internal.aliyuncs.com";
    private String bucketName = "alab-web";
    private String coursesLocation = "zm/courses/";
    private String accessKeyId = "LTAIn09uY98LyNVH";
    private String accessKeySecret = "U0FSBe9oFiaha98iRTVEPdZywQaFuC";


    public Courses(){
        inst = this;
    }

    public static Courses getInst(){
        return inst;
    }

    /***
     * 获取课程列表
     */
    @Cacheable(value = "courses")
    public List<CourseMeta> getCourseMetas(){
        // 获取课程配置文件
        OSSClient oss = new OSSClient( endPoint, accessKeyId, accessKeySecret);

        String rootURL = "http://" + bucketName + "." + endPoint + "/";

        OSSObject ossObj = oss.getObject(bucketName, coursesLocation +"courses.json");
        InputStream inputStream = ossObj.getObjectContent();

        List<CourseMeta> courseMetas = new ArrayList<>();

        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse( new InputStreamReader(inputStream, "UTF-8"));

            JSONArray exercisesJA = (JSONArray) jsonObject.get("courses");
            for( Object obj : exercisesJA) {
                JSONObject courseJO = (JSONObject) obj;

                String id = courseJO.get("id").toString();
                String location = courseJO.get("location").toString();
                String name = courseJO.get("name").toString();

                CourseMeta courseMeta = new CourseMeta();
                courseMeta.setName(name);
                courseMeta.setIcon(rootURL + coursesLocation + location + "/icon.png");
                courseMeta.setLink("/zm/course?name=" + location);

                courseMetas.add(courseMeta);
            }

        } catch (java.io.UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (java.io.IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }

        return courseMetas;
    }

    

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
        String unitURL = coursesLocation + courseName + "/" + unitName + "/";
        String lessonLocation = unitURL + lessonName;

        OSSObject obj = oss.getObject(bucketName, lessonLocation);
        InputStream inputStream = obj.getObjectContent();

        Lesson lesson = null;

        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse( new InputStreamReader(inputStream, "UTF-8"));

            lesson = new Lesson();
            lesson.setDomain( rootURL);
            lesson.setOssUrl( unitURL);
            lesson.setData( jsonObject);

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

    /**
     * 获取MarkDown内容
     */
    @Cacheable(value = "courses")
    public String getMarkDown( String ossUrl, String markDownName){
        // 获取课程配置文件
        OSSClient oss = new OSSClient( endPoint, accessKeyId, accessKeySecret);

        OSSObject obj = oss.getObject(bucketName, ossUrl + markDownName);
        InputStream inputStream = obj.getObjectContent();

        try{
            String markdown = IOUtils.readStreamAsString(inputStream, "UTF-8");

            String rootURL = "http://" + bucketName + "." + endPoint + "/";
            String markdownUrl = rootURL + ossUrl;
            markdown = markdown.replace("${OSS_CURRENT_DIR}/", markdownUrl);

            return markdown;

        } catch ( java.io.IOException e){
            e.printStackTrace();
        }

        return "";
    }

}
