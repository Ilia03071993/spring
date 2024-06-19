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
    void sub_correctResult_ifSubMoreThenOrEqualZero() {
        int n1 = 10;
        int n2 = 9;

        assertAll(
                () -> assertEquals(1, calculator.sub(n1, n2)),
                () -> assertEquals(0, calculator.sub(n1, n1))
        );
    }

    @Test
    void sub_throwArithmeticException_IfSubLessThenZero() {
        int n1 = 10;
        int n2 = 15;

        assertThrows(
                ArithmeticException.class,
                () -> calculator.sub(n1, n2));
    }

    @Test
    void div_throwArithmeticException_IfSecondParameterZero() {
        double n1 = 10;
        double n2 = 0;

        ArithmeticException ex = assertThrows(
                ArithmeticException.class,
                () -> calculator.div(n1, n2)
        );
        assertEquals("Cannot divide by zero", ex.getMessage());
    }

    @Test
    void div_correctResult_secondParameterNotZero() {
        double n1 = 30;
        double n2 = 10;
        double result = calculator.div(n1, n2);

        assertEquals(3, result);
    }

    @Test
    void multiply_thanParametersGreaterThenZero() {
        int n1 = 2;
        int n2 = 2;

        assertEquals(4, calculator.multiply(n1, n2));
    }

    @Test
    void multiply_thanParametersLessOrEqualsZero() {
        int n1 = 2;
        int n2 = 0;

        assertThrows(
                ArithmeticException.class,
                () -> calculator.multiply(n1, n2));
    }

    @Test
    void isPositive() {
        int positive = 10;
        int negative = -1;
        int zero = 0;

        assertAll(
                () -> assertTrue(calculator.isPositive(positive)),
                () -> assertFalse(calculator.isPositive(zero)),
                () -> assertFalse(calculator.isPositive(negative))
        );
    }
}