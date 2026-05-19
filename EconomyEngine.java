package com.transportgame.core;

public class EconomyEngine {

    public static final long HOUSE_INCOME         = 8L;
    public static final long GARAGE_INCOME        = 3L;
    public static final long STORAGE_INCOME       = 2L;
    public static final long PASSENGER_RATE       = 15L;
    public static final long CARGO_RATE           = 10L;
    public static final long BANKRUPTCY_THRESHOLD = 100L;

    public static long calcPassengerPayment(int passengers) {
        return passengers * PASSENGER_RATE;
    }

    public static long calcCargoPayment(int cargo) {
        return cargo * CARGO_RATE;
    }

    public static long calcBuildingIncome(int houses, int garages, int storages) {
        return houses * HOUSE_INCOME + garages * GARAGE_INCOME + storages * STORAGE_INCOME;
    }

    public static boolean isBankrupt(long cash) {
        return cash < BANKRUPTCY_THRESHOLD;
    }
}
