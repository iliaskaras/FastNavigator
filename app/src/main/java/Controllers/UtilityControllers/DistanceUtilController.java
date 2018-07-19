package Controllers.UtilityControllers;

import com.google.android.gms.maps.model.LatLng;

import Model.Distance;

/**
 * Created by ILIAS on 19/7/2018.
 */

public class DistanceUtilController {
    final static String TAG = DistanceUtilController.class.getName();
    private double distance;
    private String destLocation;
    private LatLng location;

    public DistanceUtilController() {
    }

    public DistanceUtilController(double distance, String destLocation, LatLng location) {
        this.distance = distance;
        this.destLocation = destLocation;
        this.location = location;
    }

    public Distance getDistanceObject(double distance, String destLocation, LatLng location){

        Distance distanceObject = new Distance(distance,destLocation,location);

        return distanceObject;
    }


}
