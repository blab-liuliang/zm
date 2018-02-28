package zm.service.course.account;

public class User {
    private String  email;
    private String  password;
    private String  vipExpiredDate;
    private boolean isTeacher;

    public boolean getIsTeacher()
    {
        return isTeacher;
    }
}
