package com.yearupunited;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionFileWriter {

    public static void writeTransactionFileWriter(String fileName, Transaction transaction) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.newLine();
            writer.write(transaction.getDate() + "|" +
                    transaction.getTime() + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    String.format("%.2f", transaction.getAmount()));
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
