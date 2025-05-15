package com.kloudly;

// === DIP Violation ===
class EmailNotifier {
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

class AlertServiceBad {
    private final EmailNotifier notifier = new EmailNotifier();

    public void triggerAlert(String message) {
        notifier.send(message);
    }
}

// === DIP Compliant Refactor ===
interface Notifier {
    void send(String message);
}

class EmailNotifierImpl implements Notifier {
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

class SMSNotifier implements Notifier {
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class AlertService {
    private final Notifier notifier;

    public AlertService(Notifier notifier) {
        this.notifier = notifier;
    }

    public void triggerAlert(String message) {
        notifier.send(message);
    }
}
