package com.kloudly.classes;

public class EnumExamples {
    public static void main(String[] args) {
        useColorEnum();
        useDayEnum();
        useLanguageEnum();
    }
    // Enum for basic color example
    public enum Color {
        RED, GREEN, BLUE;
    }

    // Example: Using Color enum
    public static void useColorEnum() {
        Color c = Color.RED;

        // Comparison
        if (c == Color.RED) {
            System.out.println("Color is red");
        }

        // Switch expression
        switch (c) {
            case RED -> System.out.println("Stop!");
            case GREEN -> System.out.println("Go!");
            case BLUE -> System.out.println("Cool down");
        }

        // Iteration
        for (Color color : Color.values()) {
            System.out.println(color);
        }
    }

    // Enum with constructor and member
    public enum Day {
        MONDAY("Weekday"),
        TUESDAY("Weekday"),
        WEDNESDAY("Weekday"),
        THURSDAY("Weekday"),
        FRIDAY("Weekday"),
        SATURDAY("Weekend"),
        SUNDAY("Weekend");

        private final String type;

        Day(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public static void useDayEnum() {
        System.out.println(Day.SATURDAY.getType()); // Weekend

        Day d = Day.valueOf("MONDAY");
        System.out.println(d); // MONDAY

        for (Day day : Day.values()) {
            System.out.println(day);
        }

        // Inherited methods
        System.out.println(Day.MONDAY.name());     // MONDAY
        System.out.println(Day.SUNDAY.ordinal());  // 6

        // Switch expression
        String action = switch (Day.SUNDAY) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Work";
            case SATURDAY, SUNDAY -> "Relax";
        };
        System.out.println(action); // Relax
    }

    // Interface for implementation by enum
    interface Greetable {
        void greet();
    }

    public enum Language implements Greetable {
        ENGLISH {
            public void greet() {
                System.out.println("Hello");
            }
        },
        FRENCH {
            public void greet() {
                System.out.println("Bonjour");
            }
        };
    }

    public static void useLanguageEnum() {
        Language.ENGLISH.greet(); // Hello
        Language.FRENCH.greet();  // Bonjour
    }

}

class Color {
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;
}