package com.yearupunited.service;

import com.yearupunited.model.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
        this.transactions.addAll(handleFileReader());
        this.description = "";
        this.vendor = "";
        this.amount = 0.00;
    }

    public List<Transaction> handleFileReader() {
        TransactionsFileReader fileReader = new TransactionsFileReader();

        return fileReader.readTransactionsFromCsv(fileName);
    }

    public void transactionScreen() {
        System.out.print("Enter description: ");
        description = getStringInput();

        System.out.print("Enter vendor: ");
        vendor = getStringInput();

        System.out.print("Enter amount: $");
        amount = getDoubleInput();
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

        List<Transaction> sorted = new ArrayList<>(transactions);
        sorted.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        System.out.println();

        System.out.println("====== " + title + " ======");

        for (Transaction transaction : sorted) {
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

    public String getStringInput(String... validOptions) {
        String input = scanner.nextLine();

        if (validOptions.length == 0) {
            while (input.trim().isEmpty()) {
                System.out.print("Field cannot be empty: ");
                input = scanner.nextLine();
            }
            return input;
        }

        while (true) {
            if (input.trim().isEmpty()) {
                System.out.print("Field cannot be empty! Please enter a option: ");
            } else {
                for (String option : validOptions) {
                    if (input.equalsIgnoreCase(option)) {
                        return input;
                    }
                }
                System.out.print("Invalid option! Only enter one of these options [ " + String.join(", ", validOptions) + " ]: ");
            }
            input = scanner.nextLine();
        }
    }

    public int getIntInput(int... validOptions) {
        int input = 0;
        boolean validInput = false;

        while (!validInput) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                System.out.print("Field cannot be empty! Please enter a number: ");
            } else {
                try {
                    input = Integer.parseInt(line);

                    if (validOptions.length == 0) {
                        validInput = true; // any number accepted
                    } else {
                        for (int option : validOptions) {
                            if (input == option) {
                                validInput = true;
                                break;
                            }
                        }
                        if (!validInput) {
                            System.out.print("Invalid option! Only enter one of these options [ " +
                                    Arrays.toString(validOptions).replaceAll("[\\[\\]]", "") + " ]: ");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid option! Please enter a number: ");
                }
            }
        }

        return input;
    }

    public double getDoubleInput() {
        double input = 0;
        boolean validInput = false;

        while (!validInput) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                System.out.print("Invalid input! Amount cannot be empty: $");
            } else {
                try {
                    input = Double.parseDouble(line);
                    if (input == 0) {
                        System.out.print("Invalid input! Amount cannot be zero: $");
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input! Please enter a number: $");
                }
            }
        }

        return input;
    }

    public void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
