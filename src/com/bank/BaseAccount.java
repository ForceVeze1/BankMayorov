package com.bank;

public class BaseAccount extends Account {
    public BaseAccount(String name, String accNum, double bal) { super(name, accNum, bal); }

    @Override
    protected double calculateCashback(double amount) {
        return (amount >= 10000) ? amount * 0.01 : 0;
    }

    @Override
    public void applyMonthlyFee() {
        if (balance < 100) throw new IllegalStateException("Недостаточно средств для списания комиссии");
        balance -= 100;
    }
}
