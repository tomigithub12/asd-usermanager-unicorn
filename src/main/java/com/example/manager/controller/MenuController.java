package com.example.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Scanner;


@Service
public class MenuController {



    protected ExitMenu exitMenu = new ExitMenu();

    @Autowired
    private UserController uc = new UserController();



    Scanner scn = new Scanner(System.in);
    int cnt = 0;

    public void showMenu() {
        System.out.println("------------------------------------------");
        System.out.println("++++++ Welcome to the user manager ++++++");
        System.out.println("------------------------------------------");
        System.out.println("Please enter number to choose an option: \n (1)Register \n (2)Login \n (3)Exit \n");

        try (Scanner scn = new Scanner(System.in)) {
            int userInput = scn.nextInt();

            switch (userInput) {
                case 1:
                    registerMenu();
                    break;
                case 2:
                    loginMenu();
                    break;
                case 3:
                    System.out.println("Closed UserManager!");
                    exitMenu.executeSystemExit();
                default:
                    System.out.println("Invalid Input! Restarting....");
                    showMenu();
            }
        }
        catch (Exception e){
            System.out.println("\nError while getting user input!\n Shutting down User Manager...\n");
        }
    }

    public void registerMenu() {
        System.out.println("--- Register Menu ---");

        System.out.println("Please enter firstname:");
        String firstname = scn.next();
        System.out.println("Please enter lastname:");
        String lastname = scn.next();
        System.out.println("Please enter username:");
        String username = scn.next();

        while (uc.checkIfUsernameTaken(username)) {
            System.out.println("This username is already taken. Try another name: \n");
            username = scn.next();
        }

        System.out.println("Please enter password:");
        String password = scn.next();



        uc.register(firstname, lastname, username, password);

        System.out.println("User was successfully created.");
        showMenu();
    }

    public void loginMenu() {

        System.out.println("--- Login Menu ---");


        System.out.println("Please enter username:");
        String username = scn.next();
        System.out.println("Please enter password:");
        String password = scn.next();


        while (!uc.login(username, password)) {
            System.out.println("Username or password incorrect. Try again!\n");
            System.out.println("Please enter username:");
            username = scn.next();
            System.out.println("Please enter password:");
            password = scn.next();

        }
        loggedInMenu(username, password);
    }

    public void loggedInMenu(String username, String password) {

        System.out.println("\n--- Logged in as " + username + "---\n");
        System.out.println("Choose Options: \n (1)Change password \n (2)Delete Account \n (3)Logout");

        int userInput = scn.nextInt();
        switch (userInput) {
            case 1:
                changePasswordMenu(username, password);
                break;
            case 2:
                deleteAccountMenu(username);
                break;
            case 3:
                uc.logout(username, password);
                showMenu();
        }

    }

    public void changePasswordMenu(String username, String password) {
        boolean passwordResetSucess = false;
        String newPassword = null;
        String confirmNewPassword = null;

        while(!passwordResetSucess){
            String confirmPassword = "";

            while (!confirmPassword.equals(password)) {
                System.out.println("Please enter your current password to be able to change it: ");
                confirmPassword = scn.next();
            }

            System.out.println("Please enter your new Password: ");
            newPassword = scn.next();
            System.out.println("Please confirm the new Password: ");
            confirmNewPassword = scn.next();

            if(newPassword.equals(confirmNewPassword)){
                passwordResetSucess = true;
                uc.changePassword(username, newPassword);
                System.out.println("Password changed successfully!");
                loggedInMenu(username, newPassword);
            }
            else{
                System.out.println("Passwords do not match! Try again!\n");
            }
        }
    }

    public void deleteAccountMenu(String username) {
        System.out.println("\n--- Account Deletion ---");

        System.out.println("Please enter your password for account deletion:");
        String passwordIn = scn.next();

        while (!uc.login(username, passwordIn)) {
            System.out.println("Password is incorrect. Try again!");
            passwordIn = scn.next();
        }
        uc.deleteAccount(username);
        System.out.println("--- Account log out successful ! \n --- Your account has been deleted successfully! --- \n");
        showMenu();
    }
}
