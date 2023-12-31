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

package com.priyakdey.parker.core.service;

import com.priyakdey.parker.core.model.ParkingSpace;
import com.priyakdey.parker.core.model.Vehicle;
import com.priyakdey.parker.exception.BadInputException;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Concrete implementation of {@link ParkingLotManager}.
 * <p>
 * Manages parking operations for vehicles, offering functionality to park vehicles
 * and vacate parking spaces. It acts as an interface between higher-level operations
 * and the parking lot's data structures.
 * </p>
 *
 * @author Priyak Dey
 */
public class ParkingLotManagerImpl implements ParkingLotManager {
    private final ParkingLot parkingLot;

    /**
     * Initializes a new instance of the ParkingManager with a given parking lot.
     *
     * @param parkingLot The parking lot this manager will manage.
     */
    public ParkingLotManagerImpl(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    /**
     * Attempts to park a vehicle in the parking lot.
     * <p>
     * If the parking lot has available spaces, the vehicle will be parked and
     * this method will return an Optional containing the parking space id
     * where the vehicle was parked. If the parking lot is full, the vehicle
     * will not be parked, and this method will return an empty Optional.
     * </p>
     *
     * @param vehicle The vehicle to be parked.
     * @return An Optional containing the assigned parking space id if the vehicle was successfully parked, or an empty Optional otherwise.
     */
    @Override
    public Optional<Integer> parkVehicle(Vehicle vehicle) {
        Optional<ParkingSpace> allottedParkingSpaceOptional = parkingLot.bookParkingSpace();
        if (allottedParkingSpaceOptional.isEmpty()) {
            return Optional.empty();
        }

        ParkingSpace allottedParkingSpace = allottedParkingSpaceOptional.get();
        allottedParkingSpace.setParkedVehicle(vehicle);
        vehicle.setParkedAt(allottedParkingSpace);

        return Optional.of(allottedParkingSpace.getId());
    }

    /**
     * Vacates a given parking space, freeing it up for other vehicles.
     * <p>
     * If there is a vehicle currently parked in the space, the vehicle will
     * be removed from the space. The space itself will be marked as vacant.
     * </p>
     *
     * @param registrationNumber The registration number of the car which is leaving.
     * @return The parking spot at which vehicle was parked.
     * @throws BadInputException if no such registration number is found in the parking lot.
     */
    @Override
    public String vacateParkingSpace(String registrationNumber) {
        Optional<ParkingSpace> optionalParkingSpace =
            parkingLot.getParkingSpaceByRegistrationNumber(registrationNumber);

        if (optionalParkingSpace.isEmpty()) {
            // TODO: Create another custom exception for handling this case
            throw new BadInputException(
                String.format("No vehicle with registration number %s is parked right now.",
                    registrationNumber));
        }

        ParkingSpace parkingSpace = optionalParkingSpace.get();
        parkingLot.vacateParkingSpace(parkingSpace);
        return Integer.toString(parkingSpace.getId());
    }

    /**
     * Returns the current status of the parking lot as a {@link TreeMap >
     * <p>
     * Returns a Map of occupied {@link ParkingSpace#getId()} to {@link Vehicle#getRegistrationNumber()},
     * sorted by ids.
     * </p>
     *
     * @return A Map of id of occupied {@link ParkingSpace} and parked {@link Vehicle} registtration number, sorted by ids.
     */
    @Override
    public TreeMap<Integer, String> status() {
        return parkingLot.getStatusForOccupiedSpaces();
    }
}
