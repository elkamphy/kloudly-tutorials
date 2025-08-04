
package com.kloudly;

/**
 * Demo class for properly overriding toString() in Java.
 */
public class ToStringDemo {
    private String name;
    private int age;

    public ToStringDemo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Properly overrides toString() to provide a readable representation.
     * Outputs all significant, non-sensitive fields in a clear format.
     */
    @Override
    public String toString() {
        return "ToStringDemo{name='" + name + "', age=" + age + "}";
    }
}
