package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TransactionLog {

    private String fixedDateString = new String();
    private String dateString = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    private String timeString = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 8);
    private static final String AM = " AM ";
    private static final String PM = " PM ";
    private static final String FEED_MONEY = "FEED MONEY";
    private static final String GIVE_CHANGE = "GIVE CHANGE";
    private static String itemSlot;
    private static String itemName;
    private static String itemPurchased = new String();
    private static String[] dateStringComponents;
    private double balanceBefore = 0;
    private double balanceAfter = 0;
    private String purchaseMessage = new String();
    private String feedMoneyMessage = new String();
    private String giveChangeMessage = new String();
    protected boolean purchase = false;
    protected boolean feedMoney = false;
    protected boolean giveChange = false;
    Display display = new Display();
    NumberFormat dollarAmount = NumberFormat.getCurrencyInstance();
    private String message = new String();

    public void writeMethod() {
        checkAmOrPm();
        fixDateString();
        printItemPurchased();

        String logFilePath = "Log.txt";
        File logFile = new File(logFilePath);
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(logFile, true))) {
            if(purchase) {
                writer.println(purchaseMessage);
            } else if(feedMoney) {
                writer.println(feedMoneyMessage);
            } else if(giveChange) {
                writer.println(giveChangeMessage);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found.");
        }
    }

    public String printItemPurchased() {
        purchaseMessage = fixedDateString + " " + timeString + itemPurchased + " " + dollarAmount.format(balanceBefore) + " " + dollarAmount.format(balanceAfter);
        purchase = true;
        return purchaseMessage;
    }

    public String checkAmOrPm() {
        if(Integer.valueOf(timeString.substring(0, 2)) < 12) {
            timeString += AM;
        } else {
            timeString += PM;
        }
        return timeString;
    }

    public String fixDateString() {
        dateStringComponents = dateString.split("-");
        fixedDateString = ">" + dateStringComponents[1] + "/" + dateStringComponents[2] + "/" + dateStringComponents[0];
        return fixedDateString;
    }

    public String getItemPurchased(String slotGiven) {
        itemSlot = slotGiven;
        itemName = Display.itemSlotsWithItemNames.get(itemSlot);
        itemPurchased = itemSlot + " " + itemName + " ";
        return itemPurchased;
    }

    public double setBalanceBeforeTransaction(double balance) {
        return balanceBefore = balance;
    }

    public double setBalanceAfterTransaction(double balance) {
        return balanceAfter = balance;
    }
}
