package Controllers.UtilityControllers;

import Exceptions.MyCoordinateException;
import Exceptions.MyDustbinException;
import Model.Coordinates;
import Model.Dustbin;

/**
 * Created by ILIAS on 12/7/2018.
 */

public class UtilityController {


    public UtilityController() {
    }


    public Dustbin createDustbin(String dustbinLocation, String dustbinLatitude, String dustbinLongitude) throws MyCoordinateException, MyDustbinException {
        CoordinateUtilController coordinateUtilController = new CoordinateUtilController(dustbinLatitude,dustbinLongitude);
        Coordinates _coordinates = coordinateUtilController.getCoordinateObject();
        DustbinUtilController dustbinUtilController = new DustbinUtilController(dustbinLocation,_coordinates);
        Dustbin _dustbin = dustbinUtilController.getDustbinObject();

        return _dustbin;

    }

}
