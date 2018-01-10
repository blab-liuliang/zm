package zm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zm.service.course.Courses;
import zm.service.course.UnitMeta;

import java.util.List;

@Controller
@RequestMapping(value="/zm", method = RequestMethod.GET)
public class ZMController {

    @Autowired
    private Courses courses;

    /***
     * 请求显示所有课程
     * @param model
     * @return 视图名称
     */
    @RequestMapping(value="/courses", method = RequestMethod.GET)
    public String getCourses(ModelMap model){

       //model.addAttribute("coursesSummary", courses.getSummary());

        return "zm/courses";
    }

    @RequestMapping(value="/courses/new", method=RequestMethod.GET)
    public String newCourse(ModelMap model){
        return "zm/course_new";
    }

    /***
     * 具体课程页面
     * @param model
     * @return 视图名称
     */
    @RequestMapping(value="/course", method = RequestMethod.GET)
    public String getCourse(ModelMap model,
                            @RequestParam("name") String courseName,
                            @RequestParam("edit") boolean edit){

        List<UnitMeta> meta = courses.getUnitMetas(courseName);
        model.addAttribute("unitMetas", meta);

        return "zm/course";
    }

    @RequestMapping(value = "/add_course", method = RequestMethod.GET)
    public String addCourse(@RequestParam("name") String courseName){

        return "";
    }

    /**
     * 显示单元内容页面
     */
    @RequestMapping(value="/unit", method = RequestMethod.GET)
    public String getUnit(ModelMap model,
                          @RequestParam("course") String courseName,
                          @RequestParam("unit") String unitName){



        return "zm/unit";
    }
}
