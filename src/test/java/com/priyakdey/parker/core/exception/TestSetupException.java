package com.priyakdey.parker.core.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class TestSetupException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7212306071902937075L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public TestSetupException() {
        super();
    }
}
