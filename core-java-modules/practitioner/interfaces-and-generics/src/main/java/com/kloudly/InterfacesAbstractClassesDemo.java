package com.kloudly;

// === Interface Example ===
interface Animal {
    void eat();
    void sleep();
}

class Dog implements Animal {
    public void eat() {
        System.out.println("Dog eats.");
    }

    public void sleep() {
        System.out.println("Dog sleeps.");
    }
}

// === Abstract Class Example ===
abstract class Vehicle {
    String brand;

    abstract void start();

    void fuelUp() {
        System.out.println("Filling fuel");
    }
}

class Car extends Vehicle {
    void start() {
        System.out.println("Car is starting...");
    }
}

// === Real-World: Notifier Example ===
interface Notifier {
    void send(String message);
}

class EmailNotifier implements Notifier {
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

abstract class BaseNotifier {
    void log(String message) {
        System.out.println("LOG: " + message);
    }

    abstract void send(String message);
}

class SMSNotifier extends BaseNotifier {
    public void send(String message) {
        log(message);
        System.out.println("Sending SMS: " + message);
    }
}

