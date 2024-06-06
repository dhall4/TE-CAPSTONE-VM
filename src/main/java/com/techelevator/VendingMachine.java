package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {

    public void displayItems(){
        for (Product item : inventory.values() ) {
            // "%s %-20s %4.2f %s"
            System.out.println(item.getSlot() + item.getName() + item.getPrice() + item.getQuantity());
        }

    }
    public Map<String, Product> inventory = new HashMap<>();
    public void loadInventory() throws IOException {
        File inputFile = new File("vendingmachine.csv");
        Scanner inputScanner = new Scanner(inputFile);
        while (inputScanner.hasNextLine()){
            String line = inputScanner.nextLine();
            String[] elements = line.split("\\|");
            String slot = elements[0];
            String name = elements[1];
            Double price = Double.parseDouble(elements[2]);
            String type = elements[3];

            inventory.put(slot, new Product(slot, name, price, type));
        }

    }





}
