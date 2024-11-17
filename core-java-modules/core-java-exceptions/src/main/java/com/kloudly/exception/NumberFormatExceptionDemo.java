package com.kloudly.exception;

public class NumberFormatExceptionDemo {
    public static void main(String[] args) {
        //Uncomment any of the method below to reproduce the NumberFormatException
        invalidDecimalSeparator();
        //emptyString();
        //nullInput();
    }

    public static void invalidDecimalSeparator(){
        String invalidNumber = "1,5";
        double doubleValue = Double.parseDouble(invalidNumber);//NumberFormatException
        System.out.println("Parsed double value: " + doubleValue);
    }

    public static void emptyString(){
        String emptyString = ""; // Empty string
        double doubleValue = Double.parseDouble(emptyString); // NumberFormatException
        System.out.println("Parsed double value: " + doubleValue);
    }

    public static void nullInput(){
        String nullString = "null"; // "null" string read from a file
        double doubleValue = Double.parseDouble(nullString);// NumberFormatException
        System.out.println("Parsed double value: " + doubleValue);
    }
}
