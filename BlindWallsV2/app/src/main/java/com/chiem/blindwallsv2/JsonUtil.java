package com.chiem.blindwallsv2;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {
    public static String loadJSONFromAsset(Activity activity) {
        String json = null;
        try {
            //Changed from: InputStream is = activity.getAssets().open("walls.json");
            //TO:
            InputStream is = activity.getResources().openRawResource(R.raw.walls);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}