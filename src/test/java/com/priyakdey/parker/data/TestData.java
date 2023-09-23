package com.priyakdey.parker.data;

import com.priyakdey.parker.core.model.Vehicle;
import org.junit.jupiter.api.Test;

/**
 * @author Priyak Dey
 */
public final class TestData {

    private TestData() {
    }

    public static final String REG_NUM_1 = "KA-01-HH-1234";
    public static final String REG_NUM_2 = "KA-01-HH-9999";
    public static final String REG_NUM_3 = "KA-01-BB-0001";
    public static final String REG_NUM_4 = "KA-01-HH-7777";
    public static final String REG_NUM_5 = "KA-01-HH-2701";
    public static final String REG_NUM_6 = "KKA-09-HH-0987";

    public static final String CHERRY_RED = "Cherry Red";
    public static final String BLACK = "Black";
    public static final String WHITE = "White";
    public static final String MIDNIGHT_BLUE = "Midnight Blue";
    public static final String METALLIC_GREY = "Metallic Grey";

    public static final Vehicle vehicle1 = new Vehicle(REG_NUM_1, CHERRY_RED);
    public static final Vehicle vehicle2 = new Vehicle(REG_NUM_2, BLACK);
    public static final Vehicle vehicle3 = new Vehicle(REG_NUM_3, MIDNIGHT_BLUE);
    public static final Vehicle vehicle4 = new Vehicle(REG_NUM_4, METALLIC_GREY);
    public static final Vehicle vehicle5 = new Vehicle(REG_NUM_5, WHITE);
    public static final Vehicle vehicle6 = new Vehicle(REG_NUM_6, BLACK);

}
