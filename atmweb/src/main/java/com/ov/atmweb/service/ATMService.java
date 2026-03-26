package com.ov.atmweb.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ATMService {

    private Map<String, String> userPins = new HashMap<>();
    private Map<String, Double> balances = new HashMap<>();
    private Map<String, List<String>> history = new HashMap<>();
    private Map<String, String> userNames = new HashMap<>();

    public ATMService() {

        userPins.put("1234567890123456", "1234");
        userPins.put("1111222233334444", "4567");

        balances.put("1234567890123456", 1000.0);
        balances.put("1111222233334444", 2000.0);

        history.put("1234567890123456", new ArrayList<>());
        history.put("1111222233334444", new ArrayList<>());

        userNames.put("1234567890123456", "OV Sharma");
        userNames.put("1111222233334444", "John Doe");
    }

    public boolean validateUser(String card, String pin) {
        return userPins.containsKey(card) && userPins.get(card).equals(pin);
    }

    public double getBalance(String card) {
        return balances.get(card);
    }

    public String getUserName(String card) {
        return userNames.get(card);
    }

    public List<String> getHistory(String card) {
        return history.get(card);
    }

    private String time() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return LocalDateTime.now().format(formatter);
    }

    public String deposit(String card, double amount) {

        balances.put(card, balances.get(card) + amount);

        String receipt =
                "Deposit ₹" + amount + " | " + time();

        history.get(card).add(receipt);

        return receipt;
    }

    public String withdraw(String card, double amount) {

        if (amount > balances.get(card)) {
            return "Insufficient Balance";
        }

        balances.put(card, balances.get(card) - amount);

        String receipt =
                "Withdraw ₹" + amount + " | " + time();

        history.get(card).add(receipt);

        return receipt;
    }

    public String transfer(String card, String account, double amount) {

        if (amount > balances.get(card)) {
            return "Insufficient Balance";
        }

        balances.put(card, balances.get(card) - amount);

        String receipt =
                "Transfer ₹" + amount +
                        " to " + account +
                        " | " + time();

        history.get(card).add(receipt);

        return receipt;
    }
}