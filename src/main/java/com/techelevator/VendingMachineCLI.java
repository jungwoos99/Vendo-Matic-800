package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private boolean inPurchaseMenu;
	private boolean transactionComplete = false;

	private static final String VENDING_MACHINE_START_MESSAGE = "<Thank you for choosing Vend-O>";
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "-Display Vending Machine Items-";
	private static final String MAIN_MENU_OPTION_PURCHASE = "-Purchase-";
	private static final String MAIN_MENU_OPTION_EXIT = "-Exit-";
	private static final String MAIN_MENU_SECRET_OPTION = "4";

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "-Feed Money-";
	private static final String PURCHASE_MENU_OPTION_SELECT_A_PRODUCT = "-Select Product-";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "-Finish Transaction-";

	private static final String VENDING_MACHINE_EXIT_MESSAGE = "<Vending machine terminated>";

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_A_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private Display display = new Display();
	private static Purchase purchase;
	private static Menu menu;
	private static TransactionLog log = new TransactionLog();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public VendingMachineCLI() {
	}


	//WRITE TESTS AS YOU GO

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		purchase = new Purchase();
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		System.out.println(VENDING_MACHINE_START_MESSAGE);
		cli.run();
	}

	public void exitMachine() {
		System.out.println("\n" + VENDING_MACHINE_EXIT_MESSAGE);
		System.exit(1);
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			/* */if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
					// display vending machine items
					display.displayItemsList();
			/* */} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
					// do purchase
					display.displayItemsList();
					System.out.println("\n" + purchase.getCurrentMoneyProvided());
					inPurchaseMenu = true;
					while (inPurchaseMenu) {
						String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
						//feed money
						if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
							purchase.feedMoney();
						} else if(purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_A_PRODUCT)) {
							//select a product
							purchase.purchaseProduct();
						} else if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
							//finishes purchase(s)
							inPurchaseMenu = false;
							purchase.displayTransactionCompleteMessage();
							purchase.messageDelay(purchase.SHORT_DELAY_TIME);
							purchase.printChangeTendered();
							purchase.messageDelay(purchase.MEDIUM_DELAY_TIME);
							purchase.flushTransactionBalance();
						}
					}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
					// exit vending machine
					exitMachine();
			} else if(choice.equals(MAIN_MENU_SECRET_OPTION)) {
					//print sales log
			}
		}
	}
	//git add
}
