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

    public TransactionManager() {
        this.transactions = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.fileName = "/Users/cjdeniz/Projects/AccountLedger/src/main/transactions.csv";
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

    public void addDeposit() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount);

        TransactionFileWriter.writeTransactionFileWriter(fileName, transaction);
    }

}
