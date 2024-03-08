import controller.UserController;
import view.View;

import java.util.Scanner;

public class Main {
    static UserController userController = new UserController();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        View.viewApplication();
//        userController.getUsers();
        do {
            View.renderMenu();
           switch (View.options()){
               case 1->{
                   userController.getUsers();
                   System.out.print("Press enter to continue...");
                   scanner.nextLine();
               }
               case 2->{
                   userController.addUser();
                   System.out.print("Press enter to continue...");
                   scanner.nextLine();
               }
               case 3->{
                   userController.updateUser();
                   System.out.print("Press enter to continue...");
                   scanner.nextLine();
               }
               case 4->{
                   userController.deleteUsers();
                   System.out.print("Press enter to continue...");
                   scanner.nextLine();
               }
               case 5->{

               }
               case 6->{
                   System.exit(0);
               }
               default -> {
                   System.out.println("Invalid options");
               }
           }
        }while (true);


    }
}