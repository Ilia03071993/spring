package com.example.springjunit.util;

import org.springframework.stereotype.Component;

@Component
public class Calculator {
    public int add(int n1, int n2) {
        return n1 + n2;
    }

    public double div(double n1, double n2) {
        if (n2 == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
/* for example
        if (n2 == 1) {
            throw new ArithmeticException("Cannot divide by one");
        }
**/
        return n1 / n2;
    }

    public int multiply(int n1, int n2) {
        int result = 0;
        if (isPositive(n1) && isPositive(n2)) {
            result = n1 * n2;
        } else {
            throw new ArithmeticException("Result is always zero when multiplied by zero");
        }
        return result;
    }

    public int sub(int n1, int n2) {
        int result = n1 - n2;
        if (result < 0) {
            throw new ArithmeticException();
        }
        return result;
    }

    public boolean isPositive(int n) {
        return n > 0;
    }
}