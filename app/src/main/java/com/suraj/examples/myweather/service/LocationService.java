package com.suraj.examples.myweather.service;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.suraj.examples.myweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.suraj.examples.myweather.model.Location;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by surajbhattarai on 7/12/15.
 * This class gets location data from the Search API
 */
public class LocationService {
    private ServiceCallback mServiceCallback;
    private Context mContext;
    private OkHttpClient client;

    private static final String API_ENDPOINT = "http://api.worldweatheronline.com/premium/v1/search.ashx?key=%s&query=%s&num_of_results=5&format=json";

    /** Constructor to pass a callback object and context */
    public LocationService(ServiceCallback serviceCallback, Context context) {
        this.mServiceCallback = serviceCallback;
        this.mContext = context;
        this.client = new OkHttpClient();
    }

    /**
     * Get possible location data from API using worker thread. Open URL connection and fetch data line by line
     * @param query location query
     */
    public void getLocationData(final String query) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                /** Combine API URL, API key and query into one URL */
                String apiUrl = String.format(API_ENDPOINT, Uri.encode(WeatherService.API_KEY),Uri.encode(query));
                try {
                    Request request = new Request.Builder()
                            .url(apiUrl)
                            .build();

                    Response response = client.newCall(request).execute();
                    return response.body().string();

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
                        JSONObject data = query.optJSONObject("search_api");
                        /** API sends back an error object in case of any exception. If we receive
                         * an error object - we pass the error message back to the Activity using callback object */
                        JSONObject errorData = query.optJSONObject("data");
                        if (errorData !=null) {
                            JSONArray error = errorData.optJSONArray("error");
                            if (error != null) {
                                mServiceCallback.onFailure(error.optJSONObject(0).optString("msg"));
                                return;
                            }
                        }

                        /** If no error - create a list of Location objects and populate data */
                        ArrayList<Location> locationList = new ArrayList<>();
                        JSONArray locationResults = data.getJSONArray("result");

                        for (int count=0; count<locationResults.length(); count++) {
                            Location location = new Location();
                            location.populateData(locationResults.optJSONObject(count));
                            locationList.add(location);
                        }
                        mServiceCallback.onSuccess(locationList);

                    } catch (JSONException e) {
                        /** In case of any exception, send the message back to Activity */
                        e.printStackTrace();
                        mServiceCallback.onFailure(e.getMessage());
                    }
                }
            }
        }.execute(query);
    }
}