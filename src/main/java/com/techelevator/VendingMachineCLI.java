package com.techelevator;

import com.techelevator.view.VendingMenu;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;


public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_SECRET_OPTION = "*Sales Report";

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_SECRET_OPTION };
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private VendingMachine vendingMachine;
	private VendingMenu menu;


	public VendingMachineCLI(VendingMenu menu, VendingMachine vendingMachine) {
		this.menu = menu;
		this.vendingMachine = vendingMachine;
	}

	public void run(){
		boolean running = true;
		vendingMachine.loadInventory();

		while (running) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			System.out.println();
			System.out.println("Current balance: $" + vendingMachine.getBalance());
			//String MAIN_MENU_OPTION_PURCHASE = "Purchase"
			// A switch statement could also be used here.  Your choice.
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

				vendingMachine.displayItems();
				System.out.println();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchaseMenu();
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				running = false;
			} else if (choice.equals(MAIN_MENU_SECRET_OPTION)) {
				vendingMachine.salesReport();
			}

		}
	}

	public static void main(String[] args) {
		VendingMenu menu = new VendingMenu(System.in, System.out);
		VendingMachine vendingMachine = new VendingMachine();
		VendingMachineCLI cli = new VendingMachineCLI(menu,vendingMachine);
		cli.run();
	}

	public void purchaseMenu() {

		boolean isPurchasing = true;
		Scanner userInput = new Scanner(System.in);

		while (isPurchasing) {

			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				System.out.println("Insert bills please! (Type in whole numbers how many dollars to add):");
				//Scanner userInput = new Scanner(System.in);
				String stringAmount = userInput.nextLine();
				BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(stringAmount));
				vendingMachine.feedMoney(amount);
				System.out.println();
				System.out.println("Current balance: $" + vendingMachine.getBalance());

			} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				System.out.println("Please select an item: ");
				String expectedSlot = userInput.nextLine();
				vendingMachine.purchaseProduct(expectedSlot);

			} else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				vendingMachine.finishTransaction();
				isPurchasing = false;
			}
		}
	}
}
