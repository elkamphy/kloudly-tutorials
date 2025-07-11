package com.kloudly;

// Demonstrates Java parameter passing for primitives and references
public class PassingParametersDemo {

    public static void main(String[] args) {
        PassingParametersDemo demo = new PassingParametersDemo();

        int primitive = 5;
        demo.modifyPrimitive(primitive);
        System.out.println("After method call, primitive: " + primitive);

        Person person = new Person("Original");
        demo.modifyObject(person);
        System.out.println("After method call, person's name: " + person.getName());

        demo.reassignReference(person);
        System.out.println("After reassignment, person's name: " + person.getName());

        int[] numbers = {1, 2, 3};
        demo.changeArray(numbers);
        System.out.println("After array modification, numbers[0]: " + numbers[0]);
    }

    // Changes local copy of primitive; original is unaffected
    public void modifyPrimitive(int number) {
        number += 10;
        System.out.println("Inside modifyPrimitive: " + number);
    }

    // Modifies object state via reference; original is affected
    public void modifyObject(Person person) {
        person.setName("Changed Name");
    }

    // Reassigns parameter to new object; original is unaffected
    public void reassignReference(Person person) {
        person = new Person("New Name");
    }

    // Modifies contents of the passed array
    public void changeArray(int[] arr) {
        arr[0] = 99;
    }

    // Simple Person class for demonstration
    static class Person {
        private String name;
        public Person(String name) { this.name = name; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
