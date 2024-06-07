package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class VendingMachine {
    private BigDecimal balance = BigDecimal.valueOf(0.00);
    // may need to be bigD

    public void displayItems(){
        System.out.format("\n%-5s %-20s %s  %-5s", "Slot", "Name", "Price", "Quantity");
        for (Product item : inventory.values()) {
            // "%s %-20s %4.2f %s"
            //System.out.println(  item.getSlot() + item.getName() + item.getPrice() + item.getQuantity());

            System.out.format("\n%-5s %-20s $%4.2f   %-5s", item.getSlot(), item.getName(), item.getPrice(), item.getQuantity());
        }

    }
    public Map<String, Product> inventory = new LinkedHashMap<>();
//    public List<Product> productList = new ArrayList<>();
    public void loadInventory(){
        File inputFile = new File("vendingmachine.csv");
        try (Scanner inputScanner = new Scanner(inputFile)){
            while (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
                String[] elements = line.split("\\|");
                String slot = elements[0];
                String name = elements[1];
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(elements[2]));
                String type = elements[3];

//                Product product = new Product(slot, name, price, type);
                inventory.put(slot, new Product(slot, name, price, type));
//                productList.add(product);
            }
        } catch (IOException e){
            System.err.println("Inventory file not found: " + e.getMessage());
        }

    }
    public void feedMoney(BigDecimal amount) {
        balance = balance.add(amount);
        String activity = String.format("FEED MONEY: $%s $%s", amount, balance);
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
                System.out.println();
                System.out.println("x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x");
                System.out.println();
                System.out.println("Item is OUT OF STOCK. Please select a different option.");
                System.out.println();
                System.out.println("x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x");
                System.out.println();
                displayItems();
                System.out.println();
            } else if (balance.compareTo(product.getPrice()) < 0) {
                    System.out.println("Insufficient funds! Please feed more money or select a different option.");
            } else {

                balance = balance.subtract(product.getPrice());
                product.setQuantity(product.getQuantity() -1);
                System.out.println();
                getMessage(product);
                System.out.format("Thank you for your purchase of: %s for $%.2f!",product.getName(),product.getPrice());
                System.out.println("\n\nBalance remaining: $" + getBalance());
                //return to purchase menu
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

    public void finishTransaction() {
        int quarterCount = 0;
        int dimeCount = 0;
        int nickleCount = 0;
        BigDecimal change = balance;
        String activity = "";
        while (getBalance().compareTo(BigDecimal.valueOf(0.25)) >= 0) {
            balance = balance.subtract(BigDecimal.valueOf(0.25));
            quarterCount++;
        }
        while (getBalance().compareTo(BigDecimal.valueOf(0.10)) >= 0) {
            balance = balance.subtract(BigDecimal.valueOf(0.10));
            dimeCount++;
        }
        while (getBalance().compareTo(BigDecimal.valueOf(0.05)) >= 0) {
            balance = balance.subtract(BigDecimal.valueOf(0.05));
            nickleCount++;
        }
        System.out.println("Here is your change: " + quarterCount + " quarters, " + dimeCount + " dimes, " + nickleCount + " nickles.");
        activity = String.format("GIVE CHANGE: $%.2f $%.2f", change, balance);
        try {
            logger(activity);
        } catch (IOException e) {
            System.err.println("Error writing to log..." + e.getMessage());
        }
    }

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

