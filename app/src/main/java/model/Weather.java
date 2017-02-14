package model;

import org.json.JSONObject;

/**
 * Created by suraj bhattarai on 7/10/15.
 * POJO to store weather JSON object received from the API call.
 * Weather object consists of daily max, min and Hourly information.
 */
public class Weather implements DataPopulator {

    /** Define variables */
    private String mDate;
    private int mMaxTemp;
    private int mMinTemp;
    private Hourly mHourly;

    /** Define getters */
    public String getDate() {
        return mDate;
    }

    public int getMaxTemp() {
        return mMaxTemp;
    }

    public int getMinTemp() {
        return mMinTemp;
    }

    public Hourly getHourly() {
        return mHourly;
    }

    /** populate data */
    @Override
    public void populateData(JSONObject data) {
        mDate = data.optString("date");
        mMaxTemp = data.optInt("maxtempF");
        mMinTemp = data.optInt("mintempF");

        mHourly = new Hourly();
        /** We are only interested in one hourly information (one per day) for the forecast
         * so we only get the first object of the JSON array. */
        mHourly.populateData(data.optJSONArray("hourly").optJSONObject(0));
    }
}
