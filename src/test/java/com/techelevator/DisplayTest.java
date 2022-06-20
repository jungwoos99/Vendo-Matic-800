package com.techelevator;

import org.junit.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayTest {

    Display display = new Display();
    String testFilePath = "/Users/jungwooseo/Desktop/ForkedCapstone/capstone-1/src/test/TestFile.txt";
    String emptyFilePath = "/Users/jungwooseo/Desktop/ForkedCapstone/capstone-1/src/test/EmptyTestFile.txt";
    String duplicateItemTypeTestFilePath = "/Users/jungwooseo/Desktop/ForkedCapstone/capstone-1/src/test/TestFileWithDuplicateItemType.txt";
    List<String> itemsListActual = new ArrayList<>();

    public void setItemsListActual(String filePath) {
        this.itemsListActual = display.getLinesOfText(filePath);
    }

    @Test
    public void get_lines_of_text_assigns_lines_of_text_to_list() {
        List<String> itemsListExpected = new ArrayList<>();
        itemsListExpected.add("A1|CandyMan|5.00|Candy".replaceAll("\\|", " - "));
        itemsListExpected.add("B1|PowerDrink|1.00|Drink".replaceAll("\\|", " - "));
        itemsListExpected.add("C1|ChewChew|2.50|Gum".replaceAll("\\|", " - "));
        itemsListExpected.add("D1|CrunchBunch|3.00|Chip".replaceAll("\\|", " - "));

        setItemsListActual(testFilePath);

        Assert.assertEquals(itemsListExpected, itemsListActual);
    }

    @Test
    public void get_lines_of_text_returns_empty_list() {
        List<String> itemsListExpected = new ArrayList<>();

        setItemsListActual(emptyFilePath);

        Assert.assertEquals(itemsListExpected, itemsListActual);
    }

    @Test
    public void pull_item_slots_extracts_item_slots() {
        Display display = new Display();
        List<String> itemSlotsExpected = new ArrayList<>();
        itemSlotsExpected.add("A1");
        itemSlotsExpected.add("B1");
        itemSlotsExpected.add("C1");
        itemSlotsExpected.add("D1");

        setItemsListActual(testFilePath);
        List<String> itemSlotsActual = display.pullItemSlots(itemsListActual);

        Assert.assertEquals(itemSlotsExpected, itemSlotsActual);
    }

    @Test
    public void assign_item_quantities_assigns_3_per_item() {
        Display display = new Display();
        Map<String, String> itemQuantitiesExpected = new HashMap<>();
        List<String> itemSlots = new ArrayList<>();

        setItemsListActual(testFilePath);
        itemSlots = display.pullItemSlots(itemsListActual);
        for(String itemSlot : itemSlots) {
            itemQuantitiesExpected.put(itemSlot, "3");
        }
        Map<String, String> itemQuantitiesActual = display.assignItemQuantities(3, itemSlots);

        Assert.assertEquals(itemQuantitiesExpected, itemQuantitiesActual);
    }

    @Test
    public void assign_item_quantities_assigns_0_per_item() {
        Display display = new Display();
        Map<String, String> itemQuantitiesExpected = new HashMap<>();
        List<String> itemSlots = new ArrayList<>();
        display.initialAmountsAssigned = false;

        setItemsListActual(testFilePath);
        itemSlots = display.pullItemSlots(itemsListActual);
        for(String itemSlot : itemSlots) {
            itemQuantitiesExpected.put(itemSlot, "0");
        }
        Map<String, String> itemQuantitiesActual = display.assignItemQuantities(0, itemSlots);

        Assert.assertEquals(itemQuantitiesExpected, itemQuantitiesActual);
    }

    @Test
    public void assign_slot_with_item_name_assigns_item_name_to_slot() {
        Display display = new Display();
        Map<String, String> itemsWithItemNamesExpected = new HashMap<>();
        Map<String, String> itemsWithItemNamesActual;
        itemsWithItemNamesExpected.put("A1", "CandyMan");
        itemsWithItemNamesExpected.put("B1", "PowerDrink");
        itemsWithItemNamesExpected.put("C1", "ChewChew");
        itemsWithItemNamesExpected.put("D1", "CrunchBunch");


        setItemsListActual(testFilePath);
        itemsWithItemNamesActual = display.assignSlotWithItemName(itemsListActual);

        Assert.assertEquals(itemsWithItemNamesExpected, itemsWithItemNamesActual);
    }

    @Test
    public void assign_slot_with_item_name_returns_empty_list() {
        Display display = new Display();
        Map<String, String> itemsWithNamesExpected = new HashMap<>();
        Map<String, String> itemsWithNamesActual;

        setItemsListActual(emptyFilePath);
        itemsWithNamesActual = display.assignSlotWithItemName(itemsListActual);

        Assert.assertEquals(itemsWithNamesExpected, itemsWithNamesActual);
    }

    @Test
    public void pull_item_types_extracts_item_types() {
        Display display = new Display();
        List<String> itemTypesExpected = new ArrayList<>();
        List<String> itemTypesActual;
        itemTypesExpected.add("Candy");
        itemTypesExpected.add("Drink");
        itemTypesExpected.add("Gum");
        itemTypesExpected.add("Chip");

        setItemsListActual(testFilePath);
        itemTypesActual = display.pullItemTypes(itemsListActual);

        Assert.assertEquals(itemTypesExpected, itemTypesActual);
    }

    @Test
    public void pull_item_types_extracts_item_types_excludes_duplicate_item_type() {
        Display display = new Display();
        List<String> itemTypesExpected = new ArrayList<>();
        List<String> itemTypesActual;
        itemTypesExpected.add("Candy");
        itemTypesExpected.add("Drink");
        itemTypesExpected.add("Gum");
        itemTypesExpected.add("Chip");

        setItemsListActual(duplicateItemTypeTestFilePath);
        itemTypesActual = display.pullItemTypes(itemsListActual);

        Assert.assertEquals(itemTypesExpected, itemTypesActual);
    }

    @Test
    public void pull_item_types_returns_empty_list() {
        Display display = new Display();
        List<String> itemTypesExpected = new ArrayList<>();
        List<String> itemTypesActual;

        setItemsListActual(emptyFilePath);
        itemTypesActual = display.pullItemTypes(itemsListActual);

        Assert.assertEquals(itemTypesExpected, itemTypesActual);
    }

    @Test
    public void assign_slot_to_item_type_assigns_slot_with_type() {
        Display display = new Display();
        Map<String, String> slotsWithItemTypeExpected = new HashMap<>();
        Map<String, String> slotsWithItemTypeActual;
        slotsWithItemTypeExpected.put("A1", "Candy");
        slotsWithItemTypeExpected.put("B1", "Drink");
        slotsWithItemTypeExpected.put("C1", "Gum");
        slotsWithItemTypeExpected.put("D1", "Chip");

        setItemsListActual(testFilePath);
        slotsWithItemTypeActual = display.assignSlotToItemType(itemsListActual);

        Assert.assertEquals(slotsWithItemTypeExpected, slotsWithItemTypeActual);
    }

    @Test
    public void assign_slot_to_item_type_returns_empty_map() {
        Display display = new Display();
        Map<String, String> slotsWithItemTypeExpected = new HashMap<>();
        Map<String, String> slotsWithItemTypeActual;

        setItemsListActual(emptyFilePath);
        slotsWithItemTypeActual = display.assignSlotToItemType(itemsListActual);

        Assert.assertEquals(slotsWithItemTypeExpected, slotsWithItemTypeActual);
    }

    @Test
    public void assign_item_to_message_associates_type_with_message() {
        Display display = new Display();
        Map<String, String> itemTypesMessagesExpected = new HashMap<>();
        Map<String, String> itemTypesMessagesActual;
        itemTypesMessagesExpected.put("Chip", "Crunch Crunch, Yum!");
        itemTypesMessagesExpected.put("Gum", "Chew Chew, Yum!");
        itemTypesMessagesExpected.put("Drink", "Glug Glug, Yum!");
        itemTypesMessagesExpected.put("Candy", "Munch Munch, Yum!");

        setItemsListActual(testFilePath);
        List<String> itemTypes = display.pullItemTypes(itemsListActual);
        itemTypesMessagesActual = display.assignItemToMessage(itemTypes);

        Assert.assertEquals(itemTypesMessagesExpected, itemTypesMessagesActual);
    }

    @Test
    public void assign_item_to_message_returns_empty_map() {
        Display display = new Display();
        Map<String, String> itemTypesMessagesExpected = new HashMap<>();
        Map<String, String> itemTypesMessagesActual;

        setItemsListActual(emptyFilePath);
        List<String> itemTypes = display.pullItemTypes(itemsListActual);
        itemTypesMessagesActual = display.assignItemToMessage(itemTypes);

        Assert.assertEquals(itemTypesMessagesExpected, itemTypesMessagesActual);
    }

}
