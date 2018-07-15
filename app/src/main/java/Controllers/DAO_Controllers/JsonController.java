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

//            // implement for loop for getting users list data
//            for (int i = 0; i < dustbinArray.length(); i++) {
//                // create empty dustbin object to fill
//                Dustbin dustbin = new Dustbin();
//                Id _id = new Id();
//                Coordinates coordinates = new Coordinates();
//                // create a JSONObject for fetching single dustbin data
//                JSONObject dustbinDetail = dustbinArray.getJSONObject(i);
//                // fetch location, id, coordinates and store it in dustbin object
//                dustbin.setLocation(dustbinDetail.get("location").toString());
//                _id.setOid(dustbinDetail.get("_id").toString());
//                coordinates.setStringLat((Double) dustbinDetail.get("lat"));
//                coordinates.setStringLongitude((Double) dustbinDetail.get("longitude"));
//
//                dustbin.setCoordinates(coordinates);
//                dustbin.set_id(_id);
//
//                dustbins.add(dustbin);
//            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return dustbins;
    }

    public List<Dustbin> getDustbinLists(String jsonString){
        List<Dustbin> dustbins = new ArrayList<>();
        try {
            /** Done Process */
            /** Use Gson to parse Json to Class*/
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Dustbin>>(){}.getType();
            dustbins = gson.fromJson(jsonString,listType);

//            // implement for loop for getting users list data
//            for (int i = 0; i < dustbinArray.length(); i++) {
//                // create empty dustbin object to fill
//                Dustbin dustbin = new Dustbin();
//                Id _id = new Id();
//                Coordinates coordinates = new Coordinates();
//                // create a JSONObject for fetching single dustbin data
//                JSONObject dustbinDetail = dustbinArray.getJSONObject(i);
//                // fetch location, id, coordinates and store it in dustbin object
//                dustbin.setLocation(dustbinDetail.get("location").toString());
//                _id.setOid(dustbinDetail.get("_id").toString());
//                coordinates.setStringLat((Double) dustbinDetail.get("lat"));
//                coordinates.setStringLongitude((Double) dustbinDetail.get("longitude"));
//
//                dustbin.setCoordinates(coordinates);
//                dustbin.set_id(_id);
//
//                dustbins.add(dustbin);
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dustbins;
    }
}
