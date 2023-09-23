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
import com.priyakdey.parker.core.pricing.ChargesCalculator;
import com.priyakdey.parker.core.pricing.impl.PerHourChargesCalculatorImpl;
import com.priyakdey.parker.core.service.ParkingLot;
import com.priyakdey.parker.core.service.ParkingLotManager;
import com.priyakdey.parker.core.service.ParkingLotManagerImpl;
import com.priyakdey.parker.exception.BadInputException;
import com.priyakdey.parker.service.ParkingService;
import com.priyakdey.parker.service.impl.ParkingServiceImpl;

/**
 * A command that represents the action of creating a parking lot.
 *
 * <p>
 * This command, when executed, will handle the logic and operations necessary
 * to set up and initialize a new parking lot. The specifics of its operation
 * are determined by the arguments passed in during the execution.
 * </p>
 *
 * @author Priyak Dey
 */
public class CreateParkingLotCommand implements Command {

    private static final String MSG_TMPL = "Created parking lot with %d slots%n";

    /**
     * Executes the command to initialize a new parking lot based on provided arguments.
     *
     * <p>
     * This method is tasked with interpreting the given arguments and then setting up a parking
     * lot accordingly. This may involve establishing its capacity, setting up individual parking
     * slots, and other necessary initial configurations.
     * </p>
     *
     * @param args An array of string arguments that provide configurations or parameters for the
     *             parking lot creation. For example, one might expect a capacity argument indicating
     *             how many vehicles the parking lot should be able to accommodate.
     */
    @Override
    public void execute(String... args) {
        checkArgsLength(args, 1);

        String input = args[0].trim();
        if (!Validator.isDigit(input)) {
            throw new BadInputException("Invalid `capacity`, expecting a real number");
        }

        int capacity = Integer.parseInt(input);
        init(capacity);
        System.out.printf(MSG_TMPL, capacity);
    }

    private void init(int capacity) {
        ParkingLot parkingLot = ParkingLot.withCapacity(capacity);
        ParkingLotManager parkingLotManager = new ParkingLotManagerImpl(parkingLot);

        ChargesCalculator chargesCalculator = new PerHourChargesCalculatorImpl();

        ParkingService parkingService =
            new ParkingServiceImpl(parkingLotManager, chargesCalculator);

        // push this to context
        ApplicationContext ctx = ApplicationContext.getInstance();
        ctx.put(parkingService);
    }
}
