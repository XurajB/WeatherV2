package service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.suraj.examples.myweather.R;

import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by suraj bhattarai on 7/11/15.
 * This class downloads a weather icon from provided URL using worker thread.
 * Once the icon has been downloaded, it is applied to the passed ImageView object
 */
public class DownloadIconTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView iconImageView;

    /**
     * Constructor to pass icon URL and ImageView object
     * @param iconUrl The URL for the weather icon
     * @param iconView The imageView where icon should be placed
     * @throws MalformedURLException if provided URL is not valid
     */
    public DownloadIconTask(String iconUrl, ImageView iconView) throws MalformedURLException {
        this.iconImageView = iconView;
        iconImageView.setImageResource(R.drawable.loading);
        execute(iconUrl);
    }

    /** Download image */
    protected Bitmap doInBackground(String... args) {
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(args[0]).openStream();
            mIcon = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon;
    }

    /** Download is complete. Apply the image to ImageView */
    protected void onPostExecute(Bitmap result) {
        iconImageView.setImageBitmap(result);
    }
}