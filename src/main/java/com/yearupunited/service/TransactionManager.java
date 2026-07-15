package com.yearupunited.service;

import com.yearupunited.model.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TransactionManager {

    // Shared fields accessible across all methods
    private final List<Transaction> transactions;
    private final Scanner scanner;
    private final String fileName;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private String description;
    private String vendor;
    private double amount;

    // Initializes fields and loads existing transactions from CSV on startup
    public TransactionManager() {
        this.transactions = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.fileName = "src/main/transactions.csv";
        this.transactions.addAll(handleFileReader());
        this.description = "";
        this.vendor = "";
        this.amount = 0.00;
    }

    public List<Transaction> handleFileReader() {
        TransactionsFileReader fileReader = new TransactionsFileReader();

        return fileReader.readTransactionsFromCsv(fileName);
    }

    // Returns a sorted copy of transactions ordered by date and time, newest to oldest
    private List<Transaction> getSortedTransactions() {
        List<Transaction> sorted = new ArrayList<>(transactions);
        sorted.sort((a, b) -> {
            int dateCompare = b.getDate().compareTo(a.getDate());
            if (dateCompare != 0) return dateCompare;
            return b.getTime().compareTo(a.getTime()); // tiebreaker by time
        });
        return sorted;
    }

    public void transactionScreen(String type) {
        System.out.print("Enter description: ");
        description = getStringInput();

        System.out.print("Enter vendor: ");
        vendor = getStringInput();

        System.out.print("Enter amount: $");
        amount = getDoubleInput();

        if (type.equalsIgnoreCase("sale")) {
            // Sales must be positive
            while (amount < 0) {
                System.out.print("Sale amount must be positive! Please enter a positive amount: $");
                amount = getDoubleInput();
            }
        } else {
            // Purchases are always stored as negative regardless of input
            amount = -Math.abs(amount);
        }
    }

    public void addSale() {
        transactionScreen("sale");

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount);

        System.out.println();

        System.out.println("Adding sale...");

        TransactionFileWriter.writeTransactionFileWriter(fileName, transaction);
        transactions.add(transaction);

        delay(1000);

        System.out.println("Sale added!");
    }

    public void addPurchase() {
        transactionScreen("purchase");

        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), description, vendor, amount);

        System.out.println();

        System.out.println("Adding purchase...");

        TransactionFileWriter.writeTransactionFileWriter(fileName, transaction);
        transactions.add(transaction);

        delay(1000);

        System.out.println("Purchase added!");
    }

    // Builds a single ledger line, appending a [REFUNDED: date | Time: time] tag if applicable.
