package zm.service.course;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.google.gson.Gson;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
    @Cacheable(value = "default")
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

                unitMetas.add(unitMeta);
            }
        }

        return unitMetas;
    }

    public List<LessonMeta> getLessonMetas(String courseName, String unitName){
        List<LessonMeta> lessonMetas = new ArrayList<>();

        return lessonMetas;
    }
}
