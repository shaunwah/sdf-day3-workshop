package com.shaunwah;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, FileNotFoundException
    {
        String input = "";
        String userName;
        Scanner scan = new Scanner(System.in);
        ShoppingCartDB dbHandler = new ShoppingCartDB();

        System.out.println("Welcome to your shopping cart");
        if (args.length > 0) {
            dbHandler.setDbName(args[0]);
        }

        dbHandler.loadDb();

        while (!input.startsWith("quit")) {
            input = scan.next().trim().toLowerCase();
            if (input.startsWith("login")) {
                userName = scan.nextLine().trim();
                dbHandler.setUserName(userName);
                dbHandler.loadUserData();
            } else if (input.startsWith("save")) {
                dbHandler.saveUserData();
            } else if (input.startsWith("users")) {
                String[] users = dbHandler.getUsers();
                if (users.length > 0) {
                    System.out.println("The following users are registered");
                    for (int i = 0; i < users.length; i++) {
                        String user = users[i].substring(0, users[i].length() - 3);
                        System.out.printf("%d. %s\n", i + 1, user);
                    }
                } else {
                    System.out.println("There are no registered users");
                }
            } else if (input.startsWith("list")) {
                List<String> items = dbHandler.getCartItems();
                if (items.size() > 0){
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println((i + 1) + ". " + items.get(i));
                    }
                } else {
                    System.out.println("Your cart is empty");
                }
            } else if (input.startsWith("add")) {
                String items = scan.nextLine().trim();
                dbHandler.addCartItems(items);

            } else if (input.startsWith("delete")) {
                int index = Integer.parseInt(scan.nextLine().trim()) - 1;
                dbHandler.deleteCartItem(index);
            }
        }
        scan.close();
    }
}
