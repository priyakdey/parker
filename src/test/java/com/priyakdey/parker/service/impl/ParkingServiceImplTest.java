package com.priyakdey.parker.service.impl;

import static com.priyakdey.parker.data.TestData.REG_NUM_1;
import static com.priyakdey.parker.data.TestData.REG_NUM_2;
import static com.priyakdey.parker.data.TestData.REG_NUM_3;
import static com.priyakdey.parker.data.TestData.REG_NUM_4;
import static com.priyakdey.parker.data.TestData.REG_NUM_5;
import static com.priyakdey.parker.data.TestData.REG_NUM_6;
import static com.priyakdey.parker.util.AssertionUtil.assertionExceptionMsg;
import static com.priyakdey.parker.util.AssertionUtil.assertionMsg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.priyakdey.parker.core.exception.TestSetupException;
import com.priyakdey.parker.core.model.ParkingCharge;
import com.priyakdey.parker.core.pricing.impl.PerHourChargesCalculatorImpl;
import com.priyakdey.parker.core.service.ParkingLot;
import com.priyakdey.parker.core.service.ParkingLotManager;
import com.priyakdey.parker.core.service.ParkingLotManagerImpl;
import com.priyakdey.parker.exception.BadInputException;
import com.priyakdey.parker.service.ParkingService;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ParkingServiceImpl")
class ParkingServiceImplTest {

    private ParkingService parkingService;

    ParkingServiceImplTest() {
        var parkingLotManager = new ParkingLotManagerImpl(ParkingLot.withCapacity(4));
        var chargesCalculator = new PerHourChargesCalculatorImpl();
        this.parkingService = new ParkingServiceImpl(parkingLotManager, chargesCalculator);
    }

    @DisplayName("Should return the allotted parking space id")
    @Test
    void test_park_shouldReturnAllottedParkingSpaceId() {
        Optional<Integer> parkedSpace = parkingService.park(REG_NUM_1);

        boolean isPresent = parkedSpace.isPresent();
        assertTrue(isPresent, assertionMsg(true, isPresent));
        Integer actual = parkedSpace.get();
        assertEquals(1, actual, assertionMsg(1, actual));


        parkingService.leave(REG_NUM_1, 1);
    }

    @DisplayName("Should return parking space is full")
    @Test
    void test_park_shouldReturnParkingSpaceIsFull() {
        parkingService.park(REG_NUM_1);
        parkingService.park(REG_NUM_2);
        parkingService.park(REG_NUM_3);
        parkingService.park(REG_NUM_4);

        Optional<Integer> parkedSpace = parkingService.park(REG_NUM_5);

        boolean isEmpty = parkedSpace.isEmpty();
        assertTrue(isEmpty, assertionMsg(true, isEmpty));

        parkingService.leave(REG_NUM_1, 1);
        parkingService.leave(REG_NUM_2, 1);
        parkingService.leave(REG_NUM_3, 1);
        parkingService.leave(REG_NUM_4, 1);
    }

    @DisplayName("Should return base charges")
    @Test
    void test_leave_shouldReturnBaseCharge() {
        Integer id = parkingService.park(REG_NUM_1).orElseThrow(TestSetupException::new);

        int hoursParked = 2;
        int expectedCharge = 10;

        ParkingCharge actual = parkingService.leave(REG_NUM_1, hoursParked);

        ParkingCharge expected = new ParkingCharge(REG_NUM_1, Integer.toString(id),
            Integer.toString(expectedCharge));

        assertEquals(expected, actual, assertionMsg(expected, actual));
    }

    @DisplayName("Should return incremental charges")
    @Test
    void test_leave_shouldReturnIncrementCharge() {
        Integer parkedId = parkingService.park(REG_NUM_5).orElseThrow(TestSetupException::new);

        int hoursParked = 5;
        int expectedCharge = 40;

        ParkingCharge actual = parkingService.leave(REG_NUM_5, hoursParked);

        ParkingCharge expected = new ParkingCharge(REG_NUM_5, Integer.toString(parkedId),
            Integer.toString(expectedCharge));

        assertEquals(expected, actual, assertionMsg(expected, actual));
    }

    @DisplayName("Should throw BadInputException")
    @Test
    void test_leave_shouldThrowException() {
        assertThrows(BadInputException.class, () -> parkingService.leave(REG_NUM_5, 3),
            assertionExceptionMsg(BadInputException.class));
    }

    @DisplayName("Should return empty map")
    @Test
    void test_status_shouldReturnEmptyMap() {
        parkingService.park(REG_NUM_1);     // spot 1
        parkingService.park(REG_NUM_2);     // spot 2
        parkingService.park(REG_NUM_3);     // spot 3
        parkingService.park(REG_NUM_4);     // spot 4

        parkingService.leave(REG_NUM_1, 1);     // spot 1 empty
        parkingService.leave(REG_NUM_4, 3);     // spot 4 empty

        parkingService.park(REG_NUM_6);     // spot 1

        SortedMap<Integer, String> expected = new TreeMap<>();
        expected.put(1, REG_NUM_6);
        expected.put(2, REG_NUM_2);
        expected.put(3, REG_NUM_3);

        SortedMap<Integer, String> actual = parkingService.status();

        assertEquals(expected, actual, assertionMsg(expected, actual));
    }
}