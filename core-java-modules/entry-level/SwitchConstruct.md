## 1. Introduction
Among the control flow statements in Java, `Switch` is arguably the most delicate. Many programmers, even the most experienced, have difficulty mastering the different options of the *Switch* statement. In this article, you will find out everything you need to know about that statement. You will learn about *Switch Statements*, *Switch Expressions*, and their differences.

**Affiliate Disclosure**
_This post contains affiliate links. If you purchase through these links, I may earn a small commission at no additional cost to you. As an Amazon Associate, I earn from qualifying purchases._

## 2. What is The Switch Construct?
The `Switch` Construct implements a *multi-way branch* that allows your program to be transferred to a specific entry point in the code of the *switch* block, based on an input variable. There are two types of `switch` constructs in Java: the *switch statement* and the *switch expression*. Each of these constructs can be written in two ways: using the *colon(:) notation* and using the *arrow(->) notation*.
## 3. The Switch Statement
### 3.1. The Switch Statement With The Colon Notation (:)
This statement allows the programmer to choose between several alternative actions, depending on a parameter. Its syntax is as follows:

```java
        switch(selector_expression){
            case value1: statements_value1
            case value2: statements_value2
                ....
            case valueN: statements_valueN
            default: statements_default
        }
```

1) *selector_expression* must be an expression returning a value of the following data type:
- A **primitive data type**: char, byte, short, or int
- A **wrapper class**: Character, Byte, Short, or Integer
- An **Enum type**
- A **String**(Since Java 7)

2) *value1, value2, ... valueN* must be **type-compatible** with *selector_expression*.
3) *statements_value1, statements_value2, ... statements_valueN* can be a single statement, a group of statements, or a block statement.
4) The *default* block will be executed if none of the constants matches the input value.
5) The case constants can appear in any order in the *switch block*. For instance, the *default* can be the first:

```java
        switch(selector_expression){
            default: statements_default
            case value1: statements_value1
            case value2: statements_value2
                ....
            case valueN: statements_valueN
        }
```

When different case labels have a common action, they can be grouped like this:
**Multiple Case Labels with a Common Action**
```java
        switch(selector_expression){
            case value1: statements_value1
            case value2: case value3: case value4: statements_value1_2_3
                ....
            case valueN: statements_valueN
            default: statements_default
        }
```

Or like this:

**Single Case Label, multiple constants with a Common Action**
```java
        switch(selector_expression){
            case value1: statements_value1
            case value2, value3, value4: statements_value1_2_3
                ....
            case valueN: statements_valueN
            default: statements_default
        }
```

**Example of Switch Statement with The Colon Notation**
```java
        int value = 5;
        switch (value){
            case 1:
                System.out.println("Value is 1");
            case 2:
                System.out.println("Value is 2");
            case 5:
                System.out.println("Value is 5");
            default:
                System.out.println("Value doesn't match any of the constants");
        }
```
If you run the code above, you will get the following output:
```console
Value is 5
Value doesn't match any of the constants
```
You should know that once the *switch* statement with the *colon notation* finishes executing the block of code corresponding to the input constant, it moves down (fall-through) to the next constant. To avoid that, you should use a *break* to take the execution flow out of the *switch construct*.
```java
        int value = 5;
        switch (value){
            case 1:
                System.out.println("Value is 1");
                break;
            case 2:
                System.out.println("Value is 2");
                break;
            case 5:
                System.out.println("Value is 5");
                break;
            default:
                System.out.println("Value doesn't match any of the constants");
        }
```
> Always remember to use the *break* statement in your *switch* constructs with a *colon notation* to avoid unexpected behaviour.

