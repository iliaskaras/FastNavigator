package Controllers.UtilityControllers;

import Model.Coordinates;
import Model.Dustbin;
import Model.Id;

/**
 * Created by ILIAS on 12/7/2018.
 */

public class UtilityController {

    private Id _id;
    private Coordinates coordinates;
    private Dustbin dustbin;

    public UtilityController() {
    }


    public Dustbin createDustbin(String dustbinLocation, String dustbinLatitude, String dustbinLongitude){

        Coordinates _coordinates = new Coordinates(Double.parseDouble(dustbinLatitude),Double.parseDouble(dustbinLongitude));
        Dustbin dustbin = new Dustbin(dustbinLocation,_coordinates);

        return dustbin;

    }

}
