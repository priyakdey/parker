package com.priyakdey.parker.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Validator")
class ValidatorTest {

    @DisplayName("Should match with isDigits()")
    @Test
    void test_isDigit_shouldMatch() {
        assertTrue(Validator.isDigit("6"));
        assertTrue(Validator.isDigit("16"));
        assertTrue(Validator.isDigit("69"));
        assertTrue(Validator.isDigit("256"));
    }

    @DisplayName("Should not match with isDigits()")
    @Test
    void test_isDigit_shouldNotMatch() {
        assertFalse(Validator.isDigit("1-256"));
        assertFalse(Validator.isDigit("abc"));
        assertFalse(Validator.isDigit("abc-12-3"));
        assertFalse(Validator.isDigit("987-abc-12-3"));
    }

    @DisplayName("Should match with isRegistrationNumber()")
    @Test
    void test_isRegistrationNumber_shouldMatch() {
        assertTrue(Validator.isRegistrationNumber("KA-01-P-333"));
        assertTrue(Validator.isRegistrationNumber("DL-12-AA-9999"));
        assertTrue(Validator.isRegistrationNumber("KA-01-HH-1234"));
        assertTrue(Validator.isRegistrationNumber("KA-01-BB-0001"));
        assertTrue(Validator.isRegistrationNumber("DL-12-AA-9999"));
    }

    @DisplayName("Should not match with isRegistrationNumber()")
    @Test
    void test_isRegistrationNumber_shouldNotMatch() {
        assertFalse(Validator.isRegistrationNumber("ABC-12-AA-1234"));
        assertFalse(Validator.isRegistrationNumber("12-AA-1234"));
        assertFalse(Validator.isRegistrationNumber("1234"));
        assertFalse(Validator.isRegistrationNumber("KA-01-0001"));
        assertFalse(Validator.isRegistrationNumber("DL-1ABC-AA-9999"));
    }
}