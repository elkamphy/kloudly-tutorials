package com.kloudly;

import java.io.*;

/**
 * Demonstrates Checked and Unchecked Exceptions in Java.
 */
public class CheckedUncheckedExceptionsDemo {

    // Method that demonstrates a Checked Exception
    public static void readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        System.out.println(reader.readLine());
        reader.close();
    }

    // Method that demonstrates an Unchecked Exception
    public static void causeUncheckedException() {
        String text = null;
        // This will throw a NullPointerException at runtime
        System.out.println(text.length());
    }

    public static void main(String[] args) {
        try {
            // Triggering a Checked Exception
            readFile("nonexistent.txt");
        } catch (IOException e) {
            System.out.println("Caught Checked Exception: " + e.getMessage());
        }

        
        // Triggering an Unchecked Exception: No need to try-catch
        causeUncheckedException();
    }
}
