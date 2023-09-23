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

/**
 * Represents the details of a parking charge for a vehicle.
 *
 * <p>This record abstracts the representation of hours parked and the charge incurred
 * to avoid tight coupling with specific numerical or currency types.
 * By using {@code String} for all fields, it provides flexibility in terms of
 * representing charges (which could be integers today and floating points tomorrow)
 * and currency units.</p>
 * <p>
 * It is a data classes which holds the following information:
 * <li>
 *     <ul>registrationNumber: The registration number of the parked vehicle.</ul>
 *     <ul>parkedSpot: Parking Spot id</ul>
 *     <ul>charge: The charge incurred for the parking duration. Example formats: "10", "10.6", "10 dollars 60 cents", etc.<ul/>
 * </li>
 * <p/>
 *
 * @author Priyak Dey
 */
public record ParkingCharge(String registrationNumber, String parkedSpot, String charge) {
}
