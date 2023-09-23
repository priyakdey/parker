package com.priyakdey.parker.command.impl;

import static com.priyakdey.parker.handler.InsufficientArgsExceptionHandler.checkArgsLength;

import com.priyakdey.parker.command.Command;
import com.priyakdey.parker.common.Validator;
import com.priyakdey.parker.context.ApplicationContext;
import com.priyakdey.parker.core.model.ParkingCharge;
import com.priyakdey.parker.exception.BadInputException;
import com.priyakdey.parker.service.ParkingService;

/**
 * Represents the command to process a vehicle's departure from the parking lot.
 *
 * <p>
 * Upon execution, this command facilitates the necessary operations associated
 * with a vehicle leaving the parking facility. Operations might include marking
 * a parking slot as vacant, logging departure details, or computing parking charges.
 * </p>
 *
 * @author Priyak Dey
 */
public class LeaveCommand implements Command {

    private static final String LEAVE_MSG_TMPL =
        "Registration number %s with Slot Number %s is free with Charge %s%n";

    private static final String VEHICLE_NOT_FOUND_MSG_TMPL = "Registration number %s not found%n";

    /**
     * Executes the command to process the departure of a vehicle from the parking lot.
     *
     * <p>
     * This method takes in the required arguments to facilitate the vehicle's departure,
     * such as the vehicle's registration number or the parking slot identifier. Based on these
     * arguments, it carries out the necessary operations to successfully log the vehicle's
     * departure and compute any associated charges if applicable.
     * </p>
     *
     * @param args An array of string arguments containing necessary details for processing the departure.
     *             For instance, it could include the vehicle's registration number.
     */
    @Override
    public void execute(String... args) {
        checkArgsLength(args, 2);

        String registrationNumber = args[0].trim();
        String hoursParkedS = args[1].trim();

        if (!Validator.isRegistrationNumber(registrationNumber) ||
            !Validator.isDigit(hoursParkedS)) {
            throw new BadInputException("Incorrect format of input.");
        }

        ApplicationContext ctx = ApplicationContext.getInstance();
        ParkingService parkingService = ctx.get(ParkingService.class);
        try {
            ParkingCharge parkingCharge =
                parkingService.leave(registrationNumber, Integer.parseInt(hoursParkedS));
            System.out.printf(LEAVE_MSG_TMPL, parkingCharge.registrationNumber(),
                parkingCharge.hoursParked(), parkingCharge.charge());
        } catch (BadInputException ex) {
            System.out.printf(VEHICLE_NOT_FOUND_MSG_TMPL, registrationNumber);
        }

    }
}
