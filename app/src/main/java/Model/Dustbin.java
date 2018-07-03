package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class Dustbin {

    private String location;
    private List<Coordinate> coordinates = new ArrayList<Coordinate>();

    public Dustbin(String location, List<Coordinate> coordinates) {
        this.location = location;
        this.coordinates = coordinates;
        try{

        }catch(Exception ex){
            System.out.println("exception caught");
        }
    }


}
