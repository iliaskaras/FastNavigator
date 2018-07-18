package Controllers;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.CustomDistanceComparator;
import Model.Distance;
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

        Distance foundShortestDistance = dijkstraAlgorithm(dustbinList,latLng);

        return foundShortestDistance.getLocation();

    }

    private static Distance dijkstraAlgorithm(List<Dustbin> dustbinList, LatLng currentDustbinLocation){

        List<Distance> distances = new ArrayList<>();

        LatLng startLatLng = new LatLng(currentDustbinLocation.latitude, currentDustbinLocation.longitude);

        for(Dustbin dustbin : dustbinList){
            LatLng endLatLng = new LatLng(dustbin.getLatLng().latitude, dustbin.getLatLng().longitude);
            double distance = SphericalUtil.computeDistanceBetween(startLatLng, endLatLng);
            Distance distanceObject = new Distance(distance,dustbin.getLocation(),dustbin.getLatLng());
            distances.add(distanceObject);
        }

        Collections.sort(distances, new CustomDistanceComparator());

        /** remove the first from the list because it has distance 0 meaning it compares to itself */
        return distances.get(1);
    }

}
