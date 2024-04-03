## 1. Introduction
Execution of a Java program normally occurs from **top to bottom**, statement after statement. However, there are situations where you want to run a code block only if a condition is true. At the same time, you may want to keep executing a block of statements as long as another condition is verified. **Flow control statements** are the solution to this problem. There are three main types of flow control statements: **Selection** Statements, **Iteration** Statements, and **Transfer** Statements. In this article, we will cover all the details about each category.

**Affiliate Disclosure**
_This post contains affiliate links. If you purchase through these links, I may earn a small commission at no additional cost to you. As an Amazon Associate, I earn from qualifying purchases._

## 2. Selection Statements
Java provides statements that allow your program to choose between multiple paths as it executes. The choice of which path to execute depends on the condition specified in the selection statement.
### 2.1. The Simple `if` Statement
This structure allows a statement or a bloc of statements to be executed if a condition is verified.  Its semantics is as follows:
```java
	if(condition)
    	if_body
```
Where *condition* is a boolean expression and *if_body* is a single statement or a block statement.
```java
        int i = 0;
        if(i < 5)
            System.out.println("i is less than 5");
```
It is a good practice to always use curly braces even for a single statement. Consider the following code:
```java
        int i = 0;
        int j = 5;
        if(i > 0)
            System.out.println("i is greater than 0");
            System.out.println("Division by zero will be performed: " +j /i);//is not part of the if statement
```
The code above will throw an ArithmeticException because the second statement is not part of the `if` body. This is why you should always use curly braces.
```java
        int i = 0;
        int j = 5;
        if(i > 0) {//start of if body
            System.out.println("i is greater than 0");
            System.out.println("Division by zero will NOT be performed: " + j / i);
        }//end of if body
```
### 2.2. The `if-else` Statement
Use the `if-else` if you need to choose between two or more execution paths. It has the following syntax:
```java
        if(condition)
            if_body
        else
            else_body
```
The *else_body* block is executed if the *condition* is evaluated to `false`. Just like for the simple `if`, it is recommended to always use curly braces to improve readability and prevent unexpected errors.
```java
        int i = 0;
        if(i < 5) {
            System.out.println("i is less than 5");
        }else{
            System.out.println("i is greater than 5");
        }
```
You may have more than two branches:
```java
        int i = 0;
        if(i < 5) {
            System.out.println("i is less than 5");
        }else if(i < 10){
            System.out.println("i is less than 10");
        }else{
            System.out.println("i is greater than 10");
        }
```
It is also possible to have nested `if-else`:
```java
        int i = 0;
        if(i < 5) {
            System.out.println("i is less than 5");
            if(i>3){
                System.out.println("i is less than 5 and greater than 3");
            }else{
                System.out.println("i is less than 5 and less than or equal to 3");
            }
        }
```

