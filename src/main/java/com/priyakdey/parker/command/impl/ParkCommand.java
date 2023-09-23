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
