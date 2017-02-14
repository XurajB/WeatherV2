package model;

import org.json.JSONObject;

/**
 * Created by suraj bhattarai on 7/10/15.
 * Interface with populateData method. Classes implementing this interface implement
 * this method to populate POJOs from the JSON data received from the API call.
 */
public interface DataPopulator {
    /**
     * Populate JSON data received from API call
     * @param data JSON object data
     */
    void populateData(JSONObject data);
}
