package com.suraj.examples.myweather;

import model.Location;

/**
 * Created by surajbhattarai on 7/12/15.
 * Interface to define callback method when user selects list item
 * and passes back to Activity
 */
public interface LocationDialogCallback {
    /**
     * This method is called to pass location back to Activity
     * @param location Location information
     */
    void onSelectLocation(Location location);
}
