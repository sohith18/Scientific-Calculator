package org.calculatorapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    public void testSqRoot_withPositiveNumber() {
        assertEquals(5.0, calculator.sqRoot(25.0), 1e-9);
    }

    @Test
    public void testSqRoot_withZero() {
        assertEquals(0.0, calculator.sqRoot(0), 1e-9);
    }

    @Test
    public void testSqRoot_withNegativeNumber() {
        assertTrue(Double.isNaN(calculator.sqRoot(-4)));
    }

    @Test
    public void testFactorial_ofZero() {
        assertEquals(1, calculator.factorial(0));
    }

    @Test
    public void testFactorial_ofPositiveNumber() {
        assertEquals(120, calculator.factorial(5));
        assertEquals(3628800, calculator.factorial(10));
    }

    @Test
    public void testFactorial_negative_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> calculator.factorial(-1));
        assertEquals("Number must be non-negative", ex.getMessage());
    }

    @Test
    public void testNaturalLog_positiveNumber() {
        assertEquals(Math.log(10), calculator.naturalLog(10), 1e-9);
    }

    @Test
    public void testNaturalLog_one() {
        assertEquals(0.0, calculator.naturalLog(1), 1e-9);
    }

    // Updated: zero should throw per Calculator.naturalLog
    @Test
    public void testNaturalLog_zero_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> calculator.naturalLog(0));
        assertEquals("Number must be positive", ex.getMessage());
    }

    @Test
    public void testNaturalLog_negative_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> calculator.naturalLog(-5));
        assertEquals("Number must be positive", ex.getMessage());
    }

    @Test
    public void testPower_positiveBase_positiveExponent() {
        assertEquals(8.0, calculator.power(2, 3), 1e-9);
    }

    @Test
    public void testPower_positiveBase_zeroExponent() {
        assertEquals(1.0, calculator.power(5, 0), 1e-9);
    }

    @Test
    public void testPower_negativeBase_oddExponent() {
        assertEquals(-8.0, calculator.power(-2, 3), 1e-9);
    }

    @Test
    public void testPower_negativeBase_evenExponent() {
        assertEquals(4.0, calculator.power(-2, 2), 1e-9);
    }

    @Test
    public void testPower_zeroBase_positiveExponent() {
        assertEquals(0.0, calculator.power(0, 4), 1e-9);
    }

    @Test
    public void testPower_zeroBase_zeroExponent() {
        // Math.pow(0, 0) returns 1.0
        assertEquals(1.0, calculator.power(0, 0), 1e-9);
    }
}
