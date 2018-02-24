package zm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.jvm.hotspot.oops.Mark;
import sun.misc.Request;
import zm.service.course.*;
import zm.service.course.account.User;
import zm.service.course.form.MarkDown;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value="/zm", method = RequestMethod.GET)
public class ZMController {

    @Autowired
    private Courses courses;

    @Autowired
    private Markdown2Html m2hConverter;


    //
    // 登录
    //
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(ModelMap model, HttpSession session) {

        User.Account emptyAccount = new User.Account();

        model.addAttribute( "account", emptyAccount);

        return "zm/login";
    }


    /***
     * 登录验证
     */
    @RequestMapping(value = "/login_verify", method = RequestMethod.POST)
    public @ResponseBody String logined(@ModelAttribute("account") User.Account account,
                                        BindingResult result,
                                        ModelMap model,
                                        HttpSession session){

        if (result.hasErrors()) {
            return "error";
        }

        User user = (User)session.getAttribute("user");
        if(user==null)
        {
            session.setMaxInactiveInterval( 3600 * 2);
            session.setAttribute( "user", new User());
        }

        return "SUCCESS";
    }


    /***
     * 请求显示所有课程
     * @param model
     * @return 视图名称
     */
    @RequestMapping(value="/courses", method = RequestMethod.GET)
    public String getCourses(ModelMap model){

        model.addAttribute("courses", courses.getCourseMetas());

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
                            @RequestParam("name") String courseName){

        List<UnitMeta> units = courses.getUnitMetas(courseName);
        if( units.size()==1) {
            String unitName = units.get(0).getName();

            model.addAttribute( "back_url", "/zm/courses");
            model.addAttribute( "unitIcon", courses.getUnitIconUrl(courseName, unitName));

            List<LessonMeta> lessonMetas = courses.getLessonMetas( courseName, unitName);
            model.addAttribute( "lessonMetas", lessonMetas);

            return "zm/unit";
        }
        else {
            model.addAttribute( "back_url", "/zm/courses");
            model.addAttribute( "courseName", courseName);


            model.addAttribute("unitMetas", units);

            return "zm/course";
        }
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

        model.addAttribute( "back_url", "/zm/course?name=" + courseName);
        model.addAttribute( "unitIcon", courses.getUnitIconUrl(courseName, unitName));

        List<LessonMeta> lessonMetas = courses.getLessonMetas( courseName, unitName);
        model.addAttribute( "lessonMetas", lessonMetas);

        return "zm/unit";
    }

    /**
     * 显示课程内容
     */
    @RequestMapping(value="/lesson", method = RequestMethod.GET)
    public String getLesson(ModelMap model,
                            @RequestParam("course") String courseName,
                            @RequestParam("unit") String unitName,
                            @RequestParam("lesson") String lessonName,
                            HttpSession session){

        User user = (User)session.getAttribute("user");

        model.addAttribute( "back_url", "/zm/course?name=" + courseName + "&unit=" + unitName);
        Lesson lesson = courses.getLesson( courseName, unitName, lessonName);
        model.addAttribute("lesson", lesson);
        model.addAttribute("is_edit", true);

        return "zm/lesson";
    }

    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public String edit(ModelMap model,
                       @RequestParam("course") String courseName,
                       @RequestParam("unit") String unitName,
                       @RequestParam("lesson") String lessonName,
                       @RequestParam("md") String mdName){

        MarkDown md = Courses.getInst().getMarkDown( courseName, unitName, lessonName, mdName);
        model.addAttribute( "markdown", md);

        return "zm/edit/md";
    }

    @RequestMapping(value = "/modify_md", method = RequestMethod.POST)
    public @ResponseBody String modify_md(@ModelAttribute("markdown")MarkDown markdown,
                                          BindingResult result,
                                          ModelMap model){

        if (result.hasErrors()) {
            return "error";
        }

        markdown.uploadToOss();

        return "SUCCESS";
    }
}
