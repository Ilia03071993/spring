package com.example.springjunit.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculatorTest {
    @Autowired
    Calculator calculator;

    @Test
    void add() {
        int n1 = 20;
        int n2 = 30;

        int result = calculator.add(n1, n2);
        assertEquals(50, result);
    }

    @Test
    void subCorrectResultIfSubMoreThenOrEqualZero() {
        assertAll(
                () -> assertEquals(1, calculator.sub(10, 9)),
                () -> assertEquals(0, calculator.sub(10, 10))
        );
    }

    @Test
    void subThrowArithmeticExceptionIfSubLessThenZero() {
        int n1 = 10;
        int n2 = 15;

        assertThrows(
                ArithmeticException.class,
                () -> calculator.sub(n1, n2));
    }

    @Test
    void divThrowArithmeticExceptionIfSecondParameterZero() {
        double n1 = 10;
        double n2 = 0;

        ArithmeticException ex = assertThrows(
                ArithmeticException.class,
                () -> calculator.div(n1, n2)
        );
        assertEquals("Cannot divide by zero", ex.getMessage());
    }

    @Test
    void divCorrectResultSecondParameterNotZero() {
        double n1 = 30;
        double n2 = 10;

        double result = calculator.div(n1, n2);
        assertEquals(3, result);
    }

    @Test
    void multiplyResultThanParametersGreaterThenZero() {
        int n1 = 2;
        int n2 = 2;

        assertEquals(4, calculator.multiply(n1, n2));
    }

    @Test
    void multiplyResultThanParametersLessOrEqualsZero() {
        int n1 = 2;
        int n2 = 0;

        assertThrows(
                ArithmeticException.class,
                () -> calculator.multiply(n1, n2));
    } //?

    @Test
    void isPositiveTrueIfParameterGreaterThenZero() {
        int n = 10;

        assertTrue(calculator.isPositive(n));
    }

    @Test
    void isPositiveFalseIfParameterEqualsZero() {
        int n = 0;

        assertFalse(calculator.isPositive(n));
    }

    @Test
    void isPositiveFalseIfParameterLessThenZero() {
        int n = -2;

        assertFalse(calculator.isPositive(n));
    }
}