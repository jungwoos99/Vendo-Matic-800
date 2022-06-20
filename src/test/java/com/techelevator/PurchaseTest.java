package com.techelevator;

import com.techelevator.Purchase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PurchaseTest {

    Purchase purchase = new Purchase();

    @After
    public void callResetBalance() {
        purchase.resetCurrentBalance();
    }

    @Test
    public void feed_money_affects_balance() {
        double currentBalanceExpected = 5.0;
        double currentBalanceActual = 0;

        currentBalanceActual = purchase.feedMoney("5");

        Assert.assertEquals(currentBalanceExpected, currentBalanceActual, .0);
    }

    @Test
    public void feed_money_ignores_incorrect_bill() {
        double currentBalanceExpected = 0;
        double currentBalanceActual = 0;

        currentBalanceActual = purchase.feedMoney("4");

        Assert.assertEquals(currentBalanceExpected, currentBalanceActual, .0);
    }

    @Test
    public void feed_money_takes_multiple_valid_bills() {
        double currentBalanceExpected = 20.0;
        double currentBalanceActual = 0;

        currentBalanceActual = purchase.feedMoney("10 $1.00 2 2.00 5");

        Assert.assertEquals(currentBalanceExpected, currentBalanceActual, .0);
    }

    @Test
    public void feed_money_takes_up_to_incorrect_bill_type() {
        double currentBalanceExpected = 15.0;
        double currentBalanceActual = 0;

        currentBalanceActual = purchase.feedMoney("10 5.00 $3.00");

        Assert.assertEquals(currentBalanceExpected, currentBalanceActual,.0);
    }

}
