/*
 * Copyright (C) 2015 Suraj Bhattarai
 *
 */
package com.suraj.examples.myweather;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import model.Location;
import model.Weather;

/**
 * This class displays the five day forecast dialog fragment using ForecastGridAdapter
 * Forecast weather data is passed from the calling class.
 */
public class ForecastDialog extends DialogFragment {

    /** Define variables to store user provided location and forecast data for 5 days */
    private static ArrayList<Weather> mForecast;
    private static String mCurrentLocation;

    /**
     * Constructor for passing forecast data and current location
     * @param forecastData array of forecast data
     * @param currentLocation location
     * @return forecast dialog
     */
    static ForecastDialog newInstance(ArrayList<Weather> forecastData, Location currentLocation) {
        ForecastDialog f = new ForecastDialog();
        mForecast = forecastData;
        mCurrentLocation = currentLocation.toString();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflate forecast layout */
        View forecastView = inflater.inflate(R.layout.forecast_fragment, container, false);
        getDialog().setTitle(mCurrentLocation + getActivity().getString(R.string.five_day_forecast_title));
        /** Create ForecastGridAdapter and pass context and forecast data to display in UI */
        ForecastGridAdapter forecastGridAdapter = new ForecastGridAdapter(getActivity(), mForecast);
        GridView mForecastGrid = (GridView)forecastView.findViewById(R.id.weather_fragment_forecast_grid);
        mForecastGrid.setAdapter(forecastGridAdapter);
        return forecastView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            /**
             * Resize the dialog to match parent Activity
             */
            dialog.getWindow().setLayout(width, height);
        }
    }

}
