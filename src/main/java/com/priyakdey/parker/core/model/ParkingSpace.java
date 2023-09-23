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

package com.priyakdey.parker.core.model;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Represents a single parking space within a parking lot.
 * Each parking space has a unique ID and can either be occupied or vacant.
 * <p>
 * The class also provides a method to check its occupancy status and
 * set a vehicle to the parking space.
 * </p>
 *
 * @author Priyak Dey
 */
public class ParkingSpace {
    /**
     * Unique identifier for each space.
     */
    private final int id;

    /**
     * Holds the vehicle which is currently parked in the space.
     * If no vehicle parked, this is set to null.
     */
    private Vehicle parkedVehicle;

    /**
     * Constructs a new parking space with the given unique identifier.
     *
     * @param id the unique identifier for this parking space
     */
    public ParkingSpace(int id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier for this parking space.
     *
     * @return the parking space's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the vehicle currently occupying this parking space wrapped in an {@link Optional}.
     *
     * @return an {@link Optional} containing the parked vehicle if present, otherwise empty {@link Optional}
     */
    public Optional<Vehicle> getParkedVehicle() {
        return Optional.ofNullable(parkedVehicle);
    }

    /**
     * Sets the parked vehicle.
     */
    public void setParkedVehicle(Vehicle parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
    }

    /**
     * Returns if the parking space is currently occupied.
     *
     * @return true if the parking space is occupied, else false.
     */
    public boolean isOccupied() {
        return parkedVehicle != null;
    }
}
