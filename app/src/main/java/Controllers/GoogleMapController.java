package Controllers;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import Model.Dustbin;

/**
 * Created by ILIAS on 17/7/2018.
 */

public class GoogleMapController {

    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();

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
}
