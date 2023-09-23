package com.priyakdey.parker.core.pricing.impl;

import com.priyakdey.parker.core.pricing.ParkingFeeCalculator;
import com.priyakdey.parker.exception.BadInputException;

/**
 * Implementation of the {@link ParkingFeeCalculator} that calculates parking fees
 * based on a per-hour pricing model.
 *
 * <p>
 * This implementation uses a flat rate for the first few hours (as defined by
 * {@link #FLAT_RATE_DURATION_HOURS}) and then charges a specified rate for every additional
 * hour.
 * </p>
 * <p>
 * Example:
 * <pre>
 *     ParkingFeeCalculator calculator = new PerHourFeeCalculatorImpl();
 *     Number fee = calculator.computePrice(3);  // Calculate fee for 3 hours of parking.
 * </pre>
 *
 * @author Priyak Dey
 */
public class PerHourFeeCalculatorImpl implements ParkingFeeCalculator {

    /**
     * The fixed charge applicable for parking durations up to FLAT_RATE_DURATION_HOURS.
     */
    private static final int FLAT_RATE_CHARGE = 10;

    /**
     * The number of hours for which the flat rate (FLAT_RATE_CHARGE) applies.
     */
    private static final int FLAT_RATE_DURATION_HOURS = 2;


    /**
     * Charge applied for every hour after the INITIAL_HOURS.
     */
    private static final int PER_HOUR_CHARGE = 10;


    /**
     * Computes the parking fee for the specified number of hours.
     *
     * @param hoursParked The number of hours for which the parking fee should be computed.
     * @return The computed parking fee as a {@link Number}. Implementations might return fee as
     * an integer, decimal, or other numerical representations.
     */
    @Override
    public Number computePrice(int hoursParked) {
        if (hoursParked < 0) {
            throw new BadInputException("Hours parked cannot be negative");
        }

        int overheadHours =
            hoursParked > FLAT_RATE_DURATION_HOURS ? hoursParked - FLAT_RATE_DURATION_HOURS : 0;

        return FLAT_RATE_CHARGE + overheadHours * PER_HOUR_CHARGE;
    }
}
