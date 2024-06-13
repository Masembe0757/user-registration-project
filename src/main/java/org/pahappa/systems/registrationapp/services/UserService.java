package org.pahappa.systems.registrationapp.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.pahappa.systems.registrationapp.exception.RandomException;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.views.UserView;
import org.pahappa.systems.registrationapp.dao.UserRegDao;

public class UserService {

    public boolean HasSpecialCharacters(String s){
        boolean has_character = false;
        for (int i = 0; i < s.length(); i++) {
            // Checking the character for not being a letter,digit or space
            if (!Character.isDigit(s.charAt(i))
                    && !Character.isLetter(s.charAt(i))
                    && !Character.isWhitespace(s.charAt(i))) {
                has_character =true;

            }
        }
        return has_character;
    }

    //Generic method to check if username has only digits
    public static boolean OnlyDigits(String str, int n)
    {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) < '0'
                    || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    //Generic method to check if name provided has digits in it
    public  boolean HasDigits(String str){
        boolean has_digits = false;
        for(int i =0 ; i < str.length(); i++){
            if(Character.isDigit(str.charAt(i))){
                has_digits =  true;
            }
        }
        return has_digits;
    }

    //Generic method to check date format
    public Date DateFormat(String date) throws ParseException {
        DateFormat df = new  SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println();
        return df.parse(formatter.parse(date).toString().split(" ")[3]);

    }

    //Generic method to check if there are users in the database
    public boolean UsersAvailable(){
        List<User> users_list_new = UserRegDao.ReturnAllUsers();
        return !users_list_new.isEmpty();
    }
   public void AddUser(String first_name, String last_name, String user_name, String date_of_birth) {
       User newUser = new User();
       UserView user_view = new UserView();
       if(first_name.isEmpty() || HasDigits(first_name) ||HasSpecialCharacters(first_name) ){
           user_view.Print("First name field missing or has digits or special characters in it, please refill the field below :");
           AddUser(user_view.Scan(),last_name,user_name,date_of_birth);
       }
       else if(HasDigits(last_name) || HasSpecialCharacters(last_name)){
           user_view.Print("Last name field has digits or special characters  in it, please refill the field correctly below :");
           AddUser(first_name,user_view.Scan(),user_name,date_of_birth);
       } else if (user_name.isEmpty() || user_name.length() < 6 || HasSpecialCharacters(user_name) ) {
           user_view.Print("User name field missing or characters less than 6 or has special characters , please refill the field correctly below :");
           AddUser(first_name,last_name,user_view.Scan(),date_of_birth);
       } else if (Character.isDigit(user_name.charAt(0))) {
           user_view.Print("User name field  can not start with a digit, please refill the field correctly below :");
           AddUser(first_name,last_name,user_view.Scan(),date_of_birth);
       } else if (OnlyDigits(user_name, user_name.length())) {
           user_view.Print("User name field can not contain only digits, please refill the field correctly below :");
           AddUser(first_name,last_name,user_view.Scan(),date_of_birth);
       } else if(date_of_birth.isEmpty()) {
           user_view.Print("Date of birth field missing, please refill the field correctly below with correct format:");
           AddUser(first_name, last_name, user_name, user_view.Scan());
       }else {
            try {
                Date date_of_birth_parsed = DateFormat(date_of_birth);
                User returned_user = UserRegDao.ReturnUser(user_name);
                if (returned_user!= null) {
                    user_view.Print("User name already taken please enter new user name below : ");
                    AddUser(first_name,last_name, user_view.Scan(), date_of_birth);
                } else {
                        if(date_of_birth_parsed.getYear()+1900 < Calendar.getInstance().get(Calendar.YEAR)) {
                            newUser.setDateOfBirth(date_of_birth_parsed);
                            newUser.setFirstname(first_name);
                            newUser.setLastname(last_name);
                            newUser.setUsername(user_name);
                            UserRegDao.SaveUser(newUser);
                            user_view.Print("User has been registered successfully (*_*) ");

                        }
                        else {
                            user_view.Print("Date provided is beyond current date, please enter correct date of birth bellow:");
                            AddUser(first_name, last_name, user_name, user_view.Scan());
                        }

                    }

            }catch (Exception e){
                user_view.Print("Date provided is of incorrect format yyyy-mm-dd ,invalid month of the year or invalid day of the month, please refill the field correctly below :");
                AddUser(first_name,last_name,user_name, user_view.Scan());
            }


       }

   }
   public void ReturnAllUsers() throws RandomException {
       UserView user_view = new UserView();
       List<User> users_list_new = UserRegDao.ReturnAllUsers();

       if(UsersAvailable()) {
           user_view.Print("System has registered the following users");
           for(User user : users_list_new){
               System.out.println(user);
           }
       }
       else{
           throw new RandomException("System has no registered users currently");
       }
   }

