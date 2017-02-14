package model;

import org.json.JSONObject;

/**
 * Created by surajbhattarai on 7/12/15.
 * POJO to store Location JSON object received from the search API call
 */
public class Location implements DataPopulator {

    /** Define variables */
    private String mAreaName;
    private String mCountry;
    private String mRegion;

    /** Constructors to create location object */
    public Location() {}

    /** Constructors to create location object with available city and region information */
    public Location(String areaName, String region) {
        this.mRegion = region;
        this.mAreaName = areaName;
    }

    /** Define getters and setters */
    public String getAreaName() {
        return mAreaName;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setAreaName(String mAreaName) {
        this.mAreaName = mAreaName;
    }

    public void setRegion(String mRegion) {
        this.mRegion = mRegion;
    }

    /** populate data */
    @Override
    public void populateData(JSONObject data) {
        mAreaName = data.optJSONArray("areaName").optJSONObject(0).optString("value");
        mCountry = data.optJSONArray("country").optJSONObject(0).optString("value");
        mRegion = data.optJSONArray("region").optJSONObject(0).optString("value");
    }

    @Override
    public String toString() {
        return (this.mAreaName + ", " + this.mRegion);
    }
}
