package com.kloudly.demo;

import java.lang.ScopedValue;

public class ScopedValueExample {
    private static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();

    public static void main(String[] args) {
        ScopedValue.where(CURRENT_USER, "John").run(ScopedValueExample::handleRequest);
    }

    private static void handleRequest() {
        System.out.println("Current user: " + CURRENT_USER.get());
    }
}