package Controllers;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import Model.Dustbin;

/**
 * Created by ILIAS on 10/7/2018.
 */

public class DijkstraController {



    public DijkstraController() {
    }

    public static LatLng findShortestPath(List<Dustbin> dustbinList, Marker currentDustbinLocation){

        String location = currentDustbinLocation.getTitle();
        LatLng latLng = currentDustbinLocation.getPosition();

        return latLng;

    }

    private void

}
