package com.kloudly.classes;

import java.util.Objects;

/**
 * Demo code for the article:
 * "Constructors in Java: Definition, Types, and Best Practices"
 *
 * This file contains a small snippet for each topic explained in the article.
 */
public class ConstructorDemo {

    public static void main(String[] args) {
        demoWhatIsAConstructor();
        demoUsingTypeWithoutCallingConstructor();
        demoDefaultConstructorProvidedByCompiler();
        demoDefaultConstructorNotGeneratedWhenAnyConstructorExists();
        demoParameterizedConstructor();
        demoParameterizedConstructorWithValidation();
        demoConstructorOverloadingAndThisCall();
        demoSuperCallInInheritance();
        demoPrivateConstructorUtilityClass();
        demoPrivateConstructorSingleton();
    }

    // -------------------------------------------------------------------------
    // 1) What is a constructor?
    // -------------------------------------------------------------------------

    static void demoWhatIsAConstructor() {
        System.out.println("\n=== 1) What is a constructor? ===");
        User u = new User("John");
        System.out.println("User created with name = " + u.name);
    }

    static class User {
        final String name;

        // Constructor: same name as class, no return type, initializes state.
        User(String name) {
            this.name = name;
        }
    }

    // -------------------------------------------------------------------------
    // 1) What happens if someone tries to use a type without calling constructor?
    // -------------------------------------------------------------------------

    static void demoUsingTypeWithoutCallingConstructor() {
        System.out.println("\n=== 1b) Using a reference without constructing an object ===");

        // Only declares a reference. No object is created.
        User ref = null;

        // Using it triggers NullPointerException at runtime.
        try {
            System.out.println(ref.name); // NPE
        } catch (NullPointerException e) {
            System.out.println("As expected: ref is null, so accessing ref.name throws NullPointerException.");
        }

        // Correct: construct the object (calls the constructor).
        ref = new User("Initialized");
        System.out.println("After construction: ref.name = " + ref.name);
    }

    // -------------------------------------------------------------------------
    // 2) Default constructor
    // -------------------------------------------------------------------------

    static void demoDefaultConstructorProvidedByCompiler() {
        System.out.println("\n=== 2) Default constructor (generated when none is declared) ===");

        // Product has no constructors declared; the compiler provides Product().
        Product p = new Product();
        System.out.println("Product created via default constructor: " + p);
        System.out.println("Default field values -> name=" + p.name + ", price=" + p.price);
    }

    static class Product {
        // No constructor declared => compiler generates: Product() { super(); }
        String name;   // null by default
        double price;  // 0.0 by default
    }

    static void demoDefaultConstructorNotGeneratedWhenAnyConstructorExists() {
        System.out.println("\n=== 2b) Default constructor is NOT generated if you declare any constructor ===");

        ProductWithCtor p = new ProductWithCtor("Keyboard");
        System.out.println("ProductWithCtor created with name = " + p.name);

        // Uncommenting the next line would not compile:
        // ProductWithCtor x = new ProductWithCtor();
        System.out.println("Note: new ProductWithCtor() would not compile because no no-arg constructor exists.");
    }

    static class ProductWithCtor {
        final String name;

        ProductWithCtor(String name) {
            this.name = name;
        }
    }

    // -------------------------------------------------------------------------
    // 3) Parameterized constructor
    // -------------------------------------------------------------------------

    static void demoParameterizedConstructor() {
        System.out.println("\n=== 3) Parameterized constructor ===");

        Item laptop = new Item("Laptop", 1200.0);
        System.out.println("Item created: " + laptop.name + ", price=" + laptop.price);
    }

    static class Item {
        final String name;
        final double price;

        Item(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }

    static void demoParameterizedConstructorWithValidation() {
        System.out.println("\n=== 3b) Parameterized constructor with validation ===");

        SafeItem ok = new SafeItem("Mouse", 25.0);
        System.out.println("SafeItem created: " + ok.name + ", price=" + ok.price);

        try {
            new SafeItem("Broken", -1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }
    }

    static class SafeItem {
        final String name;
        final double price;

        SafeItem(String name, double price) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            if (name.isBlank()) throw new IllegalArgumentException("name must not be blank");
            if (price < 0) throw new IllegalArgumentException("price cannot be negative");
            this.price = price;
        }
    }

    // -------------------------------------------------------------------------
    // 4) Constructor overloading + 5) this()
    // -------------------------------------------------------------------------

    static void demoConstructorOverloadingAndThisCall() {
        System.out.println("\n=== 4/5) Constructor overloading + this() chaining ===");

        Account a1 = new Account("Alice");          // uses Account(String) -> this(String, double)
        Account a2 = new Account("Bob", 1000.0);    // uses Account(String, double)

        System.out.println(a1.owner + " balance=" + a1.balance);
        System.out.println(a2.owner + " balance=" + a2.balance);
    }

    static class Account {
        final String owner;
        final double balance;

        // Overload #1: provides a default value and delegates to the main constructor.
        Account(String owner) {
            this(owner, 0.0); // this() must be first statement
        }

        // Overload #2: main constructor
        Account(String owner, double balance) {
            this.owner = Objects.requireNonNull(owner, "owner must not be null");
            this.balance = balance;
        }
    }

    // -------------------------------------------------------------------------
    // 6) super() in inheritance
    // -------------------------------------------------------------------------

    static void demoSuperCallInInheritance() {
        System.out.println("\n=== 6) super() calls the parent constructor ===");
        Employee e = new Employee("Charlie", "Engineering");
        System.out.println("Employee ready: " + e.name + " / " + e.department);
    }

    static class Person {
        final String name;

        Person(String name) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            System.out.println("Person constructor ran for: " + name);
        }
    }

    static class Employee extends Person {
        final String department;

        Employee(String name, String department) {
            super(name); // must be first statement
            this.department = Objects.requireNonNull(department, "department must not be null");
            System.out.println("Employee constructor ran for department: " + department);
        }
    }

    // -------------------------------------------------------------------------
    // 7) Private constructors
    // -------------------------------------------------------------------------

    static void demoPrivateConstructorUtilityClass() {
        System.out.println("\n=== 7) Private constructor: utility class ===");

        int sum = MathUtils.add(2, 3);
        System.out.println("MathUtils.add(2,3) = " + sum);

        // Uncommenting the next line would not compile:
        // MathUtils u = new MathUtils();
        System.out.println("Note: MathUtils cannot be instantiated because its constructor is private.");
    }

    static final class MathUtils {
        private MathUtils() {
            // Prevent instantiation
        }

        static int add(int a, int b) {
            return a + b;
        }
    }

    static void demoPrivateConstructorSingleton() {
        System.out.println("\n=== 7b) Private constructor: singleton ===");

        AppConfig c1 = AppConfig.getInstance();
        AppConfig c2 = AppConfig.getInstance();

        System.out.println("Same instance? " + (c1 == c2));
        System.out.println("Config value: " + c1.getEnvironment());
    }

    static final class AppConfig {
        private static final AppConfig INSTANCE = new AppConfig();

        private final String environment = "prod";

        private AppConfig() {
            // Initialize config once
        }

        public static AppConfig getInstance() {
            return INSTANCE;
        }

        public String getEnvironment() {
            return environment;
        }
    }
}
