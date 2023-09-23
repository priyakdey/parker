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

package com.priyakdey.parker;

import com.priyakdey.parker.command.CommandInvoker;
import com.priyakdey.parker.command.impl.CreateParkingLotCommand;
import com.priyakdey.parker.command.impl.LeaveCommand;
import com.priyakdey.parker.command.impl.ParkCommand;
import com.priyakdey.parker.command.impl.StatusCommand;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Priyak Dey
 */
public class ParkerApplication {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("ERROR: No input file provided");
            System.exit(1);
        }

        String filepath = args[0];
        Path path = Path.of(filepath);

        try {
            if (Files.notExists(path) || Files.isDirectory(path) || Files.size(path) == 0) {
                throw new IOException("Bad input file. Please re-check");
            }
        } catch (IOException ex) {
            System.err.printf("ERROR: Error occurred while reading file stats. Details = %s%n",
                ex.getMessage());
            System.exit(1);
        }

        File file = path.toFile();
        List<String> lines = readLinesFromFile(file);
        CommandInvoker commandInvoker = setupCommandInvoker();
        lines.forEach(line -> {
            String[] arguments = line.trim().split("\\s+");
            commandInvoker.execute(arguments);
        });
    }

    private static List<String> readLinesFromFile(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            System.err.printf("ERROR: Cannot read input file. Details = %s%n", ex.getMessage());
            System.exit(1);
        }

        return lines;
    }

    private static CommandInvoker setupCommandInvoker() {
        CommandInvoker commandInvoker = new CommandInvoker();
        commandInvoker.registerCommand("create_parking_lot", new CreateParkingLotCommand());
        commandInvoker.registerCommand("park", new ParkCommand());
        commandInvoker.registerCommand("leave", new LeaveCommand());
        commandInvoker.registerCommand("status", new StatusCommand());
        return commandInvoker;
    }


}
