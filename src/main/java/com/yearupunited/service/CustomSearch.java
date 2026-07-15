package com.yearupunited.service;
import com.yearupunited.model.Transaction;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * CustomSearch = the search engine. The BRAIN of the custom search feature.
 * You use it like filling out an order form, one line at a time:
 *     List<Transaction> found = new CustomSearch(allTransactions)
 *             .vendor("wren")                     // only this vendor...
 *             .minAmount(0.0)                     // ...and only sales
 *             .results();                         // go!
 * Any filter you DON'T set is simply ignored. Setting a filter to null
 * also means "ignore it" — that's what makes "press Enter to skip" work.
 */

public class CustomSearch {

    // The pile of transactions we will search through. "final" = set once, never swapped.
    private final List<Transaction> transactions;

    // The six possible filters. They all start as null, which means "no filter".
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String vendor;
    private Double minAmount;
    private Double maxAmount;

    // Constructor: runs when you write "new CustomSearch(list)".
    public CustomSearch(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Each of these sets one filter and then RETURNS THIS — meaning
    public CustomSearch startDate(LocalDate startDate)   { this.startDate = startDate;     return this; }
    public CustomSearch endDate(LocalDate endDate)       { this.endDate = endDate;         return this; }
    public CustomSearch description(String description)  { this.description = description; return this; }
    public CustomSearch vendor(String vendor)            { this.vendor = vendor;           return this; }
    public CustomSearch minAmount(Double minAmount)      { this.minAmount = minAmount;     return this; }
    public CustomSearch maxAmount(Double maxAmount)      { this.maxAmount = maxAmount;     return this; }

    /**
     * Runs the search. Read the stream below like a conveyor belt:
     * every transaction rides the belt, each .filter() is a checkpoint
     * that throws off the ones that don't match, and whatever survives
     * gets sorted newest-first and put in a list.
     */

    public List<Transaction> results() {
        return transactions.stream()
                .filter(t -> startDate == null || !t.getDate().isBefore(startDate))
                .filter(t -> endDate == null || !t.getDate().isAfter(endDate))
                .filter(t -> containsIgnoreCase(t.getDescription(), description))
                .filter(t -> containsIgnoreCase(t.getVendor(), vendor))
                .filter(t -> minAmount == null || t.getAmount() >= minAmount)
                .filter(t -> maxAmount == null || t.getAmount() <= maxAmount)
                .sorted(Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed())
                .toList();
    }

    /**
     * THE UPPER/LOWERCASE TRICK FOR SEARCHING:
     * lowercase both sides before comparing, so "STROKES", "strokes",
     * and "Strokes" all find "Vinyl - Angles by The Strokes".
     * A null filter means "the user skipped this — everything matches."
     */

    private static boolean containsIgnoreCase(String fieldValue, String filter) {
        return filter == null || fieldValue.toLowerCase().contains(filter.toLowerCase());
    }
}