   public  void ReturnUserOfUserName(String user_name) throws RandomException {

        if(UsersAvailable()) {
            User returned_user = UserRegDao.ReturnUser(user_name);
            if(returned_user == null){
                throw new RandomException("The user is not registered in the system");
            }
            else {
                System.out.println(returned_user.toString());
            }
        }else {
            throw new RandomException("System is currently empty, no users to return");
        }
   }
   public  void UpdateUserOfUserName(String user_name) throws ParseException, RandomException {
       UserView user_view = new UserView();
       List<User> users_list_new = UserRegDao.ReturnAllUsers();
       boolean  user_present = false;
        if(!UsersAvailable()) {
            throw new RandomException("No users to update, system currently empty");
        }
        else {
                User returned_user = UserRegDao.ReturnUser(user_name);
                if (returned_user!=null) {
                    user_view.Print("Enter users new first name");
                    String first_name_new = user_view.Scan();
                    user_view.Print("Enter users new last name");
                    String last_name_new = user_view.Scan();
                    user_view.Print("Enter users new date of birth format yyyy-mm-dd ");
                    String date_of_birth_new = user_view.Scan();

                    //update validation

                    if(first_name_new.isEmpty() || HasDigits(first_name_new) || HasSpecialCharacters(first_name_new) ){
                        user_view.Print("First name field missing, has digits or special characters  in it, please try again with valid update details :");
                        UpdateUserOfUserName(user_name);
                    }
                    else if(HasDigits(last_name_new) || HasSpecialCharacters(last_name_new)){
                        user_view.Print("Last name field has digits or special characters in it, please try again with valid update details :");
                        UpdateUserOfUserName(user_name);
                    } else if(date_of_birth_new.isEmpty()) {
                        user_view.Print("Date of birth field missing, please try again with valid update details :");
                        UpdateUserOfUserName(user_name);
                    }
                    else {
                        try {
                            Date date_of_birth_parsed_new = DateFormat(date_of_birth_new);

                            if (date_of_birth_parsed_new.getYear()+1900 < Calendar.getInstance().get(Calendar.YEAR)){
                                int result = UserRegDao.UpdateUser(first_name_new,last_name_new,user_name,date_of_birth_parsed_new);
                                if(result>0) {
                                    throw new RandomException("User details have been updated successfully");
                                }else {
                                    throw new RandomException("User update failed");
                                }
                            }else {
                                user_view.Print("Date provided is beyond current date, please try again with valid update details : ");
                                UpdateUserOfUserName(user_name);
                            }

                        }catch (Exception e){
                            user_view.Print("Date provided is of incorrect format, please try again with valid update details :");
                            UpdateUserOfUserName(user_name);
                        }
                    }
            }
                else {
                    throw  new RandomException("User not registered in the database");
                }
        }
   }
   public void DeleteUserOfUserName(String user_name) throws RandomException {
       UserView user_view = new UserView();

        if(UsersAvailable()) {
            User returned_user = UserRegDao.ReturnUser(user_name);
                if (returned_user!=null) {
                    int out_come = UserRegDao.DeleteUser(user_name);
                    UserRegDao.DeleteUser(user_name);
                    if(out_come >0 ) {
                        user_view.Print("User " + returned_user.getFirstname() + " " + returned_user.getLastname() + " has been deleted successfully");
                    }
                    else {
                        user_view.Print("Deletion operation failed");
                    }

                }
                else {
                    throw new RandomException("The username supplied is not in the database");
                }
        }
        else {
            throw new RandomException("No users in system to delete yet");
        }
   }
   public  void DeleteAllUsers() throws RandomException {
        UserView user_view = new UserView();
       if(UsersAvailable()){
           int out_come = UserRegDao.DeleteAllUsers();
           if(out_come >0 ) {
               user_view.Print("All users have been deleted successfully");
           }
           else {
               throw new RandomException("Delete operation failed");
           }

       }
       else {
           throw  new RandomException("There are no users currently in the system");
       }
   }


}
