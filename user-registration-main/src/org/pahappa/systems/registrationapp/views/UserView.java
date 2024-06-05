package org.pahappa.systems.registrationapp.views;
import  org.pahappa.systems.registrationapp.services.UserService;

import java.lang.reflect.Method;
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
                        Method m = UserService.class.getDeclaredMethod("registerUser");
                        m.setAccessible(true);
                        m.invoke(user_service);
                        break;
                    case 2:
                        Method m1 = UserService.class.getDeclaredMethod("displayAllUsers");
                        m1.setAccessible(true);
                        m1.invoke(user_service);
                        break;
                    case 3:
                        //getUserOfUsername();
                        Method m2 = UserService.class.getDeclaredMethod("getUserOfUsername");
                        m2.setAccessible(true);
                        m2.invoke(user_service);
                        break;
                    case 4:
                        //updateUserOfUsername();
                        Method m3 = UserService.class.getDeclaredMethod("updateUserOfUsername");
                        m3.setAccessible(true);
                        m3.invoke(user_service);
                        break;
                    case 5:
                       // deleteUserOfUsername();
                        Method m4 = UserService.class.getDeclaredMethod("deleteUserOfUsername");
                        m4.setAccessible(true);
                        m4.invoke(user_service);
                        break;
                    case 6:
                        //deleteAllUsers();
                        Method m5 = UserService.class.getDeclaredMethod("displayAllUsers");
                        m5.setAccessible(true);
                        m5.invoke(user_service);
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
            }
        }
    }


}
