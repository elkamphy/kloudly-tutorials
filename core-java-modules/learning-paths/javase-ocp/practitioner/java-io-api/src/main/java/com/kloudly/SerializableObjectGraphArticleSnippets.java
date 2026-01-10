package com.kloudly;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * This single file groups the main code snippets used in the article
 * "Serializable Object Graph Explained".
 *
 * Run it as a normal Java program. It will create a few *.ser files in the current directory.
 */
public class SerializableObjectGraphArticleSnippets {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1) Serialization / Deserialization ===");
        demoSerializationDeserialization();

        System.out.println("\n=== 2) Object Graph traversal (Order -> Customer -> Address) ===");
        demoObjectGraph();

        System.out.println("\n=== 3) Serializable marker + NotSerializableException ===");
        demoNotSerializableException();

        System.out.println("\n=== 4) transient + static fields ===");
        demoTransient();
        demoStatic();

        System.out.println("\n=== 5) Cycles + shared references ===");
        demoCyclesAndSharedReferences();

        System.out.println("\n=== 6) serialVersionUID (declared explicitly) ===");
        demoSerialVersionUid();

        System.out.println("\n=== 7) Custom hooks (rebuild transient derived field) ===");
        demoCustomHooks();

        System.out.println("\n=== 8) Common pitfall (non-serializable dependency should be transient) ===");
        demoCommonPitfall();
    }

    // ------------------------------------------------------------
    // 1) Serialization / Deserialization
    // ------------------------------------------------------------
    private static void demoSerializationDeserialization() throws Exception {
        Path file = Path.of("user.ser");

        // Serialization
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(file.toFile()))) {

            User user = new User("Alice", 30);
            out.writeObject(user);
            System.out.println("Serialized: " + user + " -> " + file.toAbsolutePath());
        }

        // Deserialization
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file.toFile()))) {

            User restoredUser = (User) in.readObject();
            System.out.println("Deserialized: " + restoredUser);
            System.out.println("Note: 'restoredUser' is a new instance with the same state.");
        }
    }

    // ------------------------------------------------------------
    // 2) Object Graph
    // ------------------------------------------------------------
    private static void demoObjectGraph() throws Exception {
        Path file = Path.of("order.ser");

        Address address = new Address("Paris");
        Customer customer = new Customer("Alice", address);
        Order order = new Order(customer);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            out.writeObject(order);
            System.out.println("Serialized Order (root). Java also serialized Customer and Address.");
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            Order restored = (Order) in.readObject();
            System.out.println("Deserialized Order: " + restored);
        }
    }

    // ------------------------------------------------------------
    // 3) Serializable marker + NotSerializableException
    // ------------------------------------------------------------
    private static void demoNotSerializableException() {
        Path file = Path.of("session.ser");

        // Socket is NOT Serializable, so this will fail.
        Session session = new Session(new User("Alice",30), new Socket());

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            out.writeObject(session);
            System.out.println("Unexpected: session serialized (this should not happen).");
        } catch (NotSerializableException e) {
            System.out.println("Expected failure: " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("I/O error: " + e);
        } finally {
            // cleanup partial file if created
            try { Files.deleteIfExists(file); } catch (IOException ignored) {}
        }
    }

    // ------------------------------------------------------------
    // 4) 1)transient
    // ------------------------------------------------------------
    private static void demoTransient() throws Exception {
        Path file = Path.of("account.ser");

        Account account = new Account("acc-1", "TOP-SECRET");
        Config.ENV = "prod";

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            out.writeObject(account);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            Account restored = (Account) in.readObject();
            System.out.println("Restored account id         : " + restored.id);
            System.out.println("Restored transient secret   : " + restored.secret); // null (default)
        }
    }

    // ------------------------------------------------------------
    // 4) 2)static
    // ------------------------------------------------------------
    private static void demoStatic() throws Exception {
        Path file = Path.of("config.ser");

        // Initial state before serialization
        Config.ENV = "prod";
        Config config = new Config();
        config.value = "v1";

        // Serialize the Config instance
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            out.writeObject(config);
        }

        // Change static field AFTER serialization
        Config.ENV = "dev";

        // Also change instance field to prove it DOES come from the stream
        config.value = "CHANGED-IN-MEMORY";

        // Deserialize
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file.toFile()))) {

            Config restored = (Config) in.readObject();

            System.out.println("Restored value (instance) : " + restored.value); // v1
            System.out.println("Current ENV (static)      : " + Config.ENV);     // dev
        }
    }

        // ------------------------------------------------------------
        // 5) Cycles + shared references
        // ------------------------------------------------------------
        private static void demoCyclesAndSharedReferences() throws Exception {
            // --- Cycle example ---
            Path cycleFile = Path.of("cycle.ser");
            Node a = new Node("A");
            Node b = new Node("B");
            a.next = b;
            b.next = a; // cycle

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cycleFile.toFile()))) {
                out.writeObject(a);
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(cycleFile.toFile()))) {
                Node restoredA = (Node) in.readObject();
                System.out.println("Cycle preserved? restoredA.next.next == restoredA -> " + (restoredA.next.next == restoredA));
            }

            // --- Shared reference example ---
            Path sharedFile = Path.of("shared.ser");
            Address shared = new Address("Paris");
            Customer c1 = new Customer("Alice", shared);
            Customer c2 = new Customer("Bob", shared);
            SharedRefRoot root = new SharedRefRoot(c1, c2);

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(sharedFile.toFile()))) {
                out.writeObject(root);
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(sharedFile.toFile()))) {
                SharedRefRoot restored = (SharedRefRoot) in.readObject();
                boolean sameAddressInstance = restored.c1.address == restored.c2.address;
                System.out.println("Shared ref preserved? restored.c1.address == restored.c2.address -> " + sameAddressInstance);
            }
        }

        // ------------------------------------------------------------
        // 6) serialVersionUID
        // ------------------------------------------------------------
        private static void demoSerialVersionUid() throws Exception {
            Path file = Path.of("person.ser");

            Person person = new Person("Chloé");
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
                out.writeObject(person);
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toFile()))) {
                Person restored = (Person) in.readObject();
                System.out.println("Restored person: " + restored);
            }

            System.out.println("Person.serialVersionUID is explicitly declared to avoid fragile, computed UIDs.");
        }

        // ------------------------------------------------------------
        // 7) Custom hooks
        // ------------------------------------------------------------
        private static void demoCustomHooks() throws Exception {
            Path file = Path.of("user-display.ser");

            UserWithDisplayName user = new UserWithDisplayName("Noël", "Kamphoa");

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
                out.writeObject(user);
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toFile()))) {
                UserWithDisplayName restored = (UserWithDisplayName) in.readObject();
                System.out.println("Restored user: " + restored);
                System.out.println("Derived transient field rebuilt? displayName != null -> " + (restored.getDisplayName() != null));
            }
        }

        // ------------------------------------------------------------
        // 8) Pitfall example
        // ------------------------------------------------------------
        private static void demoCommonPitfall() throws Exception {
            Path file = Path.of("service.ser");

            Service  service = new Service("PayrollService");
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
                out.writeObject(service);
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toFile()))) {
                Service restored = (Service) in.readObject();
                System.out.println("Restored service: " + restored);
                System.out.println("Logger after deserialization: " + restored.getLogger());
            }
        }
    }

