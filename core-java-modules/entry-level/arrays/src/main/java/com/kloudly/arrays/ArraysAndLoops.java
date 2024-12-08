package com.kloudly.arrays;

public class ArraysAndLoops {
    public static void main(String[] args) {
        miscellaneousOps();
    }

    void declareArray(){
        int[] myIntArray;
        int myOtherIntArray[]; //valid but not commonly used
        String[] arrayOfStrings;
        Person[] myObjectArray;
    }

    void badArrayInitialization(){
        int[] myIntArray ;
        //System.out.println(myIntArray[0]);// Does not compile. Array is not initialized
    }
    void initializeArray(){
        int[] myIntArray = new int[5];
        //int[] myOtherIntArray = new int[]; //Does not compile. Array size is missing
        int[] anIntArray = {1,2,3,4,5};
        int[] anotherIntArray = new int[]{1,2,3,4,5};
    }

    static void accessArray(){
        int[] anIntArray = {1,2,3,4,5};
        int anInt = anIntArray[0];//1
        int anotherInt = anIntArray[4];//5
        System.out.println(anIntArray[2]);// prints 3
    }

    static void miscellaneousOps(){
        int[] anIntArray = {1,2,3,4,5,6,7,8,9,10};
        int size = anIntArray.length; //10
        System.out.println("**Simple for loop**");
        for(int i=0; i< anIntArray.length; i++){
            System.out.println(anIntArray[i]);
        }
        System.out.println("**Enhanced for loop**");
        for(int value : anIntArray){
            System.out.println(value);
        }
        System.out.println("**While loop**");
        int i=0;
        while(i < anIntArray.length){
            System.out.println(anIntArray[i]);
            i++;
        }

        System.out.println("**Displaying an array**");
        System.out.println(anIntArray);
    }
}