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

import static com.priyakdey.parker.handler.InsufficientArgsExceptionHandler.checkArgsLength;

import com.priyakdey.parker.exception.BadInputException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Priyak Dey
 */
public class CommandInvoker {
    private final Map<String, Command> commandMap;

    public CommandInvoker() {
        commandMap = new HashMap<>();
    }

    public void registerCommand(String cmd, Command command) {
        commandMap.put(cmd, command);
    }

    public void execute(String[] args) {
        checkArgsLength(args, 1);

        String cmd = args[0].trim();
        if (cmd.isEmpty() || !commandMap.containsKey(cmd)) {
            throw new BadInputException("Invalid command.");
        }

        commandMap.get(cmd).execute(Arrays.copyOfRange(args, 1, args.length));
    }
}
