package model;

import org.json.JSONObject;

/**
 * Created by suraj bhattarai on 7/10/15.
 * POJO to store current condition JSON object received from the API call
 */
public class CurrentCondition implements DataPopulator {

    /** Define variables */
    private double mPrecipitation;
    private String mDescription;
    private String mIconUrl;
    private int mTemperature;

    /** Define getters */
    public double getPrecipitation() {
        return mPrecipitation;
    }
    public String getDescription() {
        return mDescription;
    }
    public String getIconUrl() {
        return mIconUrl;
    }
    public int getTemperature() {
        return mTemperature;
    }

    /** populate data */
    @Override
    public void populateData(JSONObject data) {
        this.mPrecipitation = data.optDouble("precipMM");
        this.mDescription = data.optJSONArray("weatherDesc").optJSONObject(0).optString("value");
        this.mIconUrl = data.optJSONArray("weatherIconUrl").optJSONObject(0).optString("value");
        this.mTemperature = data.optInt("temp_F");
    }
}
