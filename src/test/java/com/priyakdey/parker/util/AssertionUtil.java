package com.priyakdey.parker.util;

import java.util.function.Supplier;

/**
 * @author Priyak Dey
 */
public final class AssertionUtil {

    private AssertionUtil() {
    }

    public static Supplier<String> assertionMsg(Object expected, Object actual) {
        return () -> String.format("Expected %s, but got %s%n", expected.toString(),
            actual.toString());
    }

    public static Supplier<String> assertionExceptionMsg(Class<? extends Exception> clazz) {
        return () -> String.format("Expected %s to be thrown%n", clazz.getCanonicalName());
    }

}
