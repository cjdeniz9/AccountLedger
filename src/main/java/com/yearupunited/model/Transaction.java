package com.yearupunited.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

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

    @Override
    public String toString() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        return String.format("Date: %s | Time: %s | Description: %s | Vendor: %s | Amount: $%.2f",
                date.format(dateFmt),
                time.format(timeFmt),
                description,
                vendor,
                amount);
    }
}
