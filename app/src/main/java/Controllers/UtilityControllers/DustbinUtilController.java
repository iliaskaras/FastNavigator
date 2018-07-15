package Controllers.UtilityControllers;

import android.util.Log;
import Exceptions.ErrorCodes.ExceptionErrorCodes;
import Exceptions.MyDustbinException;
import Model.Coordinates;
import Model.Dustbin;

/**
 * Created by ILIAS on 13/7/2018.
 */

public class DustbinUtilController {
    final static String TAG = DustbinUtilController.class.getName();
    private String location;
    private Coordinates coordinates;

    public DustbinUtilController() {}

    public DustbinUtilController(String location, Coordinates coordinates) {
        this.location = location;
        this.coordinates = coordinates;
    }

    /** getDustbinObject we are not in need of checking coordinates status, we already did
     *  in CoordinateUtilController */
    public Dustbin getDustbinObject() throws MyDustbinException {
        
        try{
            locationValidation(this.location);
        } catch (MyDustbinException ex){
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
            throw new MyDustbinException(ExceptionErrorCodes.WRONG_LOCATION);
        }

        Dustbin dustbin = new Dustbin(this.location, this.coordinates);
        return dustbin;
    }

    private boolean locationValidation(String location) throws MyDustbinException{
        boolean isLocationEntered = false;

        try{

            if (!location.isEmpty()){
                isLocationEntered = true;
            }

        } catch(NullPointerException ex){
            Log.d(TAG, ex.getMessage());
            throw new MyDustbinException(ex, ExceptionErrorCodes.WRONG_LOCATION);
        }

        return isLocationEntered;
    }

}
