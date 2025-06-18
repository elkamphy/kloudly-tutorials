package com.kloudly;

/**
 * Demonstrates method overriding and polymorphism in Java,
 * including both the Animal and PaymentProcessor examples.
 */
public class PolymorphismDemo {

    // ==============================
    // Animal hierarchy demonstration
    // ==============================

    // Base class
    static class Animal {
        public void speak() {
            System.out.println("Animal speaks");
        }
    }

    // Dog subclass
    static class Dog extends Animal {
        @Override
        public void speak() {
            System.out.println("Dog barks");
        }
    }

    // Cat subclass
    static class Cat extends Animal {
        @Override
        public void speak() {
            System.out.println("Cat meows");
        }
    }

    // Trainer class that demonstrates polymorphism
    static class AnimalTrainer {
        public void makeItSpeak(Animal animal) {
            animal.speak(); // Dynamic dispatch based on actual instance
        }
    }

    // ==============================
    // Payment system demonstration
    // ==============================

    // Base payment class
    static class Payment {
        public void pay() {
            System.out.println("Processing generic payment");
        }
    }

    // CreditCard subclass
    static class CreditCard extends Payment {
        @Override
        public void pay() {
            System.out.println("Processing credit card payment");
        }
    }

    // PayPal subclass
    static class PayPal extends Payment {
        @Override
        public void pay() {
            System.out.println("Processing PayPal payment");
        }
    }

    // Payment processor demonstrating polymorphism
    static class PaymentProcessor {
        public void process(Payment payment) {
            payment.pay(); // Dynamic method resolution
        }
    }

    public static void main(String[] args) {
        // Animal example
        AnimalTrainer trainer = new AnimalTrainer();
        Animal genericAnimal = new Animal();
        Animal dog = new Dog();
        Animal cat = new Cat();

        trainer.makeItSpeak(genericAnimal); // Output: Animal speaks
        trainer.makeItSpeak(dog);           // Output: Dog barks
        trainer.makeItSpeak(cat);           // Output: Cat meows

        // Payment example
        PaymentProcessor processor = new PaymentProcessor();
        Payment generic = new Payment();
        Payment credit = new CreditCard();
        Payment paypal = new PayPal();

        processor.process(generic);  // Output: Processing generic payment
        processor.process(credit);   // Output: Processing credit card payment
        processor.process(paypal);   // Output: Processing PayPal payment
    }
}
