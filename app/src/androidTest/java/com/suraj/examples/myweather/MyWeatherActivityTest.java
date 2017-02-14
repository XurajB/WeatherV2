package com.suraj.examples.myweather;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import org.junit.Test;

/**
 * Created by surajbhattarai on 9/13/15.
 */
public class MyWeatherActivityTest extends ActivityInstrumentationTestCase2<MyWeatherActivity> {

    private MyWeatherActivity mMyWeatherActivity;
    private TextView mTextViewTemperature;

    public MyWeatherActivityTest() {
        super(MyWeatherActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mMyWeatherActivity = getActivity();
        mTextViewTemperature = (TextView) mMyWeatherActivity.findViewById(R.id.weather_activity_text_view_temperature);

    }

    public void testTextViewTemperature_labelText() {
        final String expected = mMyWeatherActivity.getString(R.string.weather_activity_temperature_text);
        final String actual = mTextViewTemperature.getText().toString();

        assertEquals(expected, actual);
    }


}
