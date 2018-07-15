package DAO;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ILIAS on 11/7/2018.
 */

public class JsonReader {
    final static String TAG = JsonReader.class.getName();

    public JsonReader() {}

    public String loadJSONFromAsset(AssetManager assetManager) {
        String json = null;
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("dustbins");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        }

        return json;
    }



}
