package com.kloudly.exception;

public class NullPointerExceptionDemo {
    public static void main(String[] args) throws Exception {
        //Uncomment any of the method below to reproduce the NullPointerException
        callInstanceMethodeOfNullObject();
        //unboxOfNullObject();
        //accessFieldOfNullObject();
        //takeLengthOfNullArray();
        //accessSlotOfNullArray();
        //throwNullObject();
    }

    public static void callInstanceMethodeOfNullObject(){
        String firstName = null;//null object
        String lastName = "Doe";
        String fullName =  firstName.concat(lastName);// NullPointerException

        Integer i = null;
        boolean isZero = i == 0;// NullPointerException
    }

    public static void unboxOfNullObject(){
        Integer i = null;
        boolean isZero = i == 0;// NullPointerException
    }

    public static void accessFieldOfNullObject(){
        Student student = null;//null object
        student.firstName = "John";//NullPointerException
    }

    public static void takeLengthOfNullArray(){
        int[] nullableArray = null;//null array
        int sum = 0;
        for(int i=0; i < nullableArray.length; i++){//NullPointerException
            sum += nullableArray[i];
        }
    }

    public static void accessSlotOfNullArray(){
        int[] nullableArray = null;//null array
        int firstElement = nullableArray[0];//NullPointerException
    }

    public static void throwNullObject() throws Exception {
        Student student = new Student("John","Doe");
        displayStudentName(student);
    }

    private static void displayStudentName(Student student) throws Exception {
        Exception ex = null;
        if(null != student){
            System.out.printf("Student's name is : %s %s",student.firstName(),student.lastName());
        }else{
            throw ex;//ex is null => NullPointerException
        }
    }
}

class Student{
    String firstName;
    String lastName;

    public Student(String firstName,String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String firstName(){
        return firstName;
    }

    public String lastName(){
        return lastName;
    }
}