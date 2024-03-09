package controller;

import Repository.UserRepo;
import Repository.UserRepoImpl;
import model.User;
import view.View;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserRepo userRepo = new UserRepoImpl();
    static List<User> users = userRepo.getAllUsers();
    public void getUsers(){
        View.showUser(userRepo.getAllUsers());
    }
    public void addUser(){
        User createdUser = View.createUser();
        userRepo.createUser(createdUser);
    }
    public void deleteUsers() {
        User user = View.userDelete(users);
        if (user != null) {
            userRepo.deleteUser(user.getId());
        }
    }

    public void updateUser(){
        int userId = View.updateUser(users);
        if(userId!=-1){
            for(User user : users){
                if(user.getId().equals(userId)){
                    userRepo.updateUser(user);
                    System.out.println("User with id " + user.getId() + " updated successfully");
                    break;
                }
            }
        }
        else {
            System.out.println("User not found");
        }
    }
    public void searchUsers(){
        View.searchUser(users);
    }

    public void getAllUsersSortedByName(boolean ascending) {
        List<User> userList = userRepo.getAllUsersSortedByName(ascending);
        View.showSortedUsers(userList);
    }
    public void handleSortByName() {
        do {
            View.sortMenu();
            switch (View.shortOptions()) {
                case 1: {
                    getAllUsersSortedByName(true);
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                }
                case 2: {
                    getAllUsersSortedByName(false);
                    System.out.print("Press enter to continue...");
                    scanner.nextLine();
                    break;
                }
                case 3:{
                    System.exit(0);
                   break;
                }
                default :{
                    System.out.println("Invalid option. Please try again.");
                }
            }
        } while (true);

    }
}
