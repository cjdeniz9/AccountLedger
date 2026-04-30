package com.yearupunited.service;

import com.yearupunited.model.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TransactionManager {

    // Shared fields accessible across all methods
    private final List<Transaction> transactions;
    private final Scanner scanner;
    private final String fileName;
    private String description;
    private String vendor;
    private double amount;

    // Initializes fields and loads existing transactions from CSV on startup
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

    public void addSale() {
        transactionScreen();

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount
        );

        System.out.println();

        System.out.println("Adding sale...");

        TransactionFileWriter.writeTransactionFileWriter(fileName, transaction);
        transactions.add(transaction);

        delay(1000);

        System.out.println("Sale added!");

    }

    public void addPurchase() {
        transactionScreen();

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, -amount);

        System.out.println();

        System.out.println("Adding purchase...");

        TransactionFileWriter.writeTransactionFileWriter(fileName, transaction);
        transactions.add(transaction);

        delay(1000);

        System.out.println("Purchase added!");

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

    public void displaySales() {
        int count = 1;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== ALL SALES ======");

        for (Transaction transaction : transactions) {

            // Only display transactions with positive amounts (sales)
            if (transaction.getAmount() > 0) {
                System.out.println(count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount());
                count++;
            }
        }
    }

    public void displayPurchases() {
        int count = 1;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== ALL PURCHASES ======");

        // Only display transactions with negative amounts (purchases)
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount());
                count++;
            }
        }
    }

    public void dateFiltering(String title, int option) {
        int count = 1;

        // Creates a sorted copy of transactions ordered by date, newest to oldest
        List<Transaction> sorted = new ArrayList<>(transactions);
        sorted.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        System.out.println();

        System.out.println("====== " + title + " ======");

        for (Transaction transaction : sorted) {
            String entry = count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount();

            // Checks month to date
            if (option == 1 && (transaction.getDate().getMonth() == LocalDate.now().getMonth() &&
                    transaction.getDate().getYear() == LocalDate.now().getYear())) {
                System.out.println(entry);
                count++;
            }

            // Checks previous month
            else if (option == 2 && (transaction.getDate().getMonth() == LocalDate.now().minusMonths(1).getMonth() &&
                    transaction.getDate().getYear() == LocalDate.now().getYear())) {
                System.out.println(entry);
                count++;
            }

            // Checks year to date
            else if (option == 3 && (transaction.getDate().getYear() == LocalDate.now().getYear())) {
                System.out.println(entry);
                count++;
            }

            // Checks previous year
            else if (option == 4 && (transaction.getDate().getYear() == LocalDate.now().getYear() - 1)) {
                System.out.println(entry);
                count++;
            }
        }
    }

    public void dateRange(LocalDate startDate, LocalDate endDate) {
        int count = 0;

        List<Transaction> sorted = new ArrayList<>(transactions);
        sorted.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        System.out.println();

        System.out.println("====== DATE RANGE: " + startDate + " - " + endDate + " ======");

        for (Transaction transaction : sorted) {
            String entry = count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount();

            LocalDate date = transaction.getDate();

            // Display transactions that fall within the start and end date range (inclusive)
            if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
                count++;
                System.out.println(entry);
            }
        }
    }

    public void searchByDescription(String userInput) {
        int count = 0;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== DESCRIPTION: " + userInput.toUpperCase() + " ======");

        for (Transaction transaction : transactions) {

            if (transaction.getDescription().toLowerCase().contains(userInput.toLowerCase())) {
                count++;
                System.out.println(count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount());
            }
        }

        if (count == 0) {
            System.out.println("No transactions found.");
        }
    }

    public void searchByVendor(String userInput) {
        int count = 0;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== VENDOR: " + userInput.toUpperCase() + " ======");

        for (Transaction transaction : transactions) {

            if (transaction.getVendor().toLowerCase().contains(userInput.toLowerCase())) {
                count++;
                System.out.println(count + ". Date: " + transaction.getDate() + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + transaction.getAmount());
            }
        }

        if (count == 0) {
            System.out.println("No transactions found.");
        }
    }

    public void amountRange(double startingAmount, double endingAmount) {
        int count = 0;

        Collections.reverse(transactions);

        System.out.println();

        System.out.println("====== AMOUNT RANGE: " + startingAmount + " - " + endingAmount + " ======");

        for (Transaction transaction : transactions) {

            // Display transactions that fall within the specified amount range (inclusive)
            if (transaction.getAmount() >= startingAmount && transaction.getAmount() <= endingAmount) {
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
            // No valid options specified
            while (input.trim().isEmpty()) {
                System.out.print("Field cannot be empty: ");
                input = scanner.nextLine();
            }
            return input;
        }

        while (true) {
            // Reject empty input or input that doesn't match a valid option
            if (input.trim().isEmpty()) {
                System.out.print("Field cannot be empty! Please enter a option: ");
            } else {
                for (String option : validOptions) {
                    if (input.equalsIgnoreCase(option)) {
                        return input; // Returns true if valid option
                    }
                }
                System.out.print("Invalid option! Only enter one of these options [ " + String.join(", ", validOptions) + " ]: ");
            }
            input = scanner.nextLine();
        }
    }
    // Validates integer input, optionally restricting to a set of valid options
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

    // Validates double input, rejects empty input and zero amounts
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

    // Validates date input, must be in yyyy-MM-dd format
    public LocalDate getDateInput() {
        while (true) {
            String date = scanner.nextLine().trim();

            if (date.isEmpty()) {
                System.out.print("Field cannot be empty! Please enter a date (yyyy-MM-dd): ");
            } else {
                try {
                    return LocalDate.parse(date);
                } catch (DateTimeParseException e) {
                    System.out.print("Invalid date format! Please enter date as (yyyy-MM-dd): ");
                }
            }
        }
    }

    public void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
