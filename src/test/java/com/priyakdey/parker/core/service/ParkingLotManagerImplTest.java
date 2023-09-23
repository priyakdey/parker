package com.priyakdey.parker.core.service;

import static com.priyakdey.parker.data.TestData.REG_NUM_1;
import static com.priyakdey.parker.data.TestData.REG_NUM_2;
import static com.priyakdey.parker.data.TestData.REG_NUM_3;
import static com.priyakdey.parker.data.TestData.vehicle1;
import static com.priyakdey.parker.data.TestData.vehicle2;
import static com.priyakdey.parker.data.TestData.vehicle3;
import static com.priyakdey.parker.data.TestData.vehicle4;
import static com.priyakdey.parker.util.AssertionUtil.assertionExceptionMsg;
import static com.priyakdey.parker.util.AssertionUtil.assertionMsg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.priyakdey.parker.core.exception.TestSetupException;
import com.priyakdey.parker.core.model.ParkingSpace;
import com.priyakdey.parker.core.model.Vehicle;
import com.priyakdey.parker.exception.BadInputException;
import java.util.Optional;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ParkingLotManagerImpl")
class ParkingLotManagerImplTest {

    private final ParkingLotManagerImpl parkingLotManager;

    private final ParkingLot parkingLot;

    ParkingLotManagerImplTest() {
        parkingLot = ParkingLot.withCapacity(3);
        parkingLotManager = new ParkingLotManagerImpl(parkingLot);
    }

    @DisplayName("Should return the allotted parking space id")
    @Test
    void test_parkVehicle_shouldReturnParkingSpaceId() {
        Integer id = parkingLotManager.parkVehicle(vehicle1).orElseThrow(TestSetupException::new);
        ParkingSpace space =
            parkingLot.getParkingSpaceByRegistrationNumber(REG_NUM_1)
                .orElseThrow(TestSetupException::new);
        Vehicle vehicle = space.getParkedVehicle().orElseThrow(TestSetupException::new);

        assertEquals(1, id, assertionMsg(1, id));
        assertEquals(vehicle1.getParkedAt(), space, assertionMsg(vehicle1.getParkedAt(), space));
        assertEquals(vehicle1, vehicle, assertionMsg(vehicle1, vehicle));

        parkingLotManager.vacateParkingSpace(REG_NUM_1);
    }

    @DisplayName("Should return no allotted parking space")
    @Test
    void test_parkVehicle_shouldReturnNoParkingSpaceId() {
        parkingLotManager.parkVehicle(vehicle1).orElseThrow(TestSetupException::new);
        parkingLotManager.parkVehicle(vehicle2).orElseThrow(TestSetupException::new);
        parkingLotManager.parkVehicle(vehicle3).orElseThrow(TestSetupException::new);

        Optional<Integer> optionalInteger = parkingLotManager.parkVehicle(vehicle4);

        boolean isEmpty = optionalInteger.isEmpty();
        assertTrue(isEmpty, assertionMsg(true, isEmpty));

        parkingLotManager.vacateParkingSpace(REG_NUM_1);
        parkingLotManager.vacateParkingSpace(REG_NUM_2);
        parkingLotManager.vacateParkingSpace(REG_NUM_3);
    }

    @DisplayName("Should vacate the parking space")
    @Test
    void test_vacateParkingSpace_shouldVacateTheParkingSpace() {
        parkingLotManager.parkVehicle(vehicle1).orElseThrow(TestSetupException::new);

        ParkingSpace parkingSpace =
            parkingLot.getParkingSpaceByRegistrationNumber(REG_NUM_1)
                .orElseThrow(TestSetupException::new);

        boolean isParked = parkingSpace.getParkedVehicle().isPresent();
        assertTrue(isParked, assertionMsg(true, isParked));

        // vacate the space
        parkingLotManager.vacateParkingSpace(REG_NUM_1);

        boolean isVacated = parkingSpace.getParkedVehicle().isEmpty();
        assertTrue(isVacated, assertionMsg(true, isVacated));
    }


    @DisplayName("Should throw BadInputException")
    @Test
    void test_vacateParkingSpace_shouldThrowException() {
        assertThrows(BadInputException.class, () -> parkingLotManager.vacateParkingSpace(REG_NUM_1),
            assertionExceptionMsg(BadInputException.class));
    }

    @DisplayName("Should return correct status")
    @Test
    void test_status() {
        Integer id1 = parkingLotManager.parkVehicle(vehicle1).orElseThrow(TestSetupException::new);
        Integer id2 = parkingLotManager.parkVehicle(vehicle2).orElseThrow(TestSetupException::new);
        Integer id3 = parkingLotManager.parkVehicle(vehicle3).orElseThrow(TestSetupException::new);

        parkingLotManager.vacateParkingSpace(REG_NUM_2);

        TreeMap<Integer, String> expected = new TreeMap<>();
        expected.put(id1, vehicle1.getRegistrationNumber());
        expected.put(id3, vehicle3.getRegistrationNumber());

        TreeMap<Integer, String> actual = parkingLotManager.status();

        assertEquals(expected, actual, assertionMsg(expected, actual));

        parkingLotManager.vacateParkingSpace(REG_NUM_1);
        parkingLotManager.vacateParkingSpace(REG_NUM_3);

    }

}