package com.transportgame.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EconomyEngineTest {

    @Test
    void passengerPaymentFormula() {
        assertEquals(10 * EconomyEngine.PASSENGER_RATE, EconomyEngine.calcPassengerPayment(10));
    }

    @Test
    void passengerPaymentZeroPassengers() {
        assertEquals(0L, EconomyEngine.calcPassengerPayment(0));
    }

    @Test
    void passengerPaymentSinglePassenger() {
        assertEquals(EconomyEngine.PASSENGER_RATE, EconomyEngine.calcPassengerPayment(1));
    }

    @Test
    void cargoPaymentFormula() {
        assertEquals(10 * EconomyEngine.CARGO_RATE, EconomyEngine.calcCargoPayment(10));
    }

    @Test
    void cargoPaymentZeroCargo() {
        assertEquals(0L, EconomyEngine.calcCargoPayment(0));
    }

    @Test
    void cargoPaymentMaxLoad() {
        assertEquals(10 * EconomyEngine.CARGO_RATE, EconomyEngine.calcCargoPayment(10));
    }

    @Test
    void houseIncomePerTick() {
        assertEquals(EconomyEngine.HOUSE_INCOME, EconomyEngine.calcBuildingIncome(1, 0, 0));
    }

    @Test
    void garageIncomePerTick() {
        assertEquals(EconomyEngine.GARAGE_INCOME, EconomyEngine.calcBuildingIncome(0, 1, 0));
    }

    @Test
    void storageIncomePerTick() {
        assertEquals(EconomyEngine.STORAGE_INCOME, EconomyEngine.calcBuildingIncome(0, 0, 1));
    }

    @Test
    void mixedBuildingIncome() {
        long expected = 2 * EconomyEngine.HOUSE_INCOME
                      + 1 * EconomyEngine.GARAGE_INCOME
                      + 1 * EconomyEngine.STORAGE_INCOME;
        assertEquals(expected, EconomyEngine.calcBuildingIncome(2, 1, 1));
    }

    @Test
    void noBuildingsNoIncome() {
        assertEquals(0L, EconomyEngine.calcBuildingIncome(0, 0, 0));
    }

    @Test
    void largeBuildingCountScalesLinearly() {
        long ten = EconomyEngine.calcBuildingIncome(10, 0, 0);
        long one = EconomyEngine.calcBuildingIncome(1, 0, 0);
        assertEquals(ten, one * 10);
    }

    @Test
    void cashAtThresholdIsNotBankrupt() {
        assertFalse(EconomyEngine.isBankrupt(EconomyEngine.BANKRUPTCY_THRESHOLD));
    }

    @Test
    void cashOneBelowThresholdIsBankrupt() {
        assertTrue(EconomyEngine.isBankrupt(EconomyEngine.BANKRUPTCY_THRESHOLD - 1));
    }

    @Test
    void negativeCashIsBankrupt() {
        assertTrue(EconomyEngine.isBankrupt(-500L));
    }

    @Test
    void zeroCashIsBankrupt() {
        assertTrue(EconomyEngine.isBankrupt(0L));
    }

    @Test
    void highCashIsNotBankrupt() {
        assertFalse(EconomyEngine.isBankrupt(1_000_000L));
    }

    @Test
    void cashExactlyOneBelowThreshold() {
        long threshold = EconomyEngine.BANKRUPTCY_THRESHOLD;
        assertTrue(EconomyEngine.isBankrupt(threshold - 1));
        assertFalse(EconomyEngine.isBankrupt(threshold));
        assertFalse(EconomyEngine.isBankrupt(threshold + 1));
    }
}
