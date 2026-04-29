package com.yearupunited.service;

import com.yearupunited.model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TransactionsFileReader {

    public ArrayList<Transaction> readTransactionsFromCsv(String fileName) {

        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader bufReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufReader.readLine();

            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length != 5) {
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                LocalDate date = LocalDate.parse(parts[0].trim());
                LocalTime time = LocalTime.parse(parts[1].trim());
                String description = removeQuotes(parts[2].trim());
                String vendor = removeQuotes(parts[3].trim());
                double amount = Double.parseDouble(parts[4].trim());

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return transactions;
    }

    public static String removeQuotes(String value) {
        return value.replace("\"", "").trim();
    }
}
