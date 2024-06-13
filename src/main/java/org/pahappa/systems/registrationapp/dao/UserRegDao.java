package org.pahappa.systems.registrationapp.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.systems.registrationapp.config.SessionConfiguration;
import  org.pahappa.systems.registrationapp.models.User;
import java.util.Date;
import java.util.List;

public class UserRegDao {
    public static void SaveUser(User user){
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.save(user);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public  static List<User> ReturnAllUsers(){

            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from User");
            List<User> users = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
            return users;
    }
    public  static User ReturnUser(String user_name){
        SessionFactory sf = SessionConfiguration.getSessionFactory();
        Session session = sf.openSession();
        Transaction trs = session.beginTransaction();
        Query qry = session.createQuery("from User where username = :user_name");
        qry.setParameter("user_name",user_name);
        User user = (User) qry.uniqueResult();
        trs.commit();
        SessionConfiguration.shutdown();
        return user;
    }
    public  static int DeleteUser(String user_name){
        SessionFactory sf = SessionConfiguration.getSessionFactory();
        Session session = sf.openSession();
        Transaction trs = session.beginTransaction();
        Query qry = session.createQuery("delete from User where username = :user_name");
        qry.setParameter("user_name",user_name);
        int result = qry.executeUpdate();
        trs.commit();
        SessionConfiguration.shutdown();
        return result;

    }
    public  static int DeleteAllUsers(){
        SessionFactory sf = SessionConfiguration.getSessionFactory();
        Session session = sf.openSession();
        Transaction trs = session.beginTransaction();
        Query qry = session.createQuery("delete from User");
        int result = qry.executeUpdate();
        trs.commit();
        SessionConfiguration.shutdown();
        return  result;

    }
    public  static int UpdateUser(String first_name, String last_name, String user_name, Date date_of_birth){
        SessionFactory sf = SessionConfiguration.getSessionFactory();
        Session session = sf.openSession();
        Transaction trs = session.beginTransaction();
        Query qry =session.createQuery("update User set firstname =:first_name , lastname = :last_name, dateOfBirth= :date_of_birth where username = :user_name");
        qry.setParameter("user_name",user_name);
        qry.setParameter("first_name",first_name);
        qry.setParameter("last_name",last_name);
        qry.setParameter("date_of_birth",date_of_birth);
        int result = qry.executeUpdate();
        trs.commit();
        SessionConfiguration.shutdown();
        return result;

    }
}
