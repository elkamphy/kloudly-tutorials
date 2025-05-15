package com.kloudly;

// === Single Responsibility Principle ===
class ReportGenerator {
    void generate() {
        System.out.println("Generating report...");
    }
}

class ReportSaver {
    void saveToFile() {
        System.out.println("Saving report to file...");
    }
}

// === Open/Closed Principle ===
interface Shape {
    double area();
}

class Circle implements Shape {
    double radius = 5;
    public double area() {
        return Math.PI * radius * radius;
    }
}

class Square implements Shape {
    double side = 4;
    public double area() {
        return side * side;
    }
}

// === Liskov Substitution Principle ===
class Bird {
    void fly() {
        System.out.println("Flying");
    }
}

class Sparrow extends Bird {
    // Inherits fly() method
}

// === Interface Segregation Principle ===
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

class AllInOneMachine implements Printer, Scanner {
    public void print() {
        System.out.println("Printing document");
    }

    public void scan() {
        System.out.println("Scanning document");
    }
}

// === Dependency Inversion Principle ===
interface Database {
    void connect();
}

class MySQLDatabase implements Database {
    public void connect() {
        System.out.println("Connecting to MySQL");
    }
}

class App {
    Database db;

    App(Database db) {
        this.db = db;
    }

    void start() {
        db.connect();
    }
}

