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

import Adapters.CustomAdapter;
import Controllers.AsyncTaskControllers.DownloadTaskListener;
import Controllers.AsyncTaskControllers.PutDataTaskController;
import Controllers.AsyncTaskControllers.RequestDirectionsTaskController;
import Controllers.AsyncTaskControllers.TaskStrategyContext;
import Controllers.DAO_Controllers.FileReaderController;
import Controllers.DAO_Controllers.FileWriterController;
import Controllers.DAO_Controllers.JsonController;
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
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
                   GoogleMap.OnMarkerClickListener, DownloadTaskListener {

    final static String TAG = MapsMarkerActivity.class.getName();
    private List<Dustbin> dustbins = new ArrayList<Dustbin>();
    private GoogleMap mMap;
    private Marker yourLocationMarker;
    private boolean clickYourLocationClicked = false;
    private boolean locationIsChosed = false;
    private Button btnClickYourLocation;
    private GoogleMapController googleMapController;
    private TaskStrategyContext taskStrategyContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initializeDustbinList();
        initializeLatLngMarks();
        taskStrategyContext = new TaskStrategyContext();
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
        googleMapController.refreshMap(mMap);
        googleMapController.addMarkersToGoogleMap(mMap,dustbins);

        String url = getRequestURL(foundShortestDest, yourLocationMarker.getPosition());
        taskStrategyContext.setTaskStrategy(new RequestDirectionsTaskController( MapsMarkerActivity.this, mMap));
        taskStrategyContext.executeStrategy(url);
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

        //create url to request "https://maps.googleapis.com/maps/api/directions/"
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param;

        return url;
    }

    @Override
    public void onDownloadFinish(final String downloadedData) {
        MapsMarkerActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {


            }
        });
    }

    @Override
    public void onDownloadProgress(float progress) {
        MapsMarkerActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {

            }
        });
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
