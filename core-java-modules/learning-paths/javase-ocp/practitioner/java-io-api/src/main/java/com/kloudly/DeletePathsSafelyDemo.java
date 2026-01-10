package com.kloudly;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Code snippets assembled from the article:
 * "Delete Paths Safely in Java".
 *
 * Notes:
 * - This demo creates a small sandbox folder under the current working directory (./delete-demo).
 * - It intentionally triggers and prints some common exceptions to illustrate behavior.
 */
public class DeletePathsSafelyDemo {

    public static void main(String[] args) throws Exception {
        Path sandbox = Path.of("delete-demo");
        Files.createDirectories(sandbox);

        System.out.println("Sandbox: " + sandbox.toAbsolutePath());

        demoDeleteVsDeleteIfExists(sandbox);
        demoDirectoryNotEmpty(sandbox);
        demoRecursiveDeletionWithWalkFileTree(sandbox);
        demoSymbolicLinkCheck(sandbox);
        demoRealPathResolution(sandbox);

        // Final cleanup (best-effort)
        deleteRecursivelyIfExists(sandbox);
    }

    // -------------------------------------------------------------------------
    // 1) Files.delete() vs Files.deleteIfExists()
    // -------------------------------------------------------------------------
    private static void demoDeleteVsDeleteIfExists(Path sandbox) throws IOException {
        System.out.println("\n=== 1) Files.delete() vs Files.deleteIfExists() ===");

        Path tempFile = sandbox.resolve("temp.txt");
        Files.writeString(tempFile, "temp");

        // Files.delete(Path)
        Files.delete(tempFile);
        System.out.println("Deleted with Files.delete: " + tempFile.getFileName());

        // When the path does not exist -> NoSuchFileException
        Path missing = sandbox.resolve("missing.txt");
        try {
            Files.delete(missing);
        } catch (NoSuchFileException e) {
            System.out.println("Files.delete on missing path -> " + e.getClass().getSimpleName());
        }

        // Files.deleteIfExists(Path)
        Files.writeString(tempFile, "temp");
        boolean deleted = Files.deleteIfExists(tempFile);
        System.out.println("Files.deleteIfExists deleted? " + deleted);

        // When the path does not exist -> returns false, no exception
        boolean deletedMissing = Files.deleteIfExists(missing);
        System.out.println("Files.deleteIfExists on missing path -> deleted? " + deletedMissing);
    }

    // -------------------------------------------------------------------------
    // 1) Important limitation: non-empty directory cannot be deleted
    // -------------------------------------------------------------------------
    private static void demoDirectoryNotEmpty(Path sandbox) throws IOException {
        System.out.println("\n=== 1) Limitation: non-empty directory deletion ===");

        Path dir = sandbox.resolve("logs");
        Files.createDirectories(dir);
        Files.writeString(dir.resolve("app.log"), "log");

        try {
            Files.deleteIfExists(dir);
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Deleting non-empty directory -> " + e.getClass().getSimpleName());
        }
    }

    // -------------------------------------------------------------------------
    // 3) Recursive deletion with walkFileTree
    // -------------------------------------------------------------------------
    private static void demoRecursiveDeletionWithWalkFileTree(Path sandbox) throws IOException {
        System.out.println("\n=== 3) Recursive deletion with Files.walkFileTree ===");

        Path root = sandbox.resolve("tree");
        Files.createDirectories(root.resolve("a/b"));
        Files.writeString(root.resolve("a/file1.txt"), "1");
        Files.writeString(root.resolve("a/b/file2.txt"), "2");

        System.out.println("Created directory tree: " + root);

        // --- Snippet from the article (same structure) ---
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {

                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException {

                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("Deleted directory tree: " + root + " (exists? " + Files.exists(root) + ")");
    }

    // -------------------------------------------------------------------------
    // 4) Symbolic links: check (deleting a symlink deletes the link, not the target)
    // -------------------------------------------------------------------------
    private static void demoSymbolicLinkCheck(Path sandbox) throws IOException {
        System.out.println("\n=== 4) Symbolic links: Files.isSymbolicLink(path) ===");

        Path path = sandbox.resolve("some-path.txt");
        Files.writeString(path, "content");

        // Article snippet:
        System.out.println("Files.isSymbolicLink(path) -> " + Files.isSymbolicLink(path));

        // (Optional) Try to create a symlink if the OS allows it (may require privileges on Windows).
        Path target = sandbox.resolve("target.txt");
        Files.writeString(target, "target");
        Path link = sandbox.resolve("link-to-target.txt");

        try {
            Files.deleteIfExists(link);
            Files.createSymbolicLink(link, target.getFileName()); // relative link inside sandbox
            System.out.println("Created symlink: " + link.getFileName() + " -> " + target.getFileName());
            System.out.println("Files.isSymbolicLink(link) -> " + Files.isSymbolicLink(link));
        } catch (UnsupportedOperationException | FileSystemException e) {
            System.out.println("Symlink creation not supported/allowed here: " + e.getClass().getSimpleName());
        }
    }

    // -------------------------------------------------------------------------
    // 7) Security: resolve real paths when needed
    // -------------------------------------------------------------------------
    private static void demoRealPathResolution(Path sandbox) throws IOException {
        System.out.println("\n=== 7) Security: path.toRealPath() ===");

        Path path = sandbox.resolve("real.txt");
        Files.writeString(path, "x");

        // Article snippet:
        Path real = path.toRealPath();
        System.out.println("Real path: " + real);
    }

    // -------------------------------------------------------------------------
    // Helper: best-effort recursive deletion used by this demo (not part of article snippets)
    // -------------------------------------------------------------------------
    private static void deleteRecursivelyIfExists(Path root) {
        if (!Files.exists(root)) return;

        try {
            deleteRecursivelyIfExistsChecked(root);
        } catch (IOException ignored) {
            // best-effort cleanup
        }
    }

    private static void deleteRecursivelyIfExistsChecked(Path root) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
