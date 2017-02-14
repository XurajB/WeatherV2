package com.suraj.examples.myweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Location;

/**
 * Created by surajbhattarai on 7/12/15.
 * Adapter for the location search grid dialog. This class
 * accepts Location list and converts them into Arrays to feed to
 * the grid layout UI elements.
 */
public class LocationGridAdapter extends BaseAdapter {

    /**
     * Context required for getting string resources
     */
    private Context mContext;

    /** Define arrays */
    private final String[] mAreas;
    private final String[] mCountries;
    private final String[] mRegions;
    /**
     * Constructor to pass the context and weather data.
     * @param context Application's context
     * @param locations Location array
     */
    public LocationGridAdapter(Context context, ArrayList<Location> locations) {
        this.mContext = context;

        /** ArrayList for each data elements in Weather object */
        ArrayList<String> areas = new ArrayList<>();
        ArrayList<String> countries = new ArrayList<>();
        ArrayList<String> regions = new ArrayList<>();

        /** Construct ArrayList from the data */
        for (Location location : locations) {
            areas.add(location.getAreaName());
            countries.add(location.getCountry());
            regions.add(location.getRegion());
        }

        /** Convert ArrayList to arrays so they can be used in GridView UI */
        this.mAreas = areas.toArray(new String[locations.size()]);
        this.mCountries = countries.toArray(new String[locations.size()]);
        this.mRegions = regions.toArray(new String[locations.size()]);

    }

    @Override
    public int getCount() {
        return mAreas.length;
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
        View locationGrid;
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            locationGrid = inflater.inflate(R.layout.location_single, null);
        } else {
            locationGrid = convertView;
        }

        /** Initialize UI elements */
        TextView textViewArea = (TextView)locationGrid.findViewById(R.id.location_single_text_view_area);
        TextView textViewCountry = (TextView)locationGrid.findViewById(R.id.location_single_text_view_country);
        TextView textViewRegion = (TextView)locationGrid.findViewById(R.id.location_single_text_view_region);

        /** Set values to the UI elements */
        textViewArea.setText(mAreas[position]);
        textViewCountry.setText(mCountries[position]);
        textViewRegion.setText(mRegions[position]);

        return locationGrid;
    }
}
