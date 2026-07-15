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
            String line = bufReader.readLine(); // Skips header

            while ((line = bufReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.toLowerCase().startsWith("date|")) continue;
                // -1 keeps trailing empty fields (e.g. blank refund columns) instead of dropping them
                String[] parts = line.split("\\|");
                if (parts.length !=5) {
                    System.out.println("Skippinv invalid row..." + line);
                    continue;
                }

                // 5 columns = legacy row with no refund info, 7 columns = row with refund columns present
                if (parts.length != 5 && parts.length != 7) {
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                LocalDate date = LocalDate.parse(parts[0].trim());
                LocalTime time = LocalTime.parse(parts[1].trim());
                String description = removeQuotes(parts[2].trim());
                String vendor = removeQuotes(parts[3].trim());
                double amount = Double.parseDouble(parts[4].trim());

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                if (parts.length == 7) {
                    String refundDateStr = parts[5].trim();
                    String refundTimeStr = parts[6].trim();

                    if (!refundDateStr.isEmpty() && !refundTimeStr.isEmpty()) {
                        transaction.setRefundDate(LocalDate.parse(refundDateStr));
                        transaction.setRefundTime(LocalTime.parse(refundTimeStr));
                    }
                }

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
