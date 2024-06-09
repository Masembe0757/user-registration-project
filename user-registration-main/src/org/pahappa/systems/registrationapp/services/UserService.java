package org.pahappa.systems.registrationapp.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.views.UserView;

public class UserService {
    private final List<User> users_list = new ArrayList<>();
    public static boolean onlyDigits(String str, int n)
    {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) < '0'
                    || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    public  boolean hasDigits(String str){
        boolean has_digits = false;
        for(int i =0 ; i < str.length(); i++){
            if(Character.isDigit(str.charAt(i))){
                has_digits =  true;
            }
        }
        return has_digits;
    }

    public Date dateFormat(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        return df.parse(date);

    }
    public  void firstNameValidation(String str){

    }
   public void addUser(String first_name, String last_name, String user_name,String date_of_birth) {
       User newUser = new User();
       UserView user_view = new UserView();
       if(first_name.isEmpty() || hasDigits(first_name) ){
           user_view.Print("First name field missing or has digits in it, please refill the field below :");
           addUser(user_view.Scan(),last_name,user_name,date_of_birth);
       }
       else if(last_name.isEmpty() || hasDigits(last_name) ){
           user_view.Print("Last name field missing or has digits in it, please refill the field correctly below :");
           addUser(first_name,user_view.Scan(),user_name,date_of_birth);
       } else if (user_name.isEmpty() || user_name.length() < 6 ) {
           user_view.Print("User name field missing or characters less than 6, please refill the field correctly below :");
           addUser(first_name,last_name,user_view.Scan(),date_of_birth);
       } else if (Character.isDigit(user_name.charAt(0))) {
           user_view.Print("User name field  can not start with a digit, please refill the field correctly below :");
           addUser(first_name,last_name,user_view.Scan(),date_of_birth);
       } else if (onlyDigits(user_name, user_name.length())) {
           user_view.Print("User name field can not contain only digits, please refill the field correctly below :");
           addUser(first_name,last_name,user_view.Scan(),date_of_birth);
       } else if(date_of_birth.isEmpty()) {
           user_view.Print("Date of birth field missing, please refill the field correctly below with correct format:");
           addUser(first_name, last_name, user_name, user_view.Scan());
       }else {
            try {
                Date date_of_birth_parsed = dateFormat(date_of_birth);
                newUser.setDateOfBirth(date_of_birth_parsed);
                newUser.setFirstname(first_name);
                newUser.setLastname(last_name);
                newUser.setUsername(user_name);

                boolean user_name_present = false;


                for (User x : users_list) {
                    if(x.getUsername().contains(user_name)){
                        user_name_present = true;
                    }
                    else {
                        user_name_present =false;
                    }
                }
                if (user_name_present) {
                    user_view.Print("User name already taken please enter new user name below : ");
                    addUser(first_name,last_name, user_view.Scan(), date_of_birth);
                } else {
                        if(date_of_birth_parsed.getYear()+1900 < Calendar.getInstance().get(Calendar.YEAR)) {
                            users_list.add(newUser);
                            System.out.println("User has been registered successfully (*_*) ");
                        }
                        else {
                            user_view.Print("Date provided is beyond current date, please enter correct date of birth bellow:");
                            addUser(first_name, last_name, user_name, user_view.Scan());
                        }

                    }

            }catch (Exception e){
                user_view.Print("Date provided is of incorrect format, please refill the field correctly below :");
                addUser(first_name,last_name,user_name, user_view.Scan());
            }


       }

   }
   public void  returnAllUsers(){
       UserView user_view = new UserView();

       if(!users_list.isEmpty()) {
           user_view.Print("System has registered the following users");
           for (User x : users_list) {

               user_view.Print(" User " + x.getUsername() + " has names " + x.getFirstname() + " " + x.getLastname() + " and has a date of birth  of " + x.getDateOfBirth());
           }
       }
       else{
           user_view.Print("System has no registered users currently");
       }
   }

   public  void returnUserOfUserName(String user_name){
       UserView user_view = new UserView();
       boolean user_present = false;
        if(!users_list.isEmpty()) {
            for (User x : users_list) {
                if (x.getUsername().equals(user_name)) {
                    user_present = true;
                    user_view.Print("\n User " + user_name + " has details : full name " + x.getFirstname() + " " + x.getLastname() + " and date of birth" + x.getDateOfBirth());
                }
            }
            if(!user_present){
                user_view.Print("The user is not registered in the system");
            }
        }else {
            user_view.Print("System is currently empty, no users to return");
        }
   }

   public  void updateUserOfUserName(String user_name) throws ParseException {
       UserView user_view = new UserView();
       boolean  user_present = false;
        if(users_list.isEmpty()) {
            user_view.Print("No users to update, system currently empty");
        }
        else {

            for (User x : users_list) {
                if (x.getUsername().equals(user_name)) {
                    user_present = true;
                    user_view.Print(" Enter new username");
                    String user_name_new = user_view.Scan();
                    user_view.Print("Enter users new first name");
                    String first_name_new = user_view.Scan();
                    user_view.Print("Enter users new last name");
                    String last_name_new = user_view.Scan();
                    user_view.Print("Enter users new date of birth");
                    String date_of_birth_new = user_view.Scan();

                    Date date_of_birth_parsed_new = dateFormat(date_of_birth_new);
                    x.setDateOfBirth(date_of_birth_parsed_new);
                    x.setFirstname(first_name_new);
                    x.setLastname(last_name_new);
                    x.setUsername(user_name_new);
                    user_view.Print("User details have been updated successfully");
                }
            }

            if (!user_present){
                user_view.Print("The user is not registered in the system");
            }
        }
   }
   public void deleteUserOfUserName(String user_name){
       UserView user_view = new UserView();
       boolean user_present = false;
        if(!users_list.isEmpty()) {
            for (User x : users_list) {
                if (x.getUsername().equals(user_name)) {
                    user_present =true;
                    users_list.remove(x);
                    user_view.Print("\n User " + x.getFirstname() + " " + x.getLastname() + " has been deleted from system ");
                }
            }
            if(!user_present){
                user_view.Print("The user is not registered in the system");
            }
        }
        else {
            user_view.Print("No users in system to delete yet");
        }
   }
   public  void  deleteAllUsers(){
       UserView user_view = new UserView();
       if(users_list.isEmpty()){
           user_view.Print("There are no users currently in the system");
       }
       else {
           users_list.clear();
           user_view.Print("All users have been deleted successfully");
       }
   }


}
