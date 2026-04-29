# 🎵 Remsey's Records — Account Ledger

A CLI-based accounting ledger application built in Java for tracking sales and purchases at a record store. Log customer sales, supplier purchases, and manage your full transaction history — all from the terminal.

---

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Getting Started](#getting-started)
- [How to Run](#how-to-run)
- [Application Screens](#application-screens)
- [CSV Format](#csv-format)
- [Project Structure](#project-structure)
- [Interesting Code](#interesting-code)
- [Technologies Used](#technologies-used)

---

## About

Remsey's Records Ledger is a Java CLI application designed to track all financial transactions for a record store — including vinyl sales, vintage speaker purchases, turntable sales, and supplier restocking orders. Transactions are persisted to a local CSV file and can be filtered, searched, and reported on through an interactive terminal menu.

---

## Features

- ✅ Add sales and purchases with automatic timestamps
- ✅ View full transaction ledger sorted newest to oldest
- ✅ Filter transactions by sales only or purchases only
- ✅ Pre-defined reports: Month to Date, Previous Month, Year to Date, Previous Year
- ✅ Search transactions by vendor name
- ✅ Custom search by start date, end date, description, vendor, and amount
- ✅ Input validation for all fields — handles empty input, invalid numbers, and invalid dates
- ✅ All transactions saved and loaded from a local CSV file
- ✅ Data persists between sessions

---

## Getting Started

### Prerequisites

- Java 17 or higher
- IntelliJ IDEA (recommended) or any Java IDE
- Git

### Installation

1. Clone the repository:
```bash
git clone https://github.com/cjdeniz9/AccountLedger.git
```

2. Open the project in IntelliJ IDEA

3. Navigate to `src/main/resources/` and ensure `transactions.csv` exists with the header:
```
date|time|description|vendor|amount
```

4. Run the application from `Main.java`

---

## How to Run

1. Open the project in IntelliJ IDEA
2. Locate `src/main/java/com/yearupunited/Main.java`
3. Click the green **Run** button or press `Shift + F10`
4. Interact with the application through the terminal

---

## Application Screens

### 🏠 Home Screen
```
====== REMSEY'S RECORDS ======
D) Add Sale
P) Make Purchase
L) Ledger
X) Exit
```

- **D) Add Sale** — Log a customer sale (vinyl, speakers, turntables, accessories)
- **P) Make Purchase** — Log a supplier purchase or store expense
- **L) Ledger** — View and manage all transactions
- **X) Exit** — Exit the application

---

### 📒 Ledger Screen
```
====== LEDGER ======
A) All
D) Sales
P) Purchases
R) Reports
H) Home
```

- **A) All** — Display all transactions, newest first
- **D) Sales** — Display only sales (positive amounts)
- **P) Purchases** — Display only purchases/expenses (negative amounts)
- **R) Reports** — Navigate to the Reports screen
- **H) Home** — Return to the Home screen

---

### 📊 Reports Screen
```
====== REPORTS ======
1) Month To Date
2) Previous Month
3) Year To Date
4) Previous Year
5) Search by Vendor
6) Custom Search
0) Back
```

- **1) Month To Date** — All transactions from the current month
- **2) Previous Month** — All transactions from last month
- **3) Year To Date** — All transactions from the current year
- **4) Previous Year** — All transactions from last year
- **5) Search by Vendor** — Filter by vendor or customer name
- **6) Custom Search** — Filter by any combination of fields
- **0) Back** — Return to the Ledger screen

---

### 🔍 Custom Search Screen
```
====== CUSTOM SEARCH ======
S) Start Date
E) End Date
D) Description
V) Vendor
A) Amount
L) Ledger
```

Filter transactions by any combination of:
- **Start Date / End Date** — Date range in `yyyy-MM-dd` format
- **Description** — Partial match search (e.g. "Strokes" returns all Strokes vinyl)
- **Vendor** — Exact or partial vendor/customer name match
- **Amount** — Filter by amount range (min and max)

---

## CSV Format

All transactions are stored in `src/main/resources/transactions.csv` with the following format:

```
date|time|description|vendor|amount
2026-04-19|14:30:00|Vinyl - Angles by The Strokes|Wren|18.99
2026-04-05|15:30:00|Vinyl Stock - New Arrivals x40|Supplier Records Direct|-280.00
```

| Field | Format | Example |
|---|---|---|
| date | yyyy-MM-dd | 2026-04-19 |
| time | HH:mm:ss | 14:30:00 |
| description | text | Vinyl - Angles by The Strokes |
| vendor | text | Wren / Supplier Records Direct |
| amount | decimal | 18.99 (sale) / -280.00 (purchase) |

- **Positive amounts** = sales to customers
- **Negative amounts** = purchases from suppliers or store expenses

---

## Project Structure

```
src/main/java/com/yearupunited/
├── Main.java
├── model/
│   └── Transaction.java
├── service/
│   ├── TransactionManager.java
│   ├── TransactionFileWriter.java
│   └── TransactionsFileReader.java
└── ui/
    └── UserInterface.java

src/main/resources/
└── transactions.csv
```

- **model** — `Transaction` class with all fields and `toString()`
- **service** — Business logic, file reading/writing, filtering, and input validation
- **ui** — All screen navigation and user interaction
- **Main** — Entry point, initializes manager and starts the UI

---

## Interesting Code

One of the more interesting pieces of code in this project is the **screen-based navigation loop** in `UserInterface.java`. Instead of using recursive method calls (which caused screen stacking and exit bugs), each screen method returns a `String` representing the next screen to navigate to. A single `while` loop in `start()` drives the entire application:

```java
public void start() {
    String currentScreen = "home";

    while (!currentScreen.equals("exit")) {
        switch (currentScreen) {
            case "home"    -> currentScreen = homeScreen();
            case "sale" -> currentScreen = addSaleScreen();
            case "purchase" -> currentScreen = addPurchaseScreen();
            case "ledger"  -> currentScreen = ledgerScreen();
            case "reports" -> currentScreen = reportsScreen();
            case "custom"  -> currentScreen = customSearchScreen();
            default        -> currentScreen = "home";
        }
    }

    System.out.println("Exiting Ledger...");
}
```

This pattern keeps navigation clean, predictable, and free of recursive stacking issues.

---

## Technologies Used

- Java 17
- IntelliJ IDEA
- Git & GitHub
- `java.time` — LocalDate, LocalTime, DateTimeFormatter
- `java.io` — BufferedReader, BufferedWriter, FileReader, FileWriter
- `java.util` — ArrayList, Collections, Scanner