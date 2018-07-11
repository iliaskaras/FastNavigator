package DAO;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ILIAS on 11/7/2018.
 */

public class JsonReader {


    public JsonReader() {
    }

    public String loadJSONFromAsset(AssetManager assetManager) {
        String json = null;
        try {
            InputStream is = assetManager.open("dustbins");
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
