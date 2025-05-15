package com.kloudly;

// === ISP Violation Example ===
interface FileManager {
    void read(String path);
    void write(String path, String content);
    void delete(String path);
}

class LogViewerWithViolation implements FileManager {
    public void read(String path) {
        System.out.println("Reading log file: " + path);
    }

    public void write(String path, String content) {
        throw new UnsupportedOperationException();
    }

    public void delete(String path) {
        throw new UnsupportedOperationException();
    }
}

// === ISP Compliant Interfaces ===
interface ReadableFile {
    void read(String path);
}

interface WritableFile {
    void write(String path, String content);
}

interface DeletableFile {
    void delete(String path);
}

// === ISP Compliant Implementations ===
class LogViewer implements ReadableFile {
    public void read(String path) {
        System.out.println("Reading log file: " + path);
    }
}

class ConfigManager implements ReadableFile, WritableFile {
    public void read(String path) {
        System.out.println("Reading config: " + path);
    }

    public void write(String path, String content) {
        System.out.println("Writing config to: " + path);
    }
}

