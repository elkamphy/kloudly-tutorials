package com.kloudly;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.stream.Stream;

/**
 * Demonstration class assembling the main code snippets
 * from the article "Modern Java Filesystem API (NIO.2)".
 *
 * This class is pedagogical: each method illustrates
 * one aspect of Path, Files, and filesystem traversal.
 */
public class JavaFilesystemApiDemo {

    public static void main(String[] args) throws Exception {
        demoPaths();
        demoFilesBasics();
        demoMetadata();
        demoSymbolicLinks();
        demoFileSystem();
        demoDirectoryTraversal();
    }

    // ----------------------------------------------------------------
    // 1. Path creation
    // ----------------------------------------------------------------
    private static void demoPaths() throws IOException {
        Path absolutePath = Path.of("/tmp/data.txt");
        Path windowsPath = Path.of("C:/temp/data.txt");
        Path relativePath = Path.of("data/input.txt");
        Path composedPath = Path.of("data", "logs", "app.log");

        Path homePath = Path.of(
                System.getProperty("user.home"),
                "config",
                "app.properties"
        );

        Path missing = Path.of("missing.txt");

        System.out.println("Absolute path : " + absolutePath);
        System.out.println("Relative path : " + relativePath);
        System.out.println("Composed path : " + composedPath);
        System.out.println("Home path     : " + homePath);

        try {
            Files.readString(missing);
        } catch (IOException e) {
            System.out.println("Expected failure reading missing file");
        }
    }

    // ----------------------------------------------------------------
    // 2. Files basic operations
    // ----------------------------------------------------------------
    private static void demoFilesBasics() throws IOException {
        Path dir = Path.of("data/logs");
        Files.createDirectories(dir);

        Path file = dir.resolve("app.log");
        if (!Files.exists(file)) {
            Files.createFile(file);
        }

        Files.writeString(file, "Hello, world!\n",
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        boolean exists = Files.exists(file);
        boolean isFile = Files.isRegularFile(file);
        boolean isDirectory = Files.isDirectory(dir);

        System.out.println("Exists      : " + exists);
        System.out.println("Is file     : " + isFile);
        System.out.println("Is directory: " + isDirectory);

        Files.copy(file, Path.of("backup-app.log"),
                StandardCopyOption.REPLACE_EXISTING);
    }

    // ----------------------------------------------------------------
    // 3. File metadata
    // ----------------------------------------------------------------
    private static void demoMetadata() throws IOException {
        Path file = Path.of("backup-app.log");

        long size = Files.size(file);
        FileTime lastModified = Files.getLastModifiedTime(file);

        System.out.println("File size        : " + size);
        System.out.println("Last modified at : " + lastModified);
    }

    // ----------------------------------------------------------------
    // 4. Symbolic links
    // ----------------------------------------------------------------
    private static void demoSymbolicLinks() throws IOException {
        Path path = Path.of("backup-app.log");

        boolean isSymlink = Files.isSymbolicLink(path);
        Path realPath = path.toRealPath();

        System.out.println("Is symbolic link: " + isSymlink);
        System.out.println("Real path       : " + realPath);
    }

    // ----------------------------------------------------------------
    // 5. FileSystem abstraction
    // ----------------------------------------------------------------
    private static void demoFileSystem() throws IOException {
        Path zipPath = Path.of("archive.zip");

        if (Files.exists(zipPath)) {
            try (FileSystem zipFs = FileSystems.newFileSystem(zipPath)) {
                for (Path root : zipFs.getRootDirectories()) {
                    System.out.println("ZIP root: " + root);
                }
            }
        }
    }

    // ----------------------------------------------------------------
    // 6. Directory traversal
    // ----------------------------------------------------------------
    private static void demoDirectoryTraversal() throws IOException {

        Path root = Path.of("data");

        // Files.walk
        try (Stream<Path> paths = Files.walk(root)) {
            paths
                .filter(Files::isRegularFile)
                .forEach(p -> System.out.println("walk: " + p));
        }

        // Files.walkFileTree
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.println("fileTree file: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                System.out.println("fileTree dir : " + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("Failed to access: " + file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
