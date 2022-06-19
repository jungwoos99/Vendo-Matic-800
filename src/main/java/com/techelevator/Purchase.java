package com.techelevator;

import com.techelevator.view.Menu;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Purchase extends VendingMachineCLI {

    private static boolean itemPricesExtracted;
    private static boolean purchasingProduct = true;
    private static double changeRequired = 0;
    private static boolean insufficientFunds = false;
    private static int numberOfQuarters = 0;
    private static int numberOfDimes = 0;
    private static int numberOfNickels = 0;
    private static int numberOfPennies = 0;
    private final int QUARTER = 25;
    private final int DIME = 10;
    private final int NICKEL = 5;
    private final int PENNY = 1;

    private static double currentBalance = 0.0;
    private static double totalMoneyInserted = 0;
    private static double moneyFed;
    private double selectedItemPrice = 0;
    private String itemSelected = new String();
    private static String moneyFedString = new String();

    protected static final int  MEDIUM_DELAY_TIME = 2000;
    protected static final int SHORT_DELAY_TIME = 1000;
    private static final int QUICK_DELAY_TIME = 750;
    protected static final String NEXT_LINE = System.lineSeparator();
    private static final String BALANCE_OF_ZERO_STRING = "0.00";
    private static final String TRANSACTION_COMPLETED_MESSAGE = NEXT_LINE + "<Transaction Complete>";
    private static final String MULTIPLE_SLOTS_ENTERED_MESSAGE = NEXT_LINE + "Please enter one slot at a time";
    private static final String NUMBER_OF_QUARTERS_MESSAGE = NEXT_LINE +  "Number of quarter(s): ";
    private static final String NUMBER_OF_DIMES_MESSAGE = "Number of dimes(s): ";
    private static final String NUMBER_OF_NICKELS_MESSAGE = "Number of nickel(s): ";
    private static final String NUMBER_OF_PENNIES_MESSAGE = "Number of pennies(s): ";
    private static final String INSERT_MORE_MONEY_PROMPT = "Press (1) to enter more bills" + NEXT_LINE + "Press (2) to continue purchasing";
    private static final String CHANGE_REQUIRED_MESSAGE = NEXT_LINE + "Total change: ";
    private static final String INSUFFICIENT_FUNDS_MESSAGE = NEXT_LINE + "***Insufficient funds***" + NEXT_LINE;
    private static final String SOLD_OUT_INDICATOR = "sold out";
    private static final String SOLD_OUT_MESSAGE = NEXT_LINE + "***SELECTED ITEM IS SOLD OUT***" + NEXT_LINE;
    protected static final String GET_CURRENT_MONEY_PROVIDED_MESSAGE = "Current Balance: ";
    private static final String INCORRECT_BILL_MESSAGE = NEXT_LINE + "***Incorrect bill type identified***" + NEXT_LINE;
    private static final String INVALID_SLOT_MESSAGE = NEXT_LINE + "***Please select a valid slot***" + NEXT_LINE;
    private static final String FEED_MONEY_MESSAGE = NEXT_LINE + "Please enter funds in the form of $1, $2, $5, or $10 bill(s)" + NEXT_LINE + NEXT_LINE +"If you have multiple bills, please enter one-by-one (separated by a space)" + NEXT_LINE +"Enter (0) to return" + NEXT_LINE;
    private static final String INSUFFICIENT_FUNDS_INVALID_SELECTION_MESSAGE = NEXT_LINE + "***Please select from the given options***";
    private static final String FEED_MONEY = "FEED MONEY";
    private static final String GIVE_CHANGE = "GIVE CHANGE";

    private static Map<String, Double> itemPrices = new HashMap<>();
    private static Map<String, Integer> coinAmounts = new HashMap<>();
    private static Display displayMenu = new Display();
    private TransactionLog transactionLog = new TransactionLog();
    Scanner read = new Scanner(System.in);
    private static NumberFormat dollarAmount = NumberFormat.getCurrencyInstance();
    private static Map<String, String> itemSlotWithNames = new HashMap<>();

    public Purchase() {}

    public Purchase(Menu menu) {
        super(menu);
    }

    public void setTotalMoneyInserted(double moneyFed) {
        this.totalMoneyInserted = moneyFed;
    }

    public String getCurrentMoneyProvided() {
        return String.valueOf(GET_CURRENT_MONEY_PROVIDED_MESSAGE + dollarAmount.format(currentBalance));
    }
    public double assignChange() {
        changeRequired = currentBalance;
        return changeRequired;
    }

    public String printChangeRequiredMessage() {
        return String.valueOf(CHANGE_REQUIRED_MESSAGE + dollarAmount.format(changeRequired));
    }

    public double decreaseBalance(String itemSelected) {
        double price = itemPrices.get(itemSelected);
        currentBalance -= price;
        assignChange();
        return currentBalance;
    }

    public void messageDelay(int delayTime) {
        try{
            Thread.sleep(delayTime);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<String,Double> pullItemPrice() {
        itemPricesExtracted = false;
        for(String itemInfo : displayMenu.vendingMachineItemsList) {
            String[] itemInfoSections = itemInfo.split(" - ");
            itemPrices.put(itemInfoSections[0], Double.valueOf(itemInfoSections[2]));
        }
        itemPricesExtracted = true;
        return itemPrices;
    }

    //Decreases the quantity of a certain item by 1
    public Map<String, String> decreaseItemQuantity(String itemSlot) {
        if(Integer.valueOf(displayMenu.itemQuantities.get(itemSlot)) > 1) {
            int quantity = Integer.valueOf(displayMenu.itemQuantities.get(itemSlot));
            displayMenu.itemQuantities.replace(itemSlot, displayMenu.itemQuantities.get(itemSlot), String.valueOf(quantity - 1));
        }
        else if(Integer.valueOf(displayMenu.itemQuantities.get(itemSlot)) == 1) {
            displayMenu.itemQuantities.replace(itemSlot, displayMenu.itemQuantities.get(itemSlot), SOLD_OUT_INDICATOR);
        } else if(displayMenu.itemQuantities.get(itemSlot).equals(SOLD_OUT_MESSAGE)) {
            System.out.println(SOLD_OUT_INDICATOR+"\n");
        }
        return displayMenu.itemQuantities;
    }

    public void insufficientFundsPrompt() {
        System.out.println(INSERT_MORE_MONEY_PROMPT);
        String insertMoreChoice = read.nextLine();
        if(insertMoreChoice.equals("1")) {
            feedMoney();
        } else if (insertMoreChoice.equals("2")){
            purchaseProduct();
        } else {
            System.out.println(INSUFFICIENT_FUNDS_INVALID_SELECTION_MESSAGE);
            insufficientFundsPrompt();
        }
    }

    //takes an input from user and sets the current money provided
    public double feedMoney() {
        System.out.println(FEED_MONEY_MESSAGE);
        moneyFedString = read.nextLine();
        String[] bills = moneyFedString.replaceAll("\\$", "").replaceAll(".00", "").split(" ");
        moneyFed = 0;
        for(String bill : bills) {
            if(bill.equals("1") || bill.equals("2") || bill.equals("5") || bill.equals("10")) {
                transactionLog.setBalanceBeforeTransaction(currentBalance);
                moneyFed = Double.valueOf(bill);
                currentBalance += moneyFed;
                setTotalMoneyInserted(moneyFed);
                assignChange();
                if(bill == bills[bills.length-1]) {
                    System.out.println("\n" + getCurrentMoneyProvided());
                }
                transactionLog.setBalanceAfterTransaction(currentBalance);
                transactionLog.printFeedMoney();
                transactionLog.writeMethod();
                purchasingProduct = true;
            } else if(bill.isBlank()) {
                continue;
            }else if (bill.equals("0")) {
                purchasingProduct = true;
                System.out.println("\n" + getCurrentMoneyProvided());
            }else {
                System.out.println(INCORRECT_BILL_MESSAGE);
                messageDelay(SHORT_DELAY_TIME);
                System.out.println(getCurrentMoneyProvided());
                purchasingProduct = false;
                break;
            }
        }
        return currentBalance;
    }

    public void purchaseProduct() {
        purchasingProduct = true;
        //if displayItems menu option is skipped

        if(!itemPricesExtracted) {
            pullItemPrice();
        }


        while(purchasingProduct) {
            displayMenu.displayItemsList();
            System.out.println("\nPlease select an item slot or enter (1) to return: ");
            System.out.println("\n" + getCurrentMoneyProvided());
            itemSelected = read.nextLine().toUpperCase().replaceAll(" ", "");
            transactionLog.setBalanceBeforeTransaction(currentBalance);
            if(itemSelected.length() > 2) {
                System.out.println(MULTIPLE_SLOTS_ENTERED_MESSAGE);
                messageDelay(MEDIUM_DELAY_TIME);
                purchaseProduct();
            }
            if(displayMenu.itemQuantities.containsKey(itemSelected)) {
                if(Double.valueOf(getCurrentMoneyProvided().replace("Current Balance: $", "")) > itemPrices.get(itemSelected)) {
                    if(!displayMenu.itemQuantities.get(itemSelected).equals(SOLD_OUT_INDICATOR)) {
                        selectedItemPrice = itemPrices.get(itemSelected);
                        decreaseBalance(itemSelected);
                        transactionLog.setBalanceAfterTransaction(currentBalance);
                        decreaseItemQuantity(itemSelected);
                        transactionLog.getItemPurchased(itemSelected);
                        transactionLog.printItemPurchased();
                        transactionLog.writeMethod();
                        displayMenu.returnItemMessage(itemSelected);
                        messageDelay(QUICK_DELAY_TIME);
                    }else if(displayMenu.itemQuantities.get(itemSelected).equals(SOLD_OUT_INDICATOR)) {
                        System.out.println("\n" + SOLD_OUT_MESSAGE);
                        messageDelay(MEDIUM_DELAY_TIME);
                    }
                } else {
                    System.out.println(INSUFFICIENT_FUNDS_MESSAGE);
                    messageDelay(MEDIUM_DELAY_TIME);
                    insufficientFundsPrompt();
                    insufficientFunds = true;
                }
            } else if(!displayMenu.itemQuantities.containsKey(itemSelected) && !itemSelected.equals("1")) {
                System.out.println(INVALID_SLOT_MESSAGE);
                messageDelay(SHORT_DELAY_TIME);
            } else if(itemSelected.equals("1")) {
                purchasingProduct = false;
            }
        }
        if(!insufficientFunds) {
            System.out.println("\n" + getCurrentMoneyProvided());
        }
    }

    public void convertChangeIntoCoins() {
        int currentBalanceInt =  Integer.valueOf(convertBalanceToString());

        numberOfQuarters = (currentBalanceInt / QUARTER);
        currentBalanceInt -= QUARTER * numberOfQuarters;

        numberOfDimes = (currentBalanceInt / DIME);
        currentBalanceInt -= DIME * numberOfDimes;

        numberOfNickels = currentBalanceInt / NICKEL;
        currentBalanceInt -= NICKEL * numberOfNickels;

        numberOfPennies = currentBalanceInt;
    }

    public double printChangeTendered() {
        transactionLog.setBalanceBeforeTransaction(currentBalance);
        convertChangeIntoCoins();
        printChangeRequiredMessage();
        System.out.println(printChangeRequiredMessage());
        if(numberOfQuarters > 0){
            System.out.println(NUMBER_OF_QUARTERS_MESSAGE + numberOfQuarters);
        }
        if(numberOfDimes > 0){
            System.out.println(NUMBER_OF_DIMES_MESSAGE + numberOfDimes);
        }
        if(numberOfNickels > 0){
            System.out.println(NUMBER_OF_NICKELS_MESSAGE + numberOfNickels);
        }
        if(numberOfPennies > 0){
            System.out.println(NUMBER_OF_PENNIES_MESSAGE + numberOfPennies);
        }
        changeRequired = 0;
        transactionLog.setBalanceAfterTransaction(changeRequired);
        transactionLog.printChangeGiven();
        transactionLog.writeMethod();
        return changeRequired;
    }

    public double flushTransactionBalance() {
        currentBalance = 0;
        return currentBalance;
    }

    public String convertBalanceToString() {
        String balanceString = new String();
        if(currentBalance != 0) {
            balanceString = String.valueOf(dollarAmount.format(currentBalance)).replaceAll("\\$", "");
        } else {
            balanceString = BALANCE_OF_ZERO_STRING;
        }
        String beforeDecimal = balanceString.substring(0, balanceString.indexOf('.'));
        String afterDecimal = balanceString.substring(balanceString.indexOf('.') + 1, balanceString.indexOf('.') + 3);
        balanceString = beforeDecimal + afterDecimal;
        return balanceString;
    }

    public void displayTransactionCompleteMessage() {
        System.out.println(TRANSACTION_COMPLETED_MESSAGE);
    }

}
