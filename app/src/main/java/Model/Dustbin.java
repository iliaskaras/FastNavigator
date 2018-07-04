package Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class Dustbin {

    @SerializedName("$location")
    private String location;

    @SerializedName("$oid")
    private Id _id;

    private List<Coordinate> coordinates = new ArrayList<Coordinate>();

    public Dustbin(String location, List<Coordinate> coordinates) {
        this.location = location;
        this.coordinates = coordinates;
        try{

        }catch(Exception ex){
            System.out.println("exception caught");
        }
    }

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
