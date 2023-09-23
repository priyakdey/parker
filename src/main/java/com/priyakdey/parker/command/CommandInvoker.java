package com.priyakdey.parker.command;

import com.priyakdey.parker.exception.BadInputException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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
        if (args.length < 1) {
            throw new BadInputException("Insufficient Arguments.");
        }

        String cmd = args[0].trim();
        if (cmd.isEmpty() || !commandMap.containsKey(cmd)) {
            throw new BadInputException("Invalid command.");
        }

        commandMap.get(cmd).execute(Arrays.copyOfRange(args, 1, args.length));
    }
}
