## Introduction
Mocking Static methods is always a big challenge when it comes to testing Java programs. We saw in [this previous article](https://nkamphoa.com/mock-static-methods) how to overcome this obstacle with the use of Mockito. In this article, we are going to focus on mocking FileSystems Operations. You will learn how to mock the methods from the `Path` and `Files` class of the package `java.nio`.

## The Application Under Test
The code we're going to use is a slightly modified version of the application we used in the tutorial on [how to mock statics methods](https://nkamphoa.com/mock-static-methods).
The application has three main classes:
- `SalesProcessor.java`: This class has a public method `compute` which takes a fileName as a parameter, and calculates the total sales per product in that file. The parameter is CSV file with the following structure `"productCode;saleAmount"`. The `compute()` method must return a `Sale` object. The method throws an exception is the file is not accessible or if its content is invalid.
- `SaleLineParser`: This class takes a string in the form `"productCode;saleAmount"`, parse it and return a `Sale` object. The method throws an exception is the line is invalid.
- `Sale.java` is a simple java record with two fields: `public record Sale(String productCode, int saleAmount) { }`

### The SalesProcessor.java class
Here is the snippet of the `SalesProcessor#compute()` method:
```java
    public Sale compute(String filePath) throws Exception {
        Stream<String> sales = this.readFile(filePath);
        Function<String,Sale> toSale = this.lineParser::parse;
        Map<String,List<Sale>> salesByProduct = sales
                .map(toSale)
                .collect(Collectors.groupingBy(Sale::productCode));
        return computeMaxSales(salesByProduct);
    }
```
The complete class is available [here in GitHub](https://github.com/elkamphy/kloudly-tutorials/tree/master/core-java-modules/mock-static-method).

### The SaleLineParser.java class
Here is the snippet of the `SaleLineParser#parse()` method:
```java
    public Sale parse(String line){
        String delimiter = ";";
        String[] saleData = line.split(delimiter,-1);
    
        if(isInvalidColumnCount(saleData))
            throw new IllegalArgumentException(Constants.INVALID_LINE);
    
        if(isInvalidField(saleData))
            throw new IllegalArgumentException(Constants.INVALID_FIELD);
    
        return new Sale(saleData[0],Integer.parseInt(saleData[1]));
    }
```
The complete class is available [here in GitHub](https://github.com/elkamphy/kloudly-tutorials/tree/master/core-java-modules/mock-static-method).

### The Sales.java class
```java
public record Sale(String productCode, int saleAmount) { }
```
## Procedure
The procedure for mocking a static method has already been covered in [this tutorial](https://nkamphoa.com/mock-static-methods), so we won't return to it here. We'll concentrate here on how to mock file processing operations.
## Mocking scenarios
Here we'll simulate different scenarios to thoroughly test the `SalesProcessor` class.
### Mocking a Non-existing file
First, we'll simulate a non-existent file. We'll mock the `exists()` method of the `Files` class. Since this `exists()` method takes a `Path` object as parameter, we'll also mock the `Path` class.
```java
    @Test
    void test_File_not_Accessible_NonExisting() throws Exception {
        //given
        String anyFile = "test.csv";
        bean = new SalesProcessor();

        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(false);
            //when then
            Exception expectedException =  Assertions.assertThrows(IllegalArgumentException.class,() -> bean.compute(anyFile));
            Assertions.assertEquals(Constants.FILE_NOT_ACCESSIBLE, expectedException.getMessage());
        }
    }
```
### Mocking a Not readable file
Our second test will simulate a situation where the file does exist, but is unreadable. We'll mock the `isReadable()` method of the `Files` class. As in the previous case, we'll also mock the `Path` class, which gives us the following:
```java
    @Test
    void test_File_not_Accessible_NotReadable() throws Exception {
        //given
        String anyFile = "test.csv";;
        bean = new SalesProcessor();
        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(this.mockPath)).thenReturn(false);
            //when then
            Exception expectedException =  Assertions.assertThrows(IllegalArgumentException.class,() -> bean.compute(anyFile));
            Assertions.assertEquals(Constants.FILE_NOT_ACCESSIBLE, expectedException.getMessage());
        }
    }
```
### Mocking a Content not valid file
In this third test case, we will simulate a situation in which the file content is not compatible with the desired type (CSV). We'll assume that a CSV file is a file whose MIME type is part of a set that we'll define as an Enum.  
As in the other cases, we're going to mock up the `Files` and `Path` classes. To obtain the MIME type from the `Files` class, we use the `probeContentType()` method.
```java
    @Test
    void test_File_Content_Invalid() throws Exception {
        //given
        String anyFile = "test.csv";
        String invalidMimeType = "video/x-msvideo";
        bean = new SalesProcessor();
        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.probeContentType(this.mockPath)).thenReturn(invalidMimeType);
            //when then
            Exception expectedException = Assertions.assertThrows(IllegalArgumentException.class,() -> bean.compute(anyFile));
            Assertions.assertEquals(Constants.INVALID_FILE_CONTENT, expectedException.getMessage());
        }
    }
```
### Mocking an invalid CSV format file
We're now going to simulate the situation where the file exists, is readable, is of the right type, but some lines don't conform to the desired CSV format. To do this, we'll mock the `lines()` method of the `Files` class.
```java
    @Test
    void test_File_With_Erroneous_Lines() throws Exception {
        //given
        String invalidLine = "P1;20;";
        Stream<String> simpleStream = Stream.of("P1;10","P2;5",invalidLine,"P1;30");
        String anyFile = "test.csv";
        String validMimeType = "text/csv";
        bean = new SalesProcessor();
        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.probeContentType(this.mockPath)).thenReturn(validMimeType);
            mockFiles.when(() -> Files.lines(this.mockPath)).thenReturn(simpleStream);
            //when then
            Exception expectedException = Assertions.assertThrows(IllegalArgumentException.class,() -> bean.compute(anyFile));
            Assertions.assertEquals(Constants.INVALID_LINE, expectedException.getMessage());
        }
    }
```
### Mocking a Valid file content
For our final test, we'll assume that the file is valid in every respect. Here is our final test case:
```java
    void test_Simple_File() throws Exception {
        //given
        Stream<String> simpleStream = Stream.of("P1;10","P2;5","P1;20","P1;30");
        String anyFile = "test.csv";
        String validMimeType = "text/csv";
        bean = new SalesProcessor();
        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.probeContentType(this.mockPath)).thenReturn(validMimeType);
            mockFiles.when(() -> Files.lines(this.mockPath)).thenReturn(simpleStream);
            //when
            Sale result = bean.compute(anyFile);
            // then
            Assertions.assertAll(
                    () -> Assertions.assertEquals("P1", result.productCode()),
                    () -> Assertions.assertEquals(60, result.saleAmount())
            );
        }
    }
```
## Conclusion
In this article, you learned how to mock the static methods of the `Path` and `Files` classes.  
The full code of this tutorial can be found [here on GitHub](https://github.com/elkamphy/kloudly-tutorials/tree/master/core-java-modules/mock-static-method).