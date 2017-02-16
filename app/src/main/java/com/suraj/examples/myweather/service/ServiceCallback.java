package com.suraj.examples.myweather.service;

/**
 * Created by suraj bhattarai on 7/10/15.
 * Callback interface to send successful API call or any failure
 */
public interface ServiceCallback {
    /**
     * Handle when successfully received information from remote API call
     * @param data WeatherData
     */
    void onSuccess(Object data);

    /**
     * Handle when error is received back from API call or any exception is caught
     * @param message Error message
     */
    // no need message
    void onFailure(String message);
}