### 2.3. The `switch` Statement
You can read about [the switch construct](https://nkamphoa.com/switch-statements-and-switch-expressions-in-java) in [this dedicated article](https://nkamphoa.com/switch-statements-and-switch-expressions-in-java).
## 3. Iteration Statements
These Statements are used when you want to repeat the same processing as long as a certain condition is verified.
### 3.1. the `while` loop Statement
The `while` loop is used when you do not know a priori the number of iterations to do. Its syntax is as follows:
```java
        while(loop_condition)
            loop_body
```
Where **loop_condition** is a [boolean expression](https://nkamphoa.com/expressions-statements-and-blocks-in-java) and **loop_body** can be any valid [expression statement](https://nkamphoa.com/expressions-statements-and-blocks-in-java).
**Example with a Single Statement**
```java
        int i = 0;
        while(i < 5)
            i++;
```
There is no semicolon(;) after the condition expression `while(condition)`. Doing so will result in an empty loop body.
```java
        int i = 0;
        while(i < 5);//This semicolon creates an empty loop body
            i++;//This is not part of the loop body
```
It is a good practice to always use curly braces to improve readability.
```java
        int i = 0;
        while(i < 5) {
            i++;
        }
```
**Example with a Block Statement**
```java
        int i = 0;
        while(i<5) {//start of while loop
            System.out.println("Value of i : "+i);
            i++;
        }//end of while loop
```
The `while` Statement will start by evaluating the condition expression. if it is true, it will start executing the loop body. It will keep executing it as long as the condition is met.
As a consequence: 
> The loop body will be executed **zero to many** times.  

### 3.2. the `do-while` loop Statement
The `do-while` loop is similar to the `while` loop with a few differences (See below). Below are the semantics of the `do-while`:
```java
		do
            loop_body
        while(loop_condition)
```
**Example with a Simple Statement**
```java
        int i = 0;
        do
            i++;
        while(i < 5);
```
Note that there is a semicolon (;) after the `while(condition)`.
You cannot add multiple statements without using the curly braces. The following code will not compile:
```java
        int i = 0;
        do
            System.out.println("Value of i : "+i);
            i++;
        while(i < 5);
```
**Example with a Block Statement**
```java
        int i = 0;
        do {//start of do-while loop
            System.out.println("Value of i : " + i);
            i++;
        }while(i < 5);//end of do-while loop
```

Unlike the `while` statement, the `do-while` examines the loop condition after executing the loop body. It starts by executing the loop body and keeps executing it until the loop condition becomes false.
> The `do-while` statement is executed at least **once**.  

### 3.3. the `for(;;)` loop Statement
The `for` loop is the most common loop. It is used when the number of iterations is known in advance. Its syntax is as follows:
```java
		for(initialization; loop_condition; update_expression)
            loop_body
```
The *initialization* block is generally used to declare the variables which will control the execution of the loop. The *Condition* allows you to limit the number of iterations by indicating when to stop. It must be a Boolean expression. The *update* block allows you to update the loop variables. This ensures that the loop evolves and does not lead to an infinite loop. As with the `while` and `do-while` loops, the *loop_body* must be a [valid expression]().
Any `for(;;)` loop can be converted into a `while`by proceeding as follows:
```java
		initialization;
        while(loop_condition){
            loop_body;
            update_expression
         }
```
**Example with a Simple Statement**
```java
        for(int i=0; i < 5; i++)
            System.out.println("Value of i : " + i);
```
Just like with the `while` loop, it is advised to always use curly braces for readability and to prevent any unexpected behavior.
```java
        for(int i=0; i < 5; i++) {
            System.out.println("Value of i : " + i);
        }
```
**Example with a Block Statement**
```java
        for(int i=0; i < 5; i++) {//start of for loop
            System.out.println("Value of i : " + i);
            System.out.println("Another Statement in the same block");
        }//end of for loop
```
**Example with Multiple Initialization Expressions**
You may add multiple expressions in the initialization section:
```java
        for(int i=0, j=1, k=2; i < 5; i++) {
            System.out.println("Value of i : " + i);
        }
```
**Example with Multiple Update Expressions**
You can also add multiple expressions in the *update_expression* section:
```java
        for(int i=0, j=1, k=2; i < 5; i++,j--,k++) {
            System.out.println("Value of i : " + i);
        }
```
**Example of an Infinite Loop**
All the sections of the `for(;;)` loop are optional. Leaving all of them empty will result in an infinite loop:
```java
        for(;;) {
            System.out.println("This is an infinite loop");
        }
```
### 3.4. the `for(:)` loop Statement
This loop is also called an *enhanced for loop*. It makes it easier to iterate on array or Collection objects. Its syntax is as follows:
```java
        for(itemType item : items){
            loop_body
        }
```
*itemType* is the data type of the elements in the *items* structure. The object *items* must either be an array or an object that implements the [Iterable](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html) interface. An example with  an array of integers is shown below:
```java
        int[] items = {1,2,3,4,5};
        int sum = 0;
        for(int item : items){
            sum += item;
        }
```
This `for(:)` loop is more compact than the `for(;;)` loop we saw earlier . You may consider using this enhanced form when you don't need to update the elements in the array (or the collection).
## 4. Transfer Statements
### 4.1. Label Statements
A statement may have a label:
```java
	label: statement;
```
*label* can be any valid Java identifier. Labels have their own namespace, so they cannot conflict with the names of packages, classes, methods, variables, etc.
Below is an example of a label:
```java
        myLabel:{
            int i = 0;
            System.out.println(i + 1);
        }
```
A statement can have multiple labels:
```java
        labelA:
            labelB:
                System.out.println("I belong to labelA and labelB");
```
A declaration statement cannot have a label:
```java
        myLabel:
            int i = 0;//Compile-time error
```
> The label doesn't change anything to the behaviour of a statement. A label statement is executed just as if it were unlabeled.  

### 4.2. The `break` Statement
There are two forms of `break`: the labeled version and the unlabeled version.
#### The unlabeled `break`
This form terminates the execution of the following statements: switch, while, do-while, for(;;), and for(:). Here is the syntax:
```java
	break;
```
Below is an example with a `for`loop:
```java
        for(int i=0; i < 5; i++) {
            if(i==2)
                break;//stop the loop and go to (1)
            System.out.println("Value of i : " + i);
        }
        System.out.println("I'm out of the loop");//1
```
> In case of nested loops, the unlabeled `break` applies to the innermost loop.  

#### The labeled `break`
The labeled `break`statement can be used to terminate any labeled that contains the break statement.
its semantic is as follows:
```java
	break myLabel;
```
Here is an example:
```
        int i = 0;
        myLabel:{
            //
            if(i==2)
                break myLabel;//get out of the label myLabel and go to (1)
            System.out.println("Value of i : " + i);
            i++;
            //
        }
        System.out.println("I'm out of the label");//1
```

### 4.3. The `continue` Statement
Just like the `break` statements, there are two forms of `continue`: the labeled version and the unlabeled version.
#### The unlabeled `continue`
The unlabeled `continue` works similarly to the unlabeled `break`. However, instead of stopping the execution, it will move the execution to the next iteration in case of `for(;;)` and `for(::)` loops. For `while` and `do-while` loops, it will move the execution to the *loop condition* evaluation.

An example with the `while` loop is shown below:
```java
        int i = 0;
        while(i<5) {//start of while loop
            if(i==2) {
                i++;
                continue;
            }
            System.out.println("Value of i : "+i);//This will not be printed for i == 2
            i++;
        }//end of while loop
```
> In case of nested loops, the unlabeled `continue` applies to the innermost loop.  

#### The labeled `continue`
The labeled *continue* statement skips the current iteration of an outer loop marked with the given label.
```java
        myLabel:
        for (int i=0; i< 5; i++){
            System.out.println("In the first loop i="+i);
            for(int j=0; j< 5; j++){
                if(j==2)
                    continue myLabel;
                System.out.println("In the second loop j="+j);
            }
        }
```
Without the label, this `continue` statement will apply to the innermost statement.
### 4.4. The `return` Statement
The `return` statement is used to stop the execution of a method and hand over to the caller. The syntax depends on the return type of the method.
#### A Method without a Return Value
When the return type of the method is `void`, you don't need to use the `return` statement. You may however use it if you want to end the method execution when a specific condition is met.
```java
    public void displayArray(int[] arr){
        if(arr == null)
            return;
        for(int item : arr){
            System.out.println(item);
        }
    }
```
In the code above, we are using the `return` statement to stop the execution of the `displayArray()` method if the provided array is null. This check prevents us from having a NullPointerException at runtime.
#### A Method with a Return Value
The `return` statement is **mandatory** in this case if the method is not terminated explicitly.
```java
    public int performSum(int[] arr){
        if(arr == null)
            return 0;//Removing the 0 will lead to a compile-time error
        int sum = 0;
        for(int item : arr){
            sum += item;
        }
        return sum;
    }
```
> Throwing an exception is a valid way of terminating a program. While doing that you don't have to use the `return` statement.  

## 5. Conclusion
In conclusion, mastering control flow statements in Java is crucial for you to run your programs smoothly. In this article, you learned the different statements that allow you to control the execution flow of your Java program.

## 6. References
1) [OCP Oracle Certified Professional Java SE 17 by Khalil A. Mughal and Vasily A. Strelnikov](https://amzn.to/3TbEp8D)
2) [Oracle Java Documentation](https://docs.oracle.com/javase/tutorial/)