package com.yearupunited.ui;

import com.yearupunited.service.TransactionManager;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;
    private final TransactionManager manager;

    public UserInterface(TransactionManager manager) {
        this.scanner = new Scanner(System.in);
        this.manager = manager;
    }
    public void homeScreen() {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("====== ACCOUNT LEDGER =======");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("> ");

            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("d")) {
                addDepositScreen();
                validInput = true;
            } else if (option.equalsIgnoreCase("p")) {
                makePayment();
                validInput = true;
            } else if (option.equalsIgnoreCase("l")) {
                ledgerScreen();
                validInput = true;
            } else if (option.equalsIgnoreCase("x")) {
                System.out.println();

                System.out.println("Exiting Ledger...");
                manager.delay(1000);
                System.out.println("Successfully logged out.");

                validInput = true;
            } else {
                System.out.println("Invalid option! Only enter D, P, L, or X.");
            }
        }
    }

    public void addDepositScreen() {
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
                addDepositScreen();

                validInput = true;
            } else if (option.equalsIgnoreCase("x")) {
                System.out.println();

                homeScreen();

                validInput = true;
            } else {
                System.out.println();

                System.out.print("Invalid option! Only enter D or X.");

                System.out.println();
            }
        }

    }

    public void makePayment() {
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
                makePayment();

                validInput = true;
            } else if (option.equalsIgnoreCase("x")) {
                System.out.println();

                homeScreen();

                validInput = true;
            } else {
                System.out.println();

                System.out.print("Invalid option! Only enter P or X.");

                System.out.println();
            }
        }
    }

    public void ledgerScreen() {
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
                reportsScreen();
                validInput = true;
            } else {
                System.out.println();

                System.out.println("Invalid option! Only enter A, D, P, R, or H.");

                System.out.println();
            }
        }
    }

    public void reportsScreen() {
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
                manager.dateFiltering("MONTH TO DATE", 1);
                validInputReports = true;
            } else if (filterOption == 2) {
                manager.dateFiltering("PREVIOUS MONTH", 2);
                validInputReports = true;
            } else if (filterOption == 3) {
                manager.dateFiltering("YEAR TO DATE", 3);
                validInputReports = true;
            } else if (filterOption == 4) {
                manager.dateFiltering("PREVIOUS YEAR", 4);
                validInputReports = true;
            } else if (filterOption == 5) {
                boolean searchingVendor = true;

                while (searchingVendor) {
                    System.out.println();

                    System.out.print("Enter vendor name: ");
                    String vendorName = scanner.nextLine();

                    manager.searchByVendor(vendorName);

                    boolean validVendorOption = false;

                    while (!validVendorOption) {
                        System.out.println();

                        System.out.println("Would you like to:");
                        System.out.println("V) Search another vendor");
                        System.out.println("B) Return back to Reports");
                        System.out.print("> ");

                        String option = scanner.nextLine();

                        if (option.equalsIgnoreCase("v")) {
                            validVendorOption = true; // exits inner loop, reruns vendor search
                        } else if (option.equalsIgnoreCase("b")) {
                            searchingVendor = false;  // exits both loops
                            validVendorOption = true;
                            reportsScreen();
                        } else {
                            System.out.println();
                            System.out.print("Invalid option! Only enter V or B.");
                        }
                    }
                }

                validInputReports = true;
            } else if (filterOption == 0) {
                ledgerScreen();
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
                reportsScreen();
                validInput = true;
            } else if (option.equalsIgnoreCase("l")) {
                ledgerScreen();
                validInput = true;
            } else {
                System.out.println();

                System.out.print("Invalid option! Only enter R or L.");

                System.out.println();
            }

        }
    }

    public void customSearchScreen(Scanner scanner) {
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
