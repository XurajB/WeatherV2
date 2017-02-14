package service;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by surajbhattarai on 9/11/15.
 */
public class Utils {
    // converts to celsius
    public static float convertFahrenheitToCelsius(float fahrenheit) {
        return ((fahrenheit - 32) * 5 / 9);
    }

    // converts to fahrenheit
    public static float convertCelsiusToFahrenheit(float celsius) {
        return ((celsius * 9) / 5) + 32;
    }

    public static void writeConfiguration(Context ctx) {
        BufferedWriter writer = null;
        try {
            FileOutputStream openFileOutput =
                    ctx.openFileOutput("config.txt", Context.MODE_PRIVATE);
            openFileOutput.write("This is a test1.".getBytes());
            openFileOutput.write("This is a test2.".getBytes());
            openFileOutput.write("This is a test3.".getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
