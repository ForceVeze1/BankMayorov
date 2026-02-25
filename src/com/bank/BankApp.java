package com.bank;

import java.util.ArrayList;
import java.util.List;



public class BankApp {
    public static void main(String[] args) {
        List<Account> clients = new ArrayList<>();

        try {
            // Создаем 5 базовых
            for (int i = 1; i <= 5; i++)
                clients.add(new BaseAccount("Базовый Клиент " + i, "1111222233334444550" + i, 5000));

            // Создаем 5 премиум
            for (int i = 1; i <= 5; i++)
                clients.add(new PremiumAccount("Премиум Клиент " + i, "2222333344445555660" + i, 20000));

            // Создаем 5 ВИП
            for (int i = 1; i <= 5; i++)
                clients.add(new VipAccount("ВИП Клиент " + i, "3333444455556666770" + i, 150000));

            // --- Демонстрация работы ---

            Account base = clients.get(0); // Базовый
            Account vip = clients.get(10); // ВИП

            System.out.println("--- Состояние до операций ---");
            base.showBalance();
            vip.showBalance();

            System.out.println("\n--- Оплата услуги (тест бонуса и кешбэка) ---");
            base.payService(2000); // Получит +1000 бонуса, кешбэк 0 (т.к. < 10000)
            vip.payService(120000); // Получит +1000 бонуса и 10% кешбэка (12000)

            System.out.println("\n--- Перевод средств ---");
            vip.transfer(base, 5000);

            System.out.println("\n--- Итоговое состояние ---");
            base.showBalance();
            vip.showBalance();

            // Тест ошибки (отрицательный баланс/недостаток средств)
            System.out.println("\n--- Тест ошибки ---");
            base.payService(50000);

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());

        }



    }
}
