package com.priyakdey.parker.core.service;

import com.priyakdey.parker.core.model.ParkingSpace;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a parking lot with a specified capacity.
 *
 * <p>
 * The {@code ParkingLot} class provides functionality to manage and allocate parking spaces
 * within a parking lot facility. Spaces within the lot are allocated using a priority system,
 * ensuring that the nearest available parking spot is always prioritized.
 * </p>
 * <p>
 * This ensures efficient space utilization and minimizes the distance drivers need to travel
 * within the lot.
 * </p>
 *
 * <p>
 * Typical usage involves initializing the parking lot with a specific capacity using the
 * {@link #withCapacity(int)} method.
 * </p>
 *
 * @author Priyak Dey
 */
public final class ParkingLot {

    /**
     * List containing all parking spaces within the parking lot.
     * Each parking space is uniquely identified by its position in this list.
     */
    private final List<ParkingSpace> parkingSpaces;

    /**
     * A priority-based data structure to manage available parking spaces.
     * This ensures that spaces are allocated in an optimal manner.
     */
    private final ParkingSpaceIdMinHeap availableParkingSpace;

    /**
     * The maximum number of vehicles the parking lot can accommodate.
     */
    private final int capacity;

    /**
     * Private constructor to initialize the parking lot with a specific capacity.
     *
     * <p>
     * This initializes all parking spaces and sets up internal structures to manage and allocate spaces.
     * </p>
     *
     * @param capacity The maximum number of parking spaces in the parking lot.
     */
    private ParkingLot(int capacity) {
        this.capacity = capacity;
        this.parkingSpaces =
            IntStream.rangeClosed(1, capacity).mapToObj(ParkingSpace::new).toList();
        this.availableParkingSpace = new ParkingSpaceIdMinHeap(capacity);
    }

    /**
     * Factory method to create a new parking lot instance with the specified capacity.
     *
     * @param capacity The maximum number of parking spaces in the parking lot.
     * @return A newly initialized {@code ParkingLot} instance.
     */
    public static ParkingLot withCapacity(int capacity) {
        return new ParkingLot(capacity);
    }

    /**
     * Checks if the parking lot has any vacant parking spaces.
     *
     * @return {@code true} if there's at least one empty parking space available; {@code false} otherwise.
     */
    boolean hasEmptySpace() {
        return !this.availableParkingSpace.isEmpty();
    }

    /**
     * Reserves a parking space in the parking lot.
     * <p>
     * This method attempts to book the nearest available parking space. If the parking lot is full,
     * no space is allocated.
     * </p>
     *
     * @return An {@code Optional} containing the booked {@link ParkingSpace} if one is available;
     * an empty {@code Optional} if the parking lot is full.
     */
    Optional<ParkingSpace> bookParkingSpace() {
        if (hasEmptySpace()) {
            int id = availableParkingSpace.poll();
            ParkingSpace allottedSpace = parkingSpaces.get(id - 1);
            return Optional.of(allottedSpace);
        }

        return Optional.empty();
    }

    /**
     * Releases a previously booked parking space, making it available for future bookings.
     * <p>
     * This method marks the given parking space as vacant and updates the internal data structures
     * to reflect the change.
     * </p>
     *
     * @param parkingSpace The parking space that needs to be vacated.
     */
    void vacateParkingSpace(ParkingSpace parkingSpace) {
        int id = parkingSpace.getId();
        parkingSpace.setParkedVehicle(null);
        availableParkingSpace.push(id);
    }

    /**
     * Returns the reference to the parking spot by the parked vehicle's registration number.
     *
     * @param registrationNumber The registration number of the car.
     * @return The parking space by the id wrapped in an optional.
     */
    Optional<ParkingSpace> getParkingSpaceByRegistrationNumber(String registrationNumber) {
        // NOTE: We do a find first here, we are assuming no duplicate reg numbers are in the structures.
        // If there are, there are bigger problems to deal with.
        return parkingSpaces.stream()
            .filter(parkingSpace -> parkingSpace.getParkedVehicle().isPresent())
            .filter(parkingSpace -> Objects.equals(registrationNumber,
                parkingSpace.getParkedVehicle().get().getRegistrationNumber()))
            .findFirst();
    }

    /**
     * Returns a {@link TreeMap} of occupied parking spaces
     * with parking space id as key and parked vehicle registration number as value.
     *
     * @return A sorted map of occupied parking ids and parked vehicle registration number.
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    TreeMap<Integer, String> getStatusForOccupiedSpaces() {
        return parkingSpaces.stream()
            .filter(ParkingSpace::isOccupied)
            .collect(Collectors.toMap(
                ParkingSpace::getId,
                // get() without a null check is okay here, since we filtered all un-occupied spaces
                space -> space.getParkedVehicle().get().getRegistrationNumber(),
                // collisions should never happen here
                (a, b) -> a,
                TreeMap::new
            ));
    }
}
