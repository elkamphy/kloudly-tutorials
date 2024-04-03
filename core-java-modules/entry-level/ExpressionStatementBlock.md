## 1. Introduction
**Expressions**, **statements**, and **blocks** are the basic components of Java code. They dictate **how instructions are written** and executed in a program. In this article, we'll break down these concepts to help you understand their importance in Java programming. More specifically, you learn the **different types of Statements** and master their differences.

**Affiliate Disclosure**
_This post contains affiliate links. If you purchase through these links, I may earn a small commission at no additional cost to you. As an Amazon Associate, I earn from qualifying purchases._

## 2. Expression
An expression is a structure consisting of variables, operators, and method calls, all arranged according to the programming language syntax, and returning a single value. The data type of the value returned by an expression depends on the data types of the variables used.
Here is an example with the *+ operator*:


>valueA + valueB


Another example using the *array [] operator*:


>myArray[index]


An example of a method call:

>myObject.toString()


## 3. Statement
A statement is roughly similar to a sentence in natural language. There are 3 categories of statements in Java: *declaration statements*, *expression statements*, and *control flow statements*.
### 3.1. Declaration Statement
Java is a **strongly typed** language, meaning that any variable must be declared before its use.
The declaration of a variable obeys the following syntax: `dataType variableName`
Example:

```java
	int value;
```

Optionally, you may assign an initialization value:

```java
	int value = 5;
```

### 3.2. Expression Statement
An expression statement is an expression that ends with a semicolon (;).
The following are examples of expression statements:
- Assignments : `myValue = 10D;`
- Increment and decrement operators : `i++;`, `j--;`
- Method calls : `System.out.println("Learning Java");`
- Object creation with the new operator : `Student student = new Student();`

### 3.3. Control Flow Statement.
You can read about [control flow statements](https://nkamphoa.com/flow-control-statements-in-java) in [this dedicated article](https://nkamphoa.com/flow-control-statements-in-java).

## Block
A block is a **group of zero or more statements** framed by opening ({) and closing (}) curly braces. You can use a block anywhere a single statement is permitted.

```java
        int firstValue = 7;
        int secondValue = 30;
        if(firstValue > secondValue){ // start block 1
            System.out.println("firstValue is greater than secondValue");
        } // end block 1
        else{// start block 2
            System.out.println("fistValue is less than or equal to secondValue");
        }// end block 2
```

You can **nest** blocks, meaning that a block may contain another block.

```java
        int firstValue = 7;
        int secondValue = 30;
        int thirdValue = 40;
        if(firstValue > secondValue){ // start block 1
            System.out.println("firstValue is greater than secondValue");
            if(secondValue < thirdValue){// start nested block 2
                System.out.println("secondValue is less than thirdValue");
            }// end nested block 2
        } // end block 1
```

>Please note how the closing brace of the nested block comes before the closing brace of the first block so that the whole forms a **well-balanced string of brackets** in the sense of the [Dyck Language](https://en.wikipedia.org/wiki/Dyck_language).

## 4. Conclusion
Congratulations! You now know everything about expressions, statements, and blocks in Java. You are now ready to approach your programs with confidence.

## 5. References
1) [OCP Oracle Certified Professional Java SE 17 by Khalil A. Mughal and Vasily A. Strelnikov](https://amzn.to/3TbEp8D)
2) [Oracle Java Documentation](https://docs.oracle.com/javase/tutorial/)