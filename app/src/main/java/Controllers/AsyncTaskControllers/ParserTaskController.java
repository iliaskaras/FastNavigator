package Controllers.AsyncTaskControllers;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Controllers.DirectionsParser;

/**
 * Created by ILIAS on 26/7/2018.
 */

public class ParserTaskController extends AsyncTask<String, Void,  List<List<HashMap<String, String>>>> {
    private Context mContext;
    private GoogleMap mMap;

    public ParserTaskController(Context mContext, GoogleMap mMap) {
        this.mContext = mContext;
        this.mMap = mMap;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... params) {
        JSONObject jsonObject = null;
        List<List<HashMap<String, String>>> routes = null;
        try {
            jsonObject = new JSONObject(params[0]);
            DirectionsParser directionsParser = new DirectionsParser();
            routes = directionsParser.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
        //Get list of routes and display them on map
        if(lists.isEmpty()) throw new IllegalArgumentException("onPostExecute of TaskParser exception : lists is empty, failed to request url from HTTPConnection");
        ArrayList points = null;
        PolylineOptions polylineOptions = null;

        for(List<HashMap<String, String>> path : lists){
            points = new ArrayList();
            polylineOptions = new PolylineOptions();

            for(HashMap<String, String> point : path){
                double lat = Double.parseDouble(point.get("lat"));
                double lon = Double.parseDouble(point.get("lon"));
                points.add(new LatLng(lat,lon));

            }

            polylineOptions.addAll(points);
            polylineOptions.width(15);
            polylineOptions.color(Color.BLUE);
            polylineOptions.geodesic(true);

        }

        if(polylineOptions!=null){
            this.mMap.addPolyline(polylineOptions);
        } else {
            Toast.makeText(this.mContext,"direction not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
