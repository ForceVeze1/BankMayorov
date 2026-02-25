package com.bank.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private final Connection connection;

    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void save(String name, String accNum, double balance, String type) throws SQLException {
        String sql = "INSERT INTO accounts (full_name, account_number, balance, account_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, accNum);
            stmt.setDouble(3, balance);
            stmt.setString(4, type);
            stmt.executeUpdate();
        }
    }

    // READ (Выборка по параметру - номеру счета)
    public void findByAccountNumber(String accNum) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accNum);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Найден: " + rs.getString("full_name") + " | Баланс: " + rs.getDouble("balance"));
            }
        }
    }

    // UPDATE
    public void updateBalance(String accNum, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accNum);
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void delete(String accNum) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accNum);
            stmt.executeUpdate();
        }
    }

    // TRANSACTION: Перевод средств
    public void transferMoney(String fromAcc, String toAcc, double amount) throws SQLException {
        String withdrawSQL = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        String depositSQL = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try {
            connection.setAutoCommit(false); // Начало транзакции

            try (PreparedStatement withdraw = connection.prepareStatement(withdrawSQL);
                 PreparedStatement deposit = connection.prepareStatement(depositSQL)) {

                // Списание
                withdraw.setDouble(1, amount);
                withdraw.setString(2, fromAcc);
                withdraw.setDouble(3, amount);
                int updated = withdraw.executeUpdate();

                if (updated == 0) throw new SQLException("Недостаточно средств или неверный счет");

                // Зачисление
                deposit.setDouble(1, amount);
                deposit.setString(2, toAcc);
                deposit.executeUpdate();

                connection.commit(); // Фиксация
                System.out.println("Транзакция завершена успешно!");
            }
        } catch (SQLException e) {
            connection.rollback(); // Откат при ошибке
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
