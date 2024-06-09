package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class VendingMachine {

    //initial balance of $0
    private BigDecimal balance = BigDecimal.valueOf(0.00);

    public void displayItems(){
       //top description bar
        System.out.format("\n%-5s %-20s %s  %-5s", "Slot", "Name", "Price", "Quantity");

        //actual values
        for (Product item : inventory.values()) {

            System.out.format("\n%-5s %-20s $%4.2f   %-5s", item.getSlot(), item.getName(), item.getPrice(), item.getQuantity());

        }

    }

    //prepare linked hashmap for inventory
    public Map<String, Product> inventory = new LinkedHashMap<>();

    public void loadInventory(){

        //access file
        File inputFile = new File("vendingmachine.csv");

        //read file and populate LinkedHashMap
        try (Scanner inputScanner = new Scanner(inputFile)){

            //break up elements based on pipe |
            while (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
                String[] elements = line.split("\\|");
                String slot = elements[0];
                String name = elements[1];
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(elements[2]));
                String type = elements[3];

                //place elements into hashmap
                inventory.put(slot, new Product(slot, name, price, type));

            }
        } catch (IOException e){
            System.err.println("Inventory file not found: " + e.getMessage());
        }

    }

    public void feedMoney(BigDecimal amount) {

        balance = balance.add(amount);

        String activity = String.format("FEED MONEY: $%s $%s", amount, balance);

        //LOG ACTIVITY
        try {
            logger(activity);
        } catch (IOException e) {
            System.err.println("Error writing to log..." + e.getMessage());
        }

    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void purchaseProduct(String expectedSlot){
        Product product = inventory.get(expectedSlot);
        String activity = "";

        if (inventory.containsKey(expectedSlot)) {

            if (product.getQuantity() == 0) {

                //formatting for OUT OF STOCK
                System.out.println();
                System.out.println("x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x");
                System.out.println();
                System.out.println("Item is OUT OF STOCK. Please select a different option.");
                System.out.println();
                System.out.println("x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x");
                System.out.println();

                //redisplay items so user can make informed choice
                displayItems();

                System.out.println();

                //if user doesn't have enough money
            } else if (balance.compareTo(product.getPrice()) < 0) {
                    System.out.println("Insufficient funds! Please feed more money or select a different option.");

             //if user fulfills requirement for purchase
            } else {

                //removes money from balance
                balance = balance.subtract(product.getPrice());

                //removes quantity and dispenses
                product.setQuantity(product.getQuantity() -1);

                //calls for type specific yummy message
                System.out.println();
                getMessage(product);

                //thanks user for purchase and tells them what they bought, how much they spent, and how much money they have remaining
                System.out.format("Thank you for your purchase of: %s for $%.2f!",product.getName(),product.getPrice());
                System.out.println("\n\nBalance remaining: $" + getBalance());

                //LOG ACTIVITY
                activity = String.format("%s %s $%.2f $%.2f",product.getName(), product.getSlot(), product.getPrice(), getBalance());

                try {
                    logger(activity);
                } catch (IOException e) {
                    System.err.println("Error writing to log..." + e.getMessage());
                }
            }
        } else {
            System.out.println("Please check your option.");
        }
    }

    //check out, issue change, return balance to $0
    public void finishTransaction() {

        int quarterCount = 0;
        int dimeCount = 0;
        int nickleCount = 0;

        BigDecimal change = balance;

        //prepare empty log entry
        String activity = "";

        //Quarters
        while (getBalance().compareTo(BigDecimal.valueOf(0.25)) >= 0) {
            balance = balance.subtract(BigDecimal.valueOf(0.25));
            quarterCount++;
        }

        //Dimes
        while (getBalance().compareTo(BigDecimal.valueOf(0.10)) >= 0) {
            balance = balance.subtract(BigDecimal.valueOf(0.10));
            dimeCount++;
        }

        //Nickels
        while (getBalance().compareTo(BigDecimal.valueOf(0.05)) >= 0) {
            balance = balance.subtract(BigDecimal.valueOf(0.05));
            nickleCount++;
        }

        //message to user for how much change they get
        System.out.println("Here is your change: " + quarterCount + " quarters, " + dimeCount + " dimes, " + nickleCount + " nickles.");
        activity = String.format("GIVE CHANGE: $%.2f $%.2f", change, balance);

        //LOG ACTIVITY
        try {
            logger(activity);
        } catch (IOException e) {
            System.err.println("Error writing to log..." + e.getMessage());
        }

    }


    //Different messages for different foods
    public void getMessage(Product product){

        String type = product.getType();

        if(type.equals("Chip") || type.equals("chip")){
            System.out.println("Crunch Crunch, It's Yummy!");

        } else if (type.equals("Candy") || type.equals("candy")) {
            System.out.println("Munch Munch, Mmm Mmm Good!");

        } else if (type.equals("Drink") || type.equals("drink")) {
            System.out.println("Glug Glug, Chug Chug!");

        } else if (type.equals("Gum") || type.equals("gum")) {
            System.out.println("Chew Chew, Pop!");

        } else {
            System.out.println("Invalid type within csv file.");
        }

    }

    //writes activities to log file
    public void logger(String activity) throws IOException {

        File log = new File("Log.txt");
        PrintWriter logWriter = null;

    try {

        if (log.exists()) {
            logWriter = new PrintWriter(new FileWriter(log, true));

        } else {
            logWriter = new PrintWriter(log);
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String timeStamp = String.format("%tm/%td/%tY %tI:%tM:%tS %Tp", date, date, date, time, time, time, time);

        logWriter.append(timeStamp + " " + activity + "\n");

    } catch (IOException e) {
        System.err.println("Error writing to file..." + e.getMessage());
    }

        logWriter.flush();
        logWriter.close();

    }

}

