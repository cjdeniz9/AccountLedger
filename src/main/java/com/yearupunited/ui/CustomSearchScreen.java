package com.yearupunited.ui;
import com.yearupunited.model.Transaction;
import com.yearupunited.service.CustomSearch;

import java.time.LocalDate;
import java.util.List;

/**
 * CustomSearchScreen = the FACE of the custom search feature.
 * It does exactly three things, in order:
 *   1. Asks the user six questions (the InputValidator checks every answer)
 *   2. Hands the answers to CustomSearch (the brain) to do the searching
 *   3. Prints whatever came back
 *     new CustomSearchScreen(validator, manager.getAllTransactions()).show()
 */

public class CustomSearchScreen {

    private final InputValidator validator;
    private final List<Transaction> transactions;

    // Constructor: the screen is handed the two things it needs to do its job.
    public CustomSearchScreen(InputValidator validator, List<Transaction> transactions) {
        this.validator = validator;
        this.transactions = transactions;
    }

    /**
     * Shows the screen once, then returns "ledger" so your navigation
     * loop knows where to send the user next.
     */

    public String show() {
        System.out.println("\n====== CUSTOM SEARCH ======");
        System.out.println("Fill in any filters you want. Press Enter to skip a field.\n");

        // Every answer below is either a real value or null (null = user pressed Enter = skip).
        LocalDate startDate = validator.promptOptionalDate("S) Start date (yyyy-MM-dd): ");
        LocalDate endDate   = validator.promptOptionalDate("E) End date (yyyy-MM-dd): ");
        String description  = validator.promptOptionalString("D) Description: ");
        String vendor       = validator.promptOptionalString("V) Vendor: ");
        Double minAmount    = validator.promptOptionalDouble("A) Minimum amount: ");
        Double maxAmount    = validator.promptOptionalDouble("A) Maximum amount: ");

        // Hand everything to the brain and ask for the results.
        List<Transaction> results = new CustomSearch(transactions)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .vendor(vendor)
                .minAmount(minAmount)
                .maxAmount(maxAmount)
                .results();

        if (results.isEmpty()) {
            System.out.println("\nNo transactions matched your search.");
        } else {
            System.out.println("\ndate|time|description|vendor|amount");
            results.forEach(System.out::println);   // print each one, newest first
        }

        return "ledger";   // tell the navigation loop: "send the user back to the ledger"
    }
}