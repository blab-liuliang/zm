package zm.service.course;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.stereotype.Service;

@Service
public class Markdown2Html {

    private static Markdown2Html inst = null;

    HtmlRenderer renderer = null;
    Parser parser = null;

    public Markdown2Html(){
        inst = this;

        MutableDataSet options = new MutableDataSet();
        renderer = HtmlRenderer.builder(options).build();
        parser = Parser.builder(options).build();
    }

    public static Markdown2Html getInst() {
        return inst;
    }

    public String toHtml(String mdContent){
        Node document = parser.parse(mdContent);

        return renderer.render(document); //Courses.inst().getMarkDown( lesson.getOssUrl() + url);
    }

}
