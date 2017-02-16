package com.suraj.examples.myweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.suraj.examples.myweather.model.Location;

/**
 * Created by surajbhattarai on 7/12/15.
 * Adapter for the location search grid dialog. This class
 * accepts Location list and converts them into Arrays to feed to
 * the grid layout UI elements.
 */
public class LocationAdapter extends BaseAdapter {

    /**
     * Context required for getting string resources
     */
    private Context mContext;
    private ArrayList<Location> locations;

    /**
     * Constructor to pass the context and weather data.
     * @param context Application's context
     * @param locations Location array
     */
    public LocationAdapter(Context context, ArrayList<Location> locations) {
        this.mContext = context;

        this.locations = locations;

    }

    @Override
    public int getCount() {
        return locations.size();
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
            locationGrid = inflater.inflate(R.layout.location_single, parent, false);
        } else {
            locationGrid = convertView;
        }

        /** Initialize UI elements */
        TextView textViewArea = (TextView)locationGrid.findViewById(R.id.location_single_text_view_area);
        TextView textViewCountry = (TextView)locationGrid.findViewById(R.id.location_single_text_view_country);
        TextView textViewRegion = (TextView)locationGrid.findViewById(R.id.location_single_text_view_region);

        Location location = locations.get(position);

        /** Set values to the UI elements */
        textViewArea.setText(location.getAreaName());
        textViewCountry.setText(location.getCountry());
        textViewRegion.setText(location.getRegion());

        return locationGrid;
    }
}
