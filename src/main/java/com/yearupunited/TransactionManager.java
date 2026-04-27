package com.yearupunited;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    private final List<Transaction> transactions;

    public TransactionManager() { this.transactions = new ArrayList<>(); }

    public List<Transaction> handleFileReader() {
        TransactionsFileReader fileReader = new TransactionsFileReader();

        return fileReader.readTranscationsFromCsv("/Users/cjdeniz/Projects/AccountLedger/src/main/transactions.csv");
    }

    public void addTranscation(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        transactions.add(transaction);
    }
}
