package com.priyakdey.parker.common;

import java.util.regex.Pattern;

/**
 * {@link Validator} provides common validation functions.
 *
 * @author Priyak Dey
 */
public final class Validator {

    private static final Pattern IS_DIGIT = Pattern.compile("^\\d+$");

    private static final Pattern IS_REG_NUM =
        Pattern.compile("^[A-Z]{2}-\\d{2}-[A-Z]{1,2}-\\d{3,4}$");

    private Validator() {
    }

    /**
     * Returns if input is all digits.
     *
     * @param input input string
     * @return true if input is all digits, false otherwise
     */
    public static boolean isDigit(String input) {
        return IS_DIGIT.matcher(input).matches();
    }

    /**
     * Returns if input is a valid registration number.
     *
     * @param input input string
     * @return true if input is valid registration number else false.
     */
    public static boolean isRegistrationNumber(String input) {
        return IS_REG_NUM.matcher(input).matches();
    }

}
