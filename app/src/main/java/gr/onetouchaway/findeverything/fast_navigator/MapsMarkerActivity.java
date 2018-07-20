package gr.onetouchaway.findeverything.fast_navigator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Controllers.DirectionsParser;
import Controllers.ShortestDistanceController;
import Controllers.GoogleMapController;
import Controllers.UtilityControllers.DustbinUtilController;
import Exceptions.MyDistanceException;
import Model.Dustbin;

/**
 * Created by ILIAS on 10/7/2018.
 */

public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    final static String TAG = MapsMarkerActivity.class.getName();
    private List<Dustbin> dustbins = new ArrayList<Dustbin>();
    private GoogleMap mMap;
    private Marker yourLocationMarker;
    private boolean clickYourLocationClicked = false;
    private boolean locationIsChosed = false;
    private Button btnClickYourLocation;
    private GoogleMapController googleMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initializeDustbinList();
        initializeLatLngMarks();
        googleMapController = new GoogleMapController();

        Button btnFindShortestPath = (Button) findViewById(R.id.btnFindShortestPath);

        btnFindShortestPath.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    findShortestPath();
                } catch (MyDistanceException e) {
                    e.printStackTrace();
                }
            }
        });

        btnClickYourLocation = (Button) findViewById(R.id.btnClickYourLocation);

        btnClickYourLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                btnClickYourLocationClicked(true);
            }
        });

    }

    private void findShortestPath() throws MyDistanceException {
        LatLng foundShortestDest = ShortestDistanceController.findShortestPath(dustbins, yourLocationMarker);

//        googleMapController.manuallyDrawDirection(yourLocationMarker.getPosition(),foundShortestDest,mMap,dustbins);


        String url = getRequestURL(foundShortestDest, yourLocationMarker.getPosition());
        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
        taskRequestDirections.execute(url);
    }

    private String getRequestURL(LatLng destination, LatLng origin ){

        //value of origin
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        //value of dest
        String str_destination = "destination=" + destination.latitude + "," + destination.longitude;
        //set value enable the sensor
        String sensor = "sensor=false";
        //mode for finding direction
        String mode = "mode=driving";
        //building of the full param
        String param = str_origin + "&" + str_destination + "&" + sensor + "&" + mode;
        //output format
        String output = "json";

        //create url to request "https://maps.google.googleapis.com/maps/api/directions/"
        String url = "https://maps.google.googleapis.com/maps/api/directions/" + output + "?" + param;

        return url;
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String responseString = "";
            try{
                responseString = requestDirection(params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Parse json here
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void,  List<List<HashMap<String, String>>>>{


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

            ArrayList points = null;
            PolylineOptions polylineOptions = null;

            for(List<HashMap<String, String>> path : lists){
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for(HashMap<String, String> point : path){
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);

            }

            if(polylineOptions!=null){
                mMap.addPolyline(polylineOptions);
            } else {
                Toast.makeText(getApplicationContext(),"direction not found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String requestDirection(String reqUrl) throws IOException {

        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response request
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while((line = bufferedReader.readLine())!= null){
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(inputStream != null){
                inputStream.close();
            }
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
        }

        return responseString;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapController.addMarkersToGoogleMap(googleMap, this.dustbins);
        googleMapController.zoomCameraMap(googleMap, 10.0f);

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(!clickYourLocationClicked) return false;

        if(yourLocationMarker != null){
            try{
                locationIsChosed = true;
                yourLocationMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            } catch (Exception ex){
                Log.d(TAG+"onMarkerClick exception", ex.getMessage());
            }

        }

        yourLocationMarker = marker;
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        return false;
    }


    private void btnClickYourLocationClicked(boolean isClicked){
        if(isClicked){
            clickYourLocationClicked = true;
            btnClickYourLocation.setClickable(false);
            btnClickYourLocation.setPressed(true);
            btnClickYourLocation.setText("Choose your location in map");

        } else {
            locationIsChosed = false;
            clickYourLocationClicked = false;
            btnClickYourLocation.setClickable(true);
            btnClickYourLocation.setPressed(false);
            btnClickYourLocation.setText("Click your location");

        }
    }

    private void initializeDustbinList(){
        Intent i = getIntent();
        this.dustbins = (List<Dustbin>) i.getSerializableExtra("dustbins");
    }

    private void initializeLatLngMarks(){
        DustbinUtilController dustbinUtilController = new DustbinUtilController();
        this.dustbins = dustbinUtilController.initializeLatLngMarks(this.dustbins);
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        if(latLngList.size() > 2){
//            refreshMap(mMap);
//            latLngList.clear();
//        }
//
//        latLngList.add(latLng);
//        Log.d(TAG, "Marker number " + latLngList.size());
//
//        MarkerOptions options = new MarkerOptions();
//        options.position(this.dustbins.get(0).getLatLng());
//        options.title(this.dustbins.get(0).getLocation());
//        mMap.addMarker(options);
//        mMap.addMarker(new MarkerOptions().position(latLng));
//        LatLng defaultLocation = options.getPosition();
//        LatLng destinationLocation = latLng;
//        //use Google Direction API to get the route between these Locations
////        String directionApiPath = Helper.getUrl(String.valueOf(defaultLocation.latitude), String.valueOf(defaultLocation.longitude),
////                String.valueOf(destinationLocation.latitude), String.valueOf(destinationLocation.longitude));
////        Log.d(TAG, "Path " + directionApiPath);
//
//        if(latLngList.size() > 1){
//            drawRouteOnMap(mMap,latLngList);
//
//        }
//        drawRouteOnMap(mMap,latLngList);
    }
}
