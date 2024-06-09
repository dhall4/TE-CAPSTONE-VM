package com.techelevator.view;

import com.techelevator.Product;
import com.techelevator.VendingMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineTests {

    private VendingMachine vendingMachine;

    private final String inventoryFile = "vendingmachine.csv";



    //Map<String, Product> inventory = new LinkedHashMap<>();
    @Before
    public void SetUp() throws IOException{

        vendingMachine = new VendingMachine();
        vendingMachine.loadInventory();
    }

    private BigDecimal fiveDollars = BigDecimal.valueOf(5.00); //easier way to write $5
    private BigDecimal oneDollar = BigDecimal.valueOf(1.00); //easier way to write $1


    @Test
    public void testLoadInventory(){ //Tests that inventory loads correctly into tests

        //vendingMachine.inventory;
        //Assert.assertFalse(inventory.isEmpty());

        Map<String, Product> inventory = vendingMachine.inventory;

        Assert.assertEquals("Should be 16", 16, inventory.size());

    }

    @Test
    public void testFeedMoney_ExpectReturnProportionalBalanceIncrease(){
        vendingMachine.feedMoney(fiveDollars);
        Assert.assertEquals(fiveDollars, vendingMachine.getBalance());
    }

    @Test
    public void testSelectProduct_ExpectBalanceToDecrease_ExpectQuantityToDecrease(){
        Product product = vendingMachine.inventory.get("A1");
        int startingQuantity = product.getQuantity();
        BigDecimal productPrice = product.getPrice();
        vendingMachine.feedMoney(fiveDollars);
        vendingMachine.purchaseProduct("A1");

        Assert.assertEquals("Product quantity should be 4", (startingQuantity -1) , product.getQuantity());
        Assert.assertEquals("Final balance should be 1.95", fiveDollars.subtract(productPrice), vendingMachine.getBalance());
    }

    @Test
    public void testPurchaseSoldOutItem_ExpectBalanceToStayTheSame_ExpectQuantityToStayZero(){
        Product product = vendingMachine.inventory.get("A1");
        product.setQuantity(0);
        int startingQuantity = product.getQuantity();
        vendingMachine.feedMoney(fiveDollars);
        vendingMachine.purchaseProduct("A1");

        Assert.assertEquals("Quantity should be zero, should not go lower", 0, product.getQuantity());
        Assert.assertEquals("Balance should remain the fed $5", fiveDollars, vendingMachine.getBalance());
    }

    @Test
    public void testInsufficientFunds_ExpectQuantityToStayTheSame_ExpectBalanceStaysTheSame(){
        Product product = vendingMachine.inventory.get("A1");
        int startingQuantity = product.getQuantity();
        vendingMachine.feedMoney(oneDollar);
        vendingMachine.purchaseProduct("A1");

        Assert.assertEquals("Quantity should not decrease", startingQuantity, product.getQuantity());
        Assert.assertEquals("Balance should not decrease", oneDollar, vendingMachine.getBalance());
    }


    @Test
    public void testFinishTransaction_ExpectBalanceReturnToZero(){
        vendingMachine.feedMoney(fiveDollars);
        vendingMachine.finishTransaction();
        BigDecimal bdZero = BigDecimal.valueOf(0.00);

        Assert.assertEquals("Balance should return to 0", bdZero.stripTrailingZeros(), vendingMachine.getBalance().stripTrailingZeros());
    }

    @Test
    public void testLogger_ExpectSingleLineIncrease_ExpectFEEDMONEY() throws IOException{

        File logFile = new File("Log.txt");
        String line = "";

        int preTestLines = 0;

        Scanner logScanner = new Scanner(logFile);

        while(logScanner.hasNextLine()){
            line = logScanner.nextLine();
            preTestLines++;

        }
        logScanner.close();

        vendingMachine.feedMoney(fiveDollars);

        int postTestLines = 0;

        Scanner postLogScanner = new Scanner(logFile);

        while(postLogScanner.hasNextLine()){
            line = postLogScanner.nextLine();
            postTestLines++;
        }
        logScanner.close();


        boolean hasGeneratedLines = preTestLines < postTestLines;
        boolean isContainsCorrectMessage = line.contains("FEED MONEY");

        Assert.assertTrue(hasGeneratedLines);
        Assert.assertTrue(isContainsCorrectMessage);

    }
    
}
