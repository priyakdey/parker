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

package com.priyakdey.parker.command.impl;

import static com.priyakdey.parker.handler.InsufficientArgsExceptionHandler.checkArgsLength;

import com.priyakdey.parker.command.Command;
import com.priyakdey.parker.common.Validator;
import com.priyakdey.parker.context.ApplicationContext;
import com.priyakdey.parker.exception.BadInputException;
import com.priyakdey.parker.service.ParkingService;
import java.util.Optional;

/**
 * A command that represents the action of a vehicle parking in the parking lot.
 *
 * <p>
 * This command, when executed, will handle the necessary operations to
 * allocate a parking space for a vehicle entering the lot.
 * </p>
 *
 * @author Priyak Dey
 */
public class ParkCommand implements Command {

    private static final String PARKING_FULL_MSG = "Sorry, parking lot is full";
    private static final String PARKING_ALLOCATED_MSG = "Allocated slot number: %d%n";

    /**
     * Executes the command with the specified arguments to facilitate the parking process of a vehicle.
     *
     * <p>
     * This method will typically handle operations such as identifying an available parking space,
     * marking that space as occupied, logging the arrival time, and other relevant tasks.
     * </p>
     *
     * @param args An array of string arguments to be used when executing the command. Expected to contain
     *             details relevant to the parking process, like vehicle registration number.
     */
    @Override
    public void execute(String... args) {
        checkArgsLength(args, 1);

        String registrationNumber = args[0].trim();
        if (!Validator.isRegistrationNumber(registrationNumber) || registrationNumber.isEmpty()) {
            throw new BadInputException("Bad registration number");
        }

        ApplicationContext ctx = ApplicationContext.getInstance();
        ParkingService parkingService = ctx.get(ParkingService.class);
        Optional<Integer> optional = parkingService.park(registrationNumber);

        if (optional.isEmpty()) {
            System.out.println(PARKING_FULL_MSG);
            return;
        }

        int parkingId = optional.get();
        System.out.printf(PARKING_ALLOCATED_MSG, parkingId);
    }
}
