package com.kloudly.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnsupportedOperationExceptionDemo {
    public static void main(String[] args) {
        //Uncomment any of the method below to reproduce the UnsupportedOperationException
        withListOf();
        //withStreamToList();
        //withArraysAsList();

    }

    public static void withListOf(){
        List<String> unmodifiableList = List.of("hello");
        unmodifiableList.add("world");//UnsupportedOperationException
    }

    public static void withStreamToList(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        List<String> unmodifiableList = list.stream().toList();
        unmodifiableList.add("world");//UnsupportedOperationException
    }

    public static void withArraysAsList(){
        String[] myArray = {"hello"};
        List<String> fixedSizeList = Arrays.asList(myArray);
        fixedSizeList.add("world");//UnsupportedOperationException
    }
}
