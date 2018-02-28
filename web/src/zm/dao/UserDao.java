package zm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zm.service.course.account.User;


@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;


    public UserDao(){
    }

    // 获取当前线程绑定的Session
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public void saveUser(User user){
        getSession().save( user);
        getSession().getTransaction().commit();
    }
}
