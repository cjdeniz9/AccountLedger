package com.yearupunited;

import java.io.FileWriter;
import java.io.IOException;

public class TransactionFileWriter {

    public static void writeTransactionFileWriter(String fileName, Transaction transaction) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(transaction.getDate() + "|" +
                    transaction.getTime() + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    transaction.getAmount() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
