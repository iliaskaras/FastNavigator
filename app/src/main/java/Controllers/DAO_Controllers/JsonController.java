package Controllers.DAO_Controllers;

import android.content.res.AssetManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import DAO.JsonReader;
import Model.Dustbin;

/**
 * Created by ILIAS on 11/7/2018.
 */

public class JsonController {
    final static String TAG = JsonController.class.getName();
    private AssetManager assetManager;

    public JsonController(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public JsonController() {
    }

    public List<Dustbin> getDustbinLists(){
        JsonReader jsonReader = new JsonReader();
        List<Dustbin> dustbins = new ArrayList<>();
        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(jsonReader.loadJSONFromAsset(assetManager));
            // fetch JSONArray dustbins
            JSONArray dustbinArray = obj.getJSONArray("dustbins");

            /** Done Process */
            /** Use Gson to parse Json to Class*/
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Dustbin>>(){}.getType();
            dustbins = gson.fromJson(dustbinArray.toString(),listType);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return dustbins;
    }

    //TODO make method generic
    public List<Dustbin> getDustbinLists(String jsonString){
        List<Dustbin> dustbins = new ArrayList<>();
        try {
            /** Done Process */
            /** Use Gson to parse Json to Class*/
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Dustbin>>(){}.getType();
            dustbins = gson.fromJson(jsonString,listType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dustbins;
    }
}
