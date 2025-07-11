package com.kloudly;

/**
 * Demonstrates the Factory Method Pattern using a simple notification system.
 */
public class FactoryMethodPatternDemo {

    // Product interface
    interface Notification {
        void notifyUser();
    }

    // ConcreteProduct: EmailNotification
    static class EmailNotification implements Notification {
        public void notifyUser() {
            System.out.println("Sending an Email notification");
        }
    }

    // ConcreteProduct: SMSNotification
    static class SMSNotification implements Notification {
        public void notifyUser() {
            System.out.println("Sending an SMS notification");
        }
    }

    // Creator: Abstract factory
    abstract static class NotificationFactory {
        public abstract Notification createNotification();
    }

    // ConcreteCreator: Email factory
    static class EmailFactory extends NotificationFactory {
        public Notification createNotification() {
            return new EmailNotification();
        }
    }

    // ConcreteCreator: SMS factory
    static class SMSFactory extends NotificationFactory {
        public Notification createNotification() {
            return new SMSNotification();
        }
    }

    // Client code demonstrating the pattern
    public static void main(String[] args) {
        NotificationFactory factory;

        // Use Email factory
        factory = new EmailFactory();
        Notification email = factory.createNotification();
        email.notifyUser(); // Output: Sending an Email notification

        // Use SMS factory
        factory = new SMSFactory();
        Notification sms = factory.createNotification();
        sms.notifyUser(); // Output: Sending an SMS notification
    }
}
