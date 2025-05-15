package com.kloudly;

// === InvoiceFormatter.java ===
interface InvoiceFormatter {
    void format(Invoice invoice);
}

// === PdfInvoiceFormatter.java ===
class PdfInvoiceFormatter implements InvoiceFormatter {
    public void format(Invoice invoice) {
        System.out.println("Formatting as PDF");
    }
}

// === HtmlInvoiceFormatter.java ===
class HtmlInvoiceFormatter implements InvoiceFormatter {
    public void format(Invoice invoice) {
        System.out.println("Formatting as HTML");
    }
}

// === InvoicePrinter.java ===
class InvoicePrinter {
    public void print(Invoice invoice, InvoiceFormatter formatter) {
        formatter.format(invoice);
    }
}

// === PaymentProcessor.java ===
interface PaymentProcessor {
    void process(Payment payment);
}

// === CreditCardProcessor.java ===
class CreditCardProcessor implements PaymentProcessor {
    public void process(Payment payment) {
        System.out.println("Processing credit card...");
    }
}

// === PaypalProcessor.java ===
class PaypalProcessor implements PaymentProcessor {
    public void process(Payment payment) {
        System.out.println("Processing PayPal...");
    }
}

// === StripeProcessor.java ===
class StripeProcessor implements PaymentProcessor {
    public void process(Payment payment) {
        System.out.println("Processing Stripe...");
    }
}

// === CheckoutService.java ===
class CheckoutService {
    private final PaymentProcessor processor;

    public CheckoutService(PaymentProcessor processor) {
        this.processor = processor;
    }

    public void pay(Payment payment) {
        processor.process(payment);
    }
}

// === Placeholder classes ===
class Invoice {
    // Placeholder for invoice data
}

class Payment {
    // Placeholder for payment data
}
