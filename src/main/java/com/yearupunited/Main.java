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
        System.out.println("=== ACCOUNT LEDGER ===");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("> ");
        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("d")) {
            addDepositScreen(scanner, manager);
        }

//        else if (option.equalsIgnoreCase("p")) {
//
//        }

    }

    public static void addDepositScreen(Scanner scanner, TransactionManager manager) {
        System.out.println();
        System.out.println("=== ADD DEPOSIT ===");

        manager.addDeposit();
    }

    public static void ledgerScreen(Scanner scanner) {
        System.out.println();
        System.out.println("=== LEDGER ===");
        System.out.println("A) All");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");
        System.out.print("> ");
        String option = scanner.nextLine();
    }

    public static void reportsScreen(Scanner scanner) {
        System.out.println();
        System.out.println("=== REPORTS ===");
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("6) Custom Search");
        System.out.println("0) Back");
        System.out.print("> ");
        int option = scanner.nextInt();
        scanner.nextLine();
    }

    public static void customSearchScreen(Scanner scanner) {
        System.out.println();
        System.out.println("=== CUSTOMER SEARCH ===");
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