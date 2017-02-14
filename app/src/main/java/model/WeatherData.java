package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by suraj bhattarai on 7/10/15.
 * POJO to store JSON weather data received from the API call.
 * This class parses the JSON objects and stores in respective objects.
 * Each WeatherData object consists of CurrentCondition and forecast data
 */
public class WeatherData implements DataPopulator {

    /** Define variables */
    private CurrentCondition mCurrentCondition;
    private ArrayList<Weather> mWeatherList;

    /** Define getters */
    public CurrentCondition getCurrentCondition() {
        return mCurrentCondition;
    }

    public ArrayList<Weather> getWeatherList() {
        return mWeatherList;
    }

    /** Populate data */
    @Override
    public void populateData(JSONObject data) {

        /** Populate data - variables */
        mCurrentCondition = new CurrentCondition();
        mWeatherList = new ArrayList<>();

        JSONArray conditionArray = data.optJSONArray("current_condition");
        JSONArray weatherList = data.optJSONArray("weather");

        mCurrentCondition.populateData(conditionArray.optJSONObject(0));

        /** Going through each Weather object and storing in ArrayList */
        for (int count=0; count<weatherList.length(); count++) {
            Weather weather = new Weather();
            weather.populateData(weatherList.optJSONObject(count));
            mWeatherList.add(weather);
        }
    }
}
