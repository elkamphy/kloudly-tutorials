package com.kloudly;

// === LSP Violation Example ===
/*class Bird {
    void fly() {
        System.out.println("Flying");
    }
}

class Sparrow extends Bird { }

class Ostrich extends Bird {
    // Ostrich can't fly, violates expectation
}*/

// === LSP Compliant Redesign ===
/*interface Bird { }

interface FlyingBird extends Bird {
    void fly();
}

class FlyingSparrow implements FlyingBird {
    public void fly() {
        System.out.println("Flying");
    }
}

class OstrichBird implements Bird {
    // No fly method
}*/

// === LSP Violation in Business Logic ===
abstract class DocumentExporter {
    abstract void export(String content);
}

class PdfExporter extends DocumentExporter {
    public void export(String content) {
        System.out.println("Exporting to PDF: " + content);
    }
}

class ReadOnlyExporter extends DocumentExporter {
    public void export(String content) {
        throw new UnsupportedOperationException("Read-only documents can't be exported.");
    }
}

// === LSP Compliant Business Logic ===
interface Exportable {
    void export(String content);
}

class PdfExportableDocument implements Exportable {
    public void export(String content) {
        System.out.println("Exporting to PDF: " + content);
    }
}

class ReadOnlyDocument {
    // Not exportable by design
}

