package com.yearupunited.service;

import com.yearupunited.model.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TransactionFileWriter {

    public static void writeTransactionFileWriter(String fileName, Transaction transaction) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.newLine();
            writer.write(buildLine(transaction));
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Rewrites the entire CSV from the current in-memory list.
    // Needed for refunds, since refunding updates a row that already exists
    // rather than appending a brand new one.
    public static void rewriteAllTransactions(String fileName, List<Transaction> transactions) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            writer.write("date|time|description|vendor|amount|refundDate|refundTime");

            for (Transaction transaction : transactions) {
                writer.newLine();
                writer.write(buildLine(transaction));
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Shared line-building logic so appended rows and rewritten rows always match format
    private static String buildLine(Transaction transaction) {
        String refundDateStr = transaction.isRefunded() ? transaction.getRefundDate().toString() : "";
        String refundTimeStr = transaction.isRefunded() ? transaction.getRefundTime().toString() : "";

        return transaction.getDate() + "|" +
                transaction.getTime() + "|" +
                transaction.getDescription() + "|" +
                transaction.getVendor() + "|" +
                String.format("%.2f", transaction.getAmount()) + "|" +
                refundDateStr + "|" +
                refundTimeStr;
    }
}