package com.yearupunited.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Represents a single financial transaction with date, time, description, vendor, and amount
public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    // Set only when this transaction has been refunded; null/unset means not refunded
    private LocalDate refundDate;
    private LocalTime refundTime;

    // Initializes a transaction with all required fields
    public Transaction(LocalDate _date, LocalTime _time, String _description, String _vendor, double _amount) {
        this.date = _date;
        this.time = _time;
        this.description = _description;
        this.vendor = _vendor;
        this.amount = _amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate _date) {
        this.date = _date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime _time) {
        this.time = _time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String _vendor) {
        this.vendor = _vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double _amount) {
        this.amount = _amount;
    }

    public LocalDate getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDate _refundDate) {
        this.refundDate = _refundDate;
    }

    public LocalTime getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(LocalTime _refundTime) {
        this.refundTime = _refundTime;
    }

    // A transaction is considered refunded once both refund date and time are set
    public boolean isRefunded() {
        return refundDate != null && refundTime != null;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        String base = String.format("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f",
                date.format(dateFmt),
                time.format(timeFmt),
                description,
                vendor,
                amount);

        if (isRefunded()) {
            base += " [REFUNDED: " + refundDate.format(dateFmt) + " | Time: " + refundTime.format(timeFmt) + "]";
        }

        return base;
    }
}
