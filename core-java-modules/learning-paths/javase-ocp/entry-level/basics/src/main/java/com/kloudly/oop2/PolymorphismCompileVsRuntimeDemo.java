package com.kloudly.oop2;

// Compile-time polymorphism: Method overloading
public class PolymorphismCompileVsRuntimeDemo {
    // Overloaded method for integers
    public int add(int a, int b) {
        return a + b;
    }

    // Overloaded method for doubles
    public double add(double a, double b) {
        return a + b;
    }

    public static void main(String[] args) {
        // Demonstrate compile-time polymorphism
        PolymorphismCompileVsRuntimeDemo demo = new PolymorphismCompileVsRuntimeDemo();
        System.out.println("Add integers: " + demo.add(2, 3));
        System.out.println("Add doubles: " + demo.add(2.5, 3.7));

        // Demonstrate runtime polymorphism
        Animal myAnimal = new Dog();
        myAnimal.speak(); // Will output "The dog barks."
    }
}

// Runtime polymorphism: Method overriding
class Animal {
    public void speak() {
        System.out.println("The animal makes a sound.");
    }
}

class Dog extends Animal {
    @Override
    public void speak() {
        System.out.println("The dog barks.");
    }
}
