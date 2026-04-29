package com.yearupunited.ui;

import com.yearupunited.service.TransactionManager;

import java.time.LocalDate;

public class UserInterface {

    private final TransactionManager manager;

    public UserInterface(TransactionManager manager) {
        this.manager = manager;
    }

    public void start() {
        String currentScreen = "home";

        while (!currentScreen.equals("exit")) {
            switch (currentScreen) {
                case "home" -> currentScreen = homeScreen();
                case "deposit" -> currentScreen = addDepositScreen();
                case "payment" -> currentScreen = makePayment();
                case "ledger" -> currentScreen = ledgerScreen();
                case "reports" -> currentScreen = reportsScreen();
                case "custom" -> currentScreen = customSearchScreen();
                default -> currentScreen = "home";
            }
        }

        System.out.println();

        System.out.println("Exiting Ledger...");
        manager.delay(1000);
        System.out.println("Successfully logged out.");
    }

    public String homeScreen() {
        System.out.println();
        System.out.println("====== ACCOUNT LEDGER =======");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("> ");

        String option = manager.getStringInput("D", "P", "L", "X");

        if (option.equalsIgnoreCase("d")) return "deposit";
        else if (option.equalsIgnoreCase("p")) return "payment";
        else if (option.equalsIgnoreCase("l")) return "ledger";
        else return "exit";
    }

    public String addDepositScreen() {
        System.out.println();
        System.out.println("====== ADD DEPOSIT =======");

        manager.addDeposit();

        System.out.println();
        System.out.println("Would you like to: ");
        System.out.println("D) Make another deposit");
        System.out.println("X) Return back to home screen");
        System.out.print("> ");

        String option = manager.getStringInput("D", "X");

        if (option.equalsIgnoreCase("d")) return "deposit";
        else return "home";
    }

    public String makePayment() {
        System.out.println();
        System.out.println("====== MAKE PAYMENT ======");

        manager.makePayment();

        System.out.println();
        System.out.println("Would you like to: ");
        System.out.println("P) Make another payment");
        System.out.println("X) Return back to home screen");
        System.out.print("> ");

        String option = manager.getStringInput("P", "X");

        if (option.equalsIgnoreCase("p")) return "payment";
        else return "home";
    }

    public String ledgerScreen() {
        System.out.println();
        System.out.println("====== LEDGER ======");
        System.out.println("A) All");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");
        System.out.print("> ");

        String option = manager.getStringInput("A", "D", "P", "R", "H");

        if (option.equalsIgnoreCase("a")) {
            manager.displayTransactions();
            return "ledger";
        } else if (option.equalsIgnoreCase("d")) {
            manager.displayDeposits();
            return "ledger";
        } else if (option.equalsIgnoreCase("p")) {
            manager.displayPayments();
            return "ledger";
        } else if (option.equalsIgnoreCase("r")) {
            return "reports";
        } else {
            return "home";
        }
    }

    public String reportsScreen() {
        System.out.println();
        System.out.println("====== REPORTS ======");
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("6) Custom Search");
        System.out.println("0) Back");
        System.out.print("> ");

        int filterOption = manager.getIntInput(0, 1, 2, 3, 4, 5, 6);

        if (filterOption == 1) {
            manager.dateFiltering("MONTH TO DATE", 1);
        } else if (filterOption == 2) {
            manager.dateFiltering("PREVIOUS MONTH", 2);
        } else if (filterOption == 3) {
            manager.dateFiltering("YEAR TO DATE", 3);
        } else if (filterOption == 4) {
            manager.dateFiltering("PREVIOUS YEAR", 4);
        } else if (filterOption == 5) {
            boolean searchingVendor = true;

            while (searchingVendor) {
                System.out.println();
                System.out.print("Enter vendor name: ");
                String vendorName = manager.getStringInput();

                manager.searchByVendor(vendorName);

                System.out.println();
                System.out.println("Would you like to:");
                System.out.println("V) Search another vendor");
                System.out.println("B) Return back to Reports");
                System.out.print("> ");

                String option = manager.getStringInput("V", "B");

                if (option.equalsIgnoreCase("b")) {
                    searchingVendor = false;
                }
            }

            return "reports";
        } else if (filterOption == 6) {
            return "custom";
        } else if (filterOption == 0) {
            return "ledger";
        }

        return "reports";
    }

    public String customSearchScreen() {
        System.out.println();
        System.out.println("====== CUSTOM SEARCH ======");
        System.out.println("C) Date");
        System.out.println("D) Description");
        System.out.println("V) Vendor");
        System.out.println("A) Amount");
        System.out.println("L) Ledger");
        System.out.print("> ");

        String option = manager.getStringInput("C", "D", "V", "A", "L");

        if (option.equalsIgnoreCase("c")) {
            System.out.println();
            System.out.print("Enter start date: ");

            LocalDate startDate = manager.getDateInput();

            System.out.print("Enter end date: ");
            LocalDate endDate = manager.getDateInput();

            manager.dateRange(startDate, endDate);
        } else if (option.equalsIgnoreCase("d")) {
            System.out.println();
            System.out.print("Enter description: ");

            String inputDescription = manager.getStringInput();

            manager.searchByDescription(inputDescription);
        } else if (option.equalsIgnoreCase("v")) {
            System.out.println();
            System.out.print("Enter vendor: ");

            String inputVendor = manager.getStringInput();

            manager.searchByVendor(inputVendor);
        } else if (option.equalsIgnoreCase("a")) {
            System.out.println();
            System.out.print("Enter starting amount: ");

            double startingAmount = manager.getDoubleInput();

            System.out.print("Enter ending amount: ");

            double endingAmount = manager.getDoubleInput();

            manager.amountRange(startingAmount, endingAmount);
        }

        if (option.equalsIgnoreCase("l")) return "ledger";
        else return "custom";
    }
}