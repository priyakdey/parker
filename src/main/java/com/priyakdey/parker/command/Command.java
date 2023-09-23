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
     * Executes the command with the specified arguments.
     *
     * @param args An array of string arguments to be used when executing the command.
     */
    void execute(String[] args);
}
