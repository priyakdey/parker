package com.priyakdey.parker.context;

import static org.junit.jupiter.api.Assertions.*;

import com.priyakdey.parker.core.pricing.ChargesCalculator;
import com.priyakdey.parker.core.pricing.impl.PerHourChargesCalculatorImpl;
import com.priyakdey.parker.core.service.ParkingLot;
import com.priyakdey.parker.core.service.ParkingLotManager;
import com.priyakdey.parker.core.service.ParkingLotManagerImpl;
import com.priyakdey.parker.service.ParkingService;
import com.priyakdey.parker.service.impl.ParkingServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationContextTest {

    private final ApplicationContext ctx;

    ApplicationContextTest() {
        ParkingLot parkingLot = ParkingLot.withCapacity(5);
        ChargesCalculator chargesCalculator = new PerHourChargesCalculatorImpl();
        ParkingLotManager parkingLotManager = new ParkingLotManagerImpl(parkingLot);
        ParkingService parkingService = new ParkingServiceImpl(parkingLotManager, chargesCalculator);

        ctx = ApplicationContext.getInstance();
        ctx.put(parkingService);
    }

    @DisplayName("Should return correct object of the type requested")
    @Test
    void test_get_shouldReturnCorrectType() {
        assertNotNull(ctx.get(ParkingService.class));
    }

}