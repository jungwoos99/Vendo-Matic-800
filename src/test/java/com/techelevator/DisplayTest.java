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
}
