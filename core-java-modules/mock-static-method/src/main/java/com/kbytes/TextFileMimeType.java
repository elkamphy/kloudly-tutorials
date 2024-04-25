package com.kbytes;

public enum TextFileMimeType {
    PLAIN_TEXT("text/plain"),
    CSV( "text/csv" ),
    XLS("application/vnd.ms-excel");
    final String name;

    TextFileMimeType(String name) {
        this.name = name;
    }
}
