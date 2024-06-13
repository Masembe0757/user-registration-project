package org.pahappa.systems.registrationapp.views;
import org.pahappa.systems.registrationapp.exception.RandomException;
import  org.pahappa.systems.registrationapp.services.UserService;

import java.text.ParseException;
import java.util.Scanner;

public class UserView {
    UserService user_service = new UserService();
    private final Scanner scanner;

    public UserView(){
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("********* User Registration System *********");
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Register a user");
            System.out.println("2. Display all users");
            System.out.println("3. Get a user of username");
            System.out.println("4. Update user details of username");
            System.out.println("5. Delete User of username");
            System.out.println("6. Delete all users");
            System.out.println("7. Exit");
            try{
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        displayAllUsers();
                        break;
                    case 3:
                        getUserOfUsername();
                        break;
                    case 4:
                        updateUserOfUsername();
                        break;
                    case 5:
                        deleteUserOfUsername();

                        break;
                    case 6:
                        deleteAllUsers();
                        break;
                    case 7:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (Exception e){
                System.out.println("Invalid choice. Please try again.");
                scanner.nextLine(); // Consume the newline character
            }catch (RandomException m){
                System.out.println(m.getMessage());
            }

        }
    }


    private void registerUser() {

        System.out.println("Enter users first name (Digits not allowed in this field)");
        String first_name = scanner.nextLine();

        System.out.println("Enter users last name (Digits not allowed in this field)");
        String last_name = scanner.nextLine();

        System.out.println("Enter users username, (Should not start with a digit, not less than 6 characters, not digits only)");
        String user_name = scanner.nextLine();

        System.out.println("Enter users date of birth format yyyy-mm-dd ");
        String date_of_birth = scanner.nextLine();

        user_service.AddUser(first_name, last_name, user_name, date_of_birth);

    }

    private void displayAllUsers() throws RandomException {
        user_service.ReturnAllUsers();
    }

    private void getUserOfUsername() throws RandomException {
        System.out.println("Enter user's username");
        String user_name = scanner.nextLine();
        user_service.ReturnUserOfUserName(user_name);
    }

    private void updateUserOfUsername() throws ParseException, RandomException {
        System.out.println("Enter user's username");
        String user_name = scanner.nextLine();
        user_service.UpdateUserOfUserName(user_name);

    }
    private void deleteUserOfUsername() throws RandomException {
        System.out.println("Enter user's username you wish to delete");
        String user_name = scanner.nextLine();
        user_service.DeleteUserOfUserName(user_name);
    }

    private void deleteAllUsers() throws RandomException {
        user_service.DeleteAllUsers();
    }

    public String Scan(){
        return scanner.nextLine();
    }
    public void Print(String print_out){
        System.out.println(print_out);
    }


}
