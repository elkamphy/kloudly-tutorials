package com.kloudly;

// Abstract base class
abstract class Employee {
    protected String name;
    protected double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    // Abstract method
    public abstract double calculateSalary();

    // Concrete method
    public void printInfo() {
        System.out.println("Employee: " + name);
    }
}

// Concrete subclass: Full-Time
class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name, double baseSalary) {
        super(name, baseSalary);
    }

    @Override
    public double calculateSalary() {
        return baseSalary;
    }
}

// Concrete subclass: Part-Time
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, int hoursWorked, double hourlyRate) {
        super(name, 0); // baseSalary not used for part-time
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

// Example usage
public class AbstractClassesDemo {
    public static void main(String[] args) {
        Employee emp1 = new FullTimeEmployee("Alice", 3000);
        Employee emp2 = new PartTimeEmployee("Bob", 80, 20);

        emp1.printInfo();
        System.out.println("Salary: " + emp1.calculateSalary());

        emp2.printInfo();
        System.out.println("Salary: " + emp2.calculateSalary());
    }
}
