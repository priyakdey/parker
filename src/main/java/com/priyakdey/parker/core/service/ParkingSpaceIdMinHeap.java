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

package com.priyakdey.parker.core.service;

import com.priyakdey.parker.exception.BadInputException;
import com.priyakdey.parker.exception.HeapOperationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Represents a priority-based storage (min-heap) of available parking spaces.
 * The heap allows efficient retrieval of the nearest empty spot from the entrance when
 * a new vehicle arrives. The priority is based on parking space IDs, considering that
 * 1 is the nearest spot to the entrance.
 * <p>
 * The heap is internally implemented using a linked list, where the root represents
 * the parking space closest to the entrance, and the tree structure ensures that parents
 * have a smaller ID (higher priority) than their children.
 * </p>
 * <p>
 * The heap, designed specifically for managing parking space ids;has a specific bound,
 * which is defined by `capacity`. Adding any duplicate elements,
 * adding any elements out of range (outside [1, capacity]) is illegal.
 * <p/>
 *
 * @author Priyak Dey
 */
public class ParkingSpaceIdMinHeap {

    /**
     * List of available parking space ids.
     * This acts as a backing array for a min-heap, which allows faster
     * retrieval of nearest empty spot from the entrance when a new vehicle arrives.
     * It considers that 1 is the nearest spot to the entrance to begin with.
     */
    private final List<Integer> availableParkingSpaces;

    /**
     * Total capacity of the parking lot.
     */
    private final int capacity;

    /**
     * presentIdsInHeap keeps a track of current ids in the heap to help faster lookups.
     * <p>
     * Since we say `time is money`, going for a memory overhead to reduce O(n) to O(1) lookup.
     * This performance will come handy when the parking lot is huge, and lots of add/poll.
     */
    private final Set<Integer> presentIdsInHeap;

    /**
     * Initializes the heap with a set of parking spaces based on the provided capacity.
     *
     * @param capacity the number of parking spaces to be managed by the heap
     */
    public ParkingSpaceIdMinHeap(int capacity) {
        this.capacity = capacity;
        List<Integer> ids = IntStream.rangeClosed(1, capacity).boxed().toList();
        this.availableParkingSpaces = new ArrayList<>(ids);
        this.presentIdsInHeap = new HashSet<>(ids);
    }

    /**
     * Determines whether there are any available parking spaces in the heap.
     *
     * @return true if there are no available parking spaces, false otherwise
     */
    public boolean isEmpty() {
        return availableParkingSpaces.isEmpty();
    }

    /**
     * Retrieves and removes the parking space ID with the highest priority (i.e., the closest
     * to the entrance) from the heap.
     *
     * @return the ID of the removed parking space
     * @throws HeapOperationException if the heap is empty when the method is invoked
     */
    public int poll() {
        if (isEmpty()) {
            throw new HeapOperationException("Heap is empty");
        }

        int removedSpace = availableParkingSpaces.get(0);
        removeRoot();
        presentIdsInHeap.remove(removedSpace);
        return removedSpace;
    }

    /**
     * Adds a given parking space ID back into the heap, ensuring that the heap's structural
     * integrity and properties (min-heap) are maintained. If certain conditions are not met
     * such as the heap already being at full capacity, the ID being out of range, or the ID
     * already present in the heap, appropriate exceptions are thrown.
     * <p>
     * The ID should be within the range [1, capacity], representing valid parking spaces.
     * </p>
     *
     * @param id the parking space ID to be added back into the heap.
     * @throws HeapOperationException if the heap is already at its maximum capacity.
     * @throws BadInputException      if the provided ID is not within the accepted range
     * @throws BadInputException      if the provided ID is already present in the heap
     */
    public void push(int id) {
        if (availableParkingSpaces.size() == capacity) {
            throw new HeapOperationException("Heap it at its full capacity");
        }

        if (id < 1 || id > capacity) {
            throw new BadInputException(
                String.format("Accepted ids are in the range of [1, %d]", capacity));
        }

        if (presentIdsInHeap.contains(id)) {
            throw new BadInputException(String.format("ID %d is already present in the heap.", id));
        }

        addId(id);
        presentIdsInHeap.add(id);
    }


    // ---- Internals ------

    /**
     * Retrieves the parent index of the given index within the heap data structure.
     *
     * @param index the index whose parent needs to be found
     * @return the index of the parent node in the heap
     */
    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    /**
     * Retrieves the left child index of the given index within the heap data structure.
     *
     * @param index the index whose left child needs to be found
     * @return the index of the left child node in the heap
     */
    private int getLeftChildIndex(int index) {
        return (index * 2) + 1;
    }

    /**
     * Retrieves the right child index of the given index within the heap data structure.
     *
     * @param index the index whose right child needs to be found
     * @return the index of the right child node in the heap
     */
    private int getRightChildIndex(int index) {
        return (index * 2) + 2;
    }

    /**
     * Removes the smallest ID from the available spots.
     * This method employs a min-heap property to remove the root and restructures the heap.
     */
    private void removeRoot() {
        Collections.swap(availableParkingSpaces, 0, availableParkingSpaces.size() - 1);
        availableParkingSpaces.removeLast();

        int currIndex = 0;

        while (currIndex < availableParkingSpaces.size()) {
            int leftChildIndex = getLeftChildIndex(currIndex);
            int rightChildIndex = getRightChildIndex(currIndex);

            if (leftChildIndex >= availableParkingSpaces.size()) {
                break;
            }

            int swapIndex = leftChildIndex;
            if (rightChildIndex < availableParkingSpaces.size() &&
                availableParkingSpaces.get(rightChildIndex) <
                    availableParkingSpaces.get(leftChildIndex)) {
                swapIndex = rightChildIndex;
            }

            if (availableParkingSpaces.get(swapIndex) < availableParkingSpaces.get(currIndex)) {
                Collections.swap(availableParkingSpaces, currIndex, swapIndex);
            } else {
                break;
            }

            currIndex = swapIndex;
        }
    }

    /**
     * Adds the given ID to the available spots while maintaining the min-heap property.
     *
     * @param id the parking space ID to be added to available spots
     */
    private void addId(int id) {
        availableParkingSpaces.add(id);

        int currIndex = availableParkingSpaces.size() - 1;

        while (currIndex >= 1) {
            int parentIndex = getParentIndex(currIndex);

            if (availableParkingSpaces.get(currIndex) < availableParkingSpaces.get(parentIndex)) {
                Collections.swap(availableParkingSpaces, parentIndex, currIndex);
            } else {
                break;
            }

            currIndex = parentIndex;
        }
    }

}
