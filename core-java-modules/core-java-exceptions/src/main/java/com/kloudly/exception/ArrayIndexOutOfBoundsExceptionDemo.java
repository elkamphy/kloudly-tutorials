package com.kloudly.exception;

public class ArrayIndexOutOfBoundsExceptionDemo {
    public static void main(String[] args) {
        int[] arr = new int[5];//array of size 5
        int value = arr[5];//index 5 is not in range => ArrayIndexOutOfBoundsException
    }

}
