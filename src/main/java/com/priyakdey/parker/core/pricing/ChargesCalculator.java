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

