/*
 * Copyright(c) 2023 Priyak Dey <https://priyakydey.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.priyakdey.parker.exception;

import java.io.Serial;

/**
 * Thrown to indicate that an invalid or inappropriate input has been provided.
 * <p>
 * This exception is used throughout the Parker application to signal that a
 * function or method has been passed an argument or received input that
 * doesn't conform to the expected format or value.
 * </p>
 * For example, it could be thrown when user input for a parking lot's size is
 * negative, non-numeric, or exceeds the allowed maximum.
 * <p>
 * Catching this exception can allow for graceful handling of input errors,
 * prompting the user for correct input or logging the error for future reference.
 * </p>
 *
 * @author Priyak Dey
 */
public class BadInputException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7687825291093205223L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BadInputException(String message) {
        super(message);
    }
}
