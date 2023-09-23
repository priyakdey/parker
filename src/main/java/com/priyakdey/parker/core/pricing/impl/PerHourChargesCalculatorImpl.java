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

package com.priyakdey.parker.core.pricing.impl;

import com.priyakdey.parker.core.pricing.ChargesCalculator;
import com.priyakdey.parker.exception.BadInputException;

/**
 * Implementation of the {@link ChargesCalculator} that calculates parking fees
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
public class PerHourChargesCalculatorImpl implements ChargesCalculator {

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