// -------------------------------------------------------------------------
// Classes used by the snippets
// -------------------------------------------------------------------------

// Used in section 1
class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int age;

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override public String toString() { return "UserSimple{name='" + name + "', age=" + age + "}"; }
}

// Used in section 2 (object graph)
class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    Customer customer;

    Order(Customer customer) {
        this.customer = customer;
    }

    @Override public String toString() { return "Order{customer=" + customer + "}"; }
}

class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    Address address;

    Customer(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override public String toString() { return "Customer{name='" + name + "', address=" + address + "}"; }
}

class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    String city;

    Address(String city) {
        this.city = city;
    }

    @Override public String toString() { return "Address{city='" + city + "'}"; }
}

// Used in section 3 (NotSerializableException)
class Session implements Serializable {
    private static final long serialVersionUID = 1L;
    User user;
    Socket socket; // NOT serializable

    Session(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
    }
}

class Socket{
    String address;
}

// Used in section 4 (transient + static)
class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    String id;
    transient String secret; // ignored during serialization

    Account(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }
}

class Config implements Serializable {
    private static final long serialVersionUID = 1L;
    static String ENV = "prod"; // never serialized
    String value;
}

// Used in section 5 (cycles + shared references)
class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    Node next;

    Node(String name) { this.name = name; }
}

class SharedRefRoot implements Serializable {
    private static final long serialVersionUID = 1L;
    Customer c1;
    Customer c2;

    SharedRefRoot(Customer c1, Customer c2) {
        this.c1 = c1;
        this.c2 = c2;
    }
}

// Used in section 6 (serialVersionUID)
class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;

    Person(String name) { this.name = name; }

    @Override public String toString() { return "Person{name='" + name + "'}"; }
}

// Used in section 7 (custom hooks)
class UserWithDisplayName implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;

    // derived: should not be serialized, but must be rebuilt
    private transient String displayName;

    UserWithDisplayName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = firstName + " " + lastName;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // serialize non-transient fields
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // restore non-transient fields
        this.displayName = firstName + " " + lastName; // rebuild derived state
    }

    String getDisplayName() { return displayName; }

    @Override public String toString() { return "UserWithDisplayName{displayName='" + displayName + "'}"; }
}

// Used in section 8 (pitfall)
class Service  implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;

    // Logger is NOT Serializable -> keep it transient (typical fix)
    transient Logger logger = Logger.getLogger(Service.class.getName());

    Service(String name) { this.name = name; }

    Logger getLogger() { return logger; }

    // Optionally rebuild transient dependencies after deserialization
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.logger = Logger.getLogger(Service.class.getName());
    }

    @Override public String toString() { return "Service{name='" + name + "'}"; }
}
