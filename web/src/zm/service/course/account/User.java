package zm.service.course.account;

public class User {

    public static class Account{
        private String email;
        private String password;

        public Account(){
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    //private Account account = new Account();
    private String  vipExpiredDate;
    private boolean isTeacher;

    public boolean getIsTeacher()
    {
        return isTeacher;
    }
}
