package com.kloudly.wrapperclasses;

public class WrapperClasses {
    public static void main(String[] args){
        stringToBoolean();
        characterUtilityMethods();
    }

    void autoboxing(){
        Integer intObject = 1;//autoboxing is happening
        Long longObject = 0L;
        Double doubleObject = 1.0;
        Character charObject = 'a';
        Boolean booleanObject = false;
    }

    void wrapperClassValueOf(){
        Integer intObject = Integer.valueOf(1);
        Long longObject = Long.valueOf(0L);
        Double doubleObject = Double.valueOf(1.0);
        Character charObject = Character.valueOf('a');
        Boolean booleanObject = Boolean.valueOf(false);
    }

    void stringToWrapperObjects(){
        //Using valueOf()
        Integer intObject = Integer.valueOf("1");//Cached
        Long longObject = Long.valueOf("1000");//Not Cached
        Double doubleObject = Double.valueOf("1.0");//Never cached
        Boolean booleanObject = Boolean.valueOf("false");

        System.out.println("intObject: "+intObject);
        System.out.println("longObject: "+longObject);
        System.out.println("doubleObject: "+doubleObject);
        System.out.println("booleanObject: "+booleanObject);

        //Using parseType()
        Integer intObject2 = Integer.parseInt("1");//same as Integer.valueOf("1");
        Long longObject2 = Long.parseLong("1000");//same as Long.valueOf("1000");
        Double doubleObject2 = Double.parseDouble("1.0");//same as Double.valueOf("1.0");
    }

    void wrapperClassToString(){
        Integer intObject = Integer.valueOf("1");
        Long longObject = Long.valueOf("0L");
        Double doubleObject = Double.valueOf("1.0");
        Boolean booleanObject = Boolean.valueOf("false");

        String intStr = intObject.toString();
        String longStr = longObject.toString();
        String doubleStr = doubleObject.toString();
        String booleanStr = booleanObject.toString();

        System.out.println("intStr: "+intStr);
        System.out.println("longStr: "+longStr);
        System.out.println("doubleStr: "+doubleStr);
        System.out.println("booleanStr: "+booleanStr);
    }

    void primitiveToString(){
        String intStr = Integer.toString(1);
        String longStr = Long.toString(0L);
        String doubleStr = Double.toString(1.0);
        String charStr = Character.toString('a');
        String booleanStr = Boolean.toString(false);

        System.out.println("intStr: "+intStr);
        System.out.println("longStr: "+longStr);
        System.out.println("doubleStr: "+doubleStr);
        System.out.println("charStr: "+charStr);
        System.out.println("booleanStr: "+booleanStr);
    }

    void unboxing(){
        Integer intObject = 1;
        Long longObject = 0L;
        Double doubleObject = 1.0;
        Character charObject = 'a';
        Boolean booleanObject = false;

        int primitiveInt = intObject;//unboxing is happening
        long primitiveLong = longObject;
        double primitiveDouble = doubleObject;
        char primitiveChar = charObject;
        boolean primitiveBoolean = booleanObject;
    }

    void wrapperClassTypeValue(){
        Integer intObject = Integer.valueOf(1);
        Long longObject = Long.valueOf(0L);
        Double doubleObject = Double.valueOf(1.0);
        Character charObject = Character.valueOf('a');
        Boolean booleanObject = Boolean.valueOf(false);

        int primitiveInt = intObject.intValue();
        long primitiveLong = longObject.longValue();
        double primitiveDouble = doubleObject.doubleValue();
        char primitiveChar = charObject.charValue();
        boolean primitiveBoolean = booleanObject.booleanValue();

        int primitiveInt2 = longObject.intValue();//Narrowing: Potential loss of information
        long primitiveInt3 = doubleObject.intValue();//Truncation: Potential loss of information
        long primitiveLong2 = intObject.longValue();//Widening --> No loss of information
    }

    void booleanWrapperClass(){
        Boolean wrapperObjectTrue = Boolean.TRUE;
        Boolean wrapperObjectFalse = Boolean.FALSE;
    }

    static void stringToBoolean(){
        Boolean stringToBoolean1 = Boolean.parseBoolean("true");//true
        Boolean stringToBoolean2 = Boolean.parseBoolean("True");//true
        Boolean stringToBoolean3 = Boolean.parseBoolean("false");//false
        Boolean stringToBoolean4 = Boolean.parseBoolean("FALSE");//false
        Boolean stringToBoolean5 = Boolean.parseBoolean("dummy string");//false
    }

    static void characterUtilityMethods(){
        char c1 = 'a';
        char c2 = '1';
        boolean isLowerCase = Character.isLowerCase(c1);//true
        boolean isUpperCase = Character.isUpperCase(c1);//false
        boolean isLetter = Character.isLetter(c1);//true

        boolean isDigit = Character.isDigit(c2);//true
        char c3 = Character.toUpperCase(c1);//A
    }

    static void numericWrapperClassesConstants(){
        Byte minByte = Byte.MIN_VALUE;
        Byte maxByte = Byte.MAX_VALUE;
        Short minShort = Short.MIN_VALUE;
        Short maxShort = Short.MAX_VALUE;
        int minInt = Integer.MIN_VALUE;
        int maxInt = Integer.MAX_VALUE;
        Long minLong = Long.MIN_VALUE;
        Long maxLong = Long.MAX_VALUE;
        Double minDouble = Double.MIN_VALUE;
        Double maxDouble = Double.MAX_VALUE;
    }
}
