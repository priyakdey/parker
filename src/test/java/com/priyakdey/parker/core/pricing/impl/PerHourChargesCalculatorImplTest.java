package com.priyakdey.parker.core.pricing.impl;

import static com.priyakdey.parker.util.AssertionUtil.assertionExceptionMsg;
import static com.priyakdey.parker.util.AssertionUtil.assertionMsg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.priyakdey.parker.core.pricing.ChargesCalculator;
import com.priyakdey.parker.exception.BadInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PerHourFeeCalculatorImpl")
class PerHourChargesCalculatorImplTest {

    private ChargesCalculator chargesCalculator;

    public PerHourChargesCalculatorImplTest() {
        this.chargesCalculator = new PerHourChargesCalculatorImpl();
    }

    @DisplayName("Should return base price - less than 2 hours")
    @Test
    void test_computePrice_shouldReturnBasePrice() {
        Number charges = chargesCalculator.computePrice(2);
        int actual = charges.intValue();
        assertEquals(10, actual, assertionMsg(10, actual));
    }

    @DisplayName("Should return computed price - more than 2 hours")
    @Test
    void test_computePrice_shouldReturnComputedPrice() {
        Number charges = chargesCalculator.computePrice(4);
        int actual = charges.intValue();
        int expected = 10 + (4 - 2) * 10;
        assertEquals(expected, actual, assertionMsg(expected, actual));
    }

    @DisplayName("Should throw BadInputException")
    @Test
    void test_computePrice_shouldThrowException() {
        assertThrows(BadInputException.class, () -> chargesCalculator.computePrice(-1),
            assertionExceptionMsg(BadInputException.class));
    }

}