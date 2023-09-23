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
