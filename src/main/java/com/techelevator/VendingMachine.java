package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachine {
    private double balance = 0.00; // may need to be bigD

    public void displayItems(){
        for (Product item : inventory.values()) {
            // "%s %-20s %4.2f %s"
            System.out.println(item.getSlot() + item.getName() + item.getPrice() + item.getQuantity());
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
                Double price = Double.parseDouble(elements[2]);
                String type = elements[3];

//                Product product = new Product(slot, name, price, type);
                inventory.put(slot, new Product(slot, name, price, type));
//                productList.add(product);
            }
        } catch (IOException e){
            System.err.println("Inventory file not found: " + e.getMessage());
        }

    }
    public void feedMoney(int amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public void purchaseProduct(String expectedSlot){
        Product product = inventory.get(expectedSlot);
        if (inventory.containsKey(expectedSlot)) {
            if (product.getQuantity() == 0) {
                System.out.println("Item is out of stock please select a different option.");
            } else if (balance < product.getPrice() ) {
                    System.out.println("Insufficient funds! Please feed more money or select a different option.");
            } else {

                balance -= product.getPrice();
                product.setQuantity(product.getQuantity() -1);
                // yummy message
                System.out.println(product.getSlot() + product.getName() + product.getPrice() + product.getQuantity());
                System.out.println("Balance remaining :" + getBalance());
                //return to purchase menu

            }
        } else {
            System.out.println("Please check your option.");
        }
    }

    public void finishTransaction() {
        int quarterCount = 0;
        int dimeCount = 0;
        int nickleCount = 0;
        while (getBalance() >= 0.25) {
            balance -= 0.25;
            quarterCount++;
        }
        while (getBalance() >= 0.10 ) {
            balance -= 0.10;
            dimeCount++;
        }
        while (getBalance() > 0 ) {
            balance -= 0.05;
            nickleCount++;
        }
        System.out.println("Here is your change: " + quarterCount + " quarters, " + dimeCount + " dimes, " + nickleCount + " nickles.");
        //balance = 0 giving change
        //returns to main menu
    }
}

