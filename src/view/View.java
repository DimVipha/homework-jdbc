package view;


import model.User;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.*;

public class View {
    // color
    static enum TEXT_COLOR{
        RESET("\u001B\0m"),
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");
        private String colorCode;
        TEXT_COLOR(String colorCode){
            this.colorCode = colorCode;
        }
        public String getColorCode() {
            return colorCode;
        }
    }
    static Scanner input = new Scanner(System.in);
    static CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

public static void showUser(List<User> userList){
    Table tableUser = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);


    tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     ID     ");
    tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Name     ");
    tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Email     ");
    tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Password     ");
    tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Is Deleted     ");
    tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Is Verified     ");
    tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     UUID     ");
    for (User user : userList) {
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getId().toString(), cellStyle);
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getName(), cellStyle);
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getEmail(), cellStyle);
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+hidePassword(user.getPassword()), cellStyle);
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getDeleted().toString(), cellStyle);
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getVerified().toString(), cellStyle);
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getUuid(), cellStyle);
    }
    System.out.println(tableUser.render());
}
    public static void viewApplication(){
        System.out.println("\uD83D\uDC3C".repeat(18)+ "  Hey Hey Welcome to the Application   ".toUpperCase(Locale.ROOT)+"\uD83D\uDC3C".repeat(18));
    }
    public static void renderMenu(){
        Table table = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        table.setColumnWidth(0,20,30);
        table.setColumnWidth(1, 20,30);
        table.setColumnWidth(2,20,30);
        table.setColumnWidth(3,20,30);
        table.setColumnWidth(4,20,30);
        table.setColumnWidth(5,20,30);
        table.setColumnWidth(6,20,30);
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"1)Read All User");
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"|  2)Create User");
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"|  3)Update User");
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"|  4)Delete User");
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"|  5)Search User");
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"|  6)Sort by name");
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"|  7)Exit");
        System.out.println(table.render());
    }
    private static String hidePassword(String password) {
        return "*".repeat(password.length());
    }
    public static int options() {
        int choice;
        System.out.print(" Choose an option: ");
        try {
            choice = Integer.parseInt(input.nextLine());
            if (choice >= 1 && choice <= 7) {
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
    System.out.print("Enter name :");
    userCreate.setName(input.nextLine());
    System.out.print("Enter email :");
    userCreate.setEmail(input.nextLine());
    System.out.print("Enter password :");
    userCreate.setPassword(input.nextLine());
    System.out.print("Enter Verify true or false  :");
    userCreate.setVerified(Boolean.parseBoolean(input.nextLine()));
    userCreate.setDeleted(false);
    userCreate.setUuid(uuidShort);
    return userCreate;
    }

    public static User userDelete(List<User> userList) {
        System.out.print("Enter user id to delete: ");
        int userId = Integer.parseInt(input.nextLine());

        Optional<User> userToRemove = userList.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst();


        if (userToRemove.isPresent()) {
            User user = userToRemove.get();


            System.out.print("Are you sure you want to delete users " + user.getName() + "? (y/n): ");
            String choice = input.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                return user;
            } else {
                System.out.println("Deleting canceled.");
                return null;
            }
        } else {
            System.out.println("User with id " + userId + " not found!!!");
            return null;
        }
    }

    public static int updateUser(List<User> userList){
        System.out.print("Enter user Id to update: ");
        int userId = Integer.parseInt(input.nextLine());
        for(User user : userList){
            if(user.getId().equals(userId)){
                System.out.print("Enter new name: ");
                user.setName(input.nextLine());
                System.out.print("Enter new email: ");
                user.setEmail(input.nextLine());
                System.out.print("Are you sure you want to update the user? (y/n): ");
                String c = input.nextLine().trim().toLowerCase();
                if (c.equals("y")) {
                    System.out.print("Are you sure to change delete? (true for delete): ");
                    user.setDeleted(Boolean.parseBoolean(input.nextLine()));
                    System.out.print("Is user verified? (true for verify): ");
                    user.setVerified(Boolean.parseBoolean(input.nextLine()));
                    System.out.print("Do you want to update password? (Y/N): ");
                    if (input.nextLine().equalsIgnoreCase("y")) {
                        System.out.print("Enter new password: ");
                        user.setPassword(input.nextLine());
                    }
                    return userId;
                } else {
                    System.out.println("Update canceled.");
                    return -1;
                }
            }
        }

        return -1;
    }
    public static void searchUser(List<User> userList){
        System.out.print("Enter user id to search :");
        int userId = Integer.parseInt(input.nextLine());
        Table tableUser = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     ID     ");
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Name     ");
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Email     ");
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Password     ");
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Is Deleted     ");
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     Is Verified     ");
        tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+"     UUID     ");
        Boolean isSearchFound = false;
        for(User user : userList){
            if(user.getId().equals(userId)){
                tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getId().toString(), cellStyle);
                tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getName(), cellStyle);
                tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getEmail(), cellStyle);
                tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+hidePassword(user.getPassword()), cellStyle);
                tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getDeleted().toString(), cellStyle);
                tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getVerified().toString(), cellStyle);
                tableUser.addCell(TEXT_COLOR.PURPLE.colorCode+user.getUuid(), cellStyle);
                isSearchFound = true;
                break;
            }
        }
        if (isSearchFound){
            System.out.println(tableUser.render());
        }else {
            System.out.println("Search not found ");
        }
    }

    public static void showSortedUsers(List<User> userList) {
    showUser(userList);
    }
    public static int shortOptions(){
        int choice;
        System.out.print(">> Choose one option: ");
        try {
            choice = Integer.parseInt(input.nextLine());
            if (choice >= 1 && choice <= 3) {
                return choice;
            } else {
                System.out.println("Please choose an option from [1-3]");
            }
        } catch (NumberFormatException e) {
            System.out.println(" Invalid input. Please enter an integer number from 1-3");
        }
        return 0;
    }
    public static void sortMenu(){
        Table table = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND);
        table.setColumnWidth(0,20,30);
        table.setColumnWidth(1, 20,30);
        table.setColumnWidth(2,20,30);
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"1)Sort by Name ascending order");
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"  2)Sort Name Descending");
        table.addCell(TEXT_COLOR.PURPLE.colorCode+"  3)Exit");
        System.out.println(table.render());
    }


}
