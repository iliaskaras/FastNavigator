package Controllers;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Controllers.UtilityControllers.DistanceUtilController;
import Exceptions.MyDistanceException;
import Model.CustomDistanceComparator;
import Model.Distance;
import Model.Dustbin;

/**
 * Created by ILIAS on 19/7/2018.
 */

public class DijkstraController {


    public DijkstraController() {
    }


    public static LatLng findShortestPath(List<Dustbin> dustbinList, Marker currentDustbinLocation) throws MyDistanceException {

        String location = currentDustbinLocation.getTitle();
        LatLng latLng = currentDustbinLocation.getPosition();

        List<Distance> nodeDistances = calculateDistances(dustbinList, latLng);

        Distance foundShortestDistance = getShortestRoute(nodeDistances);

        return foundShortestDistance.getLocation();

    }

    private static Distance getShortestRoute(List<Distance> nodeDistances){

        Collections.sort(nodeDistances, new CustomDistanceComparator());

        /** remove the first from the list because it has distance 0 meaning it compares to itself */
        return nodeDistances.get(1);
    }


    private static List<Distance> calculateDistances(List<Dustbin> dustbinList, LatLng currentDustbinLocation) throws MyDistanceException {
        DistanceUtilController distanceUtilController = new DistanceUtilController();
        List<Distance> distances = new ArrayList<>();
        LatLng startLatLng = new LatLng(currentDustbinLocation.latitude, currentDustbinLocation.longitude);

        for(Dustbin dustbin : dustbinList){
            double distance = getDistance(startLatLng,dustbin);
            Distance distanceObject = distanceUtilController.getDistanceObject(distance,dustbin.getLocation(),dustbin.getLatLng());
            distances.add(distanceObject);
        }

        return distances;
    }

    private static double getDistance(LatLng startLatLng, Dustbin endDustbin){
        double distance = 0.0;
        LatLng endLatLng = new LatLng(endDustbin.getLatLng().latitude, endDustbin.getLatLng().longitude);
        distance = SphericalUtil.computeDistanceBetween(startLatLng, endLatLng);

        return distance;
    }


}
