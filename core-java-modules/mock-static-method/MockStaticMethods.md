## Introduction
Have you ever got stuck writing automated tests for a class because it was using static method calls? Although the best practice is to refactor the code so that it no longer uses static methods, in some cases this simply isn't possible. This is the case, for example, if the code in question does not belong to you. The Path and Files classes in the java.nio package are practical examples. In this article, we'll show you how to mock these classes using the Mockito library.

## The Application Under Test
The application we are going to test has three classes:
 - `SalesProcessorStatic.java`: This class has a unique method `compute` which takes a list of strings representing the content of a CSV file. The String has the following structure `"productCode;saleAmount"`. The `compute()` method must return the total sales of the input List.
 - `SaleLineParser`: This class takes a string in the form `"productCode;saleAmount"`, parse it and return a `Sale` object.
 - `Sale.java`: is a simple java record with two fields: `public record Sale(String productCode, int saleAmount) { }`

### The SalesProcessorStatic.java class
```java
package com.kbytes;

import java.util.List;
import java.util.function.Function;

public class SalesProcessorStatic {

    public SalesProcessorStatic(){
    }

    public int compute(List<String> sales){
        Function<String,Sale> toSale = SaleLineParserStatic::parse;
        return sales.stream()
                .map(toSale)
                .map(Sale::saleAmount)
                .reduce(Integer::sum).orElse(0);
    }
}
```
### The Class to be Mocked: SaleLineParserStatic.java
```java
package com.kbytes;

public class SaleLineParserStatic {
    private static final int COLUMN_COUNT = 2;

    public static Sale parse(String line){
        return parse(line,";");
    }
    public static Sale parse(String line, String delimiter){
        String[] saleData = line.split(delimiter,-1);

        if(isInvalidColumnCount(saleData))
            throw new IllegalArgumentException("The file must have "+COLUMN_COUNT+" columns!");

        if(isInvalidField(saleData))
            throw new IllegalArgumentException("The file data format is invalid!");

        return new Sale(saleData[0],Integer.parseInt(saleData[1]));
    }

    private static boolean isInvalidField(String[] saleData) {
        boolean isSaleInvalid = true;
        try{
            Double.parseDouble(saleData[1]);
            isSaleInvalid = false;
        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        return saleData[0] == null || saleData[0].isEmpty() || isSaleInvalid;
    }

    private static boolean isInvalidColumnCount(String[] cityData) {
        return cityData != null && (cityData.length != COLUMN_COUNT);
    }
}
```
### The Sale.java Record
```java
public record Sale(String productCode, int saleAmount) { }
```

As you can see from this line `Function<String,Sale> toSale = SaleLineParserStatic::parse`, the `compute()` method of the `SalesProcessorStatic` class is making a static call to the `parse()` method of `SaleLineParserStatic`.  
To test the `SaleProcessorStatic` class independently of the `SaleLineParserStatic`, you have to mock the call to the static method `SaleLineParserStatic.parse()`.

## Step 1: Add the Maven Dependency
The only dependencies that you will need are JUnit 5 Engine and Mockito Core.  
Add the following dependencies to your project:
````xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.9.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>5.11.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
````

## Step 2: Mock The Static Method with `MockedStatic`
Mocking static methods is possible since [Mockito 3.4.0](https://github.com/mockito/mockito/blob/release/3.x/doc/release-notes/official.md).  The procedure for mocking a static method is as follows:
- Use the `mockStatic()` method of the `Mockito` class: `mockStatic(SaleLineParserStatic.class)`
- Retrieve the object returned by the `mockStatic()` method in a variable of type `MockedStatic`: `MockedStatic<SaleLineParserStatic> mockSaleLineParser = mockStatic(SaleLineParserStatic.class)`
- Configure the mock object: `mockSaleLineParser.when(() -> SaleLineParserStatic.parse("P1;10")).thenReturn(sale1)`

From the `MockedStatic` class Javadoc, you can read the following:
>The static mock is released when this object's `MockedStatic#close()` method is invoked. If this object is never closed, the static mock will remain active on the initiating thread. It is therefore recommended to create this object within a try-with-resources. 

Below is the complete test method for the `SalesProcessorStatic` class:
```java
    @Test
    void test_simple_compute(){
        //given
        List<String> sales = List.of("P1;10","P2;20");
        Sale sale1 = new Sale("P1",50);
        Sale sale2 = new Sale("P2",70);
        int expectedTotal = 120;
        try(MockedStatic<SaleLineParserStatic> mockSaleLineParser = mockStatic(SaleLineParserStatic.class)){
            mockSaleLineParser.when(() -> SaleLineParserStatic.parse("P1;10")).thenReturn(sale1);
            mockSaleLineParser.when(() -> SaleLineParserStatic.parse("P2;20")).thenReturn(sale2);
            //when
            SalesProcessorStatic salesProcessor = new SalesProcessorStatic();
            int salesTotal = salesProcessor.compute(sales);
            //then
            Assertions.assertEquals(expectedTotal,salesTotal);
        }
    }
```

Given that the mock object is closed at the end of the `try-with-resource` block, the verifying part (Assertions) of your test must be within that same block.

If you need to mock different classes in the same test, you should add them in the `try-with-resource` block like this:
```java
    try(MockedStatic<FirstClassWithStaticMethod> firstMock = mockStatic(FirstClassWithStaticMethod.class);
    MockedStatic<SecondClassWithStaticMethod> secondMock = mockStatic(SecondClassWithStaticMethod.class)){
       //your code  
    }
```
## Conclusion
In this quick tutorial, you learned to mock static methods using Mockito. If you want to learn how to use this approach to mock access to the FileSystem, check out [this article](https://nkamphoa.com/mock-filesystem-with-mockito).  
The full code of this tutorial can be found [here on GitHub](https://github.com/elkamphy/kloudly-tutorials/tree/master/core-java-modules/mock-static-method).