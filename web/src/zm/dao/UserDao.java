package zm.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zm.dao.pojo.User;


@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;


    public UserDao(){
    }

    // 保存用户
    public void saveUser(User user){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save( user);
        session.getTransaction().commit();
        session.close();
    }
}
