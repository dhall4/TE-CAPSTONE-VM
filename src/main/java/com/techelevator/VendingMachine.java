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

    public void purchaseProduct(String slot){

    }



}
