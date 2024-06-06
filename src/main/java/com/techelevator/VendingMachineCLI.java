package com.techelevator;

import com.techelevator.view.VendingMenu;

import java.awt.*;
import java.io.IOException;


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
			//String MAIN_MENU_OPTION_PURCHASE = "Purchase"
			// A switch statement could also be used here.  Your choice.
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				vendingMachine.displayItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchaseMenu();
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
		while (isPurchasing) {
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
			if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {

			} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {

			} else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				isPurchasing = false;
			}
		}
	}
}
