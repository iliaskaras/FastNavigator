package Controllers.UtilityControllers;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import Exceptions.ErrorCodes.ExceptionErrorCodes;
import Exceptions.MyCoordinateException;
import Exceptions.MyDistanceException;
import Exceptions.MyDustbinException;
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

    public Distance getDistanceObject(double distance, String destLocation, LatLng location) throws MyDistanceException {
        try{
            locationValidation(destLocation);
        } catch (MyDistanceException ex){
            Log.d(TAG, ex.getMessage());
            throw new MyDistanceException(ExceptionErrorCodes.WRONG_LOCATION);
        }
        Distance distanceObject = new Distance(distance,destLocation,location);

        return distanceObject;
    }

    private boolean locationValidation(String location) throws MyDistanceException{
        boolean isLocationEntered = false;

        try{

            if (!location.isEmpty()){
                isLocationEntered = true;
            }

        } catch(NullPointerException ex){
            Log.d(TAG, ex.getMessage());
            throw new MyDistanceException(ex, ExceptionErrorCodes.WRONG_LOCATION);
        }

        return isLocationEntered;
    }




}
