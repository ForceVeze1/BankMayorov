package com.bank;

public class VipAccount extends Account {
    public VipAccount(String name, String accNum, double bal) { super(name, accNum, bal); }

    @Override
    protected double calculateCashback(double amount) {
        if (amount >= 100000) return amount * 0.10;
        if (amount >= 10000) return amount * 0.05;
        return amount * 0.01;
    }

    @Override
    public void applyMonthlyFee() { /* Бесплатно */ }
}