### 3.2. The Switch Statement With The Arrow(->) Notation
The syntax of this form of the *switch* statement is shown below:
```java
        switch(selector_expression){
            case value1 -> statements_value2
            case value2: statements_value2
                ....
            case valueN: statements_valueN
            default: statements_default
        }
```
Compared to the *colon notation* construct, you have the following points of difference to note:
#### Multiple case labels are not allowed
The *arrow notation* does not allow multiple case labels to be associated with the same action, unlike the *colon notation*.
The code below will not compile:
```java
        int value = 5;
        switch (value){
            case 1 -> System.out.println("Value is 1");
            case 2 -> case 3 -> System.out.println("Value is 2");//Not allowed
            case 5 -> System.out.println("Value is 5");
            default -> System.out.println("Value doesn't match any of the constants");
        }
```
To get around this, you can specify a single case label with a list of constants:

```java
        int value = 5;
        switch (value){
            case 1 -> System.out.println("Value is 1");
            case 2, 3 -> System.out.println("Value is 2");//This is OK
            case 5 -> System.out.println("Value is 5");
            default -> System.out.println("Value doesn't match any of the constants");
        }
```

#### Actions associated with a case label are limited
You cannot use a group of statements in an *arrow notation switch*.
This will not compile:
```java
        int value = 5;
        switch (value){
            case 1 -> 
                    System.out.println("Value is 1");
                    System.out.println("This line will not compile");//Not allowed
            case 2, 3 -> System.out.println("Value is 2");
            case 5 -> System.out.println("Value is 5");
            default -> System.out.println("Value doesn't match any of the constants");
        }
```


