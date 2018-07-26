package Controllers;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import Model.Dustbin;

/**
 * Created by ILIAS on 17/7/2018.
 */

public class GoogleMapController {

    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();
    private Polyline polyline;

    public GoogleMapController() {
    }

    public GoogleMapController(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public GoogleMapController(GoogleMap googleMap, MarkerOptions options) {
        this.googleMap = googleMap;
        this.options = options;
    }

    public void addMarkersToGoogleMap(GoogleMap googleMap, List<Dustbin> dustbins){
        if(!googleMapGiven(googleMap) || !dustbinListGiven(dustbins)) return;

        MarkerOptions options = new MarkerOptions();

        for (Dustbin dustbin : dustbins){
            options.position(dustbin.getLatLng());
            options.title(dustbin.getLocation());
            googleMap.addMarker(options);
        }
    }

    public void zoomCameraMap(GoogleMap googleMap, float zoomLevel){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.092083, 23.541016),zoomLevel));
    }

    private boolean googleMapGiven(GoogleMap googleMap){
        return (googleMap != null);
    }

    private boolean dustbinListGiven(List<Dustbin> dustbins){
        return (!dustbins.isEmpty());
    }

    public void drawRouteOnMap(GoogleMap map, List<LatLng> positions){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.addAll(positions);
        Polyline polyline = map.addPolyline(options);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(positions.get(1).latitude, positions.get(1).longitude))
                .zoom(11)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void drawRouteOnMap(GoogleMap map, LatLng source, LatLng destination){
        List<LatLng> positions = new ArrayList<>();
        positions.add(source);
        positions.add(destination);

        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.addAll(positions);
        polyline = map.addPolyline(options);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(source.latitude, source.longitude))
                .zoom(11)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    public void manuallyDrawDirection(LatLng foundShortestPath, List<LatLng> latLngList, GoogleMap mMap, List<Dustbin> dustbins){
        if(latLngList.size() > 2){
            refreshMap(mMap);
            resetMap(mMap,dustbins);
            latLngList.clear();
        }
        latLngList.add(dustbins.get(0).getLatLng());
        latLngList.add(foundShortestPath);

        if(latLngList.size() > 1){
            drawRouteOnMap(mMap,latLngList);
        }
    }

    public void manuallyDrawDirection(LatLng source, LatLng destination, GoogleMap mMap, List<Dustbin> dustbins){
        resetMap(mMap,dustbins);
        if(polyline!=null) polyline.remove();
        drawRouteOnMap(mMap,source,destination);
    }

    public void refreshMap(GoogleMap mapInstance){
        if(mapInstance==null) throw new IllegalArgumentException("you haven't specify map object to clear!");
        mapInstance.clear();
    }

    private void resetMap(GoogleMap mapInstance, List<Dustbin> dustbins){
        addMarkersToGoogleMap(mapInstance, dustbins);
        zoomCameraMap(mapInstance, 10.0f);
    }
}
