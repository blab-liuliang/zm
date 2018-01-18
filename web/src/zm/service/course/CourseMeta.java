package zm.service.course;

public class CourseMeta {
    private String name;
    private String icon;
    private String link;        // 链接显示单元内所有课程

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getLink(){return link;}

    public void setLink(String link){ this.link=link; }
}
