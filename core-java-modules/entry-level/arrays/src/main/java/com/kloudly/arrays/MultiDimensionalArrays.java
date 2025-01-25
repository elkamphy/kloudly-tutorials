package com.kloudly.arrays;

import java.util.Arrays;

public class MultiDimensionalArrays {
    public static void main(String[] args) {
        simple2DArray();
        initializeNDArray();
        initializeNDArrayWithValues();
        initializeNDJaggedArrayWithValues();
        accessNDArray();
        accessNDArraySize();
        loopAndDisplayNDArray();
        displayNDArrayWithArraysClass();
    }

    static void simple2DArray(){
        int[][] simpleArray = {{1,2},{3,4}};
        System.out.println(Arrays.deepToString(simpleArray));
    }

    static void initializeNDArray(){
        int[][] my2DIntArray = new int[2][2];
        String[][][] my3DArrayOfStrings = new String[2][1][3];
        Person[][] my2DObjectArray = new Person[4][4];
        System.out.println(Arrays.deepToString(my2DIntArray));
        System.out.println(Arrays.deepToString(my3DArrayOfStrings));
        System.out.println(Arrays.deepToString(my2DObjectArray));
    }

    static void initializeNDArrayWithValues(){
        int[][] my2DIntArray = {{1,2},{3,4}};
        String[][][] my3DArrayOfStrings = {{{"a","b","c"}},{{"d","e","f"}}};
        System.out.println(Arrays.deepToString(my2DIntArray));
        System.out.println(Arrays.deepToString(my3DArrayOfStrings));
    }

    static void initializeNDJaggedArrayWithValues(){
        int[][] my2DIntJaggedArray = {{1},{3,4},{5,6,7}};//Declaring and initializing at ounce
        String[][][] my3DJaggedArrayOfStrings = new String[2][1][];//Declaring and initializing separately
        my3DJaggedArrayOfStrings[0][0] = new String[2];
        my3DJaggedArrayOfStrings[1][0] = new String[3];
        my3DJaggedArrayOfStrings[0][0][0] = "Hello";
        my3DJaggedArrayOfStrings[0][0][1] = "World";
        my3DJaggedArrayOfStrings[1][0][0] = "I'm";
        my3DJaggedArrayOfStrings[1][0][1] = "John";
        my3DJaggedArrayOfStrings[1][0][2] = "Doe";
        System.out.println(Arrays.deepToString(my2DIntJaggedArray));
        System.out.println(Arrays.deepToString(my3DJaggedArrayOfStrings));
    }

    static void accessNDArray(){
        int[][] my2DIntArray = {{1,2},{3,4}};
        int[][] my2DIntJaggedArray = {{1},{3,4},{5,6,7}};
        int[] firstRow = my2DIntArray[0];//[1, 2]
        int[] secondRow = my2DIntArray[1];//[3, 4]
        int[] firstRowJagged = my2DIntJaggedArray[0];//[1]
        int[] secondRowJagged = my2DIntJaggedArray[1];//[3, 4]
        int[] thirdRowJagged = my2DIntJaggedArray[2];//[5, 6, 7]
        int firstValue = my2DIntArray[0][0];//1
        int lastValue = my2DIntArray[1][1];//4
        int firstValueJagged = my2DIntJaggedArray[0][0];//1
        int lastValueJagged = my2DIntJaggedArray[2][2];//7
        System.out.println(Arrays.deepToString(my2DIntArray));
        System.out.println(Arrays.toString(firstRow));
        System.out.println(Arrays.toString(secondRow));
        System.out.println(Arrays.deepToString(my2DIntJaggedArray));
        System.out.println(Arrays.toString(firstRowJagged));
        System.out.println(Arrays.toString(secondRowJagged));
        System.out.println(Arrays.toString(thirdRowJagged));
        System.out.printf("First value : %d \n",firstValue);
        System.out.printf("Last value : %d \n",lastValue);
        System.out.printf("First value jagged : %d \n",firstValueJagged);
        System.out.printf("Last value jagged: %d \n",lastValueJagged);
    }
    static void accessNDArraySize(){
        int[][] my2DIntArray = {{1,2},{3,4}};
        int[][] my2DIntJaggedArray = {{1},{3,4},{5,6,7}};
        String[][][] my3DArrayOfStrings = {{{"a","b","c"}},{{"d","e","f"}}};
        int firstDimensionSize = my2DIntArray.length;//2
        int secondDimensionSize = my2DIntArray[0].length;//2

        int firstDimensionSizeJagged = my2DIntJaggedArray.length;//3
        int secondDimensionRow1SizeJagged = my2DIntJaggedArray[0].length;//1
        int secondDimensionRow2SizeJagged = my2DIntJaggedArray[1].length;//2
        int secondDimensionRow3SizeJagged = my2DIntJaggedArray[2].length;//3

        int firstDimensionStringArraySize = my3DArrayOfStrings.length;//2
        int secondDimensionStringArraySize = my3DArrayOfStrings[0].length;//1
        int thirdDimensionStringArraySize = my3DArrayOfStrings[0][0].length;//3

        System.out.printf("Int rectangular array: First dimension size : %d \n",firstDimensionSize);
        System.out.printf("Int rectangular array: second dimension size : %d \n",secondDimensionSize);
        System.out.printf("Int jagged array: First dimension size : %d \n",firstDimensionSizeJagged);
        System.out.printf("Int jagged array: second dimension row 1 size : %d \n",secondDimensionRow1SizeJagged);
        System.out.printf("Int jagged array: second dimension row 2 size : %d \n",secondDimensionRow2SizeJagged);
        System.out.printf("Int jagged array: second dimension row 3 size : %d \n",secondDimensionRow3SizeJagged);
        System.out.printf("String rectangular array: First dimension size : %d \n",firstDimensionStringArraySize);
        System.out.printf("String rectangular array: second dimension size : %d \n",secondDimensionStringArraySize);
        System.out.printf("String rectangular array: thirst dimension size : %d \n",thirdDimensionStringArraySize);
    }

    static void loopAndDisplayNDArray(){
        int[][] my2DIntArray = {{1,2},{3,4}};
        int[][] my2DIntJaggedArray = {{1},{3,4},{5,6,7}};
        for(int i=0; i<my2DIntArray.length; i++){
            for(int j=0; j<my2DIntArray[i].length;j++){
                System.out.print(" "+my2DIntArray[i][j]);
            }
            System.out.println();
        }

        for(int i=0; i<my2DIntJaggedArray.length; i++){
            for(int j=0; j<my2DIntJaggedArray[i].length;j++){
                System.out.print(" "+my2DIntJaggedArray[i][j]);
            }
            System.out.println();
        }
    }

    static void displayNDArrayWithArraysClass(){
        int[][] my2DIntArray = {{1,2},{3,4}};
        int[][] my2DIntJaggedArray = {{1},{3,4},{5,6,7}};

        System.out.println(Arrays.deepToString(my2DIntArray));
        System.out.println(Arrays.deepToString(my2DIntJaggedArray));
    }

}
