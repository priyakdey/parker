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

package com.priyakdey.parker.command.impl;

import com.priyakdey.parker.command.Command;
import com.priyakdey.parker.context.ApplicationContext;
import com.priyakdey.parker.service.ParkingService;
import java.util.Map;
import java.util.SortedMap;

/**
 * Represents the command to retrieve and display the current status of the parking lot.
 *
 * <p>
 * Upon execution, this command facilitates fetching the current occupancy status,
 * available spaces, and other related metrics or details of the parking facility.
 * </p>
 *
 * @author Priyak Dey
 */
public class StatusCommand implements Command {
    private static final String STATUS_HEADER = "Slot No. Registration No.";
    private static final String STATUS_MSG_TMPL = "%d %s%n";


    /**
     * Executes the command to retrieve the current status of the parking lot.
     *
     * <p>
     * The method is expected to handle operations like fetching the current occupancy,
     * number of free spaces, or any other metrics or details deemed necessary.
     * Note: While this method accepts arguments, it's anticipated that for a status command,
     * no specific arguments would typically be required. However, the mechanism is in place
     * for flexibility.
     * </p>
     *
     * @param args An array of string arguments, which in the context of a status command, are
     *             expected to be empty or not specifically used.
     */
    @Override
    public void execute(String... args) {
        ApplicationContext ctx = ApplicationContext.getInstance();
        ParkingService parkingService = ctx.get(ParkingService.class);

        SortedMap<Integer, String> status = parkingService.status();

        if (!status.isEmpty()) {
            System.out.println(STATUS_HEADER);

            for (Map.Entry<Integer, String> entry : status.entrySet()) {
                System.out.printf(STATUS_MSG_TMPL, entry.getKey(), entry.getValue());
            }

        }

    }
}
