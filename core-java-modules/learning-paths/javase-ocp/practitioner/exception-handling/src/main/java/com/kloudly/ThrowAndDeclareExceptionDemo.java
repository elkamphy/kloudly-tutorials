package com.kloudly;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/**
 * 
 * This class demonstrates when to throw vs. declare exceptions, exception translation,
 * try-with-resources with suppressed exceptions, cause chaining, and multi-catch.
 */
public class ThrowAndDeclareExceptionDemo {

    /**
     * Example 1: Unchecked exception for programming errors (invalid arguments).
     *
     * We Throw Exception (unchecked) to enforce preconditions.
     *
     * @param text string representation of a TCP port
     * @return parsed port
     * @throws NumberFormatException if text cannot be parsed to int
     * @throws IllegalArgumentException if null/blank or out of range
     */
    public static int parsePort(String text) {
        if (text == null || text.isBlank()) {
            // Throw Exception (unchecked) to indicate a precondition violation
            throw new IllegalArgumentException("port must be a non-empty string");
        }
        int port = Integer.parseInt(text); // may throw NumberFormatException
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("port out of range: " + port);
        }
        return port;
    }

    /**
     * Example 2: Declaring a checked exception for I/O operations.
     * We use try-with-resources to ensure resource safety.
     *
     * @param path file path to read
     * @return first line of the file, or null if empty
     * @throws IOException if the file cannot be read
     */
    public static String readFirstLine(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            return br.readLine();
        }
    }

    /**
     * Domain model used for demonstration.
     */
    public static final class Invoice {
        private final String id;

        private Invoice(String id) {
            this.id = id;
        }

        public static Invoice parse(String line) {
            // Throw Exception (unchecked) for clearly invalid format
            if (line == null || !line.startsWith("INV-")) {
                throw new IllegalArgumentException("Invalid invoice format: " + line);
            }
            return new Invoice(line.trim());
        }

        @Override
        public String toString() {
            return "Invoice{" + "id='" + id + '\'' + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Invoice)) return false;
            Invoice invoice = (Invoice) o;
            return Objects.equals(id, invoice.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    /**
     * Custom checked exception for domain-level failures.
     * Using a checked type communicates recoverability to callers.
     */
    public static class InvoiceReadException extends Exception {
        public InvoiceReadException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvoiceReadException(String message) {
            super(message);
        }
    }

    /**
     * Example 3: Exception translation — map low-level exceptions to a domain-specific type.
     *
     * @param path path to invoice file
     * @return parsed invoice
     * @throws InvoiceReadException if reading or parsing fails
     */
    public static Invoice loadInvoice(Path path) throws InvoiceReadException {
        try {
            String line = readFirstLine(path);      // may throw IOException
            return Invoice.parse(line);             // may throw IllegalArgumentException
        } catch (IOException | IllegalArgumentException e) {
            // Translate into domain-level checked exception; preserve root cause
            throw new InvoiceReadException("Invoice load failed for: " + path, e);
        }
    }

    /**
     * Example 4: Rethrow to let higher layers decide.
     *
     * @param path invoice file path
     * @throws InvoiceReadException propagated to caller
     */
    public static void process(Path path) throws InvoiceReadException {
        Invoice invoice = loadInvoice(path); // declares throws InvoiceReadException
        // continue with business logic
        System.out.println("Processing " + invoice);
    }

    /**
     * Example 5: Multi-catch when the recovery step is identical.
     *
     * @param text candidate URI string
     * @return Optional of URI or empty on failure
     */
    public static Optional<URI> tryBuildUri(String text) {
        try {
            return Optional.of(new URI(text));
        } catch (URISyntaxException | IllegalArgumentException ex) {
            // Same recovery for both: return empty
            return Optional.empty();
        }
    }

    /**
     * Example 6: Preserve root cause by chaining a cause when rethrowing.
     *
     * @param path file used to initialize state
     * @throws IllegalStateException wrapping the underlying IOException
     */
    public static void withCause(Path path) {
        try {
            readFirstLine(path); // may Throw Exception (checked)
        } catch (IOException e) {
            throw new IllegalStateException("Cannot initialize from: " + path, e);
        }
    }

    /**
     * Helper resource to demonstrate suppressed exceptions when closing fails.
     */
    static class FragileResource implements AutoCloseable {
        private final boolean failOnClose;

        FragileResource(boolean failOnClose) {
            this.failOnClose = failOnClose;
        }

        @Override
        public void close() throws IOException {
            if (failOnClose) {
                throw new IOException("close failure");
            }
        }
    }

    /**
     * Example 7: Demonstrate suppressed exceptions captured by try-with-resources.
     */
    public static void demonstrateSuppressed() {
        try (FragileResource r = new FragileResource(true)) {
            throw new RuntimeException("Primary failure");
        } catch (RuntimeException | IOException ex) {
            System.err.println("Primary: " + ex);
            for (Throwable sup : ex.getSuppressed()) {
                System.err.println("Suppressed: " + sup);
            }
        }
    }

    /**
     * Basic demo runner.
     */
    public static void main(String[] args) {
        // 1) Unchecked Throw Exception example
        try {
            System.out.println("Port: " + parsePort("8080"));
            // System.out.println("Port: " + parsePort("bad")); // would Throw Exception
        } catch (RuntimeException ex) {
            System.err.println("Invalid port: " + ex.getMessage());
        }

        // 2) Declared checked exception usage and translation
        Path path = Path.of("missing-invoice.txt");
        try {
            process(path); // may throw InvoiceReadException
        } catch (InvoiceReadException e) {
            System.err.println("Could not process invoice: " + e.getMessage());
        }

        // 3) Multi-catch example
        Optional<URI> uri = tryBuildUri("https://nkamphoa.com/hashmap-in-java");
        System.out.println("URI present? " + uri.isPresent());

        // 4) Cause chaining example
        try {
            withCause(Path.of("bootstrap.txt"));
        } catch (IllegalStateException ise) {
            System.err.println("Bootstrap failed: " + ise.getMessage());
        }

        // 5) Suppressed exceptions demonstration
        demonstrateSuppressed();
    }
}