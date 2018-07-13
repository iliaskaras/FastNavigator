package Controllers.UtilityControllers;

import android.util.Log;

import Exceptions.ErrorCodes.ExceptionErrorCodes;
import Exceptions.MyCoordinateException;
import Model.Coordinates;

/**
 * Created by ILIAS on 13/7/2018.
 */

public class CoordinateUtilController {
    final static String TAG = CoordinateUtilController.class.getName();
    private String stringLat;
    private String stringLongitude;
    private double lat;
    private double longitude;
    private Coordinates coordinates;

    public CoordinateUtilController() {
    }

    public CoordinateUtilController(String lat, String longitude) {
        this.stringLat = lat;
        this.stringLongitude = longitude;
    }

    public Coordinates getCoordinateObject() throws MyCoordinateException {
        double latResult = 0.0;
        double longitudeResult = 0.0;

        try{
            latResult = latitudeValidation(this.stringLat);
            longitudeResult = longitudeValidation(this.stringLongitude);
        } catch (MyCoordinateException ex){
            /** exception case handling just for demonstrate*/
//            switch (ex.getCode().getErrorCode()) {
//                case ExceptionErrorCodes.CODE_WRONG_LAT:
//                    Log.d(TAG,ExceptionErrorCodes.WRONG_LAT.getErrorMessage());
//                    break;
//                default:
//                    break;
//            }
            /** exception case handling just for demonstrate*/
            Log.d(TAG, ex.getMessage());
            throw new MyCoordinateException(ExceptionErrorCodes.WRONG_COORDINATES);
        }

        Coordinates coordinates = new Coordinates();
        coordinates.setLat(latResult);
        coordinates.setLongitude(longitudeResult);

        return coordinates;
    }

    public String getStringLat() {
        return stringLat;
    }

    public void setStringLat(String stringLat) {
        this.stringLat = stringLat;
    }

    public String getStringLongitude() {
        return stringLongitude;
    }

    public void setStringLongitude(String stringLongitude) {
        this.stringLongitude = stringLongitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private double latitudeValidation(String lat) throws MyCoordinateException{
        double latResult = 0.0;

        try{

            latResult = Double.parseDouble(lat);

        } catch(NullPointerException | NumberFormatException ex){
            Log.d(TAG, ex.getMessage());
            throw new MyCoordinateException(ex, ExceptionErrorCodes.WRONG_LAT);

        }

        return latResult;

    }

    private double longitudeValidation(String longitude) throws MyCoordinateException{
        double longitudeResult = 0.0;

        try{

            longitudeResult = Double.parseDouble(longitude);

        } catch(NullPointerException | NumberFormatException ex){
            Log.d(TAG, ex.getMessage());
            throw new MyCoordinateException(ex, ExceptionErrorCodes.WRONG_LONG);
        }

        return longitudeResult;

    }
}
