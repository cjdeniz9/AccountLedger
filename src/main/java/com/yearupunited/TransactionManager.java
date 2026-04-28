package com.yearupunited;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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
        transactions.add(transaction);

        delay(1000);

        System.out.println("Deposit added!");

    }

    public void makePayment() {
        transactionScreen();

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, -amount);

        System.out.println();

        System.out.println("Adding payment...");

        TransactionFileWriter.writeTransactionFileWriter(fileName, transaction);
        transactions.add(transaction);

        delay(1000);

        System.out.println("Payment added!");

    }

    public void displayTransactions() {
        int count = 1;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== ALL TRANSACTIONS ======");

        for (Transaction transaction : transactions) {
            System.out.println(count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount());
            count++;
        }
    }

    public void displayDeposits() {
        int count = 1;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== ALL DEPOSITS ======");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount());
                count++;
            }
        }
    }

    public void displayPayments() {
        int count = 1;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== ALL PAYMENTS ======");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount());
                count++;
            }
        }
    }

    public void dateFiltering(String title, int option) {
        int count = 1;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== " + title + " ======");

        for (Transaction transaction : transactions) {
            String entry = count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount();

            if (option == 1 && (transaction.getDate().getMonth() == LocalDate.now().getMonth() &&
                    transaction.getDate().getYear() == LocalDate.now().getYear())) {
                System.out.println(entry);
                count++;
            } else if (option == 2 && (transaction.getDate().getMonth() == LocalDate.now().minusMonths(1).getMonth() &&
                    transaction.getDate().getYear() == LocalDate.now().getYear())) {
                System.out.println(entry);
                count++;
            } else if (option == 3 && (transaction.getDate().getYear() == LocalDate.now().getYear())) {
                System.out.println(entry);
                count++;
            } else if (option == 4 && (transaction.getDate().getYear() == LocalDate.now().getYear() - 1)) {
                System.out.println(entry);
                count++;
            }
        }
    }

    public void searchByVendor(String userInput) {
        int count = 0;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== VENDOR: " + userInput.toUpperCase() + " ======");

        for (Transaction transaction : transactions) {

            if (transaction.getVendor().equalsIgnoreCase(userInput)) {
                count++;
                System.out.println(count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount());
            }
        }

        if (count == 0) {
            System.out.println("No transactions found.");
        }
    }



    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
