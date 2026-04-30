package com.yearupunited.ui;

import com.yearupunited.service.TransactionManager;

import java.time.LocalDate;

public class UserInterface {

    private final TransactionManager manager;

    public UserInterface(TransactionManager manager) {
        this.manager = manager;
    }

    // Drives the entire application through a screen based navigation loop
    // Each screen method returns the name of the next screen to navigate to
    // Keeping navigation clean and free of recursive stacking issues
    public void start() {
        String currentScreen = "home";

        while (!currentScreen.equals("exit")) {
            switch (currentScreen) {
                case "home" -> currentScreen = homeScreen();
                case "sale" -> currentScreen = addSaleScreen();
                case "purchase" -> currentScreen = addPurchaseScreen();
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
        System.out.println("====== REMSEY'S RECORDS =======");
        System.out.println("D) Add Sale");
        System.out.println("P) Add Purchase");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("> ");

        String option = manager.getStringInput("S", "P", "L", "X");

        if (option.equalsIgnoreCase("S")) return "sales";
        else if (option.equalsIgnoreCase("p")) return "purchase";
        else if (option.equalsIgnoreCase("l")) return "ledger";
        else return "exit";
    }

    public String addSaleScreen() {
        System.out.println();
        System.out.println("====== ADD SALE =======");

        manager.addSale();

        System.out.println();
        System.out.println("Would you like to: ");
        System.out.println("S) Add another sale");
        System.out.println("X) Return home");
        System.out.print("> ");

        String option = manager.getStringInput("S", "X");

        if (option.equalsIgnoreCase("s")) return "sale";
        else return "home";
    }

    public String addPurchaseScreen() {
        System.out.println();
        System.out.println("====== ADD PURCHASE ======");

        manager.addPurchase();

        System.out.println();
        System.out.println("Would you like to: ");
        System.out.println("P) Add another purchase");
        System.out.println("X) Return home");
        System.out.print("> ");

        String option = manager.getStringInput("P", "X");

        if (option.equalsIgnoreCase("p")) return "purchase";
        else return "home";
    }

    public String ledgerScreen() {
        System.out.println();
        System.out.println("====== LEDGER ======");
        System.out.println("A) All");
        System.out.println("S) Sales");
        System.out.println("P) Purchases");
        System.out.println("R) Reports");
        System.out.println("H) Home");
        System.out.print("> ");

        String option = manager.getStringInput("A", "S", "P", "R", "H");

        if (option.equalsIgnoreCase("a")) {
            manager.displayTransactions();
            return "ledger";
        } else if (option.equalsIgnoreCase("s")) {
            manager.displaySales();
            return "ledger";
        } else if (option.equalsIgnoreCase("p")) {
            manager.displayPurchases();
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

            // Loops vendor screen until user wants to return
            while (searchingVendor) {
                System.out.println();
                System.out.print("Enter vendor name: ");
                String vendorName = manager.getStringInput();

                manager.searchByVendor(vendorName);

                System.out.println();
                System.out.println("Would you like to:");
                System.out.println("V) Search another vendor");
                System.out.println("B) Return to Reports");
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