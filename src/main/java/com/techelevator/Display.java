package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Display {

    private static boolean itemsGivenNames;
    private static int initialAmount = 5;
    protected static boolean initialAmountsAssigned = false;
    protected static Map<String, String> itemQuantities = new HashMap<>();
    private static List<String> itemSlots = new ArrayList<>();
    protected static List<String> vendingMachineItemsList = new ArrayList<>();
    protected static List<String> itemTypes = new ArrayList<>();
    private static String vendingMachinePath = "/Users/jungwooseo/Desktop/ForkedCapstone/capstone-1/vendingmachine.csv";
    private File vendingMachineItemsCsv = new File(vendingMachinePath);
    private static Map<String, String> itemSpecificMessages = new HashMap<>();
    private static Map<String, String> itemSlotTypes = new HashMap<>();
    protected static boolean itemsListCreated;
    protected static Map<String, String> itemSlotsWithItemNames = new HashMap<>();
    private static String filePath = new String();
    private static File file;

    private static final String CHIP_MESSAGE = "Crunch Crunch, Yum!";
    private static final String CANDY_MESSAGE = "Munch Munch, Yum!";
    private static final String DRINK_MESSAGE = "Glug Glug, Yum!";
    private static final String GUM_MESSAGE = "Chew Chew, Yum!";

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
    public List<String> getLinesOfText(String filePath) {
        List<String> itemsLines = new ArrayList<>();
        File file = new File(filePath);
        String lineOfFile = new String();
        try (Scanner fileReader = new Scanner(file)) {
            while(fileReader.hasNextLine()) {
                lineOfFile = fileReader.nextLine().replaceAll("\\|", " - ");
                itemsLines.add(lineOfFile);
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        vendingMachineItemsList = itemsLines;
        itemsLines = null;
        return vendingMachineItemsList;
    }

    //Extracts slot number from the lines of the file and adds them to a list<String>
    public List<String> pullItemSlots(List<String> itemsList) {
        List<String> slots = new ArrayList<>();
        String itemSlot = new String();
        itemsList = vendingMachineItemsList;
        for(String slotPicker : itemsList) {
            itemSlot = slotPicker.substring(0, 2);
            slots.add(itemSlot);
        }
        itemSlots = slots;
        slots = null;
        return itemSlots;
    }

    //Iterates through list of slots and gives each slot a quantity of 5, then prevents a reassignment of 5 when display is called
    public Map<String, String> assignItemQuantities(int initialAmount, List<String> itemSlots) {
        Map<String, String> itemWithQuantity = new HashMap<>();
        if(initialAmountsAssigned == false) {
            for(String slot: itemSlots) {
                String initialAmountString = String.valueOf(initialAmount);
                itemWithQuantity.put(slot, initialAmountString);
            }
        }
        itemQuantities = itemWithQuantity;
        itemWithQuantity = null;
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
        display.getLinesOfText(vendingMachinePath);
        display.pullItemSlots(vendingMachineItemsList);
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
