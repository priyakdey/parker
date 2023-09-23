package com.priyakdey.parker.handler;

import com.priyakdey.parker.exception.BadInputException;

/**
 * @author Priyak Dey
 */
public final class InsufficientArgsExceptionHandler {

    private InsufficientArgsExceptionHandler() {
    }

    public static void checkArgsLength(String[] args, int expected) {
        if (args.length < expected) {
            throw new BadInputException("Insufficient number of arguments.");
        }
    }

}
