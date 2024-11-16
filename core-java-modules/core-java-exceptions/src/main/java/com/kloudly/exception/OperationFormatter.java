package com.kloudly.exception;

public class OperationFormatter {

    public String format(char operator, double firstOperand, double secondOperand, double result){
        return firstOperand + " " + operator + " "+ secondOperand +" = " + result;
    }

    public String getDefaultResult(){
        return "1 + 1 = 2";
    }
}
