package com.kloudly.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Java11NewFeaturesDemo {

    // 1. Local-Variable Syntax for Lambda Parameters (JEP 323)
    public static void demoLambdaVarSyntax() {
        var list = List.of("Java", "11", "LTS");
        list.forEach((var item) -> System.out.println(item.toUpperCase()));
    }

    // 2. HTTP Client Standardized (JEP 321)
    public static void demoHttpClient() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://nkamphoa.com"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    // 3. String Methods Enhancements
    public static void demoStringMethods() {
        System.out.println("  Hello  ".strip());
        System.out.println("Java\n11\nRocks".lines().count());
        System.out.println("Hi! ".repeat(3));
        System.out.println("   ".isBlank());
    }

    // 4. Files.readString() and writeString() (JEP 330)
    public static void demoFileReadWrite() throws Exception {
        Path file = Path.of("example.txt");
        Files.writeString(file, "Java 11 is productive!");
        String content = Files.readString(file);
        System.out.println(content);
    }

    // 5. Running Java Files Without Compilation (JEP 330)
    // This feature applies at the CLI: java HelloWorld.java

    public static void main(String[] args) throws Exception {
        demoLambdaVarSyntax();
        demoHttpClient();
        demoStringMethods();
        demoFileReadWrite();
    }
}

