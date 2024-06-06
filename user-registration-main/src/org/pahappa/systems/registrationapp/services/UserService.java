package org.pahappa.systems.registrationapp.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.pahappa.systems.registrationapp.models.User;

public class UserService {
    Scanner scn = new Scanner(System.in);
    User newUser = new User();
   public void addUser(String first_name, String last_name, String user_name, Date date_of_birth){
       boolean missing_field = false;

       if(first_name.isEmpty()){
           System.out.println("Seems you are missing the first name field, please fill all fields correctly");
       }
       else if(last_name.isEmpty()){
           System.out.println("Seems you are missing the last name field, please fill all fields correctly");
       } else if (user_name.isEmpty()) {
           System.out.println("Seems you are missing the user name field, please fill all fields correctly");
       }
       else {

           newUser.setFirstname(first_name);
           newUser.setLastname(last_name);
           newUser.setUsername(user_name);
           newUser.setDateOfBirth(date_of_birth);

           boolean user_present = false;


           for (User x : User.usrnam) {
               user_present = x.equals(newUser);
           }
           if (user_present) {
               System.out.println("User already exists in data base ");
           } else {
               User.usrnam.add(newUser);
               System.out.println("User has been registered");

           }
       }

   }
   public void  returnAllUsers(){
       if(!User.usrnam.isEmpty()) {
           for (User x : User.usrnam) {
               System.out.println("System has registered the following users");
               System.out.println(" User " + x.getUsername() + " has names " + x.getFirstname() + " " + x.getLastname() + " and has a date of birth  of " + x.getDateOfBirth());
           }
       }
       else{
           System.out.println("System has no registered users currently");
       }
   }

   public  void returnUserOfUserName(String user_name){
       for(User x : User.usrnam){
           if(x.getUsername().equals(user_name)){
               System.out.println("\n User "+ user_name+ " has details : full name "+ x.getFirstname()+" "+x.getLastname()+ " and date of birth"+ x.getDateOfBirth());
           }
           else{
               System.out.println("The user is not registered in the system");
           }
       }
   }

   public  void updateUserOfUserName(String user_name) throws ParseException {
       for(User x : User.usrnam){
           if(x.getUsername().equals(user_name)){
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
               DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
               Date date_of_birt_new_parsed = df.parse(date_of_birth_new);
               x.setDateOfBirth(date_of_birt_new_parsed);
               System.out.println("User details have been updated successfully");

           }
           else{
               System.out.println("The user is not registered in the system");
           }
       }
   }
   public void deleteUserOfUserName(String user_name){
       for(User x : User.usrnam){
           if(x.getUsername().equals(user_name)){
               User.usrnam.remove(x);
               System.out.println("\n User "+ x.getFirstname()+" "+x.getLastname()+ " has been deleted from system ");
           }
           else{
               System.out.println("The user is not registered in the system");
           }
       }
   }
   public  void  deleteAllUsers(){
       if(User.usrnam.isEmpty()){
           System.out.println("There are no users currently in the system");
       }
       else {
           User.usrnam.clear();
           System.out.println("All users have been deleted successfully");
       }
   }

    public static  void main(String args[]){

    }
}
