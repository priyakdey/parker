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
