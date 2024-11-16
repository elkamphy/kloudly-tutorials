package com.kloudly.exception;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CalculatorTest {
    private static Calculator calculator;
    private static OperationFormatter formatter;

    @BeforeAll
    static void setUp(){
        formatter = mock(OperationFormatter.class);
        calculator = new Calculator(formatter);
    }

    @Test
    public void testInvalidUseOfMatchers() {
        //Given
        String expectedResult = "1 + 1 = 2";

        //Replace eq('+') with '+' to reproduce the exception
        when(formatter.format(eq('+'),anyDouble(),anyDouble(),anyDouble())).thenReturn(expectedResult);
        //When
        String result = calculator.calculate(1,1,'+');
        //Then
        assertEquals(expectedResult,result);
    }

    @Test
    public void testUnfinishedStubbing_missingThenReturn() {
        //Given
        String expectedResult = "1 + 1 = 2";

        //Remove the ".thenReturn(expectedResult)" to reproduce the exception
        when(formatter.format(eq('+'),anyDouble(),anyDouble(),anyDouble())).thenReturn(expectedResult);
        //When
        String result = calculator.calculate(1,1,'+');
        //Then
        assertEquals(expectedResult,result);
    }

    @Test
    public void testUnfinishedStubbing_beforeThenReturnIsComplete() {
        //Given
        String expectedResult = formatter.getDefaultResult();

        //Replace the "expectedResult" with "formatter.getDefaultResult()" to reproduce the exception
        when(formatter.format(eq('+'),anyDouble(),anyDouble(),anyDouble())).thenReturn(expectedResult);
        //When
        String result = calculator.calculate(1,1,'+');
        //Then
        assertEquals(expectedResult,result);
    }

}
