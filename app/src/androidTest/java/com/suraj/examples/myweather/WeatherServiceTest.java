package com.suraj.examples.myweather;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import model.Location;
import service.WeatherService;
import service.ServiceCallback;

/**
 * Created by suraj bhattarai on 7/12/15.
 * TestCase to test the WeatherService
 */
public class WeatherServiceTest extends AndroidTestCase{

    private ServiceCallback mCallback;
    private Location mValid;
    private Location mInvalid;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mValid = new Location("Chicago","IL");
        mInvalid = new Location("BlahblahCity","NoCity");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test for a valid weather data request
     */
    public void testGetWeatherDataValid() throws Exception {
        mCallback = new ServiceCallback() {
            @Override
            public void onSuccess(Object data) {
                Assert.assertNotNull(data);
            }

            @Override
            public void onFailure(String message) {
                Assert.assertNotNull(message);
            }
        };
        WeatherService weatherService = new WeatherService(mCallback, getContext());

        // Valid request
        weatherService.getWeatherData(mValid);
    }

    /**
     * Test for a invalid weather data request. Using invalid location
     */
    public void testGetWeatherDataInvalid() throws Exception {
        mCallback = new ServiceCallback() {
            @Override
            public void onSuccess(Object data) {
                Assert.assertNotNull(data);
            }

            @Override
            public void onFailure(String message) {
                Assert.assertNotNull(message);
            }
        };
        WeatherService weatherService = new WeatherService(mCallback, getContext());

        // Invalid request
        weatherService.getWeatherData(mInvalid);
    }
}
