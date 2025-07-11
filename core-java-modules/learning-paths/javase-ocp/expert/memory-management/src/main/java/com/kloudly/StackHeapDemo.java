
package com.kloudly;

// Demo class to show how stack and heap memory work in Java
public class StackHeapDemo {

    public static void main(String[] args) {
        StackHeapDemo demo = new StackHeapDemo(); // 'demo' is on stack, object is on heap
        demo.performOperation();
    }

    // Method to demonstrate stack and heap usage
    public void performOperation() {
        int localVar = 42; // 'localVar' is stored on the stack
        MyObject obj = new MyObject("Java Memory"); // 'obj' reference is on stack, object on heap
        obj.displayMessage(localVar);
    }

    // Inner class to represent an object allocated on the heap
    static class MyObject {
        private String message; // Field stored in the heap with the object

        public MyObject(String msg) {
            this.message = msg;
        }

        public void displayMessage(int value) {
            System.out.println("Message: " + message + ", Value: " + value);
        }
    }
}
