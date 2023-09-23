package com.priyakdey.parker.command;

/**
 * Represents a command that can be executed with specific arguments.
 *
 * <p>
 * This interface defines a contract for commands that can be invoked
 * with an array of string arguments. Implementations of this interface
 * can be used to encapsulate behavior associated with specific command-line
 * or console operations.
 * </p>
 *
 * @author Priyak Dey
 */
public interface Command {

    /**
     * Executes the encapsulated command action based on the provided arguments.
     *
     * <p>
     * Implementers should provide the specific behavior associated with
     * this command, utilizing the input arguments as necessary to perform
     * the operation. Implementations should also ensure proper argument
     * validation and handle possible runtime exceptions or errors.
     * </p>
     *
     * @param args Variable-length argument list representing the parameters
     *             required for the command's execution.
     *             Specific commands determine how these arguments are used.
     */
    void execute(String... args);
}
