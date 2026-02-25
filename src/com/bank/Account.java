package com.bank;

public abstract class Account {
    private String fullName;
    private String accountNumber;
    protected double balance;
    private boolean bonusReceived = false;

    public Account(String fullName, String accountNumber, double initialBalance) {
        if (accountNumber.length() != 20 || !accountNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Номер счета должен состоять из 20 цифр");
        }
        this.fullName = fullName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void showBalance() {
        System.out.printf("Клиент: %s | Счет: %s | Баланс: %.2f руб.%n", fullName, accountNumber, balance);
    }

    public void transfer(Account target, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        if (this.balance < amount) throw new IllegalStateException("Недостаточно средств для перевода");

        this.balance -= amount;
        target.balance += amount;
        System.out.printf("Перевод %.2f руб. клиенту %s выполнен успешно.%n", amount, target.fullName);
    }

    public void payService(double cost) {
        if (cost <= 0) throw new IllegalArgumentException("Стоимость услуги должна быть положительной");
        if (this.balance < cost) throw new IllegalStateException("Недостаточно средств для оплаты");

        this.balance -= cost;

        // Начисление кешбэка
        double cashback = calculateCashback(cost);
        this.balance += cashback;

        // Приветственный бонус после первой оплаты
        if (!bonusReceived) {
            this.balance += 1000;
            bonusReceived = true;
            System.out.println("Поздравляем! Вам начислен приветственный бонус 1000 руб.");
        }

        System.out.printf("Оплата %.2f руб. выполнена. Кешбэк: %.2f руб.%n", cost, cashback);
    }

    // Абстрактный метод, который каждый тип аккаунта реализует по-своему
    protected abstract double calculateCashback(double amount);

    public abstract void applyMonthlyFee();

    public String getFullName() { return fullName; }
}
