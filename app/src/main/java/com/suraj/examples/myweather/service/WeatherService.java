package com.suraj.examples.myweather.service;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.suraj.examples.myweather.R;
import com.suraj.examples.myweather.model.Location;
import com.suraj.examples.myweather.model.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by suraj bhattarai on 7/10/15.
 * This class gets weather data from the World Weather Online API using unique API KEY.
 */
public class WeatherService {
    private ServiceCallback mServiceCallback;

    // dont need context
    private Context mContext;

    /** Unique API key and End point URL */
    private static final String API_KEY = "1c948d9233c04bb9b7940648171402";
    private static final String API_ENDPOINT = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=%s&q=%s&num_of_days=5&tp=24&format=json";

    /** Constructor to pass a callback object and context */
    public WeatherService(ServiceCallback serviceCallback, Context context) {
        this.mServiceCallback = serviceCallback;
        this.mContext = context;
    }

    /**
     * Get weather data from API using worker thread. Open URL connection and fetch data line by line
     * @param location location
     */
    public void getWeatherData(final Location location) {
        new AsyncTask<Location, Void, String>() {
            @Override
            protected String doInBackground(Location... params) {
                /** Combine API URL, API key and location into one URL */
                String apiUrl = String.format(API_ENDPOINT,Uri.encode(API_KEY),
                        Uri.encode(location.getAreaName()) + "," + Uri.encode(location.getRegion()));
                try {
                    URL endPointUrl = new URL(apiUrl);
                    URLConnection urlConnection = endPointUrl.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                    StringBuilder data = new StringBuilder();
                    String readLine;
                    while ((readLine = bufferedReader.readLine()) != null) {
                        data.append(readLine);
                    }
                    /** Close connections */
                    inputStream.close();
                    bufferedReader.close();
                    /** Return weather data */
                    return data.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                /** Program returns null only when we receive
                 * exception getting data above */
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                /** Send error message to the UI if we get null data */
                if (result == null) {
                    mServiceCallback.onFailure(mContext.getString(R.string.connection_failed_text));
                } else {
                    try {
                        /** Parse JSON data */
                        JSONObject query = new JSONObject(result);
                        JSONObject data = query.optJSONObject("data");
                        /** API sends back an error object in case of any exception. If we receive
                         * an error object - we pass the error message back to the Activity using callback object */
                        JSONArray error = data.optJSONArray("error");
                        if (error!=null) {
                            mServiceCallback.onFailure(location+ " "+ error.optJSONObject(0).optString("msg"));
                            return;
                        }
                        /** If no error - create a WeatherData object and populate */
                        WeatherData weatherData = new WeatherData();
                        weatherData.populateData(data);
                        mServiceCallback.onSuccess(weatherData);

                    } catch (JSONException e) {
                        /** In case of any exception, send the message back to Activity */
                        e.printStackTrace();
                        mServiceCallback.onFailure(e.getMessage());
                    }
                }
            }
        }.execute(location);
    }
}