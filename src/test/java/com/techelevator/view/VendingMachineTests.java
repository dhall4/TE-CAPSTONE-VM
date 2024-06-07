package com.techelevator.view;

import com.techelevator.Product;
import com.techelevator.VendingMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class VendingMachineTests {


    VendingMachine testVendingMachine = new VendingMachine();


    Product testProduct = new Product("A1", "Potato Crisps", BigDecimal.valueOf(3.05), "Chip");

    BigDecimal fiveDollars = BigDecimal.valueOf(5.00);

    @Test
    public void testFeedMoney_ExpectReturnProportionalBalanceIncrease(){
        testVendingMachine.feedMoney(fiveDollars);
        Assert.assertEquals(fiveDollars, testVendingMachine.getBalance());
    }

    @Test
    public void testSelectProduct_ExpectBalanceToDecrease_ExpectQuantityToDecrease(){
//        testVendingMachine.feedMoney(fiveDollars);
//        testVendingMachine.purchaseProduct(testProduct.getSlot());
        //Assert.assertEquals(BigDecimal.valueOf(1.95), testVendingMachine.getBalance());
        //Assert.assertEquals("Potato Crisps", testProduct.getName());

        Assert.assertEquals(4, testProduct.getQuantity());
    }











}
