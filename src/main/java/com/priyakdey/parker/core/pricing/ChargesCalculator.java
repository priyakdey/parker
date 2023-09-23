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

package com.priyakdey.parker.core.pricing;

/**
 * Represents a mechanism to compute the parking fee based on the number of hours a vehicle has been parked.
 *
 * <p>
 * Implementations of this interface will define the logic to calculate the parking fee
 * for a given number of hours. This allows for flexibility in defining different pricing
 * strategies for parking.
 * </p>
 * <p>
 * Example:
 * <pre>
 *     ParkingFeeCalculator calculator = ...;
 *     Number fee = calculator.computePrice(5);  // Calculate fee for 5 hours of parking.
 * </pre>
 *
 * @author Priyak Dey
 */
public interface ChargesCalculator {

    /**
     * Computes the parking fee for the specified number of hours.
     *
     * @param hoursParked The number of hours for which the parking fee should be computed.
     * @return The computed parking fee as a {@link Number}. Implementations might return fee as
     * an integer, decimal, or other numerical representations.
     */
    Number computePrice(int hoursParked);
}

