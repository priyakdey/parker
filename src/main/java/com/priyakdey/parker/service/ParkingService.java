package com.priyakdey.parker.service;

import com.priyakdey.parker.core.model.ParkingCharge;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Provides the primary services for parking and vehicle management.
 *
 * <p>
 * This service defines methods to park a vehicle, vacate a parking spot,
 * and retrieve the current status of the parking area.
 * </p>
 * <p>
 * Example:
 * <pre>
 *     ParkingService parkingService = // implementation;
 *     Optional<Integer> parkingSpot = parkingService.park("ABC-123");
 *     ParkingCharge charge = parkingService.leave("ABC-123", 3);  // 3 hours of parking
 *     TreeMap<Integer, String> currentStatus = parkingService.status();
 * </pre>
 *
 * @author Priyak Dey
 */
public interface ParkingService {

    /**
     * Parks a vehicle and returns the allocated parking space id.
     *
     * @param registrationNumber The registration number of the vehicle to be parked.
     * @return An {@link Optional} wrapping the parking space id if parking is successful, or an empty Optional if parking failed.
     */
    Optional<Integer> park(String registrationNumber);

    /**
     * Vacates the parking space for the specified vehicle and calculates the parking charges.
     *
     * @param registrationNumber The registration number of the vehicle that's leaving.
     * @param hoursParked        The number of hours the vehicle was parked.
     * @return The {@link ParkingCharge} associated with the vehicle's stay duration.
     */
    ParkingCharge leave(String registrationNumber, int hoursParked);

    /**
     * Retrieves the current status of the parking area.
     *
     * @return A {@link TreeMap} where the keys are occupied parking space ids and the values are the registration numbers of the parked vehicles.
     */
    SortedMap<Integer, String> status();

}
