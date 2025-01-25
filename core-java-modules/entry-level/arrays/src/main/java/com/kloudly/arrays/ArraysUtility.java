package com.kloudly.arrays;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArraysUtility {

    public static void main(String[] args) {
        //convertArrayToList();
        //copyOf();
        sort();
        //miscellaneous();
    }
    static void convertArrayToList(){
        Integer[] anIntArray = {1,2,3,4,5};
        List<Integer> listOfIntegers = Arrays.asList(anIntArray);
        //listOfIntegers.add(6);//UnsupportedOperationException
        listOfIntegers.replaceAll(i -> 2*i);
        System.out.println(listOfIntegers);//[2, 4, 6, 8, 10]
        System.out.println(anIntArray[4]);//10
        anIntArray[4] = 20;
        System.out.println(listOfIntegers.get(4));//20
    }

    static void displayArray(){
        int[] anIntArray = {1,2,3,4,5};
        int[][] my2DIntArray = {{1,2},{3,4}};
        System.out.println("**Displaying an array**");
        System.out.println(Arrays.toString(anIntArray));
        System.out.println("**Displaying a 2D array**");
        System.out.println(Arrays.deepToString(my2DIntArray));
    }

    static void sort(){
        int[] anIntArray = {4,3,1,5,2};
        Arrays.sort(anIntArray);
        System.out.println(Arrays.toString(anIntArray));//[1, 2, 3, 4, 5]
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Smith");
        Person p3 = new Person("John", "Smith");
        Person[] persons = {p1,p2,p3};
        Arrays.sort(persons);
        System.out.println(Arrays.toString(persons));
    }

    static void miscellaneous(){
        int[] anIntArray1 = {1,2,3,4,5};
        int[] anIntArray2 = {4,3,1,5,2};
        int[] anIntArray3 = {1,2,3,4,5};
        System.out.println(Arrays.equals(anIntArray1,anIntArray2));//false
        System.out.println(Arrays.equals(anIntArray1,anIntArray3));//true
        int pos = Arrays.binarySearch(anIntArray1,5);
        System.out.println(pos);//4
    }

    static void copyOf(){
        int[] originalInArray = {1,2,3,4,5};
        int[] copy1 = Arrays.copyOf(originalInArray,5);
        System.out.println(Arrays.toString(copy1));// [1, 2, 3, 4, 5]
        int[] copy2 = Arrays.copyOf(originalInArray,3);
        System.out.println(Arrays.toString(copy2));// [1, 2, 3]
        int[] copy3 = Arrays.copyOf(originalInArray,10);
        System.out.println(Arrays.toString(copy3));// [1, 2, 3, 4, 5, 0, 0, 0, 0, 0]

    }

    void convertArrayToListBad(){
        int[] anIntArray = {1,2,3,4,5};
        //List<Integer> listOfIntegers = Arrays.asList(anIntArray);//Does not compile
    }
}

record Person(String firstName, String lastName) implements Comparable<Person>{
    @Override
    public int compareTo(Person other) {
        // Compare by firstName first
        int firstNameComparison = this.firstName.compareTo(other.firstName);
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }
        // If firstName is the same, compare by lastName
        return this.lastName.compareTo(other.lastName);
    }
}
