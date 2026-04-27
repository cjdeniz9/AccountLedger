package com.yearupunited;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionManager {

    private final List<Transaction> transactions;
    private final Scanner scanner;
    private final String fileName;
    private String description;
    private String vendor;
    private double amount;

    public TransactionManager() {
        this.transactions = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.fileName = "/Users/cjdeniz/Projects/AccountLedger/src/main/transactions.csv";
        this.description = "";
        this.vendor = "";
        this.amount = 0.00;
    }

    public List<Transaction> handleFileReader() {
        TransactionsFileReader fileReader = new TransactionsFileReader();

        return fileReader.readTranscationsFromCsv(fileName);
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        transactions.add(transaction);
    }

    public void transactionScreen() {
        System.out.print("Enter description: ");
        description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        vendor = scanner.nextLine();

        System.out.print("Enter amount: $");
        amount = scanner.nextDouble();
        scanner.nextLine();
    }

    public void addDeposit() {
        transactionScreen();

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount
        );

        System.out.println();

        System.out.println("Adding deposit...");

        TransactionFileWriter.writeTransactionFileWriter(fileName, transaction);

        delay(1000);

        System.out.println("Deposit added!");

    }

    public void makePayment() {
        transactionScreen();

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, -amount);

        System.out.println();

        System.out.println("Adding payment...");

        TransactionFileWriter.writeTransactionFileWriter(fileName, transaction);

        delay(1000);

        System.out.println("Payment added!");

    }

    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
