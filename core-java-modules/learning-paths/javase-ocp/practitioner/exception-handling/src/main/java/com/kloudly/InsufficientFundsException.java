package com.kloudly;

// Declare a checked exception by extending Exception
// Use this for recoverable errors where the caller MUST be aware and handle it.
public class InsufficientFundsException extends Exception {
    private final double currentBalance;
    private final double amountRequired;

    // Constructor that accepts a message and relevant domain data
    public InsufficientFundsException(String message, double currentBalance, double amountRequired) {
        super(message);
        this.currentBalance = currentBalance;
        this.amountRequired = amountRequired;
    }

    // Getters to allow the catcher to access the contextual information
    public double getCurrentBalance() {
        return currentBalance;
    }

    public double getAmountRequired() {
        return amountRequired;
    }
}