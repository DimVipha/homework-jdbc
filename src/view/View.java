package view;


import model.User;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.*;


public class View {
    static Scanner scanner = new Scanner(System.in);
    static CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);

public static void showUser(List<User> userList){
    Table tableUser = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
//    Table tableUser = new Table(7, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

    tableUser.addCell("     ID     ");
    tableUser.addCell("     Name     ");
    tableUser.addCell("     Email     ");
    tableUser.addCell("     Password     ");
    tableUser.addCell("     Is Deleted     ");
    tableUser.addCell("     Is Verified     ");
    tableUser.addCell("     UUID     ");
    for (User user : userList) {
        tableUser.addCell(user.getId().toString(), cellStyle);
        tableUser.addCell(user.getName(), cellStyle);
        tableUser.addCell(user.getEmail(), cellStyle);
        tableUser.addCell(hidePassword(user.getPassword()), cellStyle);
        tableUser.addCell(user.getIsDeleted().toString(), cellStyle);
        tableUser.addCell(user.getIsVerified().toString(), cellStyle);
        tableUser.addCell(user.getUuid(), cellStyle);
    }
    System.out.println(tableUser.render());
}
    public static void viewApplication(){
        System.out.println("=".repeat(30));
        System.out.println("Application".toUpperCase(Locale.ROOT));

    }
    public static void renderMenu(){
        Table table = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        table.setColumnWidth(0,25,35);
        table.setColumnWidth(1, 25,35);
        table.setColumnWidth(2,25,35);
        table.setColumnWidth(3,25,35);
        table.setColumnWidth(4,25,35);
        table.setColumnWidth(5,25,35);
        table.addCell("1)Read All User");
        table.addCell("|  2)Create User");
        table.addCell("|  3)Update User");
        table.addCell("|  4)Delete User");
        table.addCell("|  5)Search User");
        table.addCell("|  6)Exit");
        System.out.println(table.render());
    }
    private static String hidePassword(String password) {
        return "*".repeat(password.length());
    }
    public static int options() {
        int choice;
        System.out.print(">> Choose one option: ");
        try {
            choice = Integer.parseInt(scanner.nextLine());
            if (choice >= 1 && choice <= 6) {
                return choice;
            } else {
                System.out.println("Please choose an option between [1-6]");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return 0;
    }
    public static User createUser(){
    User userCreate = new User();
    UUID uuid = UUID.randomUUID();
    String uuidShort= uuid.toString().substring(0,6);
    System.out.print("Enter Name :");
    userCreate.setName(scanner.nextLine());
    System.out.print("Enter Email :");
    userCreate.setEmail(scanner.nextLine());
    System.out.print("Enter Password :");
    userCreate.setPassword(scanner.nextLine());
    System.out.print("Enter Verify ture or false  :");
    userCreate.setIsVerified(Boolean.parseBoolean(scanner.nextLine()));
    userCreate.setIsDeleted(false);
    userCreate.setUuid(uuidShort);
    return userCreate;
    }

public static Integer userDelete(List<User> userList){
    System.out.print("Enter user id to delete: ");
    int userId = Integer.parseInt(scanner.nextLine());

    // Find the user by userId
    Optional<User> userToRemove = userList.stream()
            .filter(user -> user.getId().equals(userId))
            .findFirst();

    // Check if user with given userId exists
    if (userToRemove.isPresent()) {
        User user = userToRemove.get();

        // Ask for confirmation
        System.out.print("Are you sure you want to delete user " + user.getName() + "? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        // Process confirmation
        if (confirmation.equals("y")) {
            // Remove the user if confirmed
            userList.remove(user);
            System.out.println("User " + user.getName() + " deleted successfully.");
        } else {
            System.out.println("Deletion canceled.");
        }
    } else {
        System.out.println("User with id " + userId + " not found.");
    }

    return userId;
}
    public static int updateUser(List<User> userList){
        System.out.print("Enter user Id to update: ");
        int userId = Integer.parseInt(scanner.nextLine());
        for(User user : userList){
            if(user.getId().equals(userId)){
                System.out.print("Enter new name: ");
                user.setName(scanner.nextLine());
                System.out.print("Enter new email: ");
                user.setEmail(scanner.nextLine());
                System.out.print("Are you sure you want to update the user? (y/n): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("y")) {
                    System.out.print("Are you sure to change delete? (true for delete, false for no delete): ");
                    user.setIsDeleted(Boolean.parseBoolean(scanner.nextLine()));
                    System.out.print("Is user verified? (true for verify, false for no verify): ");
                    user.setIsVerified(Boolean.parseBoolean(scanner.nextLine()));
                    System.out.print("Do you want to update password? (Y/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        System.out.print("Enter new password: ");
                        user.setPassword(scanner.nextLine());
                    }
                    return userId;
                } else {
                    System.out.println("Update canceled.");
                    return -1; // Return -1 to indicate update canceled
                }
            }
        }

        return -1; // Return -1 to indicate user not found
    }



}
