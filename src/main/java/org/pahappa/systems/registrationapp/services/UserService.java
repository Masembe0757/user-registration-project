package org.pahappa.systems.registrationapp.services;

import java.awt.image.RasterFormatException;
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


    public boolean hasSpecialCharacters(String s){
        boolean hasCharacter = false;
        for (int i = 0; i < s.length(); i++) {
            // Checking the character for not being a letter,digit or space
            if (!Character.isDigit(s.charAt(i))
                    && !Character.isLetter(s.charAt(i))
                    && !Character.isWhitespace(s.charAt(i))) {
                hasCharacter =true;

            }
        }
        return hasCharacter;
    }

    //Generic method to check if username has only digits
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
    //Generic method to check if name provided has digits in it
    public  boolean hasDigits(String str){
        boolean hasDigits = false;
        for(int i =0 ; i < str.length(); i++){
            if(Character.isDigit(str.charAt(i))){
                hasDigits =  true;
            }
        }
        return hasDigits;
    }

    //Generic method to check date format
    public Date dateFormat(String date) throws ParseException {
        DateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println();
        return dateFormat.parse(formatter.parse(date).toString().split(" ")[3]);

    }

    //Generic method to check if there are users in the database
    public boolean usersAvailable(){
        List<User> usersList = UserRegDao.returnAllUsers();
        return !usersList.isEmpty();
    }
   public void addUser(String firstName, String lastName, String userName, String dateOfBirth) {
       User newUser = new User();
       UserView userView = new UserView();
       if(firstName.isEmpty() || hasDigits(firstName) || hasSpecialCharacters(firstName) ){
           UserView.Print("First name field missing or has digits or special characters in it, please refill the field below :");
           addUser(userView.Scan(),lastName,userName,dateOfBirth);
       }
       else if(hasDigits(lastName) || hasSpecialCharacters(lastName)){
           UserView.Print("Last name field has digits or special characters  in it, please refill the field correctly below :");
           addUser(firstName,userView.Scan(),userName,dateOfBirth);
       } else if (userName.isEmpty() || userName.length() < 6 || hasSpecialCharacters(userName) ) {
           UserView.Print("User name field missing or characters less than 6 or has special characters , please refill the field correctly below :");
           addUser(firstName,lastName,userView.Scan(),dateOfBirth);
       } else if (Character.isDigit(userName.charAt(0))) {
           UserView.Print("User name field  can not start with a digit, please refill the field correctly below :");
           addUser(firstName,lastName,userView.Scan(),dateOfBirth);
       } else if (onlyDigits(userName, userName.length())) {
           UserView.Print("User name field can not contain only digits, please refill the field correctly below :");
           addUser(firstName,lastName,userView.Scan(),dateOfBirth);
       } else if(dateOfBirth.isEmpty()) {
           UserView.Print("Date of birth field missing, please refill the field correctly below with correct format:");
           addUser(firstName, lastName, userName, userView.Scan());
       }else {
            try {
                Date dateOfBirthParsed = dateFormat(dateOfBirth);
                User returnedUser = UserRegDao.returnUser(userName);
                if (returnedUser!= null) {
                    UserView.Print("User name already taken please enter new user name below : ");
                    addUser(firstName,lastName, userView.Scan(), dateOfBirth);
                } else {
                        if(dateOfBirthParsed.getYear()+1900 < Calendar.getInstance().get(Calendar.YEAR)) {
                            newUser.setDateOfBirth(dateOfBirthParsed);
                            newUser.setFirstname(firstName);
                            newUser.setLastname(lastName);
                            newUser.setUsername(userName);
                            UserRegDao.saveUser(newUser);
                            UserView.Print("User has been registered successfully (*_*) ");

                        }
                        else {
                            UserView.Print("Date provided is beyond current date, please enter correct date of birth bellow:");
                            addUser(firstName, lastName, userName, userView.Scan());
                        }

                    }

            }catch (Exception e){
                UserView.Print("Date provided is of incorrect format yyyy-mm-dd ,invalid month of the year or invalid day of the month, please refill the field correctly below :");
                addUser(firstName,lastName,userName, userView.Scan());
            }


       }

   }
   public void returnAllUsers() throws RandomException {
       List<User> usersList = UserRegDao.returnAllUsers();
       if(usersAvailable()) {
           UserView.Print("\n <------------------- System has registered the following users -----------------> \n");
           for(User user : usersList){
               System.out.println(user);
           }
       }
       else{
           throw new RandomException("System has no registered users currently");
       }
   }

   public  void returnUserOfUserName(String user_name) throws RandomException {

        if(usersAvailable()) {
            User returnedUser = UserRegDao.returnUser(user_name);
            if(returnedUser == null){
                throw new RandomException("The user is not registered in the system");
            }
            else {
                System.out.println(returnedUser.toString());
            }
        }else {
            throw new RandomException("System is currently empty, no users to return");
        }
   }
   public  void updateUserOfUserName(String user_name) throws ParseException, RandomException {
       UserView userView = new UserView();
        if(!usersAvailable()) {
            throw new RandomException("No users to update, system currently empty");
        }
        else {
                User returnedUser = UserRegDao.returnUser(user_name);
                if (returnedUser!=null) {
                    UserView.Print("Enter users new first name (required , no digits or special characters)");
                    String first_name_new = userView.Scan();
                    UserView.Print("Enter users new last name (optional , no digits or special characters)");
                    String last_name_new = userView.Scan();
                    UserView.Print("Enter users new date of birth format yyyy-mm-dd (not beyond current date, right month of year, right day of month) ");
                    String date_of_birth_new = userView.Scan();

                    //update validation

                    if(first_name_new.isEmpty() || hasDigits(first_name_new) || hasSpecialCharacters(first_name_new) ){
                        UserView.Print("First name field missing, has digits or special characters  in it, please try again with valid update details :");
                        updateUserOfUserName(user_name);
                    }
                    else if(hasDigits(last_name_new) || hasSpecialCharacters(last_name_new)){
                        UserView.Print("Last name field has digits or special characters in it, please try again with valid update details :");
                        updateUserOfUserName(user_name);
                    } else if(date_of_birth_new.isEmpty()) {
                        UserView.Print("Date of birth field missing, please try again with valid update details :");
                        updateUserOfUserName(user_name);
                    }
                    else {
                        try {
                                Date dateOfBirthParsedNew = dateFormat(date_of_birth_new);

                            if (dateOfBirthParsedNew.getYear()+1900 < Calendar.getInstance().get(Calendar.YEAR)){
                                int result = UserRegDao.updateUser(first_name_new,last_name_new,user_name,dateOfBirthParsedNew);
                                if(result>0) {
                                    throw new RandomException("User details have been updated successfully");
                                }else {
                                    throw new RandomException("User update failed");
                                }
                            }else {
                                UserView.Print("Date provided is beyond current date, please try again with valid update details : ");
                                updateUserOfUserName(user_name);
                            }

                        }catch (Exception e){
                            UserView.Print("Date provided is of incorrect format, please try again with valid update details :");
                            updateUserOfUserName(user_name);
                        }
                    }
            }
                else {
                    throw  new RandomException("User not registered in the database");
                }
        }
   }
   public void deleteUserOfUserName(String user_name) throws RandomException {
        if(usersAvailable()) {
            User returnedUser = UserRegDao.returnUser(user_name);
                if (returnedUser!=null) {
                    int out_come = UserRegDao.deleteUser(user_name);
                    UserRegDao.deleteUser(user_name);
                    if(out_come >0 ) {
                        UserView.Print("User " + returnedUser.getFirstname() + " " + returnedUser.getLastname() + " has been deleted successfully");
                    }
                    else {
                        UserView.Print("Deletion operation failed");
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
   public  void deleteAllUsers() throws RandomException {
       UserView userView = new UserView();

       if(usersAvailable()){
           UserView.Print("Are you sure you want to delete all users? 1-yes 0-no");
           int confirmation = Integer.parseInt(userView.Scan());
           if(confirmation==1){
               int outCome = UserRegDao.deleteAllUsers();
               if(outCome >0 ) {
                   UserView.Print("All users have been deleted successfully");
               }
               else {
                   throw new RandomException("Delete operation failed");
               }
           }else {
                    throw new RandomException("Operation cancelling successful, thanks for using our system");
           }


       }
       else {
           throw  new RandomException("There are no users currently in the system");
       }
   }


}
