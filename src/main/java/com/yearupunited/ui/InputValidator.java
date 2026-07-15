
package com.yearupunited.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {
    private final Scanner scanner;

    // The constructor: this runs when UserInterface writes
    //     new InputValidator(scanner)
    // It's like handing the new worker the microphone on their first day.
    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * THE UPPER/LOWERCASE FIX.
     * Asks the question over and over until the user types one of the
     * allowed options. "d", "D", or even "  d  " all count as "D".
     * Example:  promptMenuChoice("Choose: ", "D", "P", "L", "X")
     * Returns:  the choice in UPPERCASE, e.g. "D"
     */

    public String promptMenuChoice(String prompt, String... validOptions) {
        while (true) {                                        // loop forever...
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            //             read the line ^     ^ remove spaces  ^ d becomes D

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please choose one of the listed options.");
                continue;                                     // ask again
            }

            for (String option : validOptions) {
                if (input.equalsIgnoreCase(option)) {
                    return input;
                }
            }

            System.out.println("\"" + input + "\" is not a valid option. Please try again.");
        }
    }

    /**
     * For custom search: asks for a date, but pressing Enter means
     * "skip this filter" and returns null. A wrong date re-asks.
     */
    public LocalDate promptOptionalDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;                 // Enter = skip
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use yyyy-MM-dd (e.g. 2026-04-19), or press Enter to skip.");
            }
        }
    }

    /**
     * Asks for text. Pressing Enter = skip (returns null).
     */
    public String promptOptionalString(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    /**
     * Asks for a number like 18.99. Pressing Enter = skip (returns null).
     * A non-number like "abc" re-asks.
     */
    public Double promptOptionalDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;                 // Enter = skip
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Enter a value like 18.99, or press Enter to skip.");
            }
        }
    }
}