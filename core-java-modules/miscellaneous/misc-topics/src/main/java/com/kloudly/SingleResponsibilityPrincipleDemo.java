package com.kloudly;

// === RegistrationValidator.java ===
class RegistrationValidator {
    public void validate(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}

// === PasswordEncoder.java ===
/*class PasswordEncoder {
    public String encode(String password) {
        return BCrypt.hash(password); // Simulated call
    }
}*/

// === WelcomeMailer.java ===
/*class WelcomeMailer {
    private final EmailClient client;

    public WelcomeMailer(EmailClient client) {
        this.client = client;
    }

    public void send(User user) {
        client.send(user.getEmail(), "Welcome", "Thanks for joining!");
    }
}*/

// === UserService.java ===
/*class UserService {

    private final UserRepository userRepository;
    private final RegistrationValidator validator;
    private final PasswordEncoder encoder;
    private final WelcomeMailer mailer;

    public UserService(UserRepository userRepository,
                       RegistrationValidator validator,
                       PasswordEncoder encoder,
                       WelcomeMailer mailer) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.encoder = encoder;
        this.mailer = mailer;
    }

    public void register(String email, String password) {
        validator.validate(email);
        String hashed = encoder.encode(password);
        User user = new User(email, hashed);
        userRepository.save(user);
        mailer.send(user);
    }
}*/

// === Example SRP Violation in Microservice ===
class OrderService {
    void createOrder() {
        // Business logic
    }

    void sendConfirmationEmail() {
        // Notification logic
    }

    void updateInventory() {
        // Stock logic
    }
}

// === SRP Compliant Delegation ===
class OrderProcessor {
    void createOrder() {
        // Business logic
    }
}

class InventoryUpdater {
    void updateStock() {
        // Stock logic
    }
}

class NotificationService {
    void sendEmail() {
        // Email logic
    }
}

