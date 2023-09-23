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
import java.util.StringJoiner;

/**
 * Represents a vehicle which can be parked within a parking lot.
 * Each vehicle has a unique registration number, a color, and timestamps
 * indicating when it entered and exited the parking lot.
 * <p>
 * This class also provides methods to assign and retrieve the parking space
 * where the vehicle is parked.
 * </p>
 *
 * @author Priyak Dey
 */
public class Vehicle {
    private final String registrationNumber;
    private final String color;

    private ParkingSpace parkedAt;

    /**
     * Constructs a new vehicle with the given registration number, color, and entry timestamp.
     *
     * @param registrationNumber the vehicle's unique registration number
     * @param color              the vehicle's color
     */
    public Vehicle(String registrationNumber, String color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    /**
     * Returns the vehicle's unique registration number.
     *
     * @return the vehicle's registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Returns the vehicle's color.
     *
     * @return the vehicle's color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Returns the parking space allocated to the vehicle.
     *
     * @return the vehicle's parking space.
     */
    public ParkingSpace getParkedAt() {
        return parkedAt;
    }

    /**
     * Sets the allotted parking space for the vehicle
     */
    public void setParkedAt(ParkingSpace parkedAt) {
        this.parkedAt = parkedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(registrationNumber, vehicle.registrationNumber) &&
            Objects.equals(color, vehicle.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, color);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vehicle.class.getSimpleName() + "[", "]")
            .add("registrationNumber='" + registrationNumber + "'")
            .add("color='" + color + "'")
            .toString();
    }
}
