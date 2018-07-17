package gr.onetouchaway.findeverything.fast_navigator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import Controllers.GoogleMapController;
import Controllers.UtilityControllers.DustbinUtilController;
import Model.Dustbin;

/**
 * Created by ILIAS on 10/7/2018.
 */

public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private List<Dustbin> dustbins = new ArrayList<Dustbin>();

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
        googleMapController.addMarkersToGoogleMap(null, null);

        googleMapController.addMarkersToGoogleMap(googleMap, this.dustbins);
        googleMapController.zoomCameraMap(googleMap, 10.0f);
    }



}
