/*
 * Copyright (C) 2015 Suraj Bhattarai
 *
 */
package com.suraj.examples.myweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.util.ArrayList;

import model.Weather;
import service.DownloadIconTask;

/**
 * Created by suraj bhattarai on 7/10/15.
 * Adapter for the five day forecast grid dialog. This class
 * accepts Weather objects and converts them into Arrays to feed to
 * the grid layout UI elements.
 */
public class ForecastGridAdapter extends BaseAdapter {

    /**
     * Context required for getting string resources
     */
    private Context mContext;

    /** Define arrays */
    private final String[] mDays;
    private final Integer[] mHighs;
    private final Integer[] mLows;
    private final String[] mDescriptions;
    private final String[] mIconUrls;
    private final Double[] mPrecipations;

    /**
     * Constructor to pass the context and weather data.
     * @param context Application's context
     * @param forecasts Weather array
     */
    public ForecastGridAdapter(Context context, ArrayList<Weather> forecasts) {
        this.mContext = context;

        /** ArrayList for each data elements in Weather object */
        ArrayList<String> days = new ArrayList<>();
        ArrayList<String> iconUrls = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        ArrayList<Integer> highs = new ArrayList<>();
        ArrayList<Integer> lows = new ArrayList<>();
        ArrayList<Double> precipitations = new ArrayList<>();

        /** Construct ArrayList from the data */
        for (Weather forecast : forecasts) {
            days.add(forecast.getDate());
            highs.add(forecast.getMaxTemp());
            lows.add(forecast.getMinTemp());
            iconUrls.add(forecast.getHourly().getIconUrl());
            descriptions.add(forecast.getHourly().getDescription());
            precipitations.add(forecast.getHourly().getPrecipitation());
        }

        /** Convert ArrayList to arrays so they can be used in GridView UI */
        this.mDays = days.toArray(new String[forecasts.size()]);
        this.mHighs = highs.toArray(new Integer[forecasts.size()]);
        this.mLows = lows.toArray(new Integer[forecasts.size()]);
        this.mDescriptions = descriptions.toArray(new String[forecasts.size()]);
        this.mIconUrls = iconUrls.toArray(new String[forecasts.size()]);
        this.mPrecipations = precipitations.toArray(new Double[forecasts.size()]);

    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View forecastGrid;
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            forecastGrid = inflater.inflate(R.layout.forecast_single, null);
        } else {
            forecastGrid = convertView;
        }

        /** Initialize UI elements */
        TextView textViewDay = (TextView)forecastGrid.findViewById(R.id.forecast_single_text_view_day);
        ImageView imageViewCondition = (ImageView)forecastGrid.findViewById(R.id.forecast_single_image_view_condition);
        TextView textViewHighLow = (TextView)forecastGrid.findViewById(R.id.forecast_single_text_view_high_low);
        TextView textViewDescription = (TextView)forecastGrid.findViewById(R.id.forecast_single_text_view_description);
        TextView textViewPrecipitation = (TextView)forecastGrid.findViewById(R.id.forecast_single_text_view_precipitation);

        /** Set values to the UI elements */
        textViewDay.setText(mDays[position]);
        textViewDescription.setText(mDescriptions[position]);
        textViewPrecipitation.setText(mContext.getString(R.string.precipitation_title) +
                mPrecipations[position] + mContext.getString(R.string.precipitation_unit));

        try {
            /** Start the download process for the weather icon */
            new DownloadIconTask(mIconUrls[position], imageViewCondition);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /** Set min and max weather forecast */
        textViewHighLow.setText(mHighs[position] + mContext.getString(R.string.fahrenheit_unit) +
                "/" + mLows[position] + mContext.getString(R.string.fahrenheit_unit));

        return forecastGrid;
    }
}
