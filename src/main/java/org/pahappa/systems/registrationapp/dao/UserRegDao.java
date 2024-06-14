package org.pahappa.systems.registrationapp.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.systems.registrationapp.config.SessionConfiguration;
import  org.pahappa.systems.registrationapp.models.User;
import  org.pahappa.systems.registrationapp.views.UserView;
import java.util.Date;
import java.util.List;

public class UserRegDao {
    public static void saveUser(User user){
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.save(user);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            UserView.Print("Session to save not created successfully");
            SessionConfiguration.shutdown();
        }
    }

    public  static List<User> returnAllUsers(){
        List<User> users = null;
            try{
                SessionFactory sf = SessionConfiguration.getSessionFactory();
                Session session = sf.openSession();
                Transaction trs = session.beginTransaction();
                Query qry = session.createQuery("from User");
                users = qry.list();
                trs.commit();
                SessionConfiguration.shutdown();

            }
            catch (Exception e){
                UserView.Print("Session to return users not created successfully");
                SessionConfiguration.shutdown();
            }
                return users;
    }
    public  static User returnUser(String user_name){
        User user = null;
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from User where username = :user_name");
            qry.setParameter("user_name", user_name);
            user = (User) qry.uniqueResult();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            UserView.Print("Session to return a user not created successfully");
            SessionConfiguration.shutdown();
        }
            return user;
    }
    public  static int deleteUser(String user_name){
        int result = -1;
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("delete from User where username = :user_name");
            qry.setParameter("user_name", user_name);
            result = qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }catch (Exception e){
            UserView.Print("Session to delete a user not created successfully");
            SessionConfiguration.shutdown();
        }
        return result;
    }
    public  static int deleteAllUsers(){
        int result = -1;
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("delete from User");
            result = qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            UserView.Print("Session to delete users not created successfully");
            SessionConfiguration.shutdown();
        }
        return  result;
    }
    public  static int updateUser(String first_name, String last_name, String user_name, Date date_of_birth){
        int result = -1;
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("update User set firstname =:first_name , lastname = :last_name, dateOfBirth= :date_of_birth where username = :user_name");
            qry.setParameter("user_name", user_name);
            qry.setParameter("first_name", first_name);
            qry.setParameter("last_name", last_name);
            qry.setParameter("date_of_birth", date_of_birth);
            result = qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            UserView.Print("Session to update a user not created successfully");
            SessionConfiguration.shutdown();
        }
        return result;
    }
}
