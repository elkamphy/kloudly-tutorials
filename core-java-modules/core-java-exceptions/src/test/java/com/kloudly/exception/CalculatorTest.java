package com.kloudly.exception;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        when(formatter.format(eq('+'),anyDouble(),anyDouble(),anyDouble())).thenReturn(expectedResult);
        //When
        String result = calculator.calculate(1,1,'+');
        //Then
        assertEquals(expectedResult,result);
    }
}
