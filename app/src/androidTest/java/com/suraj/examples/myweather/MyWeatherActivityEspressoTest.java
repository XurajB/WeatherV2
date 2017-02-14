package com.suraj.examples.myweather;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by surajbhattarai on 9/13/15.
 */

@RunWith(AndroidJUnit4.class)
public class MyWeatherActivityEspressoTest {

    @Rule
    public ActivityTestRule<MyWeatherActivity> rule = new ActivityTestRule<>(MyWeatherActivity.class);

    @Test
    public void ensureCityName() {
        onView(withId(R.id.weather_activity_text_view_location)).check(matches(withText("Chicago, Illinois")));
    }

    @Test
    public void ensure5DayForcastFragmentIsLoaded() {
        onView(withId(R.id.weather_activity_relative_layout_main)).perform(click());

        ViewInteraction fragmentDescriptionTest = onView(withId(R.id.weather_fragment_forecast_grid));
        fragmentDescriptionTest.check(ViewAssertions.matches(isDisplayed()));
    }
}
