package com.kloudly.numbers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class BigIntegerClass {
    public static void main(String[] args) {
        createFromConstructors();
        createFromStaticFactoryMethods();
        arithmeticOperations();
        compareBigIntegers();
        manipulateBit();
    }

    static void createFromConstructors(){
        //Creating from strings
        BigInteger bigInteger1 = new BigInteger("123456789");
        System.out.println(bigInteger1);

        BigInteger bigIntegerBase2 = new BigInteger("10000",2);//10000 in base 2, i.e. 16
        System.out.println(bigIntegerBase2);//16

        //Random bigintegers
        BigInteger probablyPrimeNumber = new BigInteger(5,1,new Random());//certainty=1 -> probably prime number
        BigInteger probablyCompositeNumber = new BigInteger(5,0,new Random());//certainty=0 -> probably composite number
        System.out.println("Probably prime number: "+probablyPrimeNumber);
        System.out.println("Probably composite number: "+probablyCompositeNumber);

        BigInteger uniformlyDistributedBigInteger = new BigInteger(5,new Random());//any number from 0 to 31
        System.out.println("Uniformly distributed : "+uniformlyDistributedBigInteger);

    }


    static void createFromStaticFactoryMethods(){
        BigInteger bigInteger = BigInteger.valueOf(123456789L);
        BigInteger probablyPrimeNumber = BigInteger.probablePrime(5,new Random());//17 or 19 or 23 or 29 or 31
        System.out.println(probablyPrimeNumber);
    }

    static void createFromConstants(){
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger ten = BigInteger.TEN;
    }

    static void arithmeticOperations(){
        BigInteger val1 = BigInteger.valueOf(5);
        BigInteger val2 = BigInteger.TWO;

        BigInteger val3 = val1.add(val2); // 7;
        BigInteger val4 = val1.subtract(val2); // 3
        BigInteger val5 = val1.multiply(val2); // 10
        BigInteger val6 = val1.divide(val2); // 2 --> integer division
        BigInteger gcd = val1.gcd(val2); // 1
    }

    static void compareBigIntegers(){
        //compareTo
        BigInteger val1 = BigInteger.valueOf(5);
        BigInteger val2 = BigInteger.TWO;

        int value = val1.compareTo(val2);//1 because 5 > 2
        //equals
        BigInteger val3 = BigInteger.TEN;
        BigInteger val4 = BigInteger.valueOf(10);

        boolean result = val3.equals(val4);//true
    }

    static void manipulateBit(){
        BigInteger val1 = BigInteger.valueOf(8);
        BigInteger val2 = val1.shiftLeft(1);// 16
        BigInteger val3 = val1.shiftRight(1);// 4
    }
}
