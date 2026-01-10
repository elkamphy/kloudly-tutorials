package com.kloudly;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class JavaIOIntroductionDemo {
    public static void main(String[] args) throws IOException {
        classicIOExample();
        nioExample();
        nio2Example();
        tryWithRessourceExample();
        largeFileProcessingExample();
        sequentialStatefulParsing();
        binaryFileReading();


    }

    private static void binaryFileReading() throws IOException {
        try (InputStream in = Files.newInputStream(Path.of("image.png"))) {
            byte[] bytes = in.readAllBytes();
        }
    }

    private static void sequentialStatefulParsing() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("data.csv"))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                parse(line);
            }
        }
    }

    private static void parse(String line) {
        if (line.contains("ERROR")) {
            System.err.println(line);
        }
    }


    private static void largeFileProcessingExample() {
        // Process large files line by line without loading everything into memory
        Path path = Paths.get("data.txt");
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {

            long errorCount = lines
                    .filter(line -> line.contains("ERROR"))
                    .peek(System.out::println)
                    .count();

            System.out.println("Found " + errorCount + " errors in the log file.");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static void tryWithRessourceExample() {
        Path inputPath = Paths.get("in.txt");
        Path outputPath = Paths.get("out.txt");
        // This ensures resources are closed automatically, even on exceptions
        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {

            // Your I/O operations here
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write("Processed: " + line);
                writer.newLine();
            }

            System.out.println("File processed successfully!");

        } catch (IOException e) {
            // Handle exception
            System.err.println("Error processing file: " + e.getMessage());
        }
        // No need for finally block - resources are automatically closed
    }

    private static void nio2Example() {
        Path path = Paths.get("data.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void nioExample() {
        FileChannel channel = null;
        try {
            channel = FileChannel.open(
                    Paths.get("data.txt"),
                    StandardOpenOption.READ
            );
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void classicIOExample() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }


}
