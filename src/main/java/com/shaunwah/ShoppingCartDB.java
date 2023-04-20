package com.shaunwah;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDB {
    private String dbName = "db";
    private String userName;
    private String userDataPath = dbName + File.separator + userName + ".db";
    private List<String> cartItems;

    public ShoppingCartDB() {
        this.cartItems = new ArrayList<String>();
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        this.userDataPath = dbName + File.separator + userName + ".db";
    }

    public void loadDb() {
        File newDb = new File(dbName);
        if (!newDb.exists()) {
            newDb.mkdir();
            System.out.printf("%s does not exist, creating one for you\n", dbName);
        }
    }

    public void loadUserData() throws IOException, FileNotFoundException {
        cartItems.clear();
        File userData = new File(userDataPath);
        if (userData.exists()) {
            FileReader fileReader = new FileReader(userDataPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if (userData.length() > 0) {
                System.out.printf("%s, your cart contains the following items\n", userName);
                String item;
                int index = 1; // For list points
                while ((item = bufferedReader.readLine()) != null) {
                    cartItems.add(item);
                    System.out.printf("%d. %s\n", index++, item);
                }
            } else {
                System.out.printf("%s, your cart is empty\n", userName);
            }
            bufferedReader.close();
            fileReader.close();
        } else {
            userData.createNewFile();
            System.out.printf("%s not found, creating new user\n", userName);
        }
    }

    public void saveUserData() throws IOException, FileNotFoundException {
        File userData = new File(userDataPath);
        if (!userData.exists()) {
            userData.createNewFile();
            System.out.printf("%s not found, creating new user\n", userName);
        }
        if (cartItems.size() > 0) {
            FileWriter fileWriter = new FileWriter(userDataPath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String item: cartItems) {
                bufferedWriter.write(item);
                bufferedWriter.append("\n");
            }
            System.out.println("Your cart has been saved");
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } else {
            System.out.println("Your cart is empty");
        }
    }

    public String[] getUsers() {
        File dir = new File(dbName);
        String[] dirFiles = dir.list();
        return dirFiles;
    }

    public void addCartItems(String items) {
        if (items.length() > 0) {
            String[] itemsArr = items.split(",");
            for (String item: itemsArr) {
                item = item.trim();
                if (cartItems.contains(item)) {
                    System.out.printf("You have %s in your cart\n", item);
                } else {
                    cartItems.add(item);
                    System.out.printf("%s added to cart\n", item);
                }
            }
        }
    }

    public List<String> getCartItems() {
        return cartItems;
    }

    public void deleteCartItem(int index) {
        if (!(index < 0) && !(index >= cartItems.size())) {
            String removedItem = cartItems.get(index);
            cartItems.remove(index);
            System.out.printf("%s removed from cart\n", removedItem);
        } else {
            System.out.println("Incorrect item index");
        }
    }
    
}
