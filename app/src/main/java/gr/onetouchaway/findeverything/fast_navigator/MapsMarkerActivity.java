package gr.onetouchaway.findeverything.fast_navigator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import Controllers.DijskstraController;
import Controllers.GoogleMapController;
import Controllers.UtilityControllers.DustbinUtilController;
import Model.Dustbin;

/**
 * Created by ILIAS on 10/7/2018.
 */

public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private List<Dustbin> dustbins = new ArrayList<Dustbin>();
    private List<LatLng> latLngList;
    private GoogleMap mMap;
    final static String TAG = MapsMarkerActivity.class.getName();
    private Marker lastSelectedDustbinLocation;
    // Include the OnCreate() method here too, as described above.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.fragment_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initializeDustbinList();
        initializeLatLngMarks();

        Button btnFindShortestPath = (Button) findViewById(R.id.btnFindShortestPath);

        btnFindShortestPath.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                LatLng foundShortestPath = DijskstraController.findShortestPath(dustbins,lastSelectedDustbinLocation);
//                manuallyDrawDirection(foundShortestPath);

                GoogleMapController googleMapController = new GoogleMapController();
                googleMapController.manuallyDrawDirection(foundShortestPath,latLngList,mMap,dustbins);
            }
        });

        latLngList = new ArrayList<LatLng>();


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
    public void onMapReady(GoogleMap googleMap) {
        GoogleMapController googleMapController = new GoogleMapController();
        googleMapController.addMarkersToGoogleMap(googleMap, this.dustbins);
        googleMapController.zoomCameraMap(googleMap, 10.0f);

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
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

    private void drawRouteOnMap(GoogleMap map, List<LatLng> positions){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.addAll(positions);
        Polyline polyline = map.addPolyline(options);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(positions.get(1).latitude, positions.get(1).longitude))
                .zoom(11)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    private void refreshMap(GoogleMap mapInstance){
        mapInstance.clear();
    }

    private void resetMap(GoogleMap mapInstance){
        GoogleMapController googleMapController = new GoogleMapController();
        googleMapController.addMarkersToGoogleMap(mapInstance, this.dustbins);
        googleMapController.zoomCameraMap(mapInstance, 10.0f);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.d(TAG, "MARKER " + marker.getTitle());
        lastSelectedDustbinLocation = marker;
        if(latLngList.size() > 2){
            refreshMap(mMap);
            resetMap(mMap);
            latLngList.clear();
        }

        latLngList.add(marker.getPosition());

        if(latLngList.size() > 1){
            GoogleMapController googleMapController = new GoogleMapController();
            googleMapController.drawRouteOnMap(mMap,latLngList);
        }

        return false;
    }

    private void manuallyDrawDirection(LatLng foundShortestPath){
        if(latLngList.size() > 2){
            refreshMap(mMap);
            resetMap(mMap);
            latLngList.clear();
        }
        latLngList.add(dustbins.get(0).getLatLng());
        latLngList.add(foundShortestPath);

        if(latLngList.size() > 1){
            drawRouteOnMap(mMap,latLngList);
        }
    }
}
