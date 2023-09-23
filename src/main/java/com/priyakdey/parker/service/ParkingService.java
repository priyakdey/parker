package com.priyakdey.parker.service;

import com.priyakdey.parker.core.model.ParkingCharge;
import java.util.Optional;

/**
 * @author Priyak Dey
 */
public interface ParkingManagementService {

    Optional<Integer> park(String registrationNumber);

    ParkingCharge leave(String registrationNumber);

}
