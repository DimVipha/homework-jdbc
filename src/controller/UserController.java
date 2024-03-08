package controller;

import Repository.UserRepo;
import Repository.UserRepoImpl;
import model.User;
import view.View;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static final UserRepo userRepo = new UserRepoImpl();
    static List<User> users = userRepo.getAllUsers();
    public void getUsers(){
        View.showUser(userRepo.getAllUsers());
    }
    public void addUser(){
        User userCreated = View.createUser();
        userRepo.createUser(userCreated);
    }
    public void deleteUsers(){
        int userId = View.userDelete(users);
        userRepo.deleteUser(userId);
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
}