You can only use one of the following actions:
- An [expression statement](https://nkamphoa.com/expressions-statements-and-blocks-in-java)
```java
            case 2, 3 -> System.out.println("Value is 2");
```

- A [block](https://nkamphoa.com/expressions-statements-and-blocks-in-java) of statements
```java
            case 1 ->{
                System.out.println("Value is 1");
                System.out.println("This line will compile");
            }
```

- *Throw an exception*
```java
	default -> throw  new IllegalArgumentException("Not a valid value");
```
#### The Arrow Switch Execution is Mutually Exclusive
Unlike the *colon notation switch*, there is no need for a *break* statement. Once the execution of the statements associated with a case label has been completed, the *switch* construct also terminates(no **fall-through**).

#### Full Example of The Arrow Notation Switch
```
        int value = 5;
        switch (value){
            case 1 ->{
                System.out.println("Value is 1");
                System.out.println("This line will compile");
            }
            case 2, 3 -> System.out.println("Value is either 2 or 3");
            case 5 -> System.out.println("Value is 5");
            default -> throw  new IllegalArgumentException("Not a valid value");
        }
```

#### Using Strings as Case Constants
Starting from **Java 7**, you can use the String data type in your *switch* constructs. However, there are a couple of things you should be aware of while switching on Strings.
- The Java compiler compares the string constants based on their hash values first(integer values), followed by an object equality (using the `equals()` method) to rule out any collision.
- Switching on strings is less efficient than switching on integers. Therefore, you must only switch on strings if the values are already of type `String`.
- The compiler must be able to determine the value of all the *case constants* at compile-time.

**Correct Example of Switching on Strings**
Find below an example of a switch statement using String constants.
```java
        String color = "blue";
        final String YELLOW = "yellow";
        switch (color){
            case "red" -> System.out.println("Color is red");
            case "blue" -> System.out.println("Color is blue");
            case YELLOW -> System.out.println("Color is yellow");
            default ->  throw new IllegalArgumentException("Not a valid color");
        }
```

**Incorrect Way of Switching on Strings**
The following example will not compile because the compiler cannot guarantee that the value of the *case constants* will not change at runtime.
```java
        String color = "blue";
        String YELLOW = "yellow";
        String RED = new String("red");
        switch (color){
            case RED -> System.out.println("Color is red");//Compile-time error, RED is not a constant
            case "blue" -> System.out.println("Color is blue");
            case YELLOW -> System.out.println("Color is yellow");//Compile-time error, YELLOW is not a constant
            default ->  throw new IllegalArgumentException("Not a valid color");
        }
```

#### Using Enum as Case Constants
An [enum type](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html) is a special data type that enables for a variable to be a set of predefined constants. We'll talk about this in a future article. For now, you should know that this data type has been introduced since Java 5 and since then can be used as a constant in *switch* constructs.
Here are the key points you need to remember when it comes to using the Enum data type in a *switch*:
- The Java compiler assigns a **unique integer value** to each enum constant. Switching on enum values is based on equality comparison on that integer  value.
- The enum constants must be of the **same type** as the selector expression.
- You **must not use the fully qualified name** when specifying the enum constants.

  **Correct Example of Switching on Enum Values**
```java
    enum Color {BLUE,RED,YELLOW}
    public static void main(String[] args) {
        Color color = Color.BLUE;
        switch (color){
            case RED -> System.out.println("Color is red");
            case BLUE -> System.out.println("Color is blue");
            case YELLOW -> System.out.println("Color is yellow");
            default ->  throw new IllegalArgumentException("Not a valid color");
        }
    }
```

**Incorrect Way of Switching on Enum Values**
The code below will not compile because the case constant `RED` is using a fully qualified name.
```java
    enum Color {BLUE,RED,YELLOW};
    public static void main(String[] args) {
        Color color = Color.BLUE;
        switch (color){
            case Color.RED -> System.out.println("Color is red");//Compile-time error
            case BLUE -> System.out.println("Color is blue");
            case YELLOW -> System.out.println("Color is yellow");
            default ->  throw new IllegalArgumentException("Not a valid color");
        }
    }
```
## 4. The Switch Expression
A **Switch expression** has the same semantics as a *switch* statement, with the difference that it returns a value. Just like *switch* statements, there are two forms of *switch* expressions: *Colon notation* and *Arrow notation* `switch` expressions.
### 4.1. The `yield` Statement
In *switch* expressions, the *yield* statement plays a similar role to *break* in *switch* statements. The `yield` statement can only be used in `switch` expressions.
```java
	yield expression;
```
Execution of the statement above will return the value of *expression* as the result of the *switch* expression.

### 4.2. The Switch Expression With The Colon(:) Notation
The *switch* expression with the *colon notation* is analogous to the *switch* statement with the *colon notation* with the difference that it returns a value (or throws an exception).
```java
        dataType switchValue = switch(selector_expression){
            case value1: statements_value1;
                         yield someValue1;
            case value2: statements_value2;
                        yield someValue2;
                ....
            case valueN: statements_valueN;
                         yield someValueN;
            default: statements_default;
                    yield someDefaultValue;
        }
```
- *dataType* is the data type of *switch* expression value
- *yield* is used to return a value to the *switch* expression.
- Just like the *switch* statement, if there is no *yield* at the end of the group of statements, the execution will *fall through* the next group, if any.
- The *switch* expression with the *colon notation* must be **exhaustive**, meaning that the case labels, and if necessary the *default* label, must cover all values of the selector expression type. **Failure to cover all values will result in a compile-time error.**
> The *default* label is usually used to make sure the *switch* expression is exhaustive.

Below is an example of the *switch* expression with colon(:) notation:
```java
        int value = 5;
        int switchValue = switch(value){
            case 1:
                System.out.println("Value is 1");
                yield 1;
            case 2:
                System.out.println("Value is 2");
                yield 2;
            case 3,4:
                System.out.println("Value is 3 or 4");
                yield 3;
            default:
                System.out.println("Value not in range");
                yield 0;
        };//Don't forget the semicolon (;)
        System.out.println(switchValue);
```
> Note that since the *switch* expression return a value into a variable, you must add a semicolon(;) after the closing curly brace (}).

### 4.3. The Switch Expression With The Arrow(->) Notation
The *switch* expression with *arrow notation* is simply a *switch* statement with the *arrow notation* that returns a value(or throws an exception). Its syntax is as follows:
```java
        dataType switchValue = switch(selector_expression){
            case value1 -> statements_value1;
                yield someValue1;
            case value2 -> statements_value2;
                yield someValue2;
                ....
            case valueN -> statements_valueN;
                yield someValueN;
            default -> statements_default;
                yield someDefaultValue;
        }
```
Find the list of important points to note below:
1) The execution of the *switch* rules in a *switch* expression is **mutually exclusive**, just like in *switch* statements. Once the action in the *switch* has completed its execution, the value is returned to the *switch* expression and the *switch* body terminates. There is no **fall-through**.
2) Unlike *switch* statements, the action body is not limited to *expression statements*. In addition to using a statement block, or throwing an exception, you can use **any type of expression**.
```java
	case 1 -> 1;//simple expression, must return a valid value directly
    case 2 -> yield 2; //compiled-time error,  yield is not allowed here
    case 3 -> {//statement block
    		System.out.println("Value is 3");
    		yield 3;
            }
    case 4 -> {//statement block
    		yield 4;//Compiled-time error, yield must be the last statement in the block
    		System.out.println("Value is 4");
            }
    default -> throw  new IllegalArgumentException("Not a valid value");
```
3) The *switch* statement with the *arrow notation* must also be **exhaustive**. All possible selector values must be covered.

