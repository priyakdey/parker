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

package com.priyakdey.parker.service.impl;

import com.priyakdey.parker.core.model.ParkingCharge;
import com.priyakdey.parker.core.model.Vehicle;
import com.priyakdey.parker.core.pricing.ChargesCalculator;
import com.priyakdey.parker.core.service.ParkingLotManager;
import com.priyakdey.parker.service.ParkingService;
import java.util.Collections;
import java.util.Optional;
import java.util.SortedMap;

/**
 * @author Priyak Dey
 */
public class ParkingServiceImpl implements ParkingService {

    private final ParkingLotManager parkingLotManager;
    private final ChargesCalculator chargesCalculator;

    public ParkingServiceImpl(ParkingLotManager parkingLotManager,
                              ChargesCalculator chargesCalculator) {
        this.parkingLotManager = parkingLotManager;
        this.chargesCalculator = chargesCalculator;
    }

    /**
     * Parks a vehicle and returns the allocated parking space id.
     *
     * @param registrationNumber The registration number of the vehicle to be parked.
     * @return An {@link Optional} wrapping the parking space id if parking is successful, or an empty Optional if parking failed.
     */
    @Override
    public Optional<Integer> park(String registrationNumber) {
        Vehicle vehicle = new Vehicle(registrationNumber);
        return parkingLotManager.parkVehicle(vehicle);
    }

    /**
     * Vacates the parking space for the specified vehicle and calculates the parking charges.
     *
     * @param registrationNumber The registration number of the vehicle that's leaving.
     * @param hoursParked        The number of hours the vehicle was parked.
     * @return The {@link ParkingCharge} associated with the vehicle's stay duration.
     */
    @Override
    public ParkingCharge leave(String registrationNumber, int hoursParked) {
        String parkingSpaceId = parkingLotManager.vacateParkingSpace(registrationNumber);
        Number number = chargesCalculator.computePrice(hoursParked);
        return new ParkingCharge(registrationNumber, parkingSpaceId, number.toString());
    }

    /**
     * Retrieves the current status of the parking area.
     *
     * @return A {@link SortedMap} where the keys are occupied parking space ids and the values are the registration numbers of the parked vehicles.
     */
    @Override
    public SortedMap<Integer, String> status() {
        return Collections.unmodifiableSortedMap(parkingLotManager.status());
    }
}