// Used by every display/search method so refund status shows up consistently everywhere.
    private String formatTransactionLine(int count, Transaction transaction) {
        String line = count + ". Date: " + transaction.getDate().format(fmt) + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + formatAmount(transaction.getAmount());

        if (transaction.isRefunded()) {
            line += " [REFUNDED: " + transaction.getRefundDate().format(fmt) + " | Time: " + transaction.getRefundTime() + "]";
        }

        return line;
    }


    public void displayTransactions() {
        int count = 1;

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();

        System.out.println("====== ALL TRANSACTIONS ======");

        for (Transaction transaction : sorted) {
            System.out.println(count + ". Date: " + transaction.getDate().format(fmt) + " | Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Vendor: " + transaction.getVendor() + " | Amount: " + formatAmount(transaction.getAmount()));
            count++;
        }
    }

    public void displaySales() {
        int count = 1;

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();

        System.out.println("====== ALL SALES ======");

        for (Transaction transaction : sorted) {

            // Only display transactions with positive amounts (sales)
            if (transaction.getAmount() > 0) {
                System.out.println(formatTransactionLine(count, transaction));
                count++;                count++;
            }
        }
    }

    public void displayPurchases() {
        int count = 1;

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();

        System.out.println("====== ALL PURCHASES ======");

        // Only display transactions with negative amounts (purchases)
        for (Transaction transaction : sorted) {
            if (transaction.getAmount() < 0) {
                System.out.println(formatTransactionLine(count, transaction));
                count++;                count++;
            }
        }
    }

    public void dateFiltering(String title, int option) {
        int count = 1;

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();

        System.out.println("====== " + title + " ======");

        for (Transaction transaction : sorted) {
            String entry = formatTransactionLine(count, transaction);
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
        // Ensure end date is not before start date
        while (endDate.isBefore(startDate)) {
            System.out.print("End date cannot be before start date! Please enter a date after " + startDate.format(fmt) + ": ");
            endDate = getDateInput();
        }

        int count = 0;

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();

        System.out.println("====== DATE RANGE: [ " + startDate.format(fmt) + " - " + endDate.format(fmt) + " ] ======");

        for (Transaction transaction : sorted) {
            String entry = formatTransactionLine(count, transaction);

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

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();

        System.out.println("====== DESCRIPTION: " + userInput.toUpperCase() + " ======");

        for (Transaction transaction : sorted) {
            if (transaction.getDescription().toLowerCase().contains(userInput.toLowerCase())) {
                count++;
                System.out.println(formatTransactionLine(count, transaction));            }
        }

        if (count == 0) {
            System.out.println("No transactions found.");
        }
    }

    public void searchByVendor(String userInput) {
        int count = 0;

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();

        System.out.println("====== VENDOR: " + userInput.toUpperCase() + " ======");

        for (Transaction transaction : sorted) {
            if (transaction.getVendor().toLowerCase().contains(userInput.toLowerCase())) {
                count++;
                System.out.println(formatTransactionLine(count, transaction));            }
        }

        if (count == 0) {
            System.out.println("No transactions found.");
        }
    }

    public void amountRange(double startingAmount, double endingAmount, String type) {

        // Flip amounts to negative for purchases
        if (type.equalsIgnoreCase("p")) {
            double tempStart = -Math.abs(endingAmount);
            double tempEnd = -Math.abs(startingAmount);
            startingAmount = tempStart;
            endingAmount = tempEnd;
        }

        // Ensure ending amount is not less than starting amount
        while (endingAmount < startingAmount) {
            System.out.print("Ending amount cannot be less than starting amount! Please enter an amount greater than " + formatAmount(startingAmount) + ": ");
            endingAmount = getDoubleInput();
        }

        int count = 0;

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();
        System.out.println("====== AMOUNT RANGE: [ " + formatAmount(startingAmount) + " - " + formatAmount(endingAmount) + " ] ======");

        for (Transaction transaction : sorted) {
            double amt = transaction.getAmount();

            boolean inRange = switch (type.toUpperCase()) {
                case "S" -> amt > 0 && amt >= startingAmount && amt <= endingAmount;
                case "P" -> amt < 0 && amt >= startingAmount && amt <= endingAmount;
                default  -> amt >= startingAmount && amt <= endingAmount;
            };

            // Display transactions that fall within the specified amount range (inclusive)
            if (inRange) {
                count++;
                System.out.println(formatTransactionLine(count, transaction));            }
        }

        if (count == 0) {
            System.out.println("No transactions found.");
        }
    }

    public String getStringInput(String... validOptions) {
        String input = scanner.nextLine();

        if (validOptions.length == 0) {
            // No valid options specified — accept any non-empty input
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
                        return input; // valid option found, return it
                    }
                }
                System.out.print("Invalid option! Only enter one of these options [ " + String.join(", ", validOptions) + " ]: ");
            }
            input = scanner.nextLine();
        }
    }

    public void refundTransaction(String searchTerm) {
        List<Transaction> allMatches = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                allMatches.add(transaction);
            }
        }

        if (allMatches.isEmpty()) {
            System.out.println();
            System.out.println("No transaction found matching \"" + searchTerm + "\".");
            return;
        }

        // Only transactions that aren't already refunded are eligible to be refunded again
        List<Transaction> refundable = new ArrayList<>();
        for (Transaction transaction : allMatches) {
            if (!transaction.isRefunded()) {
                refundable.add(transaction);
            }
        }

        if (refundable.isEmpty()) {
            System.out.println();
            System.out.println("\"" + searchTerm + "\" has already been refunded.");
            return;
        }

        Transaction target;

        if (refundable.size() == 1) {
            target = refundable.get(0);
        } else {
            // Multiple matching purchases (e.g. bought the same item twice) — let the user pick which one
            System.out.println();
            System.out.println("Multiple matching transactions found, please choose one:");

            for (int i = 0; i < refundable.size(); i++) {
                System.out.println(formatTransactionLine(i + 1, refundable.get(i)));
            }

            System.out.print("> ");
            int choice = getIntInput(range(1, refundable.size()));
            target = refundable.get(choice - 1);
        }

        target.setRefundDate(LocalDate.now());
        target.setRefundTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));

        // Refunding updates an existing row rather than adding a new one, so the whole file is rewritten
        TransactionFileWriter.rewriteAllTransactions(fileName, transactions);

        System.out.println();
        System.out.println("\"" + target.getDescription() + "\" has been marked as [REFUNDED].");
    }

    public void displayRefundedTransactions() {
        int count = 0;

        List<Transaction> sorted = getSortedTransactions();

        System.out.println();
        System.out.println("====== ITEMS REFUNDED ======");

        for (Transaction transaction : sorted) {
            if (transaction.isRefunded()) {
                count++;
                System.out.println(formatTransactionLine(count, transaction));
            }
        }

        if (count == 0) {
            System.out.println("No refunded transactions found.");
        }
    }

    // Small helper to build an int[] of consecutive numbers for getIntInput's valid-options check
    private int[] range(int start, int end) {
        int[] values = new int[end - start + 1];
        for (int i = 0; i < values.length; i++) {
            values[i] = start + i;
        }
        return values;
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

    // Validates date input, must be in MM-dd-yyyy format
    public LocalDate getDateInput() {
        while (true) {
            String date = scanner.nextLine().trim();

            if (date.isEmpty()) {
                System.out.print("Field cannot be empty! Please enter a date (MM-dd-yyyy): ");
            } else {
                try {
                    return LocalDate.parse(date, fmt);
                } catch (DateTimeParseException e) {
                    System.out.print("Invalid date format! Please enter date as (MM-dd-yyyy): ");
                }
            }
        }
    }

    // Formats amount with $ symbol, preserving negative sign for purchases
    public String formatAmount(double amount) {
        if (amount < 0) {
            return "-$" + String.format("%.2f", Math.abs(amount));
        }
        return "$" + String.format("%.2f", amount);
    }

    public void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}