**Example of Switch Expression with the Arrow(->) Notation**
```java
        int value = 3;
        int switchValue = switch(value){
            case 1 ->{
                System.out.println("Value is 1");
                yield 1;
            }
            case 2-> 2;
            case 3,4->{
                System.out.println("Value is 3 or 4");
                yield 3;
            }
            default->
                throw  new IllegalArgumentException("Not a valid value");
        };
        System.out.println(switchValue);
```
Executing this program will produce the following output:
```console
Value is 3 or 4
3
```

## 5. Summary of The Switch Statement and The Switch Expression
The table below summarizes the main differences between the *switch statement* and the *switch expression*.

| **Notation**             | **Switch Statement**                                                                                                                                                       | **Switch Expression**                                                                                                                                                                                                                                                                                                                                   |
|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| The colon  _notation_:   | Executes statements associated  with the matching case label                                                                                                               | - Executes statements associated with the matching case label, and must have a _yield_  statement to return a value                                                                                                                                                                                                                                     |
| Case label: _statements_ | - Fall-through can occur  - No compile-time check for exhaustiveness  - Only _break_ and _return_ statements allowed to control fall-through                               | - Fall-through can occur  - Compile-time check for exhaustiveness  - No _break_ or _return_ allowed                                                                                                                                                                                                                                                     |
| The arrow  _notation_->  | - Actions associated with a switch rule can be an _expression statement_, a _block_,  or can _throw_ an exception.  																																			                    | - Actions associated with a switch rule can be _any expression_, a _block_, or can _throw_ an exception.   																																																																														                                                                                                                                                               |
| case label -> _action_   | - Mutually-exclusive rules, no fall-through can occur  - No compile-time check for exhaustiveness  - _break_ and _return_ statements allowed 																													 | - Mutually-exclusive switch rules, no fall-through  - Compile-time check for exhaustiveness  - No _break_ or _return_ statement allowed  - Must return a value that is either the value _stand-alone expression_ or the value of the expression in a _yield_  statement that can occur as the _last statement_ in a _block_  																										 |

## 6. Conclusion
In this article, you learned the *switch* construct in Java. You have discovered the main differences between the *switch statement* and the *switch expression*. You also learned the difference between the *colon(:) notation* and the *arrow(->) notation*.
Do you want to learn more about [flow control statements](https://nkamphoa.com/flow-control-statements-in-java) in general? Check out [this article](https://nkamphoa.com/flow-control-statements-in-java).

## 7. References
1) [OCP Oracle Certified Professional Java SE 17 by Khalil A. Mughal and Vasily A. Strelnikov](https://amzn.to/3TbEp8D)
2) [Oracle Java Documentation](https://docs.oracle.com/javase/tutorial/)