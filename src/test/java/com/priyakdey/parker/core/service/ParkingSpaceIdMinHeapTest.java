package com.priyakdey.parker.core.service;

import static com.priyakdey.parker.util.AssertionUtil.assertionExceptionMsg;
import static com.priyakdey.parker.util.AssertionUtil.assertionMsg;
import static org.junit.jupiter.api.Assertions.*;

import com.priyakdey.parker.exception.BadInputException;
import com.priyakdey.parker.exception.HeapOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ParkingSpaceIdMinHeap")
class ParkingSpaceIdMinHeapTest {

    @DisplayName("Should return there are no empty parking spaces")
    @Test
    void test_isEmpty_shouldReturnTrue() {
        final var parkingSpaceIdMinHeap = new ParkingSpaceIdMinHeap(2);
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();

        boolean isEmpty = parkingSpaceIdMinHeap.isEmpty();
        assertTrue(isEmpty, assertionMsg(true, isEmpty));
    }

    @DisplayName("Should return there are empty parking spaces")
    @Test
    void test_isEmpty_shouldReturnFalse() {
        final var parkingSpaceIdMinHeap = new ParkingSpaceIdMinHeap(2);

        boolean isEmpty = parkingSpaceIdMinHeap.isEmpty();
        assertFalse(isEmpty, assertionMsg(false, isEmpty));
    }

    @DisplayName("Should return nearest parking space id")
    @Test
    void test_poll_shouldReturnMinId() {
        final var parkingSpaceIdMinHeap = new ParkingSpaceIdMinHeap(5);

        int id = parkingSpaceIdMinHeap.poll();
        assertEquals(1, id, assertionMsg(1, id));

        id = parkingSpaceIdMinHeap.poll();
        assertEquals(2, id, assertionMsg(2, id));

        id = parkingSpaceIdMinHeap.poll();
        assertEquals(3, id, assertionMsg(3, id));

        id = parkingSpaceIdMinHeap.poll();
        assertEquals(4, id, assertionMsg(4, id));

        parkingSpaceIdMinHeap.push(3);
        parkingSpaceIdMinHeap.push(1);

        // current state = [1, 3, 4, 5]

        id = parkingSpaceIdMinHeap.poll();
        assertEquals(1, id, assertionMsg(1, id));
    }

    @DisplayName("Should thrown HeapOperationException")
    @Test
    void test_poll_shouldThrowException() {
        final var parkingSpaceIdMinHeap = new ParkingSpaceIdMinHeap(5);

        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();

        assertThrows(HeapOperationException.class, parkingSpaceIdMinHeap::poll,
            assertionExceptionMsg(HeapOperationException.class));
    }

    @DisplayName("Should add the ids back in the heap")
    @Test
    void test_add_shouldAddId() {
        final var parkingSpaceIdMinHeap = new ParkingSpaceIdMinHeap(7);

        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();

        parkingSpaceIdMinHeap.push(5);
        parkingSpaceIdMinHeap.push(7);
        parkingSpaceIdMinHeap.push(1);
        parkingSpaceIdMinHeap.push(3);

        // current state = [1, 3, 5, 7]

        boolean isEmpty = parkingSpaceIdMinHeap.isEmpty();
        assertFalse(isEmpty, assertionMsg(false, isEmpty));
    }

    @DisplayName("Should throw HeapOperationException")
    @Test
    void test_add_overflow_shouldThrowException() {
        final var parkingSpaceIdMinHeap = new ParkingSpaceIdMinHeap(5);

        assertThrows(HeapOperationException.class, () -> parkingSpaceIdMinHeap.push(6),
            assertionExceptionMsg(HeapOperationException.class));
    }

    @DisplayName("Should throw BadInputException")
    @Test
    void test_add_outOfRange_shouldThrowException() {
        final var parkingSpaceIdMinHeap = new ParkingSpaceIdMinHeap(5);

        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();

        assertThrows(BadInputException.class, () -> parkingSpaceIdMinHeap.push(6),
            assertionExceptionMsg(BadInputException.class));

        assertThrows(BadInputException.class, () -> parkingSpaceIdMinHeap.push(0),
            assertionExceptionMsg(BadInputException.class));
    }


    @DisplayName("Should throw BadInputException")
    @Test
    void test_add_duplicateId_shouldThrowException() {
        final var parkingSpaceIdMinHeap = new ParkingSpaceIdMinHeap(5);

        parkingSpaceIdMinHeap.poll();
        parkingSpaceIdMinHeap.poll();

        // current state = [3, 4, 5]

        assertThrows(BadInputException.class, () -> parkingSpaceIdMinHeap.push(3),
            assertionExceptionMsg(BadInputException.class));
    }

}