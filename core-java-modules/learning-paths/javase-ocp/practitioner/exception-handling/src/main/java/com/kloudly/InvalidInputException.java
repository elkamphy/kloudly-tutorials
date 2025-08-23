package com.kloudly;

// Declare an unchecked exception by extending RuntimeException
// Use this for programming errors or unrecoverable conditions.
public class InvalidInputException extends RuntimeException {
    private final String fieldName;
    private final String invalidValue;

    // Constructor for capturing invalid input details
    public InvalidInputException(String message, String fieldName, String invalidValue) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}