package com.suraj.examples.myweather;

import org.junit.Test;
import static org.junit.Assert.*;

import service.Utils;

/**
 * Created by surajbhattarai on 9/11/15.
 */
public class UtilsTest {
    @Test
    public void convertFToC() {
        float actual = Utils.convertFahrenheitToCelsius(212);
        float expected = 100;
        assertEquals("Conversion from celsius to fahrenheit failed", expected,
                actual, 0.001);
    }

    @Test
    public void convertCToF() {
        float actual = Utils.convertCelsiusToFahrenheit(100);
        float expected = 212;
        assertEquals("Conversion from celsius to fahrenheit failed", expected,
                actual, 0.001);
    }

}
