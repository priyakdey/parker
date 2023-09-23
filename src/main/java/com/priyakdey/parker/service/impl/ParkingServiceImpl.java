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
