package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Display {

    private static boolean itemsGivenNames;
    private static int initialAmount = 5;
    private static boolean initialAmountsAssigned = false;
    protected static Map<String, String> itemQuantities = new HashMap<>();
    private static List<String> itemSlots = new ArrayList<>();
    protected static List<String> vendingMachineItemsList = new ArrayList<>();
    protected static List<String> itemTypes = new ArrayList<>();
    private static File vendingMachineItemsCsv = new File("/Users/jungwooseo/Downloads/capstone-1-main/vendingmachine.csv");
    private static Map<String, String> itemSpecificMessages = new HashMap<>();
    private static Map<String, String> itemSlotTypes = new HashMap<>();
    protected static boolean itemsListCreated;
    protected static Map<String, String> itemSlotsWithItemNames = new HashMap<>();

    private static final String CHIP_MESSAGE = "Crunch Crunch, Yum!";
    private static final String CANDY_MESSAGE = "Munch Munch, Yum!";
    private static final String DRINK_MESSAGE = "Glug Glug, Yum!";
    private static final String GUM_MESSAGE = "Chew Chew, Yum!";

    private static Purchase purchase = new Purchase();
    private static Display display = new Display();

    //Displays the lines of the csv file line by line
    public void displayItemsList() {
        while(!itemsListCreated) {
            display.generateItemsList();
            itemsListCreated = true;
        }
        while(!initialAmountsAssigned) {
            assignItemQuantities(initialAmount, vendingMachineItemsList);
            initialAmountsAssigned = true;
        }
        for(String itemInfo : vendingMachineItemsList) {
            String itemSlot = itemInfo.substring(0, 2);
            System.out.println(itemInfo + " (" + itemQuantities.get(itemSlot) + ")");
        }
    }

    //Gets lines from file and assigns them to a list to circumvent opening and closing the file multiple times
    public List<String> getLinesOfText() {
        String lineOfFile = new String();
        try (Scanner fileReader = new Scanner(vendingMachineItemsCsv)) {
            while(fileReader.hasNextLine()) {
                lineOfFile = fileReader.nextLine().replaceAll("\\|", " - ");
                vendingMachineItemsList.add(lineOfFile);
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return vendingMachineItemsList;
    }

    //Extracts slot number from the lines of the file and adds them to a list<String>
    public List<String> pullItemSlots() {
        String itemSlot = new String();
        for(String slotPicker : vendingMachineItemsList) {
            itemSlot = slotPicker.substring(0, 2);
            itemSlots.add(itemSlot);
        }
        return itemSlots;
    }

    //Iterates through list of slots and gives each slot a quantity of 5, then prevents a reassignment of 5 when display is called
    public Map<String, String> assignItemQuantities(int initialAmount, List<String> itemSlots) {
        if(initialAmountsAssigned == false) {
            for(String slot: itemSlots) {
                String initialAmountString = String.valueOf(initialAmount);
                itemQuantities.put(slot, initialAmountString);
            }
        }
        initialAmountsAssigned = true;
        return itemQuantities;
    }

    public List<String> pullItemTypes() {
        String itemType = new String();
        for(String type : vendingMachineItemsList) {
            String[] itemInfo = type.split(" - ");
            itemType = itemInfo[3];
            if(!itemTypes.contains(itemType)) {
                itemTypes.add(itemType);
            }
        }
        return itemTypes;
    }

    public Map<String, String> assignSlotToItemType() {
        String itemSlot = new String();
        String itemType = new String();
        for(String itemInfo : vendingMachineItemsList) {
            String[] itemInformation = itemInfo.split(" - ");
            itemSlot = itemInformation[0];
            itemType = itemInformation[3];
            itemSlotTypes.put(itemSlot, itemType);
        }
        return itemSlotTypes;
    }

    public Map<String, String> assignItemToMessage() {
        for(String itemType : itemTypes) {
            if(itemType.equals("Chip")) {
                itemSpecificMessages.put(itemType, CHIP_MESSAGE);
            } else if(itemType.equals("Candy")) {
                itemSpecificMessages.put(itemType, CANDY_MESSAGE);
            } else if(itemType.equals("Drink")) {
                itemSpecificMessages.put(itemType, DRINK_MESSAGE);
            } else if(itemType.equals("Gum")) {
                itemSpecificMessages.put(itemType, GUM_MESSAGE);
            }
        }
        return itemSpecificMessages;
    }

    public void returnItemMessage(String itemSlot) {
        pullItemTypes();
        assignSlotToItemType();
        assignItemToMessage();
        String itemType = itemSlotTypes.get(itemSlot);
        System.out.println("\n" + itemSpecificMessages.get(itemType));
    }

    public void generateItemsList() {
        display.getLinesOfText();
        display.pullItemSlots();
        display.assignItemQuantities(initialAmount, itemSlots);
        assignSlotWithItemName();
    }

    public Map<String, String> assignSlotWithItemName() {
        for(String itemInfo : vendingMachineItemsList) {
            String[] itemInfoParts = itemInfo.split(" - ");
            itemSlotsWithItemNames.put(itemInfoParts[0], itemInfoParts[1]);
        }
        itemsGivenNames = true;
        return itemSlotsWithItemNames;
    }
}
