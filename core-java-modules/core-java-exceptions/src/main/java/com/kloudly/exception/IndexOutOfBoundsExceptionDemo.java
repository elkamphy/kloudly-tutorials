package com.kloudly.exception;

import java.util.ArrayList;
import java.util.List;

public class IndexOutOfBoundsExceptionDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();//empty list
        int nonExistingValue = list.get(0);//list has no element => IndexOutOfBoundsException

        List<Integer> anotherList = new ArrayList<>();
        anotherList.add(10);//anotherList is of size 1
        anotherList.add(5);//anotherList is of size 2

        int wrongIndexValue =  anotherList.get(2);//list has 2 items, index 0 and 1 => IndexOutOfBoundsException
    }
}
