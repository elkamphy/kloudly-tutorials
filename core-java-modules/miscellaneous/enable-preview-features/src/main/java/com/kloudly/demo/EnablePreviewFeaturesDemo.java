package com.kloudly.demo;

/**
 * This class use JDK 17
 * In JDK 17, the pattern matching for switch is only available as a preview
 * Therefore, to compile and run this code, you need to enable preview features.
 */
public class EnablePreviewFeaturesDemo {

    public static void main(String[] args) {
        System.out.println(formatter("Hello from nkamphoa.com"));
    }
    static String formatter(Object o) {
        return switch (o) {
            case Integer i -> "Integer: " + i;
            case String s  -> "String: " + s;
            default        -> "Unknown type";
        };
    }
}

