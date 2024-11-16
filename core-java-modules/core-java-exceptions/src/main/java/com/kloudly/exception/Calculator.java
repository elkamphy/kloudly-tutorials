package com.kloudly.exception;

public class Calculator {
    private OperationFormatter formatter;
    public Calculator(OperationFormatter formatter){
        this.formatter = formatter;
    }
    public String calculate(double firstOperand, double secondOperand, char operator){
        double result = 0d;
        switch (operator){
            case '+':{
                result = add(firstOperand,secondOperand);
                return this.formatter.format(operator,firstOperand,secondOperand,result);
            }
            case '-':{
                result = subtract(firstOperand,secondOperand);
                return this.formatter.format(operator,firstOperand,secondOperand,result);
            }
            case '*':{
                result = multiply(firstOperand,secondOperand);
                return this.formatter.format(operator,firstOperand,secondOperand,result);
            }
            case ':':{
                result = divide(firstOperand,secondOperand);
                return this.formatter.format(operator,firstOperand,secondOperand,result);
            }
            default:
                throw new IllegalArgumentException("Unsupported operation :"+operator);
        }

    }

    private double divide(double firstOperand, double secondOperand) {
        if(secondOperand == 0)
            throw new IllegalArgumentException("Second argument must not be zero!");
        return firstOperand / secondOperand;
    }

    private double multiply(double firstOperator, double secondOperand) {
        return firstOperator * secondOperand;
    }

    private double subtract(double firstOperand, double secondOperand) {
        return firstOperand - secondOperand;
    }

    private double add(double firstOperand, double secondOperand) {
        return firstOperand + secondOperand;
    }
}
