package org.pahappa.systems.registrationapp.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import  org.pahappa.systems.registrationapp.models.User;

public class UserService {
    Scanner scn = new Scanner(System.in);
    private void registerUser() throws ParseException {
        User newUser = new User();
        System.out.println("Enter users first name");
        String first_name = scn.nextLine();
        newUser.setFirstname(first_name);
        System.out.println("Enter users last name");
        String last_name = scn.nextLine();
        newUser.setLastname(last_name);
        System.out.println("Enter users username");
        String user_name = scn.nextLine();
        newUser.setUsername(user_name);
        System.out.println("Enter users date of birth format mm/dd/yyyy ");
        String date_of_birth = scn.nextLine();
        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
        Date date_of_birt = df.parse(date_of_birth);
        newUser.setDateOfBirth(date_of_birt);

        boolean user_present = false;
        for(User x : User.usrnam){
            if(x.equals(newUser)){
                user_present = true;
            }else{

                user_present= false;
            }
        }
        if(user_present){
            System.out.println("User already exists in data base ");
        }
        else {
            User.usrnam.add(newUser);
            System.out.println("User has been registered");
        }

    }

    private void displayAllUsers() {
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

    private void getUserOfUsername() {
        System.out.println("Enter user's username");
        String user_name = scn.nextLine();
        for(User x : User.usrnam){
            if(x.getUsername().equals(user_name)){
                System.out.println("\n User "+ user_name+ " has details : full name "+ x.getFirstname()+" "+x.getLastname()+ " and date of birth"+ x.getDateOfBirth());
            }
            else{
                System.out.println("The user is not registered in the system");
            }
        }
    }

    private void updateUserOfUsername() throws ParseException {
        System.out.println("Enter user's username");
        String user_name = scn.nextLine();

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

    private void deleteUserOfUsername() {
        System.out.println("Enter user's username you with to delete");
        String user_name = scn.nextLine();
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

    private void deleteAllUsers() {
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
