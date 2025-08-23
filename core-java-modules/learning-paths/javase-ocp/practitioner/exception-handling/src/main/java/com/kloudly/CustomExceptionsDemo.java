package com.kloudly;

/**
 * A demo class to illustrate the usage of custom checked and unchecked exceptions.
 * This simulates a simple bank account operation.
 */
public class CustomExceptionsDemo {

    private double balance;

    public CustomExceptionsDemo(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * Demonstrates throwing a custom CHECKED exception.
     * The caller MUST handle this exception.
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            // Throw the checked exception with rich context
            throw new InsufficientFundsException(
                "Withdrawal failed. Amount exceeds available balance.",
                balance,
                amount
            );
        }
        balance -= amount;
        System.out.println("Withdrawal of " + amount + " successful. New balance: " + balance);
    }

    /**
     * Demonstrates throwing a custom UNCHECKED exception.
     * The caller is not forced by the compiler to handle it.
     */
    public void validateInput(String accountId) {
        if (accountId == null || accountId.isBlank()) {
            // Throw the unchecked exception with details on the invalid field
            throw new InvalidInputException(
                "Account identifier cannot be null or empty.",
                "accountId",
                accountId
            );
        }
        System.out.println("Input validation passed for account ID: " + accountId);
    }

    public static void main(String[] args) {
        CustomExceptionsDemo account = new CustomExceptionsDemo(500.0);

        // Example 1: Handling a custom checked exception
        try {
            account.withdraw(600.0);
        } catch (InsufficientFundsException e) {
            // Handle the exception using its specific data
            System.err.println(e.getMessage());
            System.err.printf("Current Balance: %.2f, Amount Required: %.2f%n",
                    e.getCurrentBalance(), e.getAmountRequired());
            // Potentially trigger an alert or a user-friendly message here
        }

        // Example 2: Handling a custom unchecked exception
        try {
            account.validateInput(""); // This will cause an exception
        } catch (InvalidInputException e) {
            System.err.println("Validation Error: " + e.getMessage());
            System.err.println("Field: " + e.getFieldName() + ", Invalid Value: '" + e.getInvalidValue() + "'");
            // Prompt the user to correct the specific field
        }

        // Example 3: This will also throw the unchecked exception, but is not caught.
        // This demonstrates the "optional" nature of handling unchecked exceptions.
        account.validateInput(null);
    }
}