package com.bank;

public class PremiumAccount extends Account {
    public PremiumAccount(String name, String accNum, double bal) { super(name, accNum, bal); }

    @Override
    protected double calculateCashback(double amount) {
        return (amount >= 10000) ? amount * 0.05 : 0;
    }

    @Override
    public void applyMonthlyFee() { /* Бесплатно */ }
}
