package com.techelevator;

import java.math.BigDecimal;

public class Product {
    private String slot;
    private String name;
    private BigDecimal price;
    private String type;
    private int quantity;


    public Product(String slot, String name, BigDecimal price, String type){
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
    public BigDecimal getPrice() {
        return price;
    }
    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
