package org.pahappa.systems.registrationapp.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.views.UserView;

public class UserService {
    Scanner scn = new Scanner(System.in);
    private final List<User> users_list = new ArrayList<>();

    public Date dateFormat(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        return df.parse(date);
    }
   public void addUser(String first_name, String last_name, String user_name,String date_of_birth) {
       User newUser = new User();
       if(first_name.isEmpty() || first_name.length() < 5){
           System.out.println("First name field missing or characters less than 5, please refill the field below :");
           addUser(scn.nextLine(),last_name,user_name,date_of_birth);
       }
       else if(last_name.isEmpty() || last_name.length() < 5){
           System.out.println("Last name field missing or characters less than 5, please refill the field correctly below :");
           addUser(first_name,scn.nextLine(),user_name,date_of_birth);
       } else if (user_name.isEmpty() || user_name.length() < 5) {
           System.out.println("User name field missing or characters less than 5, please refill the field correctly below :");
           addUser(first_name,last_name,scn.nextLine(),date_of_birth);
       }
       else if(date_of_birth.isEmpty()) {
           System.out.println("Date of birth field missing, please refill the field correctly below with correct format:");
           addUser(first_name, last_name, user_name, scn.nextLine());
       }else {
            try {
                Date date_of_birth_parsed = dateFormat(date_of_birth);
                newUser.setDateOfBirth(date_of_birth_parsed);
                newUser.setFirstname(first_name);
                newUser.setLastname(last_name);
                newUser.setUsername(user_name);

                boolean user_name_present = false;


                for (User x : users_list) {
                    if(x.getUsername().equals(user_name)){
                        user_name_present = true;
                    }
                    else {
                        user_name_present =false;
                    }
                }
                if (user_name_present) {
                    System.out.println("User name already taken please enter new user name below : ");
                    addUser(first_name,last_name, scn.nextLine(), date_of_birth);
                } else {
                    boolean user_present =false;
                    if(user_present){
                        System.out.println("User already exists in the system");
                    }
                    else {
                        users_list.add(newUser);
                        System.out.println("User has been registered successfully (*_*) ");
                    }

                }

            }catch (Exception e){
                System.out.println("Date provided is of incorrect format, please refill the field correctly below :");
                addUser(first_name,last_name,user_name, scn.nextLine());
            }


       }

   }
   public void  returnAllUsers(){

       if(!users_list.isEmpty()) {
           System.out.println("System has registered the following users");
           for (User x : users_list) {

               System.out.println(" User " + x.getUsername() + " has names " + x.getFirstname() + " " + x.getLastname() + " and has a date of birth  of " + x.getDateOfBirth());
           }
       }
       else{
           System.out.println("System has no registered users currently");
       }
   }

   public  void returnUserOfUserName(String user_name){
        if(!users_list.isEmpty()) {
            for (User x : users_list) {
                if (x.getUsername().equals(user_name)) {
                    System.out.println("\n User " + user_name + " has details : full name " + x.getFirstname() + " " + x.getLastname() + " and date of birth" + x.getDateOfBirth());
                } else {
                    System.out.println("The user is not registered in the system");
                }
            }
        }else {
            System.out.println("System is currently empty, no users to return");
        }
   }

   public  void updateUserOfUserName(String user_name) throws ParseException {
        if(users_list.isEmpty()) {
            System.out.println("No users to update, system currently empty");
        }
        else {

            for (User x : users_list) {
                if (x.getUsername().equals(user_name)) {
                    System.out.println(" Enter new username");
                    String user_name_new = scn.nextLine();
                    x.setUsername(user_name_new);
                    System.out.println("Enter users new first name");
                    String first_name_new = scn.nextLine();
                    x.setFirstname(first_name_new);
                    System.out.println("Enter users new last name");
                    String last_name_new = scn.nextLine();
                    x.setLastname(last_name_new);
                    System.out.println("Enter users new date of birth");
                    String date_of_birth_new = scn.nextLine();
                    x.setDateOfBirth(dateFormat(date_of_birth_new));
                    System.out.println("User details have been updated successfully");

                } else {
                    System.out.println("The user is not registered in the system");
                }
            }
        }
   }
   public void deleteUserOfUserName(String user_name){
        if(!users_list.isEmpty()) {
            for (User x : users_list) {
                if (x.getUsername().equals(user_name)) {
                    users_list.remove(x);
                    System.out.println("\n User " + x.getFirstname() + " " + x.getLastname() + " has been deleted from system ");
                } else {
                    System.out.println("The user is not registered in the system");
                }
            }
        }
        else {
            System.out.println("No users in system to delete yet");
        }
   }
   public  void  deleteAllUsers(){
       if(users_list.isEmpty()){
           System.out.println("There are no users currently in the system");
       }
       else {
           users_list.clear();
           System.out.println("All users have been deleted successfully");
       }
   }


}
