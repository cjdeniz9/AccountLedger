package com.yearupunited;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TransactionManager manager = new TransactionManager();

        List<Transaction> transactions = manager.handleFileReader();

        for (Transaction transaction : transactions) {
            manager.addTransaction(transaction);
        }

        homeScreen(scanner, manager);

        scanner.close();

    }

    public static void homeScreen(Scanner scanner, TransactionManager manager) {
        System.out.println("====== ACCOUNT LEDGER =======");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("> ");

        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("d")) {
            addDepositScreen(scanner, manager);
        } else if (option.equalsIgnoreCase("p")) {
            makePayment(scanner, manager);
        } else if (option.equalsIgnoreCase("l")) {
            ledgerScreen(scanner, manager);
        }

    }

    public static void addDepositScreen(Scanner scanner, TransactionManager manager) {
        System.out.println();

        System.out.println("====== ADD DEPOSIT =======");

        manager.addDeposit();

        boolean validInput = false;

        while (!validInput) {
            System.out.println();

            System.out.println("Would you like to: ");
            System.out.println("D) Make another deposit");
            System.out.println("X) Return back to home screen");
            System.out.print("> ");

            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("d")) {
                addDepositScreen(scanner, manager);

                validInput = true;
            } else if (option.equalsIgnoreCase("x")) {
                System.out.println();

                homeScreen(scanner, manager);

                validInput = true;
            } else {
                System.out.println();

                System.out.print("Invalid option! Only enter one of the two options available. ");

                System.out.println();
            }
        }

    }

    public static void makePayment(Scanner scanner, TransactionManager manager) {
        System.out.println();

        System.out.println("====== MAKE PAYMENT ======");

        manager.makePayment();

        boolean validInput = false;

        while (!validInput) {
            System.out.println();

            System.out.println("Would you like to: ");
            System.out.println("P) Make another payment");
            System.out.println("X) Return back to home screen");
            System.out.print("> ");

            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("p")) {
                makePayment(scanner, manager);

                validInput = true;
            } else if (option.equalsIgnoreCase("x")) {
                System.out.println();

                homeScreen(scanner, manager);

                validInput = true;
            } else {
                System.out.println();

                System.out.print("Invalid option! Only enter one of the two options available.");

                System.out.println();
            }
        }
    }

    public static void ledgerScreen(Scanner scanner, TransactionManager manager) {
        boolean validInput = false;

        while (!validInput) {
            System.out.println();

            System.out.println("====== LEDGER ======");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("> ");

            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("a")) {
                manager.displayTransactions();
                validInput = true;
            } else if (option.equalsIgnoreCase("d")) {
                manager.displayDeposits();
                validInput = true;
            } else if (option.equalsIgnoreCase("p")) {
                manager.displayPayments();
                validInput = true;
            } else if (option.equalsIgnoreCase("r")) {
                reportsScreen(scanner, manager);
                validInput = true;
            } else {
                System.out.println();

                System.out.println("Invalid option! Only enter one of the five options available.");

                System.out.println();
            }
        }
    }

    public static void reportsScreen(Scanner scanner, TransactionManager manager) {
        boolean validInputReports = false;

        System.out.println();

        while (!validInputReports) {

            System.out.println("====== REPORTS ======");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");
            System.out.print("> ");

            int filterOption = scanner.nextInt();
            scanner.nextLine();

            if (filterOption == 1) {
                manager.handleDateFiltering("MONTH TO DATE", 1);
                validInputReports = true;
            } else if (filterOption == 2) {
                manager.handleDateFiltering("PREVIOUS MONTH", 2);
                validInputReports = true;
            } else if (filterOption == 3) {
                manager.handleDateFiltering("YEAR TO DATE", 3);
                validInputReports = true;
            } else if (filterOption == 4) {
                manager.handleDateFiltering("PREVIOUS YEAR", 4);
                validInputReports = true;
            } else if (filterOption == 0) {
                ledgerScreen(scanner, manager);
                validInputReports = true;
            } else {
                System.out.println();

                System.out.println("Invalid option! Select an option that's a number between [0-6] only.");

                System.out.println();
            }
        }

        boolean validInput = false;

        while (!validInput) {
            System.out.println();

            System.out.println("Would you like to:");
            System.out.println("R) Run another report");
            System.out.println("L) Return back to Ledger");
            System.out.print("> ");

            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("r")) {
                reportsScreen(scanner, manager);
                validInput = true;
            } else if (option.equalsIgnoreCase("l")) {
                ledgerScreen(scanner, manager);
                validInput = true;
            } else {
                System.out.println();

                System.out.print("Invalid option! Only enter one of the two options available.");

                System.out.println();
            }

        }
    }

    public static void customSearchScreen(Scanner scanner) {
        System.out.println();

        System.out.println("====== CUSTOMER SEARCH ======");
        System.out.println("S) Start Date");
        System.out.println("E) End Date");
        System.out.println("D) Description");
        System.out.println("V) Vendor");
        System.out.println("A) Amount");
        System.out.println("L) Ledger");
        System.out.print("> ");

        String option = scanner.nextLine();
    }
}