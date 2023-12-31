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
