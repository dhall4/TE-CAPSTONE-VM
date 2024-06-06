package com.techelevator;

public class Product {
    private String slot;
    private String name;
    private double price;
    private String type;
    private int quantity;


    public Product(String slot, String name, double price, String type){
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = 5; //five items to start
    }

    public String getSlot() {
        return slot;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
}
